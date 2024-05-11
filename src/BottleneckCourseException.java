/* This exception is triggered when a there is a bottleneck course detected in the curriculum */
public class BottleneckCourseException extends Exception {
  public BottleneckCourseException() {
    super("Bottleneck course in the curriculum!");
  }
}
