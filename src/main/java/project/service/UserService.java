package project.service;

import java.util.List;
import project.dao.IUserDao;
import project.dao.UserDao;
import project.exceptions.RegisterUserFailedException;
import project.models.User;
import org.apache.log4j.Logger;


public class UserService {
	
	// Dependency Injection
	public IUserDao udao = new UserDao();
	Logger logger = Logger.getLogger(UserService.class);
	
	public User register(User u) {
		logger.info("Registering user.");
		System.out.println("Registering user....");
		
		// Let's make sure the registering user has an id of 0 before trying to register
		// This is just an additional layer of data validation
		
		if (u.getId()!= 0) {
			throw new RegisterUserFailedException("User not valid to register because Id was not 0");
		}
		
		// If the id is 0, we can call the dao to create a new object
		
		int generatedId = udao.insert(u);
		
		// Let's do some checking before finishing
		
		if (generatedId != -1 && generatedId != u.getId()) {
			u.setId(generatedId);
		} else {
			throw new RegisterUserFailedException("User's Id was either -1 or did not change after insertion");
		}
		
		System.out.println("Successfully registered user with the Id of " + u.getId());
		
		
		return u;
	}
	
	
	public User login(String username, String password) {
		
		// We now need to call upon our userDAO to get us some information about the user with this specific username
		
		User returnedUser = udao.findByUsername(username);

		// Check to see if returned password matches the entered password
		
		if (returnedUser.getPassword().equals(password)) {
			logger.info("logged in.");
			System.out.println("Successfully Logged in!");
			
			System.out.println("Reached the inside of the if statement");
			return returnedUser;
		}
		
		// Otherwise the password is not equal
		return null;
	}
	
	public void viewAllCustomers() {
		List<User> uList = udao.findAllCustomer();
		logger.info("View all customers.");
		for (User u: uList) {
			System.out.println(u);
		}
	}
	
	public boolean checkUsername(String username) {
		if (udao.isUsernameExists(username)) {
			logger.info("The username: " + username + " already exists in the database");
			return true;
		}else {
			logger.info("The username: " + username + " is not in database");
			return false;
		}
	}
	

	
	
}
