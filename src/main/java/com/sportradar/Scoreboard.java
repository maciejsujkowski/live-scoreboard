package com.sportradar;

import com.sportradar.exception.MatchAlreadyStartedException;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Scoreboard {

    private Set<ScoreboardMatch> liveMatches = ConcurrentHashMap.newKeySet();

    ScoreboardMatch startNewMatch(String homeTeam, String awayTeam) throws MatchAlreadyStartedException {
        ScoreboardMatch scoreboardMatch = new ScoreboardMatch(homeTeam, awayTeam);
        if (liveMatches.contains(scoreboardMatch)) {
            throw new MatchAlreadyStartedException(String.format("Match %s-%s is already on the scoreboard.", homeTeam, awayTeam));
        }
        liveMatches.add(scoreboardMatch);
        return scoreboardMatch;
    }

    void finishMatch(ScoreboardMatch scoreboardMatch) {

    }

    void updateScore(ScoreboardMatch scoreboardMatch, int homeTeamScore, int awayTeamScore) {

    }

    String getSummary() {
        return "";
    }

}
