package project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import project.dao.UserDao;
import project.models.Role;
	import project.models.User;
	import project.service.AccountService;
	import project.service.UserService;
import project.util.ConnectionUtil;

	public class App {
		
		static Scanner scan = new Scanner(System.in);
		public static boolean canApply = false;
		public static void main(String[] args) {
			
			
			
			//Let's just fetch our accounts to test
			
			AccountService as = new AccountService();
			as.viewAllAccounts();
			
			run();
			//ConnectionUtil.getConnection();
			
			//ConnectionUtil.getConnection();
			
			
		}
		
		public static void run() {
			
			UserService us = new UserService();

			
			System.out.println("Welcome to our Bank!");
			
			System.out.println("Press 1 if you're an existing member trying to log in. \nPress 2 if you'd like to register.");
			
			int input = scan.nextInt();
			
			if (input == 1) {
				
				System.out.println("Please enter your username");
				
				String username = scan.next();
				
				System.out.println("Please enter your password");
				
				String password = scan.next();
				
				User loggedInUser = us.login(username, password);
				
				System.out.println("Welcome to your account: " + loggedInUser.getUsername());
				
				// Maybe some data validation
				
				// Maybe creating a basic menu for your accounts
				
				// Giving option for accounts etc.
				if (loggedInUser.getRole() == Role.Admin) {
					adminMenu(loggedInUser);
				}else if (loggedInUser.getRole() == Role.Employee) {
					emplMenu(loggedInUser);
				} else cusMenu(loggedInUser);
				
			} else if (input == 2) {
				
				System.out.println("Please enter a username");
				String username = scan.next();
				
				System.out.println("Please enter a secure password");
				String password = scan.next();
				
				User u = new User();

				
				int roleNum=0;

				do {
				System.out.println("Enter your role: 1 for customer. 2 for employee. 3 for admin.");
				roleNum = scan.nextInt();
				
				if (roleNum == 1 || roleNum == 2 || roleNum == 3){
				if (roleNum == 1) {
				System.out.println("Your role is customer.");
				u = new User(username, password, Role.Customer, null);
				us.register(u);
				cusMenu(u);
				} else if (roleNum == 2){
					System.out.println("Your role is employee.");
				u = new User(username, password, Role.Employee, null);
				us.register(u);
				emplMenu(u);
				} else if (roleNum == 3){
					System.out.println("Your role is admin.");
				u = new User(username, password, Role.Admin, null);
				us.register(u);
				adminMenu(u);
				
				
				}
				}else
					System.out.println("You enter the wrong input.");
				} while (!isValid(roleNum));
				
			}
			}
		
		
		public static boolean isValid(int r) {
			return (r == 1 || r == 2 || r == 3)? true: false;
		}
	

	public static void cusMenu(User u) {
		AccountService as = new AccountService();
		System.out.println("You can do customer banking: deposit, withdraw, and transfer your accounts");
		int choice1 = 0;
		System.out.println("Press 1 to view your accounts."
				+ "\nPress 2 to make an account."
				+ "\nPress 3 to do customer banking"
				+ "\nPress 4 to log out.");
		Scanner scan = new Scanner(System.in);
		choice1 = scan.nextInt();
		switch (choice1) {
			case 1: System.out.println("You can view accounts that you own.");
			int input = u.getId();
			as.viewAccountsById(input);
			break;
			case 2: System.out.println("You can make an account");
			as.makeAccount(u.getId());
			break;
			case 3: System.out.println("If you have an active account, you can do customer banking");
			as.showCusBanking(u.getId());
			if (as.showCusBanking(u.getId()) == true) {
				int accNum = -1;
				int choice2 = 0;
				do {
				System.out.println("Choose the account id to do banking");
				accNum = scan.nextInt();
				if (as.validateAccountId(accNum, u.getId()) == true) {
				as.viewAccountsById(accNum);
				System.out.println("Press 1 to Deposit."
						+ "Press 2 to withdraw."
						+ "Press 3 to transfer.");
				choice2 = scan.nextInt();
				double amount = 0;
				int tar = 0;
				switch (choice2) {
				case 1:
				System.out.println("Enter the amount:");
				amount = scan.nextDouble();
				System.out.println("Depositing $" + amount);
				as.depositService(accNum, amount);
				break;
				case 2:
				System.out.println("Enter the amount:");
				amount = scan.nextDouble();
				System.out.println("Withdrawing $" + amount);
				as.withdrawService(accNum, amount);
				break;
				case 3:
				do {
				if (as.validateAccountId(tar, u.getId()) == true) {	
				System.out.println("Enter target account id:");
				tar = scan.nextInt();
				System.out.println("Enter the amount:");
				amount = scan.nextDouble();
				System.out.println("Transfer $" + amount + " to account "+ tar);
				as.transferService(accNum, tar, amount);
				}else {
				System.out.println("Entered the wrong account id.");
				}
				} while (as.validateAccountId(tar, u.getId()) == false);
				break;
				default:
				break;
				}
				} else {
					System.out.println("Entered the wrong account id.");
				}
				} while (as.validateAccountId(accNum, u.getId()) == false);
			}
			
			break;
		}
		scan.close();
	}
	
	
	public static void emplMenu(User u) {
		System.out.println("You can view customers info and their account balance.");
		int choice1 = 0;
		System.out.println("Press 1 to view all customer accounts."
				+ "Press 2 to view all customer info."
				+ "Press 3 to approve or deny open application for accounts."
				+ "Press 4 to log out.");
		choice1 = scan.nextInt();
		switch (choice1) {
		case 1: System.out.println("You can view all customer accounts.");
		break;
		case 2: System.out.println("You can view all customer info.");
		break;
		case 3: System.out.println("You can approve or deny open application for accounts.");
		break;
		case 4: System.out.println("You can log out.");
		break;
	}
	}
	
	public static void adminMenu(User u) {
		AccountService as = new AccountService();
		UserService us = new UserService();
		System.out.println("You can view customer info and account balance. "
				+ "\nYou can also do banking on all customers, and cancel accounts.");
		int choice1 = 0;
		int choice2 = 0;
		int accNum = -1;
		int tar = -1;
		double amount = 0;
		System.out.println("Press 1 to view all accounts."
				+ "Press 2 to view all customers."
				+ "Press 3 to do banking on customers."
				+ "Press 4 to approve or deny account."
				+ "Press 5 to cancel account."
				+ "Press 6 to log out.");
		choice1 = scan.nextInt();
		switch (choice1) {
		case 1: System.out.println("You can view all customer accounts.");
		as.viewAllAccounts();
		break;
		case 2: System.out.println("You can view all customers.");
		us.viewAllCustomers();
		break;
		case 3: System.out.println("You can do banking on customers.");
		System.out.println("Enter the avaliable bank account id to do banking.");
		accNum = scan.nextInt();
		System.out.println("Enter the banking choice: 1: deposit, 2: withdraw, 3: transfer");
		choice2 = scan.nextInt();
		if (choice2 == 3) {
			System.out.println("Enter the target account.");
			tar = scan.nextInt();
		}
		System.out.println("Enter the amount");
		amount = scan.nextDouble();
		
		switch(choice2) {
		case 1: as.depositService(accNum, amount); break;
		case 2: as.withdrawService(accNum, amount); break;
		case 3: as.transferService(accNum, tar, amount);
		}
		break;
		case 4: System.out.println("You can approve or deny account.");
		break;
		case 5: System.out.println("You can cancel account.");
		break;
		case 6: System.out.println("You can log out.");
		break;
	}
		
	}
	}
