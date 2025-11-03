package common.model;

public class User {
    protected int id;
    protected String username;   // or email as username
    protected String email;
    protected String name;
    protected Role role;

    public User(int id, String username, String email, String name, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public int getID() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public Role getRole() { return role; }
}
