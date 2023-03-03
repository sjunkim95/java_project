package edu.java.project01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import oracle.jdbc.OracleDriver;

import static edu.java.project01.User.Entity.*;
import static edu.java.project01.OracleConnection.*;
import static edu.java.project01.MatchSql.*;
import static edu.java.project01.LoginMain.*;

public class UserDaoImpl implements UserDao {
    
    private static UserDaoImpl instance = null;
    
    private UserDaoImpl() {}
    
    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }
    
    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private void closeResources(Connection conn, Statement stmt) throws SQLException{
        stmt.close();
        conn.close();
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) throws SQLException{
        rs.close();
        closeResources(conn,stmt);
    }
    

    @Override
    public int create(User user) {
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            System.out.println(SQL_USER_CREATE);
            stmt = conn.prepareStatement(SQL_USER_CREATE);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn,stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

    @Override
    public int userLogin(String id, String pw) {
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            System.out.println(SQL_LOGIN_INFO);
            stmt = conn.prepareStatement(SQL_LOGIN_INFO);
            stmt.setString(1, id);
            stmt.setString(2, pw);
            rs = stmt.executeQuery();
            
            if (rs.next()) {                        
                result = 1;
            }
            
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

}
