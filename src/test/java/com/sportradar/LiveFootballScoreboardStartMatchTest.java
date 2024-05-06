package com.sportradar;

import com.sportradar.exception.MatchAlreadyStartedException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LiveFootballScoreboardStartMatchTest {

    private LiveFootballScoreboard scoreboard;

    @BeforeEach
    void setupEach() {
        scoreboard = new LiveFootballScoreboard();
    }

    @ParameterizedTest
    @CsvSource({"Poland,England", "Kazakhstan,Turks & Caicos"})
    @SneakyThrows
    void shouldStartMatch(String homeTeam, String awayTeam) {
        // when
        ScoreboardMatch match = scoreboard.startNewMatch(homeTeam, awayTeam);

        // then
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(0, match.getHomeTeamScore());
        assertEquals(0, match.getAwayTeamScore());
        assertNotNull(match.getStartedAt());
        List<ScoreboardMatch> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());
        assertTrue(summary.contains(match));
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    @SneakyThrows
    void shouldNotStartMatch_InvalidTeamName(String homeTeam) {
        // when
        Executable createMatchOp = () -> scoreboard.startNewMatch(homeTeam, "Brazil");

        // then
        assertThrows(IllegalArgumentException.class, createMatchOp);
        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    @SneakyThrows
    void shouldNotStartMatch_MatchAlreadyOnTheScoreboard() {
        // given
        ScoreboardMatch match = scoreboard.startNewMatch("Argentina", "Brazil");

        // when
        Executable createDuplicateMatchOp = () -> scoreboard.startNewMatch("Argentina", "Brazil");

        // then
        assertThrows(MatchAlreadyStartedException.class, createDuplicateMatchOp);
        List<ScoreboardMatch> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());
        assertTrue(summary.contains(match));
    }

}
