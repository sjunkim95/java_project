package edu.java.project01;


import java.util.List;

public interface MatchDao {
    List<Match> read();
    Match read(Integer match_no);
    List<Match> read(String keyword);
    int create(Match match);
    int update(Match match);
    int delete(Integer match_no);
}
