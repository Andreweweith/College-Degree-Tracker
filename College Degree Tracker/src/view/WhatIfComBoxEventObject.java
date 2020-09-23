package view;

import java.util.EventObject;

public class WhatIfComBoxEventObject extends EventObject{

	private String studentWhatIfMajor;
	
	public WhatIfComBoxEventObject(Object source){
		super(source);
	}
	
	public WhatIfComBoxEventObject(Object source, String studentWhatIfMajor){
		super(source);
		this.setStudentWhatIfMajor(studentWhatIfMajor);
	}

	public String getStudentWhatIfMajor() {
		return studentWhatIfMajor;
	}

	public void setStudentWhatIfMajor(String studentWhatIfMajor) {
		this.studentWhatIfMajor = studentWhatIfMajor;
	}
}
