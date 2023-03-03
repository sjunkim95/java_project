package edu.java.contact.ver02;

// MVC 아키텍쳐에서 Controller (구현) 클래스
public class ContactDaoImpl implements ContactDao {
    
    private static final int MAX_LENGTH = 3;
    
    private Contact[] contactList = new Contact[MAX_LENGTH];
    
    private int count = 0;
    
    private static ContactDaoImpl instance = null;
    
    private ContactDaoImpl() {}
    
    public static ContactDaoImpl getInstance() {
        if (instance == null) {
            instance = new ContactDaoImpl();
        }
        
        return instance;
    }
    
    @Override
    public Contact[] read() {
        // contactList에 저장된 Contact 객체의 개수만큼 크기의 배열을 새로 생성.
        Contact[] contacts = new Contact[count];
        for (int i = 0; i < count; i++) {
            contacts[i] = contactList[i]; // null이 아닌 값들만 복사.
        }
        
        return contacts;
    }
    
    @Override
    public Contact read(int index) {
        if (index >= 0 && index < count) {
            return contactList[index];
        } else {
            return null;
        }
    }
    
    @Override
    public int create(Contact c) {
        if (count == MAX_LENGTH) {
            // 배열에 저장된 개수와 배열의 최대 크기가 같으면
            return 0;
        }
        
        contactList[count] = c;
        count++;
        
        return 1;
    }
    
    @Override
    public int update(int index, Contact c) {
        if (index < 0 || index > count) { // 유효하지 않은 인덱스이면
            return 0;
        }
        
        contactList[index].setName(c.getName());
        contactList[index].setPhone(c.getPhone());
        contactList[index].setEmail(c.getEmail());
        
        return 1;
    }

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < count);
    }

    public boolean isMemoryAvailable() {
        return count < MAX_LENGTH;
    }

}
