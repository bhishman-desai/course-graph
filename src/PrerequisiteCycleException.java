/* This exception is triggered when a cycle in the prerequisite is detected */
public class PrerequisiteCycleException extends Exception {
  public PrerequisiteCycleException() {
    super("Cycle in the graph.");
  }
}
