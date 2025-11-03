package common.service;
import common.model.Account;
import java.io.*;
import java.util.*;

	public class AccountData {
	    private static final String FILE_NAME = "accounts.dat";
	    private Map<String, Account> accountsCache = new HashMap<>();
	    
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
	                    accountsCache.put(account.getEmail().toLowerCase(), account);
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
	            for (Account account : accountsCache.values()) {
	                writer.println(account.toFileString());
	            }
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
	        
	        accountsCache.put(account.getEmail().toLowerCase(), account);
	        writeToFile();
	        return true;
	    }
	    
	    public boolean emailExists(String email) {
	        return accountsCache.containsKey(email.toLowerCase());
	    }
	    
	    public List<Account> getAllAccounts() {
	        return new ArrayList<>(accountsCache.values());
	    }
	    
	    public boolean validateLogin(String email, String password) {
	        Account account = accountsCache.get(email.toLowerCase());
	        return account != null && account.getPassword().equals(password);
	    }
	    
	 
	    public int getAccountCount() {
	        return accountsCache.size();
	    }
	}
