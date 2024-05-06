package com.sportradar;

import com.sportradar.exception.MatchNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class ScoreboardUpdateScoreTest {

    private Scoreboard scoreboard;
    private ScoreboardMatch match;

    @BeforeEach
    @SneakyThrows
    void setupEach() {
        scoreboard = new Scoreboard();
        match = scoreboard.startNewMatch("Germany", "France");
    }

    @Test
    @SneakyThrows
    void shouldUpdateScore() {
        // when
        scoreboard.updateScore(match, 1, 0);

        // then
        Assertions.assertEquals(1, match.getHomeTeamScore());
        Assertions.assertEquals(0, match.getAwayTeamScore());
    }

    @Test
    @SneakyThrows
    void shouldUpdateScoreTwice() {
        // when
        scoreboard.updateScore(match, 1, 0);
        scoreboard.updateScore(match, 1, 1);

        // then
        Assertions.assertEquals(1, match.getHomeTeamScore());
        Assertions.assertEquals(1, match.getAwayTeamScore());
    }

    @Test
    @SneakyThrows
    // e.g. when a goal has been disallowed by VAR
    void shouldAllowToDecreaseScore() {
        // when
        scoreboard.updateScore(match, 4, 5);
        scoreboard.updateScore(match, 4, 4);

        // then
        Assertions.assertEquals(4, match.getHomeTeamScore());
        Assertions.assertEquals(4, match.getAwayTeamScore());
    }

    @ParameterizedTest
    @CsvSource({"0,0", "-1,0"})
    void shouldNotAllowToUpdateScore(int homeTeamScore, int awayTeamScore) {
        // when
        Executable updateScoreOp = () -> scoreboard.updateScore(match, homeTeamScore, awayTeamScore);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, updateScoreOp);
    }

    @Test
    void shouldNotAllowToUpdateScore_MatchNotFoundOnTheScoreboard() {
        // given
        ScoreboardMatch detachedMatch = new ScoreboardMatch("Latvia", "Zanzibar");
        // when
        Executable updateScoreOp = () -> scoreboard.updateScore(detachedMatch, 1, 0);

        // then
        Assertions.assertThrows(MatchNotFoundException.class, updateScoreOp);
    }

}
