package com.sportradar;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LiveFootballScoreboardSummaryTest {

    private LiveFootballScoreboard scoreboard;
    private ScoreboardMatch first;

    @BeforeEach
    @SneakyThrows
    void setupEach() {
        scoreboard = new LiveFootballScoreboard();
        first = scoreboard.startNewMatch("Germany", "France");
    }

    @Test
    @SneakyThrows
    void shouldGenerateSummary_MostGoalsMatchFirst() {
        // given
        ScoreboardMatch second = scoreboard.startNewMatch("Norway", "Portugal");
        first.updateScore(1, 0);

        // when
        List<ScoreboardMatch> summary = scoreboard.getSummary();

        // then
        assertEquals(summary.size(), 2);
        assertEquals(first, summary.get(0));
        assertEquals(second, summary.get(1));
    }

    @Test
    @SneakyThrows
    void shouldGenerateSummary_RecentlyStartedMatchFirst() {
        // given
        ScoreboardMatch second = scoreboard.startNewMatch("Norway", "Portugal");

        // when
        List<ScoreboardMatch> summary = scoreboard.getSummary();

        // then
        assertEquals(summary.size(), 2);
        assertEquals(second, summary.get(0));
        assertEquals(first, summary.get(1));
    }

    @Test
    @SneakyThrows
    void shouldGenerateSummary() {
        // given
        ScoreboardMatch polandVsCzechRepublic = scoreboard.startNewMatch("Poland", "Czech Republic");
        ScoreboardMatch netherlandsVsSpain = scoreboard.startNewMatch("Netherlands", "Spain");
        ScoreboardMatch belgiumVsWales = scoreboard.startNewMatch("Belgium", "Wales");
        ScoreboardMatch denmarkVsItaly = scoreboard.startNewMatch("Denmark", "Italy");

        // when
        scoreboard.updateScore(polandVsCzechRepublic, 1, 0);
        scoreboard.updateScore(belgiumVsWales, 0, 1);
        scoreboard.updateScore(polandVsCzechRepublic, 1, 1);
        scoreboard.updateScore(polandVsCzechRepublic, 2, 1);
        scoreboard.updateScore(polandVsCzechRepublic, 3, 1);
        scoreboard.updateScore(denmarkVsItaly, 0, 1);
        scoreboard.finishMatch(netherlandsVsSpain);
        scoreboard.finishMatch(first);

        // then
        List<ScoreboardMatch> summary = scoreboard.getSummary();
        assertEquals(3, summary.size());
        assertEquals(polandVsCzechRepublic, summary.get(0));
        assertEquals(denmarkVsItaly, summary.get(1));
        assertEquals(belgiumVsWales, summary.get(2));
    }

}
