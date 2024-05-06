package com.sportradar;

import com.sportradar.exception.MatchAlreadyStartedException;
import com.sportradar.exception.MatchNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface Scoreboard {

    ScoreboardMatch startNewMatch(String homeTeam, String awayTeam) throws MatchAlreadyStartedException;

    void updateScore(ScoreboardMatch match, int homeTeamScore, int awayTeamScore) throws MatchNotFoundException;

    void finishMatch(ScoreboardMatch match) throws MatchNotFoundException;

    List<ScoreboardMatch> getSummary();

}
