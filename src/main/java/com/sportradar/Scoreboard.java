package com.sportradar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scoreboard {

    Map<String, Match> liveMatchesMap = new ConcurrentHashMap<>();

    Match startNewMatch(String homeTeam, String awayTeam) {
        return null;
    }

    void finishMatch(Match match) {

    }

    void updateScore(Match match, int homeTeamScore, int awayTeamScore) {

    }

    String getSummary() {
        return "";
    }

}
