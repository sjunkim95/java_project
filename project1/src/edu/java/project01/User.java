package edu.java.project01;

// USER MVC
public class User {
    
    public interface Entity{
        String TBL_USER_INFO = "USER_INFO";
        String COL_USERNAME = "USERNAME";
        String COL_PASSWORD = "PASSWORD";
    }
    
    private String username;
    private String password;
    
    public User() {}
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString() {
        return String.format("User(username=%s, password=%s)", username, password);
    }

}
