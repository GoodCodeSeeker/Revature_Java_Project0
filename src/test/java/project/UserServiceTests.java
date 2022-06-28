package project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import project.dao.UserDao;
import project.exceptions.RegisterUserFailedException;
import project.models.Account;
import project.models.Role;
import project.models.User;
import project.service.UserService;

public class UserServiceTests {
	
	// Declare a variable of the class to be tested
	private UserService us;
	
	// Declare the dependencies of the class to be tested
	private UserDao mockDao;
	
	//Let's create a before to set up before our tests
	
	// Let's define a stub
	private User dummyUser;
	
	@Before
	public void setup() {
		us = new UserService();
		
		// Let's mock the class that's being injected
		UserDao Mock = mock(UserDao.class);
		
		// Let's set the user dao inside of the userservice to this mocked one
		us.udao = mockDao;
		
		// Let's also set up a stub user for passing in and stuff
		dummyUser = new User();
		dummyUser.setAccounts(new LinkedList<Account>());
		dummyUser.setId(0); // We're doing to emulate a user generated from the console
	}
	
	// Let's create a teardown method to just reset what we had
	@After
	public void teardown() {
		
		us = null;
		dummyUser = null;
		mockDao = null;
	}
	
	// Let's look at some actual testing 
	
	// Test register new user, every time we register we should return the PK (id) that's generated by the DB
	
	@Test
	public void testRegisterUserReturnsNewPKId() {
		// Let's make a user object to test
		dummyUser = new User(0, "spongebob", "pass", Role.Admin, new LinkedList<Account>());
		
		// Let's generate a random number to pretend the DB created it
		Random r = new Random();
		int fakePK = r.nextInt(100);
		
		// So now before we run the actual user service method we have to set up a mock
		// to emulate the function of the insert method from the dao
		
		when(mockDao.insert(dummyUser)).thenReturn(fakePK);
		
		// The registered user of our register method SHOULD have the id that's returned from insert
		User registeredUser = us.register(dummyUser);
		
		// The final thing to do is call our assert method and the id of the registered user
		// to see if it matches the fakePK
		assertEquals(registeredUser.getId(), fakePK);
	}
	
	// Let's test our exception stuff as well
	// We want to make sure ALL the functionality of the method is test
	
	@Test(expected = RegisterUserFailedException.class)
	public void testRegisterUserWithNonZeroId() {
		
		dummyUser.setId(1);
		
		us.register(dummyUser); // This should throw an exception before insert ever gets off because of our data validation
	}
	
	// Let's test our other branch to be thorough
	// This one comes after insert so we'll have to mock it again
	@Test(expected = RegisterUserFailedException.class)
	public void testInsertedUserReturnedNegativeOne() {
		
		// Dummy user we're passing to the insert method
		dummyUser = new User(0, "spongebob", "pass", Role.Admin, new LinkedList<Account>());
		
		// Returning this value from the insert method to cause an exception
		int fakePK = -1;
		
		// Mock insert method and return fakePK
		when(mockDao.insert(dummyUser)).thenReturn(fakePK);
		
		// This should return an exception
		User registeredUser = us.register(dummyUser);

	}
	
	// Our code coverage was below the expected 80% threshold
	// Let's test more methods to make sure we get there
	@Test
	public void testLoginReturnsUser() {
		
		// Let's set up a dummy to test our login
		dummyUser = new User(0, "spongebob", "pass", Role.Admin, new LinkedList<Account>());
		
		// Let's create our login attempt
		String username = "spongebob";
		String password = "pass";
		
		when(mockDao.findByUsername(username)).thenReturn(dummyUser);
		
		User loggedIn = us.login(username, password);
		
		assertEquals(loggedIn.getUsername(), username);
		
		
	}
}