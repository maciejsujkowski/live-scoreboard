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
        Assertions.assertEquals(homeTeam, match.getHomeTeam());
        Assertions.assertEquals(awayTeam, match.getAwayTeam());
        Assertions.assertEquals(0, match.getHomeTeamScore());
        Assertions.assertEquals(0, match.getAwayTeamScore());
        Assertions.assertNotNull(match.getStartedAt());
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    @SneakyThrows
    void shouldNotStartMatch_InvalidTeamName(String homeTeam) {
        // when
        Executable createMatchOp = () -> scoreboard.startNewMatch(homeTeam, "Brazil");

        // then
        Assertions.assertThrows(IllegalArgumentException.class, createMatchOp);
    }

    @Test
    @SneakyThrows
    void shouldNotStartMatch_MatchAlreadyOnTheScoreboard() {
        // given
        ScoreboardMatch match = scoreboard.startNewMatch("Argentina", "Brazil");

        // when
        Executable createDuplicateMatchOp = () -> scoreboard.startNewMatch("Argentina", "Brazil");

        // then
        Assertions.assertThrows(MatchAlreadyStartedException.class, createDuplicateMatchOp);
    }

}
