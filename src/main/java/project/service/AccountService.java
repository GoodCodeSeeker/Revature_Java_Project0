package project.service;



import java.util.List;
import java.util.Scanner;

//import org.apache.log4j.Logger;

import project.dao.AccountDao;
import project.dao.IAccountDao;
import project.models.Account;

public class AccountService {
	
	// Let's inject our DAO to the service
	private IAccountDao adao = new AccountDao();
	
	// Lets make a logger here
	//Logger logger = Logger.getLogger(AccountService.class);
	
	private static Scanner scan = new Scanner(System.in);
	
	public void viewAllAccounts() {
		
//		System.out.println("Fetching accounts...");
		
		//logger.info("Fetching Accounts...");
		
		// Lets call upon our DAO to get all of our accounts
		
		List<Account> accList = adao.findAll();
		
		for (Account a: accList) {
			System.out.println(a);
		}
		
	}
	
	public boolean validateAccountId(int id, int ownerId) {
		if (adao.isAccountValid(id, ownerId)) {
			System.out.println("The user account id input is valid.");
			return true;
		}else {
			System.out.println("The user account id input is invalid.");
			return false;
		}
	}
	
	
	public void viewAccountsById(int id) {
		List<Account> accList = adao.findByOwner(id);
		for (Account a: accList) {
			System.out.println(a);
		}
	}
	
	public void makeAccount(int id) {
		adao.insert(id);
	}
	
	public boolean showCusBanking(int id) {
		boolean canCusBanking = false;
		 if (adao.activeAccountsByOwner(id) != null) {
			 System.out.println("You have active bank account(s).");
				List<Account> accList = adao.activeAccountsByOwner(id);
				for (Account a: accList) {
					System.out.println(a);
				}
				canCusBanking = true;
		 }else {
			 System.out.println("No active account available. Cannot do banking.");
			 canCusBanking = false;
		 }
		 return canCusBanking;
	}
	
	public void depositService(int id, double money) {
		adao.updateAddBalance(id, money);
	}
	
	public void withdrawService(int id, double money) {
		if (isOpValid(id, money)) {
			adao.updateSubBalance(id, money);
		}else {
			System.out.println("Invalid amount.");
		}
	}
	
	public void transferService(int id1, int id2, double money) {
		if (isOpValid(id1, money)) {
			adao.updateTransferBalance(id1, id2, money);
		}else {
			System.out.println("Invalid amount.");
		}
	}
	
	public boolean isOpValid(int id, double money) {
		Account a = adao.findById(id);
		return (money < a.getBalance() && money > 0)? true: false;
	}

}