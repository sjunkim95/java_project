package edu.java.contact.ver06;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContactUpdateFrame extends JFrame {
    @FunctionalInterface
    public interface ContactUpdateListener {
        void contactUpdateNotify();
    }
    
    private ContactUpdateListener listener;

    private Component parent; // 업데이트 창을 실행시킨 부모 컴포넌트
    private Integer cid; // 수정할 연락처의 아이디
    private ContactDaoImpl dao; // 연락처 검색(read), 업데이트(update), ...
    
    private JPanel contentPane;
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textEmail;

    /**
     * Launch the application.
     */
    public static void newContactUpdateFrame(Component parent, Integer cid, 
            ContactUpdateListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ContactUpdateFrame frame = new ContactUpdateFrame(parent, cid, listener);
                frame.setVisible(true);
            }
        });
    }

    /**
     * Create the frame.
     */
    public ContactUpdateFrame(Component parent, Integer cid, ContactUpdateListener listener) {
        this.parent = parent; // 부모 컴포넌트를 초기화.
        this.cid = cid; // 수정할 연락처의 인덱스를 멤버로 저장.
        this.dao = ContactDaoImpl.getInstance(); // DAO 싱글턴 객체를 가져옴.
        this.listener = listener; // 업데이트 창을 만든 메인 창의 주소를 저장.
        
        initialize(); // UI 컴포넌트들을 생성, 초기화.
        
        initializeContactInfo(); // 수정하려는 인덱스의 연락처 정보를 JTextField에 채움.
    }
    
    void initializeContactInfo() {
        Contact contact = dao.read(cid); // 수정하려는 연락처 정보
        
        // 각 JTextField에 연락처 정보를 씀.
        textName.setText(contact.getName());
        textPhone.setText(contact.getPhone());
        textEmail.setText(contact.getEmail());
    }
    
    /**
     * Initialize UI components.
     */
    private void initialize() {
        setTitle("연락처 보기/수정");
        // 닫기 버튼을 클릭했을 때의 기본 동작 설정 - 현재 창만 닫기
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        int x = parent.getX(); // 부모 컴포넌트의 x 좌표
        int y = parent.getY(); // 부모 컴포넌트의 y 좌표
        setBounds(x, y, 450, 300);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblName = new JLabel("이름");
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblName.setBounds(12, 10, 120, 48);
        contentPane.add(lblName);
        
        JLabel lblPhone = new JLabel("전화번호");
        lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhone.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblPhone.setBounds(12, 68, 120, 48);
        contentPane.add(lblPhone);
        
        JLabel lblEmail = new JLabel("이메일");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("D2Coding", Font.PLAIN, 20));
        lblEmail.setBounds(12, 122, 120, 48);
        contentPane.add(lblEmail);
        
        textName = new JTextField();
        textName.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textName.setBounds(144, 10, 278, 48);
        contentPane.add(textName);
        textName.setColumns(10);
        
        textPhone = new JTextField();
        textPhone.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textPhone.setColumns(10);
        textPhone.setBounds(144, 69, 278, 48);
        contentPane.add(textPhone);
        
        textEmail = new JTextField();
        textEmail.setFont(new Font("D2Coding", Font.PLAIN, 20));
        textEmail.setColumns(10);
        textEmail.setBounds(144, 122, 278, 48);
        contentPane.add(textEmail);
        
        JButton btnSave = new JButton("업데이트");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });
        btnSave.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnSave.setBounds(12, 180, 145, 48);
        contentPane.add(btnSave);
        
        JButton btnCancel = new JButton("취소");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancel.setFont(new Font("D2Coding", Font.PLAIN, 20));
        btnCancel.setBounds(169, 180, 120, 48);
        contentPane.add(btnCancel);
    }

    private void updateContact() {
        // 각 JTextField에 입력된 내용을 읽음.
        String name = textName.getText();
        String phone = textPhone.getText();
        String email = textEmail.getText();
        
        // Contact 타입 객체 생성
        Contact contact = new Contact(cid, name, phone, email);
        
        // DAO의 메서드를 사용해서 연락처 정보 (파일) 업데이트.
        int result = dao.update(contact);
        if (result == 1) {
            // 창 닫기
            dispose();
            // 메인 창(window)에게 연락처 정보가 업데이트됐다고 알려줌.
            listener.contactUpdateNotify();
        } else {
            JOptionPane.showMessageDialog(this, 
                    "연락처 업데이트 실패", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
