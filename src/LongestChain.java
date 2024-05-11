import java.util.*;
/* This class is used to identify the Longest chain/chains of prerequisites in the curriculum */
public class LongestChain {
  private Graph<Course> prerequisiteGraph;
  private CourseHelper courseHelper;

  public LongestChain(Graph<Course> prerequisiteGraph) {
    this.prerequisiteGraph = prerequisiteGraph;
    this.courseHelper = new CourseHelper(prerequisiteGraph);
  }

  public Set<List<String>> longestPrerequisiteChain() {
    Set<List<String>> resultLongestPaths = new HashSet<>();
    /* Used to check if the current computed path/chain is greater or smaller than a previous max chain */
    int maxChainLength = 0;

    /* A map to store the longest chain starting from each course */
    Map<String, List<String>> longestChainsMap = new HashMap<>();

    /* Iterate through all courses to find the longest chains */
    for (Course course : prerequisiteGraph.getAllCourses()) {
      /* Computes longest chain for the current course */
      List<String> longestChainForCurrentCourseList =
          findLongestChain(course.getCourseID(), longestChainsMap);

      /* If this condition matches, it means that the computed chain/path is longer/greater than the previous max chain/path */
      if (longestChainForCurrentCourseList.size() > maxChainLength) {
        maxChainLength =
            longestChainForCurrentCourseList
                .size(); /* Updating the maxChainLength with currentMaxChainLength */
        resultLongestPaths.clear(); /* Clearing the old result */
        resultLongestPaths.add(
            longestChainForCurrentCourseList); /* Adding the current computed longest path */
      }
      /* In case there are multiple longest path/chain with same length in the curriculum, return all of them */
      else if (longestChainForCurrentCourseList.size() == maxChainLength) {
        resultLongestPaths.add(longestChainForCurrentCourseList);
      }
    }

    return resultLongestPaths;
  }

  /**
   * Find the longest chain to a course
   *
   * @param currentCourseID The course ID of the current course
   * @param longestChainsMap The longest chain map which keeps track of the longest paths to every
   *     course
   * @return Longest chain to a course
   */
  private List<String> findLongestChain(
      String currentCourseID, Map<String, List<String>> longestChainsMap) {

    /* If the current course already exists in the map, return the path associated with that course */
    if (longestChainsMap.containsKey(currentCourseID)) {
      return longestChainsMap.get(currentCourseID);
    }

    /* Creating an empty list to compute the longest chain with respect to the prerequisite */
    List<String> longestPrerequisiteChain = new ArrayList<>();

    for (String prerequisite : courseHelper.getPrerequisiteCourseIDs(currentCourseID)) {
      /* Computing the longest path for the current prerequisite of the current course.
       *   Using recursive approach
       *  */
      List<String> currentPrerequisiteChain = findLongestChain(prerequisite, longestChainsMap);
      /* If the current chain if greater/longer than the existing longest chain, replace the existing chain with the current chain */
      if (currentPrerequisiteChain.size() > longestPrerequisiteChain.size()) {
        longestPrerequisiteChain = new ArrayList<>(currentPrerequisiteChain);
      }
    }

    /* After computing the longest chain after traversing all prerequisites, add current course to the chain as well */
    Course course = courseHelper.getCourse(currentCourseID);
    longestPrerequisiteChain.add(course.getCourseName());
    /* Update the map with key as current course ID and value as it's longest chain */
    longestChainsMap.put(currentCourseID, longestPrerequisiteChain);
    return longestPrerequisiteChain;
  }
}
