package edu.java.project01;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class UserCreateFrame extends JFrame {
    
    @FunctionalInterface
    public interface UserInsertListener{
        void userInsertNotify();
    }
    
    private UserInsertListener listener;
    private Component parent;
    private UserDaoImpl dao;

    private JPanel contentPane;
    private JTextField textUsername;
    private JPasswordField textPassword;

    /**
     * Launch the application.
     */
    public static void newUserCreateFrame(Component parent, UserInsertListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                    UserCreateFrame frame = new UserCreateFrame(parent, listener);
                    frame.setVisible(true);
            }
        });
    }

    /**
     * Create the frame.
     */
    public UserCreateFrame(Component parent, UserInsertListener listener) {
        this.parent = parent;
        this.listener = listener;
        this.dao = UserDaoImpl.getInstance();
        
        userInitialize();
        
    }
    
    private void userInitialize() {
        setTitle("Create ID/Password");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x, y, 445, 317);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblUsername.setBounds(12, 78, 102, 36);
        contentPane.add(lblUsername);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblPassword.setBounds(12, 142, 102, 36);
        contentPane.add(lblPassword);
        
        JLabel lblTitle = new JLabel("LoginSystem");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblTitle.setBounds(123, 22, 214, 36);
        contentPane.add(lblTitle);
        
        textUsername = new JTextField();
        textUsername.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textUsername.setBounds(151, 78, 242, 36);
        contentPane.add(textUsername);
        textUsername.setColumns(10);
        
        textPassword = new JPasswordField();
        textPassword.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        textPassword.setBounds(151, 142, 242, 36);
        contentPane.add(textPassword);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancel.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnCancel.setBounds(148, 213, 110, 36);
        contentPane.add(btnCancel);
        
        JButton btnSave = new JButton("Save");
        btnSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewUser();
            }
        });
        btnSave.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnSave.setBounds(283, 213, 110, 36);
        contentPane.add(btnSave);
        
        JButton btnReset = new JButton("Reset");
        btnReset.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textUsername.setText(null);
                textPassword.setText(null);
            }
        });
        btnReset.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnReset.setBounds(12, 213, 110, 36);
        contentPane.add(btnReset);
    }

    private void createNewUser() {
        String username = textUsername.getText();
        String password = textPassword.getText();
        
        User user = new User(username, password);
        int result = dao.create(user);
        if (result == 1) {
            dispose();
            listener.userInsertNotify();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Create a User", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
