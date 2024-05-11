import java.util.*;

public class Graph<T> {
  private Map<T, Set<T>> adjacencyList;

  public Graph() {
    adjacencyList = new HashMap<>();
  }

  /* Add a vertex (course) to the graph */

  public Set<T> getAllCourses() {
    return adjacencyList.keySet();
  }

  public void addCourse(T course) {
    adjacencyList.put(course, new HashSet<>());
  }

  /* Add a directed edge (prerequisite relationship) from source to destination */
  public void addPrerequisite(T source, T destination) {
    adjacencyList.get(source).add(destination);
  }

  /* Get all adjacent vertices (prerequisites) of a vertex (course) */
  public Set<T> getPrerequisites(T course) {
    return adjacencyList.get(course);
  }

  /* Check if a vertex (course) exists in the graph */
  public boolean hasCourse(T course) {
    return adjacencyList.containsKey(course);
  }

  /* Check if there's a directed edge from source to destination */
  public boolean hasPrerequisite(T source, T destination) {
    Set<T> prerequisites = getPrerequisites(source);
    return prerequisites != null && prerequisites.contains(destination);
  }

  /* Check if there's a cycle in the graph */
  public boolean hasCycle() {
    /* Create a set to keep track of visited vertices */
    Set<T> visited = new HashSet<>();
    /* Create a set to keep track of vertices in the current recursion stack */
    Set<T> recursionStack = new HashSet<>();

    /* Iterate through each vertex (course) in the graph */
    for (T course : adjacencyList.keySet()) {
      /* If the DFS traversal from this vertex results in a cycle, return true */
      if (checkingCycleDFS(course, visited, recursionStack)) {
        return true;
      }
    }

    /* No cycle was found */
    return false;
  }

  /* Helper */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<T, Set<T>> entry : adjacencyList.entrySet()) {
      Course key = (Course) entry.getKey();
      Set<Course> values = (Set<Course>) entry.getValue();
      sb.append(key.getCourseName()).append(" -> ");
      for (Course value : values) {
        sb.append(value.getCourseName()).append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Checking if there is cycle in the graph recursively
   *
   * @param course Current course
   * @param visited Set of visited courses
   * @param tempStack Maintaining a set of recursive stack to detect a cycle
   * @return true if a cycle is detected
   */
  private boolean checkingCycleDFS(T course, Set<T> visited, Set<T> tempStack) {
    /* If the course is in the recursion stack, a cycle is detected */
    if (tempStack.contains(course)) {
      return true;
    }

    /* If the course is already visited and not in the recursion stack, it's not part of a cycle */
    if (visited.contains(course)) {
      return false;
    }

    /* Mark the course as visited and add it to the recursion stack */
    visited.add(course);
    tempStack.add(course);

    /* Recursively check prerequisites */
    for (T prerequisite : getPrerequisites(course)) {
      if (checkingCycleDFS(prerequisite, visited, tempStack)) {
        return true;
      }
    }

    /* Remove the course from the recursion stack */
    tempStack.remove(course);

    return false;
  }
}
