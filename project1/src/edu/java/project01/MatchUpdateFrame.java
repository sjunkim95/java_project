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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class MatchUpdateFrame extends JFrame {

    @FunctionalInterface
    public interface MatchUpdateListener{
        void matchUpdateNotify();
    }
    
    private MatchUpdateListener listener;
    
    private Component parent;
    private Integer match_no;
    private MatchDaoImpl dao;
    
    private JPanel contentPane;
    private JTextField textHomeTeam;
    private JTextField textAwayTeam;
    private JTextField textMatchDay;
    private JTextField textMemo;

    /**
     * Launch the application.
     */
    public static void newMatchUpdateFrame(Component parent, Integer match_no, MatchUpdateListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MatchUpdateFrame frame = new MatchUpdateFrame(parent, match_no, listener);
                frame.setVisible(true);
                
            }
        });
    }

    /**
     * Create the frame.
     */
    public MatchUpdateFrame(Component parent, Integer match_no, MatchUpdateListener listener) {
        this.parent = parent;
        this.match_no = match_no;
        this.dao = MatchDaoImpl.getInstance();
        this.listener = listener;
        
        initialize();
        
        initializeMatchInfo();
    }
    
    void initializeMatchInfo() {
        Match match = dao.read(match_no);
        
        textHomeTeam.setText(match.getHomeTeam());
        textAwayTeam.setText(match.getAwayTeam());
        textMatchDay.setText(match.getMatchDay());
        textMemo.setText(match.getMemo());
    }
    
    private void initialize() {
        setTitle("Match Update");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        
        setBounds(x, y, 424, 347);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblHomeTeam = new JLabel("Home Team");
        lblHomeTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblHomeTeam.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomeTeam.setBounds(12, 47, 125, 35);
        contentPane.add(lblHomeTeam);
        
        JLabel lblAwayTeam = new JLabel("Away Team");
        lblAwayTeam.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwayTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblAwayTeam.setBounds(12, 96, 125, 35);
        contentPane.add(lblAwayTeam);
        
        JLabel lblMatchDay = new JLabel("Match Day");
        lblMatchDay.setHorizontalAlignment(SwingConstants.CENTER);
        lblMatchDay.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblMatchDay.setBounds(12, 148, 125, 35);
        contentPane.add(lblMatchDay);
        
        JLabel lblMemo = new JLabel("Memo");
        lblMemo.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemo.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblMemo.setBounds(12, 201, 125, 35);
        contentPane.add(lblMemo);
        
        JLabel lblUpdate = new JLabel("Update");
        lblUpdate.setHorizontalAlignment(SwingConstants.CENTER);
        lblUpdate.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        lblUpdate.setBounds(175, 10, 125, 35);
        contentPane.add(lblUpdate);
        
        textHomeTeam = new JTextField();
        textHomeTeam.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textHomeTeam.setBounds(170, 47, 199, 35);
        contentPane.add(textHomeTeam);
        textHomeTeam.setColumns(10);
        
        textAwayTeam = new JTextField();
        textAwayTeam.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        textAwayTeam.setColumns(10);
        textAwayTeam.setBounds(170, 97, 199, 35);
        contentPane.add(textAwayTeam);
        
        textMatchDay = new JTextField();
        textMatchDay.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        textMatchDay.setColumns(10);
        textMatchDay.setBounds(170, 149, 199, 35);
        contentPane.add(textMatchDay);
        
        textMemo = new JTextField();
        textMemo.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        textMemo.setColumns(10);
        textMemo.setBounds(170, 202, 199, 35);
        contentPane.add(textMemo);
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });
        btnUpdate.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnUpdate.setBounds(276, 248, 93, 44);
        contentPane.add(btnUpdate);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancel.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnCancel.setBounds(171, 248, 93, 44);
        contentPane.add(btnCancel);
    }
    

    private void updateContact() {
        // Jfield입력된 내용 읽음
        String homeTeam = textHomeTeam.getText();
        String awayTeam = textAwayTeam.getText();
        String matchDay = textMatchDay.getText();
        String memo = textMemo.getText();
        
        // Match 객체 생성
        Match match = new Match(match_no, homeTeam, awayTeam, matchDay, memo);
        
        // Dao 메서드를 사용해서 연락처 정보 (파일) 업데이트.
        int result = dao.update(match);
        if (result == 1) {
            dispose();
            listener.matchUpdateNotify();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
