import java.util.*;

public class Curriculum {
  private Graph<Course> prerequisiteGraph;
  private CourseHelper courseHelper;

  /** Constructor of Curriculum class */
  public Curriculum() {
    prerequisiteGraph = new Graph<>();
    courseHelper = new CourseHelper(prerequisiteGraph);
  }

  /**
   * Add the course to the courseMap
   *
   * @param course - The course to be added to the courseMap
   */
  public void addCourse(Course course) {
    prerequisiteGraph.addCourse(course);
  }

  /**
   * Sets the prerequisites and the demand for the course
   *
   * @param courseID The ID of the course
   * @param prerequisiteCourseID The ID of the Prerequisite Course
   * @param demand demand % of students interested in this course who took the Prerequisite Course
   * @return true if prerequisites were set successfully
   * @throws CourseNotFoundException When Course is not found in the system
   * @throws PrerequisiteCycleException When it detects a Cycle in Prerequisite Courses
   * @throws PrerequisiteNotScheduledYetException When prerequisite is not scheduled
   */
  public boolean coursePrerequisite(String courseID, String prerequisiteCourseID, int demand)
      throws PrerequisiteCycleException,
          PrerequisiteNotScheduledYetException,
          CourseNotFoundException {

    boolean inputValidations =
        courseID == null
            || courseID.isEmpty()
            || prerequisiteCourseID == null
            || prerequisiteCourseID.isEmpty()
            || (demand > 100 || demand < 0);

    if (inputValidations) {
      return false;
    }

    /* Check if both courses exist in the curriculum */
    if (courseHelper.getCourse(courseID) != null
        && courseHelper.getCourse(prerequisiteCourseID) != null) {
      /* Get courses by ID */
      Course course = courseHelper.getCourse(courseID);
      Course prerequisiteCourse = courseHelper.getCourse(prerequisiteCourseID);

      /* Check for cycles before adding the prerequisite */
      if (!hasPrerequisiteCycle()) {
        /* Check if prerequisite is scheduled */
        if (prerequisiteCourse.getCapacity() >= 0) {
          /* Add the prerequisite to the course.
          Add a directed edge to represent the prerequisite relationship in the graph */
          prerequisiteGraph.addPrerequisite(course, prerequisiteCourse);

          /* Set the demand % for the course */
          /* The rationale behind this approach is to ensure that the course's demand percentage is not unrealistically high,
          which could lead to overestimating the demand for the course and potentially causing issues*/
          if (course.getDemandPercentage() == 0 || course.getDemandPercentage() > demand) {
            course.setDemandPercentage(demand);
            /* Set the demand value from the current demand and capacity */
            int demandValue = (demand * prerequisiteCourse.getCapacity()) / 100;
            course.setDemandValue(demandValue);
          }
          return true;
        } else {
          throw new PrerequisiteNotScheduledYetException();
        }
      } else {
        throw new PrerequisiteCycleException();
      }
    } else {
      throw new CourseNotFoundException();
    }
  }

  /**
   * Schedules a course in the curriculum and sets the capacity
   *
   * @param courseID The ID of the course
   * @param capacity Capacity of course
   * @return true if course was scheduled successfully
   * @throws CourseNotFoundException When Course is not found in the system
   * @throws CourseAlreadyScheduledException When Course is already scheduled
   */
  public boolean scheduleCourse(String courseID, int capacity)
      throws CourseNotFoundException, CourseAlreadyScheduledException {
    boolean inputValidations = courseID == null || courseID.isEmpty() || capacity <= 0;

    if (inputValidations) {
      return false;
    }

    if (courseHelper.getCourse(courseID) != null) {
      Course course = courseHelper.getCourse(courseID);

      if (course.getCapacity() < 0) {
        course.setCapacity(capacity);
      } else {
        throw new CourseAlreadyScheduledException();
      }
      return true;
    } else {
      throw new CourseNotFoundException();
    }
  }

  /**
   * Tells if a curriculum has a cycle in the courses
   *
   * @return true if a cycle is detected
   */
  public boolean hasPrerequisiteCycle() throws CourseNotFoundException {
    if (prerequisiteGraph.getAllCourses().isEmpty()) {
      throw new CourseNotFoundException();
    }
    return prerequisiteGraph.hasCycle();
  }

  /**
   * Checks if a course in the curriculum is a bottleneck or not
   *
   * @return returns the course which is a bottleneck
   */
  public Set<String> bottleneckCourses() throws PrerequisiteNotScheduledYetException {
    Set<String> bottleneckCourses = new HashSet<>();

    for (Course course : prerequisiteGraph.getAllCourses()) {
      /* Checking weather the demand value (number of students interested) is greater than the course capacity
       *  If so, the course is a bottleneck
       * */
      if (course.getCapacity() < 0) {
        throw new PrerequisiteNotScheduledYetException();
      }
      if (course.getDemandValue() > course.getCapacity()) {
        bottleneckCourses.add(course.getCourseID());
      }
    }

    return bottleneckCourses;
  }

  /**
   * Gives the path to follow in order to reach the end courses or target courses
   *
   * @param endCourseIDs The target course IDs
   * @return List of Path to be followed to reach the target
   * @throws PrerequisiteCycleException When it detects a Cycle in Prerequisite Courses
   * @throws CourseNotFoundException When Course is not found in the system
   */
  public List<String> coursePath(Set<String> endCourseIDs)
      throws PrerequisiteCycleException,
          CourseNotFoundException,
          BottleneckCourseException,
          PrerequisiteNotScheduledYetException {
    if (endCourseIDs == null || endCourseIDs.isEmpty()) {
      return null;
    }
    if (hasPrerequisiteCycle()) {
      throw new PrerequisiteCycleException();
    }
    if (!bottleneckCourses().isEmpty()) {
      throw new BottleneckCourseException();
    }
    /* Check if the courses in endCourseIDs are valid and are in the graph */
    for (String currentEndCourseID : endCourseIDs) {
      if (courseHelper.getCourse(currentEndCourseID) == null) {
        throw new CourseNotFoundException();
      }
    }
    CoursePath coursePathObject = new CoursePath(prerequisiteGraph);

    return coursePathObject.coursePath(endCourseIDs);
  }

  /**
   * Returns the courses which are equivalent and/or interchangeable in the curriculum
   *
   * @param interchangeable boolean to get either equivalent or equivalent and interchangeable
   *     courses
   * @return If interchangeable is true then return equivalent and interchangeable courses else only
   *     equivalent courses
   */
  public Set<Set<String>> equivalentCourses(boolean interchangeable)
      throws PrerequisiteCycleException,
          BottleneckCourseException,
          CourseNotFoundException,
          PrerequisiteNotScheduledYetException {
    if (hasPrerequisiteCycle()) {
      throw new PrerequisiteCycleException();
    }
    if (!bottleneckCourses().isEmpty()) {
      throw new BottleneckCourseException();
    }
    EquivalentCourses equivalentCoursesObject = new EquivalentCourses(prerequisiteGraph);

    return equivalentCoursesObject.equivalentCourses(interchangeable);
  }

  /**
   * The longest chain from the first year courses (courses with no pre-requisites) to last year
   * courses (courses which has no follow-ups)
   *
   * @return Set of list of the longest path
   * @throws PrerequisiteCycleException When it detects a Cycle in Prerequisite Courses
   */
  public Set<List<String>> longestPrerequisiteChain()
      throws PrerequisiteCycleException,
          CourseNotFoundException,
          BottleneckCourseException,
          PrerequisiteNotScheduledYetException {
    if (hasPrerequisiteCycle()) {
      throw new PrerequisiteCycleException();
    }
    if (!bottleneckCourses().isEmpty()) {
      throw new BottleneckCourseException();
    }
    LongestChain longestChain = new LongestChain(prerequisiteGraph);
    return longestChain.longestPrerequisiteChain();
  }

  /* Helper */
  @Override
  public String toString() {
    return prerequisiteGraph + "\n" + prerequisiteGraph.getAllCourses();
  }
}
