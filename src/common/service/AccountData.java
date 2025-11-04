package common.service;
import common.model.Account;
import common.model.AccountCache;

import java.io.*;
import java.util.*;

	public class AccountData {
	    private static final String FILE_NAME = "src/common/repo/AccountData.txt";
	    private Map<String, Account> accountsCache = new HashMap<>();
        private AccountCache accountCache = AccountCache.getInstance();
	    
	    public AccountData() {
	        checkFile();
	        loadAccountsFromFile();
	    }
	    
	    public void loadAccountsFromFile() {
	        try {
	            FileReader reader = new FileReader(FILE_NAME);
	            BufferedReader buffer = new BufferedReader(reader);
	            String line = buffer.readLine();
	            
	            while (line != null) {
	                Account account = Account.fromFileString(line);
	                if (account != null) {
	                    //accountsCache.put(account.getEmail().toLowerCase(), account);

                        accountCache.setAccount(account);

	                }
	                line = buffer.readLine();
	            }
	            buffer.close();
	            
	        } catch (IOException e) {
	            System.err.println("Error loading accounts: " + e.getMessage());
	        }
	    }
	    
	    public void writeToFile() {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, false))) {
                for (Account account : accountCache.getAllValues()) {
                    writer.println(account.toFileString());
                }
//	            for (Account account : accountsCache.values()) {
//	                writer.println(account.toFileString());
//	            }
	        } catch (IOException e) {
	            System.err.println("Error saving accounts: " + e.getMessage());
	        }
	    }
	    
	    public boolean checkFile() {
	        File file = new File(FILE_NAME);
	        if (!file.exists()) {
	            try {
	                file.createNewFile();
	                System.out.println("Created new account file: " + FILE_NAME);
	            } catch (IOException e) {
	                System.err.println("Failed to create account file: " + e.getMessage());
	            }
	            return false;
	        }
	        return true;
	    }
	    
	    public boolean saveAccount(Account account) {
	        if (emailExists(account.getEmail())) {
	            return false;
	        }
	        
	        //accountsCache.put(account.getEmail().toLowerCase(), account);
            accountCache.setAccount(account);
	        writeToFile();
	        return true;
	    }
	    
	    public boolean emailExists(String email) {
	        //return accountsCache.containsKey(email.toLowerCase());
            return  accountCache.emailExists(email);

	    }
	    
	    public List<Account> getAllAccounts() {
	        return new ArrayList<>(accountCache.getAllValues());//accountsCache.values()
	    }
	    
	    public boolean validateLogin(String email, String password) {
	        //Account account = accountsCache.get(email.toLowerCase());
            Account account = accountCache.getAccount(email);
	        return account != null && account.getPassword().equals(password);
	    }
	    
	 
	    public int getAccountCount() {
	        //return accountsCache.size();
            return accountCache.getSize();
	    }
	}
