import java.util.*;

/* This class holds all the methods which are required throughout the system and prevents repetition of the methods in different classes */
public class CourseHelper {
  private Graph<Course> prerequisiteGraph;

  public CourseHelper(Graph<Course> prerequisiteGraph) {
    this.prerequisiteGraph = prerequisiteGraph;
  }

  /**
   * Get the course by courseID from the courseMap
   *
   * @param courseID - The ID of the course to be fetched
   * @return The Course object
   */
  public Course getCourse(String courseID) {
    Set<Course> coursesSet = prerequisiteGraph.getAllCourses();
    for (Course currentCourse : coursesSet) {
      if (currentCourse.getCourseID().equals(courseID)) {
        return currentCourse;
      }
    }
    return null;
  }

  /**
   * Getting the set of prerequisitesIDs
   *
   * @param courseID The course ID
   * @return Set of prerequisitesIDs
   */
  public Set<String> getPrerequisiteCourseIDs(String courseID) {
    Set<String> prerequisites = new HashSet<>();
    Course course = getCourse(courseID);

    for (Course prerequisite : prerequisiteGraph.getPrerequisites(course)) {
      prerequisites.add(prerequisite.getCourseID());
    }

    return prerequisites;
  }
}
