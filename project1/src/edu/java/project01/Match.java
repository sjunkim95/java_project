package edu.java.project01;

public class Match {
    
    public interface Entity{
        String TBL_FIXTURE = "FIXTURES";
        String COL_MATCH_NO = "MATCH_NO";
        String COL_TEAM1 = "TEAM_1";
        String COL_TEAM2 = "TEAM_2";
        String COL_MATCH_DATE = "MATCH_DATE";
        String COL_MEMO = "MEMO";
    }
    
    private Integer match_no;
    private String homeTeam;
    private String awayTeam;
    private String matchDay;
    private String memo;
    
    public Match() {}
    
    public Match(Integer match_no, String homeTeam, String awayTeam, String matchDay, String memo) {
        
        this.match_no = match_no;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDay = matchDay;
        this.memo = memo;
    }

    public Integer getMatch_no() {
        return match_no;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getMatchDay() {
        return matchDay;
    }

    public String getMemo() {
        return memo;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Match(match_no=%d, team1=%s, team2=%s, match_day=%s, user_memo=%s)",
                match_no, homeTeam, awayTeam, matchDay, memo);
    }
    

}
