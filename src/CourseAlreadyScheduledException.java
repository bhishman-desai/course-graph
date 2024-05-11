/* This exception is triggered when a course is already scheduled and user tries to schedule it again */
public class CourseAlreadyScheduledException extends Exception {
  public CourseAlreadyScheduledException() {
    super("Course Already Scheduled!");
  }
}
