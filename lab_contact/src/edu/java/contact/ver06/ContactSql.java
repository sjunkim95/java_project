package edu.java.contact.ver06;

import static edu.java.contact.ver06.Contact.Entity.*;

public interface ContactSql {
    
    // ContactDao.read() 메서드에서 사용할 SQL
    // select * from CONTACTS order by NAME
    String SQL_SELECT_ORDER_BY_NAME = String.format(
            "select * from %s order by %s", 
            TBL_CONTACTS, COL_NAME);
    
    // ContactDao.read(Integer cid) 메서드에서 사용할 SQL
    // select * from CONTACTS where CID = ?
    String SQL_SELECT_BY_CID = String.format(
            "select * from %s where %s = ?", 
            TBL_CONTACTS, COL_CID);
    
    // select * from CONTACTS where lower(NAME) like ? or lower(PHONE) like ? or lower(EMAIL) like ? order by NAME
    String SQL_SELECT_BY_KEYWORD = String.format(
            "select * from %s where lower(%s) like ? or lower(%s) like ? or lower(%s) like ? order by %s", 
            TBL_CONTACTS, COL_NAME, COL_PHONE, COL_EMAIL, COL_NAME);
    
    // ContactDao.create(Contact contact) 메서드에서 사용할 SQL
    // insert into CONTACTS (NAME, PHONE, EMAIL) values (?, ?, ?)
    String SQL_INSERT = String.format(
            "insert into %s (%s, %s, %s) values (?, ?, ?)", 
            TBL_CONTACTS, COL_NAME, COL_PHONE, COL_EMAIL);
    
    // ContactDao.update(Contact contact) 메서드에서 사용할 SQL
    // update CONTACTS set NAME = ?, PHONE = ?, EMAIL = ? where CID = ?
    String SQL_UPDATE = String.format(
            "update %s set %s = ?, %s = ?, %s = ? where %s = ?", 
            TBL_CONTACTS, COL_NAME, COL_PHONE, COL_EMAIL, COL_CID);
    
    // ContactDao.delete(Integer cid) 메서드에서 사용할 SQL
    // delete from CONTACTS where CID = ?
    String SQL_DELETE = String.format(
            "delete from %s where %s = ?", 
            TBL_CONTACTS, COL_CID);

}
