package common.model;

public class Account {
	private String name;
	private String email;
	private String password;
    private String role;
	
	public Account(String name, String email, String password, String role) {
		this.name = name;
		this.email = email;
		this.password = password;
        this.role = role;
		
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

    public String getRole() {
		return role;
	}

    public String toFileString() {
		return name + "/" + email +"/" + password + "/" + role;
	}
	public static Account fromFileString(String fileString) {
		String[]parts = fileString.split("/",4);
		if(parts.length ==4) {
			return new Account(parts[0],parts[1],parts[2],parts[3]);
		}
		return null;
		
		}
	@Override
    public String toString() {
        return name + "," + email + "," + password + "," + role;
    }
}

