/* This exception is triggered when a user tries to add a course as a prerequisite but that course has not been scheduled yet */
public class PrerequisiteNotScheduledYetException extends Exception {
  public PrerequisiteNotScheduledYetException() {
    super("Prerequisite Course not scheduled yet!");
  }
}
