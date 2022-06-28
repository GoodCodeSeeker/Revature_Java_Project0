package project;

import static org.mockito.Mockito.mock;

import org.junit.Before;

import project.dao.AccountDao;
import project.models.Account;
import project.service.AccountService;

public class AccountServiceTests {

private AccountService as;

private AccountDao mockDao;
	
private Account dummyAccount;

@Before
public void setup() {
	as = new AccountService();
	
	AccountDao Mock = mock(AccountDao.class);
	as.adao = mockDao;
	
	dummyAccount = new Account();
	dummyAccount.setId(0);
}








}
