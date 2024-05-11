public class Course {
  /* Properties */
  private String courseID;
  private String courseName;
  private int capacity = -1; /* Would be -1 initially to indicate that course has not been scheduled yet */
  private int demandPercentage;
  private int demandValue;

  /* Constructor */
  public Course(String courseID, String courseName) {
    this.courseID = courseID;
    this.courseName = courseName;
  }

  /* Getters and Setters */
  public String getCourseID() {
    return courseID;
  }

  public String getCourseName() {
    return courseName;
  }

  public int getCapacity() {
    return capacity;
  }
  public int getDemandValue() {
    return demandValue;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getDemandPercentage() {
    return demandPercentage;
  }

  public void setDemandPercentage(int demandPercentage) {
    this.demandPercentage = demandPercentage;
  }
  public void setDemandValue(int demandValue) {
    this.demandValue = demandValue;
  }

  /* Helper functions */
  @Override
  public String toString() {
    return courseID + " " + courseName + " " + capacity + " " + demandValue + " " + demandPercentage;
  }
}
