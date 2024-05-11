import java.util.*;

/* This class is used to identify the equivalent and/or interchangeable courses in the curriculum */
public class EquivalentCourses {

  private Graph<Course> prerequisiteGraph;

  public EquivalentCourses(Graph<Course> prerequisiteGraph) {
    this.prerequisiteGraph = prerequisiteGraph;
  }

  public Set<Set<String>> equivalentCourses(boolean interchangeable) {
    if (interchangeable) {
      return findInterchangeable(getAllPrerequisites());
    }
    return findEquivalent(getAllPrerequisites());
  }

  private Set<Set<String>> findInterchangeable(Set<Set<String>> currentPrerequisites) {
    Set<Set<String>> result = new HashSet<>();
    /* Looping through All Prerequisites of all courses */
    for (Set<String> currentPrerequisite : currentPrerequisites) {
      /* Creating the sub-sets within the current prerequisite
      for checking if they exist in another course's prerequisite */
      for (Set<String> currentPrerequisiteSubSet : generateSubSets(currentPrerequisite)) {
        boolean interchangeable = false;
        for (Set<String> followingPrerequisite : currentPrerequisites) {
          /* If the subset exists in another course's prerequisite, those courses are interchangeable */
          if (!followingPrerequisite.equals(currentPrerequisite)
              && followingPrerequisite.containsAll(currentPrerequisiteSubSet)) {
            /* Splitting the remaining part apart from the current prerequisite sub-set */
            for (String subSetOfSubSet : currentPrerequisiteSubSet) {
              /* Looping through the set again to check if the sub-set of sub-set exists somewhere where the currentPrerequisite do not exist */
              if (currentPrerequisites.stream()
                  .anyMatch(
                      set ->
                          set.contains(subSetOfSubSet)
                              && !set.containsAll(currentPrerequisiteSubSet))) {
                continue;
              }
              interchangeable = true;
            }
          }
        }
        if (interchangeable) {
          result.add(currentPrerequisiteSubSet);
        }
      }
    }
    return result;
  }

  private Set<Set<String>> findEquivalent(Set<Set<String>> currentPrerequisites) {
    Set<Set<String>> equivalent = new HashSet<>();

    /* Looping through the interchangeable loop */
    for (Set<String> interchangeable : findInterchangeable(currentPrerequisites)) {
      /* Getting all the current prerequisite courses */
      for (Set<String> currentPrerequisite : currentPrerequisites) {
        /* If interchangeable is a sub-set of current prerequisite and also not the same as current prerequisite */
        if (currentPrerequisite.containsAll(interchangeable)
            && !currentPrerequisite.equals(interchangeable)) {
          /* Splitting the remaining part apart from the current prerequisite */
          Set<String> otherPart = new HashSet<>(Set.copyOf(currentPrerequisite));
          otherPart.removeAll(interchangeable);
          /* Looping through all the prerequisites again to check if there's any prerequisite that has the other splitter part but not the interchangeable
           *  If so, just skip that set as they are not equivalent
           * */
          if (currentPrerequisites.stream()
              .anyMatch(set -> set.containsAll(otherPart) && !set.containsAll(interchangeable))) {
            continue;
          }
          /* If not, add that set as they are equivalents */
          equivalent.add(currentPrerequisite);
        }
      }
    }

    return equivalent;
  }

  private Set<Set<String>> getAllPrerequisites() {
    Set<Set<String>> allPrerequisites = new HashSet<>();

    for (Course course : prerequisiteGraph.getAllCourses()) {
      if (!prerequisiteGraph.getPrerequisites(course).isEmpty()) {
        Set<String> courseNameSet = new HashSet<>();
        for (Course prerequisiteCourse : prerequisiteGraph.getPrerequisites(course)) {
          courseNameSet.add(prerequisiteCourse.getCourseName());
        }
        allPrerequisites.add(courseNameSet);
      }
    }

    return allPrerequisites;
  }

  private ArrayList<Set<String>> generateSubSets(Set<String> prerequisites) {
    ArrayList<Set<String>> prerequisiteSubSet = new ArrayList<>();
    generateSubSetsHelper(prerequisites, prerequisiteSubSet, new HashSet<>());
    prerequisiteSubSet.remove(prerequisites);
    return prerequisiteSubSet;
  }

  private void generateSubSetsHelper(
      Set<String> remaining, ArrayList<Set<String>> subsets, Set<String> currentSubset) {
    if (remaining.isEmpty()) {
      if (currentSubset.size() > 1) {
        subsets.add(new HashSet<>(currentSubset));
      }
      return;
    }

    String element = remaining.iterator().next();
    remaining.remove(element);

    currentSubset.add(element);
    generateSubSetsHelper(remaining, subsets, currentSubset);

    currentSubset.remove(element);
    generateSubSetsHelper(remaining, subsets, currentSubset);

    remaining.add(element);
  }
}
