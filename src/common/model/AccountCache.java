package common.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountCache {
    private static AccountCache instance;
    private Map<String, Account> accountsCache = new HashMap<>();

    private AccountCache(){
        String name = "test";
        String email = "test@vcrts.com";
        String pass = "test";
        Account account = new Account(name,email,pass,"controller");
        accountsCache.put(email.toLowerCase(),account);
    }
    
    public static AccountCache getInstance(){
        if (instance == null){
            instance = new AccountCache();
        }
        return instance;

    }
    public void setAccount(Account account) {

        accountsCache.put(account.getEmail().toLowerCase(), account);
    }
    public Account getAccount(String email){
        return accountsCache.get(email.toLowerCase());

    }
    public int getSize(){
        return accountsCache.size();
    }

    public Collection<Account> getAllValues(){
        return  accountsCache.values();
    }
    public boolean emailExists(String email){
        return accountsCache.containsKey(email.toLowerCase());
    }
   
    public Map<String, Account> getAccountsMap() {
    return new HashMap<>(accountsCache); 
}

}
