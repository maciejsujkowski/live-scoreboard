package com.sportradar;

import com.sportradar.exception.MatchAlreadyStartedException;
import com.sportradar.exception.MatchNotFoundException;

import java.util.List;

public interface Scoreboard {

    /**
     * Start a new match and add it to the Scoreboard.
     * @param homeTeam home team name
     * @param awayTeam away team name
     * @return a started match
     * @throws MatchAlreadyStartedException if match has already been started on the current Scoreboard
     */
    ScoreboardMatch startNewMatch(String homeTeam, String awayTeam) throws MatchAlreadyStartedException;

    /** Update a score of a match.
     * @param match a match
     * @param homeTeamScore new home team score
     * @param awayTeamScore new away team score
     * @throws MatchNotFoundException if match is not present on the current Scoreboard
     */
    void updateScore(ScoreboardMatch match, int homeTeamScore, int awayTeamScore) throws MatchNotFoundException;

    /**
     * Finish a match.
     * @param match a match
     * @throws MatchNotFoundException if match is not present on the current Scoreboard
     */
    void finishMatch(ScoreboardMatch match) throws MatchNotFoundException;

    /**
     *
     * @return the current Scoreboard summary
     */
    List<ScoreboardMatch> getSummary();

}
