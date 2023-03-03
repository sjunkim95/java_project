package edu.java.project01;

import static edu.java.project01.Match.Entity.*;
import static edu.java.project01.User.Entity.*;

public interface MatchSql {
    
    // Match Create 문장
    String SQL_CREATE_MATCH = String.format(
            "insert into %s (%s, %s, %s, %s) values (?, ?, ?, ?)",
            TBL_FIXTURE, COL_TEAM1, COL_TEAM2, COL_MATCH_DATE, COL_MEMO);
    
    // select * from FIXTURES order by NAME
    String SQL_SELECT_ORDER_BY_NAME = String.format(
            "select * from %s order by %s", 
            TBL_FIXTURE, COL_TEAM1);
    
    // read(Integer match_no)
    String SQL_SELECT_BY_NO = String.format("select * from %s where %s = ?", TBL_FIXTURE, COL_MATCH_NO);
    
    // Match Update 문장
    String SQL_UPDATE = String.format(
            "update %s set %s = ?, %s =?, %s = ?, %s =? where %s =?",
            TBL_FIXTURE, COL_TEAM1, COL_TEAM2, COL_MATCH_DATE, COL_MEMO, COL_MATCH_NO);
    
    // Match Delete 문장
    String SQL_DELETE = String.format(
            "delete from %s where %s = ?", 
            TBL_FIXTURE, COL_MATCH_NO);
    
    // USER CREATE 문장
    String SQL_USER_CREATE = String.format(
            "insert into %s (%s, %s) values (?, ?)",
            TBL_USER_INFO, COL_USERNAME, COL_PASSWORD);
    
    // ID 접속 SQL
    String SQL_LOGIN_ID = String.format("select %s from %s where %s = ?", COL_USERNAME, TBL_USER_INFO, COL_USERNAME);
    
    // PW 접속 SQL
    String SQL_LOGIN_PW = String.format("select %s from %s where %s = ?", COL_PASSWORD, TBL_USER_INFO, COL_PASSWORD);
    
    // ID PW 한번에
    String SQL_LOGIN_INFO = String.format(
            "select * from %s where username=? and password=?",
            TBL_USER_INFO, COL_USERNAME, COL_PASSWORD);
    
    // 키워드 검색
    String SQL_SELECT_BY_KEYWORD = String.format(
            "select * from %s where lower(%s) like ? or lower(%s) like ? or lower(%s) like ? or lower(%s) like ? order by %s",
            TBL_FIXTURE, COL_TEAM1, COL_TEAM2, COL_MATCH_DATE, COL_MEMO, COL_TEAM1);
            
            
}
