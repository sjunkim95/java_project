package edu.java.project01;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import static edu.java.project01.MatchSql.*;
import edu.java.project01.LoginPage;
import edu.java.project01.UserCreateFrame.UserInsertListener;
import oracle.jdbc.OracleDriver;

import static edu.java.project01.OracleConnection.*;
import static edu.java.project01.User.Entity.*;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class LoginMain implements UserInsertListener {
    
    private String userID;
    private String userPW;
    
    private JFrame frame;
    private JTextField txtUserId;
    private JPasswordField txtPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginMain window = new LoginMain();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginMain()  {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(192, 192, 192));
        frame.setBackground(new Color(204, 153, 204));
        frame.setBounds(100, 100, 497, 397);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblUserId = new JLabel("Username");
        lblUserId.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserId.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblUserId.setBounds(12, 108, 135, 42);
        frame.getContentPane().add(lblUserId);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblPassword.setBounds(12, 188, 135, 42);
        frame.getContentPane().add(lblPassword);
        
        JLabel lblTitle = new JLabel("Enter and Login");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblTitle.setBounds(79, 24, 296, 42);
        frame.getContentPane().add(lblTitle);
        
        txtUserId = new JTextField();
        txtUserId.setBounds(185, 108, 255, 42);
        frame.getContentPane().add(txtUserId);
        txtUserId.setColumns(10);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(185, 188, 255, 42);
        frame.getContentPane().add(txtPassword);
        
        JButton btnCreate = new JButton("Create");
        btnCreate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserCreateFrame.newUserCreateFrame(frame,LoginMain.this);
            }
        });
        btnCreate.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnCreate.setBounds(12, 289, 118, 35);
        frame.getContentPane().add(btnCreate);
        
        JButton btnReset = new JButton("Reset");
        btnReset.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUserId.setText(null);
                txtPassword.setText(null);
            }
        });
        btnReset.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnReset.setBounds(165, 289, 118, 35);
        frame.getContentPane().add(btnReset);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryLogin();
            }
                
        });
        btnLogin.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnLogin.setBounds(322, 289, 118, 35);
        frame.getContentPane().add(btnLogin);
    }
    
    private void tryLogin() {
        String id = txtUserId.getText();
        String pw = txtPassword.getText();

        UserDaoImpl dao = UserDaoImpl.getInstance();
        int result = dao.userLogin(id, pw);
        if (result == 1) {
            LoginPage.loginMain(null);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Login Details", "Login Error", JOptionPane.ERROR_MESSAGE);
            txtUserId.setText(null);
            txtPassword.setText(null);
        }
        
    }

    

    public void userInsertNotify() {
        JOptionPane.showMessageDialog(frame, "User Created");
    }
}
