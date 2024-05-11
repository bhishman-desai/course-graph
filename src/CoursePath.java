import java.util.*;

/* This class is used to find the course path to the given End Course IDs */
public class CoursePath {
  private Graph<Course> prerequisiteGraph;
  private CourseHelper courseHelper;

  public CoursePath(Graph<Course> prerequisiteGraph) {
    this.prerequisiteGraph = prerequisiteGraph;
    this.courseHelper = new CourseHelper(prerequisiteGraph);
  }

  public List<String> coursePath(Set<String> endCourseIDs) {
    /* Keeps track of the path which must be followed to get to the target courses */
    List<String> coursePath = new ArrayList<>();
    /* A set to keep track of the courses which are already traversed so that we can save some iterations */
    Set<String> visited = new HashSet<>();

    /* Iterate through the endCourseIDs and traverse through the prerequisites */
    for (String currentEndCourseID : endCourseIDs) {
      if (!visited.contains(currentEndCourseID)) {
        traverseThroughPrerequisites(currentEndCourseID, visited, coursePath);
      }
    }

    return coursePath;
  }

  /**
   * Checks through each prerequisite course recursively and adds the courses with no pre-requisite
   * or those which have been already visited
   *
   * @param currentEndCourseID The course ID of the current course
   * @param visited The list of courses which we've already visited
   * @param coursePath The final path which is to be followed
   */
  private void traverseThroughPrerequisites(
      String currentEndCourseID, Set<String> visited, List<String> coursePath) {
    /* Mark the course as visited */
    visited.add(currentEndCourseID);

    for (String prerequisite : courseHelper.getPrerequisiteCourseIDs(currentEndCourseID)) {
      if (!visited.contains(prerequisite)) {
        /* Checking recursively until first year course (course with no prerequisite)
        or course which is already visited and adding those to the course path */
        traverseThroughPrerequisites(prerequisite, visited, coursePath);
      }
    }

    /* Adding the course to the path */
    coursePath.add(courseHelper.getCourse(currentEndCourseID).getCourseName());
  }
}
