package common.model;

public class Account {
	private String name;
	private String email;
	private String password;
	
	public Account(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		
	}
	public String getName() {
	return name;
	}
	
	public String getEmail(){
	return email;
	}
	
	public String getPassword() {
		return password;
	}	
	
	public String toFileString() {
		return name + "/" + email +"/" + password;
	}
	public static Account fromFileString(String fileString) {
		String[]parts = fileString.split("/",3);
		if(parts.length ==3) {
			return new Account(parts[0],parts[1],parts[2]);
		}
		return null;
		
		}
	@Override
    public String toString() {
        return name + "," + email + "," + password;
    }
}

