package project.service;



import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import project.dao.AccountDao;
import project.dao.IAccountDao;
import project.models.Account;

public class AccountService {
	
	// Let's inject our DAO to the service
	public IAccountDao adao = new AccountDao();
	
	// Lets make a logger here
	Logger logger = Logger.getLogger(AccountService.class);
	
	private static Scanner scan = new Scanner(System.in);
	
	public void viewAllAccounts() {
		
		System.out.println("Fetching accounts...");
		
		logger.info("Fetching Accounts...");
		
		// Lets call upon our DAO to get all of our accounts
		
		List<Account> accList = adao.findAll();
		
		for (Account a: accList) {
			System.out.println(a);
		}
	}
	
	public boolean validateAccountId(int id, int ownerId) {
		if (adao.isAccountValid(id, ownerId)) {
			logger.info("The user number: "+ ownerId + "'s The user input of account id: " + id + " is valid.");
			System.out.println("The user account id input is valid.");
			return true;
		}else {
			logger.info("The user number: "+ ownerId + "'s The user input of account id: " + id + " is invalid.");
			System.out.println("The user account id input is invalid.");
			return false;
		}
	}
	
	
	public void viewAccountsById(int id) {
		logger.info("The accounts owned by user id: " + id);
		List<Account> accList = adao.findByOwner(id);
		for (Account a: accList) {
			System.out.println(a);
		}
	}
	
	public void makeAccount(int id) {
		logger.info("Making a account.");
		adao.insert(id);
	}
	
	public boolean showCusBanking(int id) {
		boolean canCusBanking = false;
		 if (adao.activeAccountsByOwner(id) != null) {
			 logger.info("The user id: " + id + " has active accounts and can do banking in the active accounts.");
			 System.out.println("You have active bank account(s).");
				List<Account> accList = adao.activeAccountsByOwner(id);
				for (Account a: accList) {
					System.out.println(a);
				}
				canCusBanking = true;
		 }else {
			 logger.info("The user id: " + id + " has no active accounts and cannot do customer banking");
			 System.out.println("No active account available. Cannot do banking.");
			 canCusBanking = false;
		 }
		 return canCusBanking;
	}
	
	public void depositService(int id, double money) {
		logger.info("The account id: " + id + " deposit $" + money);
		adao.updateAddBalance(id, money);
	}
	
	public void withdrawService(int id, double money) {
		if (isOpValid(id, money)) {
			logger.info("The account id: " + id + " withdraw $" + money);
			adao.updateSubBalance(id, money);
		}else {
			logger.info("The withdraw amount " + money + " is invalid.");
			System.out.println("Invalid amount.");
		}
	}
	
	public void transferService(int id1, int id2, double money) {
		if (isOpValid(id1, money)) {
			logger.info("The account " + id1 + " transfer to account " + id2 + " with $" + money);
			adao.updateTransferBalance(id1, id2, money);
		}else {
			logger.info("The transfer amount " + money + " is invalid.");
			System.out.println("Invalid amount.");
		}
	}
	
	public void accountToggle(int id, boolean choice) {
		if (choice == true) {
			logger.info("The account id: " + id + " update to active");
			adao.updateToActive(id);
		}else {
			logger.info("The account id: " + id + " update to inactive");
			adao.updateToInactive(id);
		}
	}
	
	public void deleteAccount(int id) {
		logger.info("The account id: " + id + " deleted.");
		adao.delete(id);
	}

	public boolean isOpValid(int id, double money) {
		Account a = adao.findById(id);
		return (money < a.getBalance() && money > 0)? true: false;
	}

}