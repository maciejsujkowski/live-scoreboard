package com.sportradar;

import com.sportradar.exception.MatchNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LiveFootballScoreboardFinishMatchTest {

    private LiveFootballScoreboard scoreboard;
    private ScoreboardMatch match;

    @BeforeEach
    @SneakyThrows
    void setupEach() {
        scoreboard = new LiveFootballScoreboard();
        match = scoreboard.startNewMatch("Germany", "France");
    }

    @Test
    @SneakyThrows
    void shouldFinishMatch() {
        // when
        scoreboard.finishMatch(match);
        List<ScoreboardMatch> summary = scoreboard.getSummary();

        // then
        assertEquals(0, summary.size());
    }

    @Test
    @SneakyThrows
    void shouldNotFinishMatch_MatchAlreadyFinished() {
        // given
        scoreboard.finishMatch(match);

        // given
        Executable finishMatchOp = () -> scoreboard.finishMatch(match);

        // then
        assertThrows(MatchNotFoundException.class, finishMatchOp);
        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    void shouldNotFinishMatch_MatchNotStarted() {
        // given
        ScoreboardMatch detachedMatch = new ScoreboardMatch("Nigeria", "New Zealand");

        // given
        Executable finishMatchOp = () -> scoreboard.finishMatch(detachedMatch);

        // then
        assertThrows(MatchNotFoundException.class, finishMatchOp);
        List<ScoreboardMatch> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());
        assertTrue(summary.contains(match));
    }

}
