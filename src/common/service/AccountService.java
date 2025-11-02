package common.service;
import common.model.Account;

public class AccountService {
	 private AccountData accountRepo;
    
    public AccountService() {
        this.accountRepo = new AccountData();
    }
    
    public boolean createNewAccount(String name, String email, String password) {
        
        Account account = new Account(name, email, password);
        return accountRepo.saveAccount(account);
    }
    
    public boolean emailExists(String email) {
        return accountRepo.emailExists(email);
    }
    
    public boolean validateLogin(String email, String password) {
        return accountRepo.validateLogin(email, password);
    }
}

