package Pj.models;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private String accName;
	private double bal = 0;

	public double getBal() {
		return this.bal;
	}

	public void deposit(double fund) {
		this.bal += fund;
	}
	
	public void withdraw(double fund) {
		if (fund >= this.bal) {
			System.out.println("Invalid input: withdraw amount cannot be more than account total: " + this.bal);
		}else {
		this.bal -= fund;
		}
	}
	
	public void transfer(List<Account> cusAccount, String tar, double fund) {
		if (fund >= this.bal) {
			System.out.println("Invalid input: withdraw amount cannot be more than account total: " + this.bal);	
		}else {
		withdraw(fund);
		
		for (Account acc: cusAccount) {
			if (acc.getAccName().equals(tar)) {
				acc.deposit(fund);
			} else {
				System.out.println("Wrong target account name");
			}
		}
		}
		}

	public String getAccName() {
		return accName;
	}

	
	Account(String n){
		accName = n;
	}
	
	public String toString() {
		String text = "The Account Name: " + this.getAccName() + "\nThe Account Balance: " + this.getBal();
		return text;
	}
	
	
}
