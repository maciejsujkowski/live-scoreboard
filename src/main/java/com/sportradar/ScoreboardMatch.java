package com.sportradar;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"homeTeam", "awayTeam"})
public class ScoreboardMatch {

    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
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

}
