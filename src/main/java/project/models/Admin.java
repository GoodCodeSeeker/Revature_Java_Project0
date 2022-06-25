package Pj.models;

import java.util.ArrayList;
import java.util.HashSet;

public class Admin extends Profile{
	private String firstName;
	private String lastName;
	private String uName;
	private String passwd;
	private String email;
	private String phoneNum;
	
	public Admin(String uName, String passwd) {
		this.uName = uName;
		this.passwd = passwd;
	}
	
	public Admin(String firstName,String lastName,String uName,String passwd,String email,String phoneNum){
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
	//Method of admin: approving account from the decision variable
	
	public boolean accApv(boolean decis) {
		boolean output = decis ? true: false;
		return output;
	}
	
	
	
	//Method of admin: output text for customer who can get an account and who cannot get an account.
	public void textOutput(Customer x, boolean decis) {
		String str = decis ? "Admin approved the account creation of Customer " + x.getuName() : 
			"Admin disapproved the account creation of Customer " + x.getuName();
		System.out.println(str);
	}
	
	//Method of admin: traversal of array list of all customer account and view name and balance.
	public void viewAllAccs(ArrayList<Account> all) {
		System.out.println("Text Output of all Accounts:");
		for (Account i: all) {
			System.out.println(i.toString());
		}
	}
	
	//Method of admin: deposit money into any customer account
	public void adminDeposit(ArrayList<Account> all, String accName, double bal) {
		for (Account acc: all) {
			if (acc.getAccName().equals(accName)) {
				acc.deposit(bal);
			}else {
				System.out.println("Wrong account name");
			}
		}
	}
	
	//Method of admin: withdraw money from any customer account
	public void adminWithdraw(ArrayList<Account> all, String accName, double bal) {
		for (Account acc: all) {
			if (acc.getAccName().equals(accName)) {
				acc.withdraw(bal);
			}else {
				System.out.println("Wrong account name");
			}
		}
	}
	
	//Method of admin: transfer money from any source account to any target account
	public void adminTransfer(ArrayList<Account> all, String srcAccName, String tarAccName, double bal) {
		for (Account acc: all) {
			if (acc.getAccName().equals(srcAccName)) {
				acc.withdraw(bal);
			}else {
				System.out.println("Wrong source account name");
				break;
			}
		}
		
		for (Account acc: all) {
			if (acc.getAccName().equals(tarAccName)) {
				acc.deposit(bal);
			} else {
				System.out.println("Wrong target account name");
				break;
			}
		}
	}


	//Method of admin: cancel an account from two lists: array list of all account and array list of customer account
	public void accCancel(ArrayList<Account> all, ArrayList<Account> cusAccounts, String accName) {
		
		for (Account acc: all) {
			if (acc.getAccName().equals(accName)) {
				acc = null;
				System.gc();
			}
		}

		for (Account acc: cusAccounts) {
			if (acc.getAccName().equals(accName)) {
				acc = null;
				System.gc();
			}
		}
	}
}
