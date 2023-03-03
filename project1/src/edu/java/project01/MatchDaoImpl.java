package edu.java.project01;

import static edu.java.project01.OracleConnection.PASSWORD;
import static edu.java.project01.OracleConnection.URL;
import static edu.java.project01.OracleConnection.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleDriver;

import static edu.java.project01.Match.Entity.*;
import static edu.java.project01.MatchSql.*;

public class MatchDaoImpl implements MatchDao {
    
    private static MatchDaoImpl instance = null;
    
    private MatchDaoImpl() {}
    
    public static MatchDaoImpl getInstance() {
        
        if(instance == null) {
            instance = new MatchDaoImpl();
        }
        
        return instance;
        
    }
    
    private Connection getConnection() throws SQLException{
        DriverManager.registerDriver(new OracleDriver());
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private void closeResources(Connection conn, Statement stmt) throws SQLException{
        stmt.close();
        conn.close();
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) throws SQLException{
        rs.close();
        closeResources(conn, stmt);
    }

    @Override
    public List<Match> read() {
        
        List<Match> list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            
            System.out.println(SQL_SELECT_ORDER_BY_NAME);
            stmt = conn.prepareStatement(SQL_SELECT_ORDER_BY_NAME);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer match_no = rs.getInt(COL_MATCH_NO);
                String homeTeam = rs.getString(COL_TEAM1);
                String awayTeam = rs.getString(COL_TEAM2);
                String matchDay = rs.getString(COL_MATCH_DATE);
                String memo = rs.getString(COL_MEMO);
                Match match = new Match(match_no, homeTeam, awayTeam, matchDay, memo);
                list.add(match);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public Match read(Integer match_no) {
        Match match = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            System.out.println(SQL_SELECT_BY_NO);
            stmt = conn.prepareStatement(SQL_SELECT_BY_NO);
            stmt.setInt(1, match_no);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String homeTeam = rs.getString(COL_TEAM1);
                String awayTeam = rs.getString(COL_TEAM2);
                String matchDay = rs.getString(COL_MATCH_DATE);
                String memo = rs.getString(COL_MEMO);
                match = new Match(match_no, homeTeam, awayTeam, matchDay, memo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return match;
    }

    @Override
    public List<Match> read(String keyword) {
        List<Match> list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
     
        try {
            conn = getConnection();
            
            System.out.println(SQL_SELECT_BY_KEYWORD);
            stmt=conn.prepareStatement(SQL_SELECT_BY_KEYWORD);
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            stmt.setString(2, "%" + keyword.toLowerCase() + "%");
            stmt.setString(3, "%" + keyword.toLowerCase() + "%");
            stmt.setString(4, "%" + keyword.toLowerCase() + "%");
            
            rs= stmt.executeQuery();
            while(rs.next()) {
                Integer match_no = rs.getInt(COL_MATCH_NO);
                String homeTeam = rs.getString(COL_TEAM1);
                String awayTeam = rs.getString(COL_TEAM2);
                String matchDay = rs.getString(COL_MATCH_DATE);
                String memo = rs.getString(COL_MEMO);
                Match match = new Match(match_no, homeTeam, awayTeam, matchDay, memo);
                list.add(match);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
        
    }

    @Override
    public int create(Match match) {
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            System.out.println(SQL_CREATE_MATCH);
            stmt = conn.prepareStatement(SQL_CREATE_MATCH);
            stmt.setString(1, match.getHomeTeam());
            stmt.setString(2, match.getAwayTeam());
            stmt.setString(3, match.getMatchDay());
            stmt.setString(4, match.getMemo());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        return result;
    }

    @Override
    public int update(Match match) {
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            System.out.println(SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, match.getHomeTeam());
            stmt.setString(2, match.getAwayTeam());
            stmt.setString(3, match.getMatchDay());
            stmt.setString(4, match.getMemo());
            stmt.setInt(5, match.getMatch_no());
            
            result = stmt.executeUpdate();
                    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

    @Override
    public int delete(Integer match_no) {
        int result = 0;
            
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            System.out.println(SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, match_no);
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

}
