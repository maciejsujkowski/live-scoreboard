package com.sportradar;

import com.sportradar.exception.MatchNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


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

        // then
        // TODO: check if match is no longer on the scoreboard summary
    }

    @Test
    @SneakyThrows
    void shouldNotFinishMatch_MatchAlreadyFinished() {
        // given
        scoreboard.finishMatch(match);

        // given
        Executable finishMatchOp = () -> scoreboard.finishMatch(match);

        // then
        Assertions.assertThrows(MatchNotFoundException.class, finishMatchOp);
    }

    @Test
    void shouldNotFinishMatch_MatchNotStarted() {
        // given
        ScoreboardMatch detachedMatch = new ScoreboardMatch("Nigeria", "New Zealand");

        // given
        Executable finishMatchOp = () -> scoreboard.finishMatch(detachedMatch);

        // then
        Assertions.assertThrows(MatchNotFoundException.class, finishMatchOp);
    }

}
