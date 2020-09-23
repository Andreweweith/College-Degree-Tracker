package view;

import java.util.EventObject;

public class GenerateButtonEventObject extends EventObject{

	private String studentIdNumber;
	private String studentMajor;
	
	public GenerateButtonEventObject(Object source) {
		super(source);
	}
	
	public GenerateButtonEventObject(Object source, String studentIdNumber,
			String studentMajor) {
		super(source);
		this.setStudentIdNumber(studentIdNumber);
		this.setStudentMajor(studentMajor);
	}

	public String getStudentIdNumber() {
		return studentIdNumber;
	}

	public void setStudentIdNumber(String studentIdNumber) {
		this.studentIdNumber = studentIdNumber;
	}

	public String getStudentMajor() {
		return studentMajor;
	}

	public void setStudentMajor(String studentMajor) {
		this.studentMajor = studentMajor;
	}
}
