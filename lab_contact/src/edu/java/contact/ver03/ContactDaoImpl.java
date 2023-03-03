package edu.java.contact.ver03;

import java.util.ArrayList;
import java.util.List;

import edu.java.contact.ver02.Contact;

public class ContactDaoImpl implements ContactDao {
    
    // 연락처 정보를 저장할 List
    private List<Contact> contacts = new ArrayList<>();
    
    // singleton
    private static ContactDaoImpl instance = null;
    
    private ContactDaoImpl() {}
    
    public static ContactDaoImpl getInstance() {
        if (instance == null) {
            instance = new ContactDaoImpl();
        }
        
        return instance;
    }

    @Override
    public List<Contact> read() {
        return contacts;
    }

    @Override
    public Contact read(int index) {
        try {
            return contacts.get(index);
        } catch (IndexOutOfBoundsException e) {
            // index < 0 || index >= size 인 경우
            return null;
        }
        
//        if (!isValidIndex(index)) {
//            return null;
//        }
//        
//        return contacts.get(index);
    }

    @Override
    public int create(Contact contact) {
        if (contacts.add(contact)) {
            return 1;
        } else {
            return 0;
        }
        
//        contacts.add(contact);
//        return 1;
    }

    @Override
    public int update(int index, Contact contact) {
        if (!isValidIndex(index)) {
            // valid index: index >= 0 && index < size
            // not valid: index < 0 || index >= size
            return 0;
        }
        
        contacts.set(index, contact);
//        Contact before = contacts.get(index);
//        before.setName(contact.getName());
//        before.setPhone(contact.getPhone());
//        before.setEmail(contact.getEmail());
        
        return 1;
    }

    @Override
    public int delete(int index) {
        if (!isValidIndex(index)) {
            return 0;
        }
        
        contacts.remove(index);
        
        return 1;
    }

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < contacts.size());
    }
    
}
