package project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import project.models.Account;
import project.models.Role;
import project.util.ConnectionUtil;

public class AccountDao implements IAccountDao{

	public void insert(int ownerId) {
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			//Insert account with the account owner id
			String sql = "insert into accounts(balance, accowner, active) VALUES (0, ?, FALSE);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ownerId);
			stmt.executeUpdate();
			ResultSet rs = stmt.executeQuery(sql);
			
			if ((rs = stmt.executeQuery()) != null) {
				String sql2 = "insert into user_accounts_jt (acc_owner, account) values (?, ?);";
						PreparedStatement stmt2= conn.prepareStatement(sql2);
			int a_id = rs.getInt(1);
			int a_owner = rs.getInt(3);
			stmt2.setInt(1, a_owner);
			stmt2.setInt(2, a_id);
			}
			
		} catch(SQLException e) {
			
		}
	}

	public List<Account> findAll() {
		
		// Instantiate a linkedlist to store all of the objects that we retrieve
		List<Account> accList = new LinkedList<Account>();
		
		// Obtain a connection using try with resources
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			// Create a statement to execute against the DB
			Statement stmt = conn.createStatement();
			
			// Let's create our SQL query
			String sql = "SELECT * FROM accounts";
			
			// We'll return all of the data queried so we need a ResultSet obj to iterate over it
			ResultSet rs = stmt.executeQuery(sql);
			
			// Open a while loop to get all the info
			while (rs.next()) {
				
				// Gather the id of the accounts, balance, accOwnerId, and isActive
				int id = rs.getInt("id"); // Capture the value in the id column
				double balance = rs.getDouble("balance");
				int accOwnerId = rs.getInt("acc_owner");
				boolean isActive = rs.getBoolean("active");
				
				// Let's create an Account object to store all of this
				
				Account a = new Account(id, balance, accOwnerId, isActive);
				
				accList.add(a);
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
		
		
		return accList;
	}

	public Account findById(int id) {
		
		Account a = new Account();
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				
				rs.next();
				
				int ret_id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int acc_owner = rs.getInt("acc_owner");
				boolean isActive = rs.getBoolean("active");
				
				a.setId(ret_id);
				a.setBalance(balance);
				a.setAccOwner(acc_owner);
				a.setActive(isActive);
			}
		
	} catch (SQLException e ) {
		System.out.println("SQL Exception Thrown - can't retrieve account from DB");
		e.printStackTrace();
	}
		return a;
	}

	public List<Account> findByOwner(int accOwnerId) {
		List<Account> accList = new LinkedList<Account>();
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "SELECT * FROM accounts WHERE acc_owner = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, accOwnerId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			double balance = rs.getDouble("balance");
			accOwnerId = rs.getInt("acc_owner");
			boolean isActive = rs.getBoolean("active");
			
			Account a = new Account(id, balance, accOwnerId, isActive);
			accList.add(a);
			
		}
		} catch (Exception e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
		return accList;
	}
	
	public List<Account> activeAccountsByOwner(int accOwnerId){
		List<Account> accList = new LinkedList<Account>();
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "SELECT * FROM accounts WHERE acc_owner = ? AND ACTIVE = TRUE;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, accOwnerId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			double balance = rs.getDouble("balance");
			accOwnerId = rs.getInt("acc_owner");
			boolean isActive = rs.getBoolean("active");
			
			Account a = new Account(id, balance, accOwnerId, isActive);
			accList.add(a);
			
		}
		} catch (Exception e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
		return accList;
	}
	
	public boolean isAccountValid(int id, int ownerId) {
		// If the account id belongs to the owner and the account is active, the account is valid for banking use.
	try (Connection conn = ConnectionUtil.getConnection()){
		
		String sql = "SELECT * FROM accounts WHERE id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setInt(1, id);
		
		ResultSet rs;
		
		if ((rs = stmt.executeQuery()) != null) {
			
			// Move the cursor forward
			rs.next();
			int owner = rs.getInt("acc_owner");
			boolean isActive = rs.getBoolean("active");
			
			if (isActive == true && owner == ownerId) {
				return true;
			}else {

				return false;
			}
			
			
			}
	} catch (Exception e) {
		System.out.println("Unable to retrieve accounts due to SQL Exception");
		e.printStackTrace();
	}
	return false;
	}

	public void updateToActive(int id) {
		
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "update accounts set active = true id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
	}
	
	public void updateToInactive(int id) {
		
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "update accounts set active = false id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
	}
	
	public void updateAddBalance(int id, double money) {
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "update accounts set balance = balance + ? where id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, money);
		stmt.setInt(2, id);
		stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
	}
	
	public void updateSubBalance(int id, double money) {
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "update accounts set balance = balance - ? where id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, money);
		stmt.setInt(2, id);
		stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
	}
	
	public void updateTransferBalance(int s_id, int t_id, double money) {
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "update accounts set balance = balance - ? where id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, money);
		stmt.setInt(2, s_id);
		stmt.executeUpdate();
		String sql2 = "update accounts set balance = balance + ? where id = ?;";
		PreparedStatement stmt2 = conn.prepareStatement(sql2);
		stmt2.setDouble(1, money);
		stmt2.setInt(2, t_id);
		stmt2.executeUpdate();
	} catch (Exception e) {
		System.out.println("Unable to retrieve accounts due to SQL Exception");
		e.printStackTrace();
	}
	}
	

	public boolean delete(Account a) {
		
		return false;
	}
	
	public void delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()){
		
		String sql = "delete from user_account_jt where account = ?;";
		String sql2 = "delete from accounts where id = ? cascade;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		PreparedStatement stmt2 = conn.prepareStatement(sql2);
		
		stmt.setInt(1, id);
		stmt2.setInt(1, id);
		stmt.executeUpdate();
		stmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
	}

}
