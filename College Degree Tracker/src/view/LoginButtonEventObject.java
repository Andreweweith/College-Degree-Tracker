package view;

import java.util.EventObject;

public class LoginButtonEventObject extends EventObject{

	private String username;
	private String password;
	
	public LoginButtonEventObject(Object source) {
		super(source);
	}
	
	public LoginButtonEventObject(Object source, String username, String password){
		super(source);
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	
}
