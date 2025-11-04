package common.model;

public class User {
    protected String username;   // or email as username
    protected String email;
    private static final User instance = new User();
    public User() {
    }
    public static User getInstance(){
        return  instance;
    }
    public void login(String email, String username){
        instance.setEmail(email);
        instance.setUsername(username);
    }
    private void setUsername(String password){
        this.username = password;
    }
    private void setEmail(String email){
        this.email =email;
    }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
