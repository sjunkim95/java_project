package edu.java.contact.ver02;

// MVC 아키텍쳐에서 Controller의 인터페이스 - 메서드 선언
public interface ContactDao {
    
    /**
     * 연락처 검색 기능.
     * 
     * @return 저장된 연락처 개수 길이(크기)의 연락처 배열(Contact[])을 리턴.
     */
    Contact[] read();
    
    /**
     * 인덱스 검색 기능.
     * 
     * @param index 연락처를 검색에 사용할 인덱스. 0 이상의 정수.
     * @return 배열에서 해당 인덱스에 저장된 Contact 객체 또는 null.
     */
    Contact read(int index);
    
    /**
     * 연락처 정보 저장 기능.
     * 
     * @param c 배열에 저장할 Contact 객체.
     * @return 배열에 저장 성공하면 1, 그렇지 않으면 0.
     */
    int create(Contact c);
    
    /**
     * 연락처 정보 업데이트 기능.
     * 
     * @param index 업데이트하려는 연락처의 인덱스. 0 이상 저장된 연락처 개수보다 작은 정수.
     * @param c 업데이트하려는 연락처 정보가 저장된 Contact 객체.
     * @return 업데이트 성공하면 1, 그렇지 않으면 0.
     */
    int update(int index, Contact c);
    // int update(int index, String newName, String newPhone, String newEmail);

}
