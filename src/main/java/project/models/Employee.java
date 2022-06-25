package Pj.models;


public class Employee extends Profile{
	private String firstName;
	private String lastName;
	private String uName;
	private String passwd;
	private String email;
	private String phoneNum;
	
	public Employee(String uName, String passwd) {
		this.uName = uName;
		this.passwd = passwd;
	}
	
	
	public Employee(String firstName,String lastName,String uName,String passwd,String email,String phoneNum){
		this.firstName = firstName;
		this.lastName = lastName;
		this.uName = uName;
		this.passwd = passwd;
		this.email = email;
		this.phoneNum = phoneNum;
	}
	
	@Override
	public String getFirstName() {
		
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		
	}

	@Override
	public String getLastName() {
		
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
		
	}

	@Override
	public String getuName() {
		
		return uName;
	}

	@Override
	public void setuName(String uName) {
		this.uName = uName;
		
	}

	@Override
	public String getPasswd() {
		
		return passwd;
	}

	@Override
	public void setPasswd(String passwd) {
		this.passwd = passwd;
		
	}

	@Override
	public String getEmail() {
		
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
		
	}

	@Override
	public String getPhoneNum() {
		
		return phoneNum;
	}

	@Override
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
		
	}
	
	AccApplStatus status = AccApplStatus.getInstance();
	public void setApplOpen(boolean canOpen) {
		status.setOpen(canOpen);
	}
	
	public boolean getApplStatus() {
		return status.isOpen();
	}
	
}
