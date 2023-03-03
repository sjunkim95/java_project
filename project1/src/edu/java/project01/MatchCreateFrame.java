package edu.java.project01;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;


public class MatchCreateFrame extends JFrame {
    
    @FunctionalInterface
    public interface MatchInsertListener{
        void matchInsertNotify();
    }
    
    private MatchInsertListener listener;
    private Component parent;
    private MatchDaoImpl dao;

    private JPanel contentPane;
    private JTextField txtHomeTeam;
    private JTextField txtAwayTeam;
    private JTextField txtMatchDay;
    private JTextField txtMemo;

    /**
     * Launch the application.
     */
    public static void newMatchCreateFrame(Component parent, MatchInsertListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MatchCreateFrame frame = new MatchCreateFrame(parent, listener);
                frame.setVisible(true);      
            }
        });
    }

    /**
     * Create the frame.
     */
    public MatchCreateFrame(Component parent, MatchInsertListener listener) {
        this.parent = parent;
        this.listener = listener;
        this.dao = MatchDaoImpl.getInstance();
        
        initialize();
        
    }
    
    private void initialize() {
        setTitle("New Match");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x, y, 413, 298);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblHomeTeam = new JLabel("Home Team");
        lblHomeTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblHomeTeam.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomeTeam.setBounds(12, 10, 110, 39);
        contentPane.add(lblHomeTeam);
        
        JLabel lblAwayTeam = new JLabel("Away Team");
        lblAwayTeam.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwayTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblAwayTeam.setBounds(12, 59, 110, 39);
        contentPane.add(lblAwayTeam);
        
        JLabel lblMatchDay = new JLabel("Match Day");
        lblMatchDay.setHorizontalAlignment(SwingConstants.CENTER);
        lblMatchDay.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblMatchDay.setBounds(12, 108, 110, 39);
        contentPane.add(lblMatchDay);
        
        JLabel lblMemo = new JLabel("Memo");
        lblMemo.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemo.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblMemo.setBounds(12, 157, 110, 39);
        contentPane.add(lblMemo);
        
        JButton btnCreate = new JButton("OK");
        btnCreate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewMatch();
            }
        });
        btnCreate.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnCreate.setBounds(265, 206, 110, 44);
        contentPane.add(btnCreate);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancel.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        btnCancel.setBounds(142, 206, 110, 44);
        contentPane.add(btnCancel);
        
        txtHomeTeam = new JTextField();
        txtHomeTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        txtHomeTeam.setBounds(158, 10, 217, 39);
        contentPane.add(txtHomeTeam);
        txtHomeTeam.setColumns(10);
        
        txtAwayTeam = new JTextField();
        txtAwayTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        txtAwayTeam.setColumns(10);
        txtAwayTeam.setBounds(158, 59, 217, 39);
        contentPane.add(txtAwayTeam);
        
        txtMatchDay = new JTextField();
        txtMatchDay.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        txtMatchDay.setColumns(10);
        txtMatchDay.setBounds(158, 108, 217, 39);
        contentPane.add(txtMatchDay);
        
        txtMemo = new JTextField();
        txtMemo.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        txtMemo.setColumns(10);
        txtMemo.setBounds(158, 157, 217, 39);
        contentPane.add(txtMemo);
    }

    protected void createNewMatch() {
        // 3개의 JTextField의 입력된 문자열을 읽음
        String homeTeam = txtHomeTeam.getText();
        String awayTeam = txtAwayTeam.getText();
        String matchDay = txtMatchDay.getText();
        String memo = txtMemo.getText();
        
        Match match = new Match(null, homeTeam, awayTeam, matchDay, memo);
        
        int result = dao.create(match);
        
        if (result == 1) {
            dispose();
            listener.matchInsertNotify();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Create a Match", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }



}
