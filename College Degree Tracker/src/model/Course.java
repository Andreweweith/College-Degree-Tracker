package model;

public class Course{

	private String deptId;
	private String courseNumber;
	private String title;
	private String credits;
	
	private String grade;

	public Course(String deptId,
			String courseNumber,
			String title,
			String credits) {
		this.deptId = deptId;
		this.courseNumber = courseNumber;
		this.title = title;
		this.credits = credits;
	}
	
	public Course(String deptId, 
			String courseNumber,
			String title,
			String credits,
			String grade) {
		this.deptId = deptId;
		this.courseNumber = courseNumber;
		this.title = title;
		this.credits = credits;
		this.grade = grade;
	}
	
	public Course(){}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
