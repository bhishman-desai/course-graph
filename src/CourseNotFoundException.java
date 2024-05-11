/* This exception is triggered when a user will try to access the course which does not exist in the system */
public class CourseNotFoundException extends Exception {
  public CourseNotFoundException() {
    super("Course not found.");
  }
}
