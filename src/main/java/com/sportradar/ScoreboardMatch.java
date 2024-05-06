package com.sportradar;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@EqualsAndHashCode(of = {"homeTeam", "awayTeam"})
public class ScoreboardMatch {

    private String homeTeam;
    private String awayTeam;
    @Setter
    private int homeTeamScore;
    @Setter
    private int awayTeamScore;
    private LocalDateTime startedAt;

    ScoreboardMatch(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be null, blank or empty.");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.startedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("%s %s:%s %s-%s", startedAt.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME), homeTeamScore, awayTeamScore, homeTeam, awayTeam);
    }

}
