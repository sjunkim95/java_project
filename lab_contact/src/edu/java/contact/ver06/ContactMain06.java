package edu.java.contact.ver06;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.java.contact.ver06.ContactCreateFrame.ContactInsertListener;
import edu.java.contact.ver06.ContactUpdateFrame.ContactUpdateListener;

public class ContactMain06 
    implements ContactInsertListener, ContactUpdateListener {
    private static final String[] COLUMN_NAMES = {"번호", "이름", "전화번호"};

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private ContactDaoImpl dao;
    private JTextField textSearchKeyword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ContactMain06 window = new ContactMain06();
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
    public ContactMain06() {
        initialize(); // UI 컴포넌트 생성, 초기화
        dao = ContactDaoImpl.getInstance(); // 연락처 데이터 관리(추가, 삭제, 변경, 검색, ...)
        initializeTable();
    }
    
    private void initializeTable() {
        resetTableModel();
        List<Contact> list = dao.read();
        addDataToTableModel(list);
    }
    
    private void resetTableModel() {
        tableModel = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(tableModel);
    }

    private void addDataToTableModel(List<Contact> list) {
        for (Contact c : list) {
            // 테이블 모델에 추가할 행(row) 데이터
            Object[] rowData = {c.getCid(), c.getName(), c.getPhone()};
            // 테이블 모델에 데이터 추가
            tableModel.addRow(rowData);
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Contact Version 0.6"); // 애플리케이션 타이틀을 설정
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel buttonPanel = new JPanel();
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        
        JButton btnCreate = new JButton("새 연락처");
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContactCreateFrame.newContactCreateFrame(frame, ContactMain06.this);
            }
        });
        
        JButton btnReadAll = new JButton("전체 목록");
        btnReadAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeTable();
            }
        });
        btnReadAll.setFont(new Font("D2Coding", Font.PLAIN, 20));
        buttonPanel.add(btnReadAll);
        btnCreate.setFont(new Font("D2Coding", Font.PLAIN, 20));
        buttonPanel.add(btnCreate);
        
        JButton btnUpdate = new JButton("연락처 수정");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateFrame();
            }
        });
        btnUpdate.setFont(new Font("D2Coding", Font.PLAIN, 20));
        buttonPanel.add(btnUpdate);
        
        JButton btnDelete = new JButton("연락처 삭제");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });
        btnDelete.setFont(new Font("D2Coding", Font.PLAIN, 20));
        buttonPanel.add(btnDelete);
        
        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        table = new JTable();
        tableModel = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(tableModel);
        scrollPane.setViewportView(table);
        
        JPanel searchPanel = new JPanel();
        frame.getContentPane().add(searchPanel, BorderLayout.SOUTH);
        
        textSearchKeyword = new JTextField();
        textSearchKeyword.setFont(new Font("D2Coding", Font.PLAIN, 20));
        searchPanel.add(textSearchKeyword);
        textSearchKeyword.setColumns(10);
        
        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContacts();
            }
        });
        btnSearch.setFont(new Font("D2Coding", Font.PLAIN, 20));
        searchPanel.add(btnSearch);
    }

    private void searchContacts() {
        String keyword = textSearchKeyword.getText();
        if (keyword.equals("")) {
            JOptionPane.showMessageDialog(frame, 
                    "검색 키워드를 먼저 입력하세요.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            textSearchKeyword.requestFocus();
            
            return;
        }
        
        List<Contact> list = dao.read(keyword);
        resetTableModel();
        addDataToTableModel(list);
        
    }

    private void deleteContact() {
        int row = table.getSelectedRow(); // 테이블에서 선택된 행 인덱스
        if (row == -1) { // 테이블에서 선택된 행이 없으면
            JOptionPane.showMessageDialog(frame, 
                    "삭제할 행을 먼저 선택하세요", // 메시지 
                    "Warning", // 타이틀 
                    JOptionPane.WARNING_MESSAGE); // 메시지 타입
            return;
        }
        
        // 삭제할 CID
        Integer cid = (Integer) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, 
                "선택한 연락처를 정말 삭제할까요?", // 메시지
                "삭제 확인", // 타이틀
                JOptionPane.YES_NO_OPTION); // Yes-No-Cancel 옵션
        if (confirm == JOptionPane.YES_OPTION) {
            // DAO의 메서드를 사용해서 연락처를 삭제, 파일에 저장.
            int result = dao.delete(cid);
            if (result == 1) {
                initializeTable(); // 테이블 갱신
                JOptionPane.showMessageDialog(frame, "삭제 성공");
            } else {
                JOptionPane.showMessageDialog(frame, "삭제 실패!");
            }
        }
    }

    private void showUpdateFrame() {
        // 테이블에서 수정하기 위해서 선택한 행 번호를 찾음.
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, 
                    "수정할 행을 먼저 선택하세요.", // 메시지 
                    "Warning", // 타이틀 
                    JOptionPane.WARNING_MESSAGE); // 메시지 타입
            return;
        }
        
        // 업데이트 창에서는 수정 전의 정보를 화면에 출력하기 위해서, 
        // 선택된 행의 CID 정보를 읽고, ContactUpdateFrame에 전달.
        Integer cid = (Integer) tableModel.getValueAt(row, 0);
        ContactUpdateFrame.newContactUpdateFrame(frame, cid, ContactMain06.this);
    }

    @Override // ContactCreateFrame.ContactInsertListener 인터페이스의 메서드를 구현.
    public void contactInsertNotify() {
        // 메인 화면의 테이블을 갱신
        initializeTable();
        JOptionPane.showMessageDialog(frame, "새 연락처가 추가됐습니다.");
    }

    @Override // ContactUpdateListener 인터페이스를 구현
    public void contactUpdateNotify() {
        // 연락처 데이터 새로 로딩
        initializeTable();
        JOptionPane.showMessageDialog(frame, "연락처 정보가 업데이트됐습니다.");
    }

}
