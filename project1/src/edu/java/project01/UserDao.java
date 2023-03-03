package edu.java.project01;

import java.util.List;

public interface UserDao {
    
    int create(User user);
    int userLogin(String id, String pw);
}
