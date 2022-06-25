package project.models;

import java.io.Serializable;

//Turn this into a java bean
public class Account implements Serializable{
	
	// Specify fields to match DB columns in a way
	// Name do not have to reflect column names in DB
	
	private int id;
	private double balance;
	private int accOwner;
	private boolean active;
	
	
	public Account() {
		super();
	}


	public Account(int id, double balance, int accOwner, boolean active) {
		super();
		this.id = id;
		this.balance = balance;
		this.accOwner = accOwner;
		this.active = active;
	}

	
	// We're going to add one more constructor that might be useful for creating accounts in the DB
	// Since it should take care of id creation automatically
	
	public Account(double balance, int accOwner, boolean active) {
		super();
		this.balance = balance;
		this.accOwner = accOwner;
		this.active = active;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public int getAccOwner() {
		return accOwner;
	}


	public void setAccOwner(int accOwner) {
		this.accOwner = accOwner;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accOwner;
		result = prime * result + (active ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accOwner != other.accOwner)
			return false;
		if (active != other.active)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", accOwner=" + accOwner + ", active=" + active + "]";
	}
	
	

}

