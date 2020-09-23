package model;

public class Account {
	
	protected String username;
	protected String password;
	
	protected String id;
	protected String accountType;
	protected String fName;
	protected String lName;
	protected String address;
	protected String phone;
	protected String deptId;
		
	public Account(String username, String password) {	
		super();
		this.username = username;
		this.password = password;
	}
	
	public Account(String id, String accountType,
			String fName, String lName,
			String address, String phone, String deptId) {
		
		this.id = id;
		this.accountType = accountType;
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.phone = phone;
		this.deptId = deptId;
		
	}

	public Account(){}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
