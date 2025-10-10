public class User {
    private String username;
    private String password;
    private int clientCode;
    private String role;

    public User(String username, String password, int clientCode, String role) {
        this.username = username;
        this.password = password;
        this.clientCode = clientCode;
        this.role = role;
    }
    
    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public int getClientCode() {return this.clientCode;}
    public String getRole() {return this.role;}
}