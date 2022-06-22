package Pj.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Profile{
	
	public Customer() {
	}

	public Customer(String uName, String passwd) {
		this.uName = uName;
		this.passwd = passwd;
	}
	
	public Customer(String firstName,String lastName,String uName,String passwd,String email,String phoneNum){
		this.firstName = firstName;
		this.lastName = lastName;
		this.uName = uName;
		this.passwd = passwd;
		this.email = email;
		this.phoneNum = phoneNum;
	}
	
	//Customer basic info: the name, id, user name, password, email, phone number
	private String firstName;
	private String lastName;
	private String uName;
	private String passwd;
	private String email;
	private String phoneNum;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


	

	//Customer counter for making account name
	private int counter = 0;
	
	//Customer hash set for user name: every user name must be unique
	
	//Customer account array list: storing all accounts of each customer
	private List<Account> cusAccounts= new ArrayList <Account>();
	
	//All Customer array list: storing all accounts of all customers
	private static List<Account> allCusAccounts = new ArrayList <Account>();

	
	//Customer scan class
	private Scanner scan = new Scanner(System.in);
	
	
	//Setter and getter for first name
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	//Setter and getter for last name
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	//Setter and getter for user name
	public String getuName() {
		return uName;
	}
	
	public void setuName(String uName) {
		this.uName = uName;
	}
	//Setter and getter for password
	public String getPasswd() {
		return passwd;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	//Getter for individual account list of each customer
	public List<Account> getCusAccounts() {
		return cusAccounts;
	}
	
	public void AddToLists(List<Account> a, List<Account> b, Account x) {
		a.add(x);
		b.add(x);
	}
	//Method for registration
	public void register() {
		System.out.println("Enter first name:");
		firstName = scan.next();
		this.setFirstName(firstName);
		System.out.println("Enter last name:");
		lastName = scan.next();
		this.setLastName(lastName);
		System.out.println("Enter email address:");
		email = scan.next();
		this.setEmail(email);
		System.out.println("Enter phone number:");
		phoneNum = scan.next();
		this.setPhoneNum(phoneNum);
		System.out.println("Enter username:");
		uName = scan.next();
		this.setuName(uName);
		System.out.println("Enter password:");
		passwd = scan.next();
		this.setPasswd(passwd);
	}
	
	
	//Method for view information of each user
	public void viewInfo() {
		System.out.println("Customer Info:\n"
				+ "First Name: " + this.getFirstName()
				+ "\nLast Name: " + this.getLastName()
				+ "\nemail: " + this.getEmail()
				+ "\nPhone Number: " + this.getPhoneNum()
				+ "\nUser Name: " + this.getuName()
				+ "\nPassword: " + this.getPasswd());
	}
	
	//Method for apply account as an user
	public void applAccount(boolean isOpen, boolean adminApv) {

		
		if (isOpen == true && adminApv == true) {
			System.out.println("Account Application is sucessful");
			String accName = this.getuName() + "_" + counter;
			counter++;
			Account ac = new Account(accName);
			AddToLists(cusAccounts, allCusAccounts, ac);
			System.out.println("Your new bank account name is: " + ac.getAccName());

		}else {
			System.out.println("Account Application is unsuccessful");
		}
	}
	
	//View All accounts created by the one user
	public void viewCusAccounts(List<Account> cusAccounts) {
		for (Account i: cusAccounts) {
			System.out.println(i.toString());
		}
	};
	
	public void cusBkg() {
		boolean exitFlag = false;
		boolean wrongAccFlag = false;
		do {
		System.out.println("The accounts that you have: ");
		viewCusAccounts(this.cusAccounts);
		
		if (this.cusAccounts.isEmpty()) {
			System.out.println("You don't have an account to do customer banking.");
			exitFlag = true;
		}else
		{
		System.out.println("Enter the account name to do customer banking");
		String accName = scan.next();
		
		for (Account i: this.getCusAccounts()) {
		if (accName.equals(i.getAccName())) {
		System.out.println("You selected the account " + i.getAccName());
		
		int res2 = -1;
		double res3 = 0.0;
		String targetAccount;
		
		while (res2 != 3) {
		System.out.println("Three option: Press 0 to deposit. Press 1 to withdraw. Press 2 to transfer. Press 3 to exit");
		res2 = scan.nextInt();
		
		switch (res2) {
		case 0: System.out.println("Enter the amount to deposit:");
		res3 = scan.nextDouble();
		i.deposit(res3);
		break;
		case 1: System.out.println("Enter the amount to withdraw:");
		res3 = scan.nextDouble();
		i.withdraw(res3);
		break;
		case 2: System.out.println("Enter the amount to transfer:");
		res3 = scan.nextDouble();
		System.out.println("Enter the target account name: ");
		targetAccount = scan.next();
		i.transfer(cusAccounts, targetAccount,res3);
		break;	
		}
		}	
		}
		wrongAccFlag = true;
		}
		if (wrongAccFlag == true) {
		System.out.println("Wrong account name.");
		}
		if (exitFlag!=true) {
		System.out.println("Do you want to quit? Press y or Y to quit.");
		
		String exitAsk = scan.next();
		if (exitAsk.equalsIgnoreCase("y")) exitFlag = true;
		}
		}
		}while(exitFlag == false);
			
			System.out.println("Exiting customer banking.");
		}

}

