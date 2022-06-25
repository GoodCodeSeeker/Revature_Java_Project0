package Pj;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import Pj.models.Admin;
import Pj.models.Customer;
import Pj.models.Employee;

public class App {
	private static Scanner scan = new Scanner(System.in);
	private static String res;
	public static void main(String[] args) {
		cusMenu();
	}
	
	public static void cusMenu() {
		do {
		System.out.println("It is bank service. Press y or Y to register. press q or any other key to quit.");
		res = scan.next();
		if (res.equalsIgnoreCase("y")) {
			Customer x = new Customer();
			x.register();
			String res2 = "";
			do {
			System.out.println("You are registered. Press y or Y to apply for an account. "
					+ "\nPress p or P to view profile. "
					+ "\nPress v or V to view all your (customer) accounts."
					+ "\nPress b or B to do customer banking."
					+ "\nPress q or Q key to quit.");
			res2 = scan.next();
			if (res2.equalsIgnoreCase("y")) {
				System.out.println("System processing.");
				
				Employee e1 = new Employee("Hench","Man", "henchman", "workhard", "henchman@mail.com", "12345");
				Admin a1 = new Admin("Big", "Boss", "quantumsnake", "topboss", "bigboss@mail.com", "55555");
				e1.setApplOpen(true);
				boolean apply_status = e1.getApplStatus();
				boolean admin_step = a1.accApv(true);
				a1.textOutput(x, a1.accApv(true));
				x.applAccount(apply_status, admin_step);
				System.out.println("Press Y or y to continue. Press q or Q to quit");
				res2 = scan.next();
			}else if (res2.equalsIgnoreCase("b")) {
				x.cusBkg();
				System.out.println("Press Y or y to continue. Press q or Q to quit");
				res2 = scan.next();
			}

			else if (res2.equalsIgnoreCase("p")){
				x.viewInfo();
				System.out.println("Press Y or y to continue. Press q or Q to quit");
				res2 = scan.next();
			}else if (res2.equalsIgnoreCase("v")) {
				x.viewCusAccounts(x.getCusAccounts());
				System.out.println("Press any to continue. Press q or Q to quit");
				res2 = scan.next();
			}
			} while (!res2.equalsIgnoreCase("q"));
		}else {
			res = "q";
			System.out.println("Quiting app...");
			System.exit(0);
		}

		}while(!res.equalsIgnoreCase("q"));
	}
	
	
	
	
}

