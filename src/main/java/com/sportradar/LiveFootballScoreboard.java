package com.sportradar;

import com.sportradar.exception.MatchAlreadyStartedException;
import com.sportradar.exception.MatchNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LiveFootballScoreboard implements Scoreboard {

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
        match.updateScore(homeTeamScore, awayTeamScore);
    }

    public void finishMatch(ScoreboardMatch match) throws MatchNotFoundException {
        if (!liveMatches.contains(match)) {
            throw new MatchNotFoundException("Match " + match + " not found on the scoreboard!");
        }
        liveMatches.remove(match);
    }

    public List<ScoreboardMatch> getSummary() {
        return new ArrayList<>();
    }

}
