package edu.java.contact.ver02;

import java.util.Scanner;

import edu.java.contact.menu.MainMenu;

// MVC 아키텍쳐에서 View(UI)에 해당하는 클래스
public class ContactMain02 {
    
    private Scanner scanner = new Scanner(System.in);
    private ContactDaoImpl dao = ContactDaoImpl.getInstance(); // 컨트롤러(연락처 저장, 검색, 수정) 클래스.

    public static void main(String[] args) {
        System.out.println("***** 연락처 프로그램 Version 0.2 *****");
        
        // static이 아닌 메서드들을 호출하기 위해서 객체를 생성할 필요가 있음.
        ContactMain02 app = new ContactMain02();
        
        boolean run = true; // 프로그램을 계속 실행할 지, 종료할 지를 결정하기 위한 변수.
        while (run) {
            // 메인 메뉴 선택
            int n = app.showMainMenu();
            // 메인 메뉴 숫자를 enum MainMenu 타입으로 변환.
            MainMenu menu = MainMenu.getValue(n);
            
            // switch-case
            switch (menu) {
            case QUIT: // 종료
                run = false;
                break;
            case SELECT_ALL: // 전체리스트
                app.selectAllContacts();
                break;
            case SELECT_BY_INDEX: // 인덱스검색
                app.selectContactByIndex();
                break;
            case CREATE: // 새연락처 추가
                app.insertNewContact();
                break;
            case UPDATE: // 연락처 정보 수정
                app.updateContact();
                break;
            default:
                System.out.println("지원하지 않는 메뉴입니다. 다시 선택하세요.");
            }
            
        }
        
        System.out.println("***** 프로그램 종료 *****");
    }
    
    private void updateContact() {
        System.out.print("수정할 인덱스>> ");
        int index = Integer.parseInt(scanner.nextLine());
        
        if (!dao.isValidIndex(index)) {
            // 인덱스가 유효하지 않으면. (0보다 작거나 또는 저장된 연락처 개수보다 많다면)
            System.out.println("해당 인덱스에는 연락처 정보가 없습니다.");
            return; // 메서드 종료
        }
        
        Contact before = dao.read(index); // controller 메서드 사용해서 수정 전 데이터 읽어옴.
        System.out.println("수정 전: " + before);
        
        System.out.print("수정할 이름 입력>> ");
        String name = scanner.nextLine();
        
        System.out.print("수정할 전화번호 입력>> ");
        String phone = scanner.nextLine();
        
        System.out.print("수정할 이메일 입력>> ");
        String email = scanner.nextLine();
        
        Contact after = new Contact(name, phone, email); // 업데이트할 정보를 가지고 있는 연락처 객체.
        
        int result = dao.update(index, after); // controller 메서드 사용해서 연락처 업데이트.
        if (result == 1) {
            System.out.println("연락처 수정 성공");
        } else {
            System.out.println("연락처 수정 실패");
        }
        
    }

    private void insertNewContact() {
        if (!dao.isMemoryAvailable()) {
            // 배열에 저장할 공간이 없으면. (배열에 저장된 연락처 개수가 배열 최대 길이와 같다면)
            System.out.println("새 연락처를 저장할 공간이 부족합니다.");
            return; // 메서드 종료
        }
        
        System.out.print("이름 입력>> ");
        String name = scanner.nextLine();
        
        System.out.print("전화번호 입력>> ");
        String phone = scanner.nextLine();
        
        System.out.print("이메일 입력>> ");
        String email = scanner.nextLine();
        
        Contact c = new Contact(name, phone, email);
        
        int result = dao.create(c); // controller 메서드 사용해서 배열에 연락처 정보 저장.
        if(result == 1) {
            System.out.println("새 연락처 저장 성공");
        } else {
            System.out.println("새 연락처 저장 실패");
        }
    }

    private void selectContactByIndex() {
        System.out.print("검색할 인덱스>> ");
        int index = Integer.parseInt(scanner.nextLine());
        
        if (!dao.isValidIndex(index)) {
            // 인덱스가 유효하지 않으면. (0보다 작거나 또는 저장된 연락처 개수보다 많다면)
            System.out.println("해당 인덱스에는 연락처 정보가 없습니다.");
            return; // 메서드 종료
        }
        
        Contact c = dao.read(index); // controller 메서드 사용.
        System.out.println(c);
    }

    private void selectAllContacts() {
        Contact[] contacts = dao.read(); // controller의 메서드를 호출.
        
        System.out.println("--- 연락처 리스트 ---");
        for (Contact c : contacts) {
            System.out.println(c);
        }
        System.out.println("---------------------");
        
    }

    private int showMainMenu() {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("[1]전체리스트 [2]인덱스검색 [3]새연락처 [4]수정 [0]종료");
        System.out.println("------------------------------------------------------------");
        System.out.print("메뉴 선택> ");
        
        int n = Integer.parseInt(scanner.nextLine());
        
        return n;
    }
    
}
