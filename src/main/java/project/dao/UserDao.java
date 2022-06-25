package project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import project.dao.IUserDao;
import project.models.Account;
import project.models.Role;
import project.models.User;
import project.util.ConnectionUtil;

public class UserDao implements IUserDao{

	public int insert(User u) {
		// Let's use insert to practice creating a SQL operation
		
		// Step 1. Capture the DB connection by using the connection util
		
		Connection conn = ConnectionUtil.getConnection(); // Pulls in current connection from connection util
		
		// Step 2. Generate a sql statement like "Insert into ....."
		
		// Use ? to represent data we're looking to enter into our sql statment
		String sql = "INSERT INTO users (username, pwd, user_role) values (?, ?, ?) RETURNING users.id";
		
		// Step 2b. Use a prepared statement to avoid SQL injection
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Start process to set the values we're entering into the DB
			
			// Go through each ? and give it a values
			
			// Let's start with username
			stmt.setString(1, u.getUsername());
			
			// Let's do the password as well
			stmt.setString(2, u.getPassword());
			
			// User role will be handled slightly since it's a java enum
			stmt.setObject(3, u.getRole(), Types.OTHER);
			
			// Use a resultset to extract the primary key from the user object that was persisted
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// if we return data, we can iterate over it
				
				rs.next();
				
				// We need to capture the first column of data return (which is the id of the return user object)
				
				int id = rs.getInt(1); 
				
				System.out.println("We returned a user with id #" + id);
				
				return id;
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Unable to insert user - sql exception");
			e.printStackTrace();
		}
		 
		// If our database fails to enter someone in we should return an index that we know our DB could never generate
		
		return -1;
	}

	public User findById(int id) {
		User u = new User();
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				
				rs.next();
				
				int ret_id = rs.getInt("id");
				String username = rs.getString("username");
				String pwd = rs.getString("pwd");
				Role userRole = Role.valueOf(rs.getString("user_role"));
				
				u.setId(ret_id);
				u.setUsername(username);
				u.setPassword(pwd);
				u.setRole(userRole);
			}
		
	} catch (SQLException e ) {
		System.out.println("SQL Exception Thrown - can't retrieve account from DB");
		e.printStackTrace();
	}
		return u;
	}

	public User findByUsername(String username) {
		
		// Let's instantiate a user to hold our retrieved user
		
		User u = new User();
		
		// Try with Resources to connect and work with database
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM users WHERE username = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				int id = rs.getInt("id");
				String returnedUsername = rs.getString("username");
				String password = rs.getString("pwd");
				Role role = Role.valueOf(rs.getString("user_role"));
				
				u.setId(id);
				u.setUsername(returnedUsername);
				u.setPassword(password);
				u.setRole(role);
				
			} 
		} catch (SQLException e) {
			System.out.println("SQL Exception Thrown - can't retrieve user from DB");
			e.printStackTrace();
		}
		
		
		return u;
	}
	
	public boolean isUsernameExists(String username) {

		
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "select * from users where username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return true;
			} else return false;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public List<User> findAllCustomer() {
		
		// Instantiate a linkedlist to store all of the objects that we retrieve
		List<User> uList = new LinkedList<User>();
		
		// Obtain a connection using try with resources
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			// Create a statement to execute against the DB
			Statement stmt = conn.createStatement();
			
			// Let's create our SQL query
			String sql = "SELECT * FROM users where user_role = Role.Customer";
			
			// We'll return all of the data queried so we need a ResultSet obj to iterate over it
			ResultSet rs = stmt.executeQuery(sql);
			
			// Open a while loop to get all the info
			while (rs.next()) {
				User u = new User();
				// Gather the id of the accounts, balance, accOwnerId, and isActive
				int id = rs.getInt("id"); // Capture the value in the id column
				String username = rs.getString("username");
				String pwd = rs.getString("pwd");
				Role role = Role.valueOf(rs.getString("user_role"));
				
				// Let's create an Account object to store all of this
				
				u.setId(id);
				u.setUsername(username);
				u.setPassword(pwd);
				u.setRole(role);
				
				uList.add(u);
			}
		} catch (Exception e) {
			System.out.println("SQL Exception Thrown - can't retrieve user from DB");
			e.printStackTrace();
		}
		return uList;
	}
		
	public void display(ResultSet rs) throws SQLException{
        while (rs.next()) {
            System.out.println(rs.getString("id") + "\t"
                    + rs.getString("username") + "\t"
                    + rs.getString("pwd"));
        }
	}
	

	


	public boolean update(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}

