import java.util.Set;

public class Main {

  public static void main(String[] args) {
    /* Create the curriculum */
    Curriculum curriculum = new Curriculum();

    /* Adding courses to curriculum */
    curriculum.addCourse(new Course("A1", "A"));
    curriculum.addCourse(new Course("B1", "B"));
    curriculum.addCourse(new Course("C1", "C"));
    curriculum.addCourse(new Course("D1", "D"));
    curriculum.addCourse(new Course("E1", "E"));
    curriculum.addCourse(new Course("F1", "F"));
    curriculum.addCourse(new Course("G1", "G"));
    curriculum.addCourse(new Course("X1", "X"));
    curriculum.addCourse(new Course("Y1", "Y"));

    try {
      curriculum.scheduleCourse("A1", 120);
      curriculum.scheduleCourse("B1", 120);
      curriculum.scheduleCourse("C1", 120);
      curriculum.scheduleCourse("D1", 120);
      curriculum.scheduleCourse("E1", 120);
      curriculum.scheduleCourse("F1", 120);
      curriculum.scheduleCourse("G1", 120);
      curriculum.scheduleCourse("X1", 120);
      curriculum.scheduleCourse("Y1", 120);

      curriculum.coursePrerequisite("B1", "A1", 70);
      curriculum.coursePrerequisite("C1", "B1", 70);
      curriculum.coursePrerequisite("C1", "E1", 70);
      curriculum.coursePrerequisite("D1", "A1", 70);
      curriculum.coursePrerequisite("D1", "B1", 70);
      curriculum.coursePrerequisite("F1", "A1", 70);
      curriculum.coursePrerequisite("F1", "B1", 70);
      curriculum.coursePrerequisite("F1", "E1", 70);
      curriculum.coursePrerequisite("G1", "A1", 70);
      curriculum.coursePrerequisite("G1", "B1", 70);
      curriculum.coursePrerequisite("G1", "C1", 70);
      curriculum.coursePrerequisite("X1", "F1", 70);
      curriculum.coursePrerequisite("Y1", "B1", 70);
      curriculum.coursePrerequisite("Y1", "E1", 70);
      curriculum.coursePrerequisite("Y1", "X1", 70);

      System.out.println(curriculum.hasPrerequisiteCycle());
      System.out.println(curriculum.bottleneckCourses());
      System.out.println(curriculum.coursePath(Set.of("Y1")));
      System.out.println(curriculum.equivalentCourses(true));
      System.out.println(curriculum.equivalentCourses(false));
      System.out.println(curriculum.longestPrerequisiteChain());

    } catch (CourseNotFoundException
        | PrerequisiteCycleException
        | PrerequisiteNotScheduledYetException
        | BottleneckCourseException
        | CourseAlreadyScheduledException exceptionMessage) {
      System.out.println(exceptionMessage);
    }
    System.out.println("--------------------------------------------------------");
    System.out.println(curriculum);
  }
}
