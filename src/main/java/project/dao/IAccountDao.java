package project.dao;

import java.util.List;

import project.models.Account;

public interface IAccountDao {
	// Let's define our crud methods
	
	//Create
	//int insert(Account a);
	void insert(int ownerId);
	
	// A few methods for read
	
	List<Account> findAll();
	
	Account findById(int id); // Returns account based off Id
	
	List<Account> findByOwner(int accOwnerId); // Since a user can have many accounts we want to be able to return all
	List<Account> activeAccountsByOwner(int accOwner);
	public boolean isAccountValid(int id, int ownerId);
	// Update
	//boolean update(Account a);
	public void updateAddBalance(int id, double money);
	public void updateSubBalance(int id, double money);
	public void updateTransferBalance(int s_id, int t_id, double money);
	// Delete
	
	//boolean delete(Account a);
}
