package edu.java.contact.ver06;

// MVC에서 Model
public class Contact {
    
    public interface Entity {
        String TBL_CONTACTS = "CONTACTS";
        String COL_CID = "CID";
        String COL_NAME = "NAME";
        String COL_PHONE = "PHONE";
        String COL_EMAIL = "EMAIL";
    }
    
    private Integer cid;
    private String name;
    private String phone;
    private String email;

    public Contact() {}

    public Contact(Integer cid, String name, String phone, String email) {
        this.cid = cid;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Integer getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return String.format("Contact(cid=%d, name=%s, phone=%s, email=%s)", 
                cid, name, phone, email);
    }
    
}
