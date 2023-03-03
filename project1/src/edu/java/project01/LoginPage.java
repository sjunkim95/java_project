package edu.java.project01;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import edu.java.project01.MatchCreateFrame.MatchInsertListener;
import edu.java.project01.MatchUpdateFrame.MatchUpdateListener;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class LoginPage 
    implements MatchInsertListener, MatchUpdateListener { //MatchUpdateLIstener
    private static final String[] COLUMN_NAMES = {"Match_No", "Home Team", "Away Team", "Match Day", "Memo"};
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private MatchDaoImpl dao;
    private JTextField textSearchKeyword;

    /**
     * Launch the application.
     */
    public static void loginMain(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPage window = new LoginPage();
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
    public LoginPage() {
        loginInit();
        dao = MatchDaoImpl.getInstance();
        loginInitializeTable();
    }
    
    private void loginInitializeTable() {
        resetTableModel();
        List<Match> list = dao.read();
        addDataToTableModel(list);
    }
    
    private void resetTableModel() {
        tableModel = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(tableModel);
    }
    
    private void addDataToTableModel(List<Match> list) {
        for (Match m: list) {
            Object[] rowData = {m.getMatch_no(), m.getHomeTeam(), m.getAwayTeam(), m.getMatchDay(), m.getMemo()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void loginInit() {
        frame = new JFrame();
        frame.setBounds(100, 100, 704, 443);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        
        JButton btnReadAll = new JButton("See All");
        btnReadAll.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnReadAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginInitializeTable();
            }
        });
        btnReadAll.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        panel.add(btnReadAll);
        
        JButton btnCreateFixture = new JButton("Create");
        btnCreateFixture.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnCreateFixture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MatchCreateFrame.newMatchCreateFrame(frame, LoginPage.this);
            }
        });
        btnCreateFixture.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        panel.add(btnCreateFixture);
        
        JButton btnUpdateFixture = new JButton("Update");
        btnUpdateFixture.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnUpdateFixture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateFrame();
            }
        });
        btnUpdateFixture.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        panel.add(btnUpdateFixture);
        
        JButton btnFixtureDelete = new JButton("Delete");
        btnFixtureDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnFixtureDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMatch();
            }
        });
        btnFixtureDelete.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        panel.add(btnFixtureDelete);
        
        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        table = new JTable();
        table.setFont(new Font("D2Coding ligature", Font.PLAIN, 20));
        tableModel = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "Match_No", "Home Team", "Away Team", "Match Day", "Memo"
            }
        ));
        scrollPane.setViewportView(table);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(192, 192, 192));
        frame.getContentPane().add(searchPanel, BorderLayout.SOUTH);
        
        textSearchKeyword = new JTextField();
        textSearchKeyword.setFont(new Font("D2Coding", Font.PLAIN, 20));
        searchPanel.add(textSearchKeyword);
        textSearchKeyword.setColumns(10);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMatchKeyword();
            }
        });
        btnSearch.setFont(new Font("D2Coding", Font.PLAIN, 20));
        searchPanel.add(btnSearch);
    }

    private void searchMatchKeyword() {
        String keyword = textSearchKeyword.getText();
        if (keyword.equals("")) {
            JOptionPane.showMessageDialog(frame, // parentComponent
                    "Insert the keyword.", // message
                    "Error", // title     
                    JOptionPane.WARNING_MESSAGE);
            textSearchKeyword.requestFocus();
            
            return;
        }
        List<Match> list = dao.read(keyword);
        resetTableModel();
        addDataToTableModel(list);
    }

    private void showUpdateFrame() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Select the row you want to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // 업데이트 창에서는 수정 전, 선택된 정보 읽고 match UpdateFrame에 전달
        Integer match_no = (Integer) tableModel.getValueAt(row, 0);
        MatchUpdateFrame.newMatchUpdateFrame(frame, match_no, LoginPage.this);
        
    }

    private void deleteMatch() {
        int row = table.getSelectedRow(); // 테이블에서 선택된 행
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Select the data you want to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer match_no = (Integer) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Do you really want to delete this data?", "Delete Message", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            int result = dao.delete(match_no);
            if (result == 1) {
                loginInitializeTable();
                JOptionPane.showMessageDialog(frame, "Delete Completed");
            } else {
                JOptionPane.showConfirmDialog(frame, "Failed to Delete!");
            } 
        }
    }

    @Override
    public void matchInsertNotify() {
        loginInitializeTable();
        JOptionPane.showMessageDialog(frame, "The match has been added.");
    }
    
    @Override
    public void matchUpdateNotify() {
        loginInitializeTable();
        JOptionPane.showMessageDialog(frame, "The update has been completed");
    }
    
    
}
