package com.sportradar;

import com.sportradar.exception.MatchAlreadyStartedException;
import com.sportradar.exception.MatchNotFoundException;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Scoreboard {

    private Set<ScoreboardMatch> liveMatches = ConcurrentHashMap.newKeySet();

    public ScoreboardMatch startNewMatch(String homeTeam, String awayTeam) throws MatchAlreadyStartedException {
        ScoreboardMatch scoreboardMatch = new ScoreboardMatch(homeTeam, awayTeam);
        if (liveMatches.contains(scoreboardMatch)) {
            throw new MatchAlreadyStartedException(String.format("Match %s-%s is already on the scoreboard.", homeTeam, awayTeam));
        }
        liveMatches.add(scoreboardMatch);
        return scoreboardMatch;
    }

    public void updateScore(ScoreboardMatch match, int homeTeamScore, int awayTeamScore) throws MatchNotFoundException {
        if (!liveMatches.contains(match)) {
            throw new MatchNotFoundException("Match " + match + " not found on the scoreboard!");
        }
        if (homeTeamScore == match.getHomeTeamScore() && awayTeamScore == match.getAwayTeamScore()) {
            throw new IllegalArgumentException("Nothing to update for match " + match);
        }
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException(String.format("Cannot update match %s to %s:%s", match, homeTeamScore, awayTeamScore));
        }
        match.setHomeTeamScore(homeTeamScore);
        match.setAwayTeamScore(awayTeamScore);
    }

    public void finishMatch(ScoreboardMatch match) {

    }

    String getSummary() {
        return "";
    }

}
