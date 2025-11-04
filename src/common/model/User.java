package common.model;

public class User {
    private String username;   // or email as username
    private String email;
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
