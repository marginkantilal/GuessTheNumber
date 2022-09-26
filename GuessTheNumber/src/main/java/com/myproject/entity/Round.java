package com.myproject.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Round {

    private int roundId;
    private int gameId;
    private String guess;
    int numberOfMatch;
    int numberOfPartialMatch;
    LocalDateTime guessTime;
    private String result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return roundId == round.roundId && gameId == round.gameId && numberOfMatch == round.numberOfMatch && numberOfPartialMatch == round.numberOfPartialMatch && Objects.equals(guess, round.guess) && Objects.equals(guessTime, round.guessTime) && Objects.equals(result, round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundId, gameId, guess, numberOfMatch, numberOfPartialMatch, guessTime, result);
    }
    
    // // in form of e:0:p:0
    public void parseResult() {
        String[] tokens = this.result.split(":");
        this.setNumberOfMatch(Integer.parseInt(tokens[1]));
        this.setNumberOfPartialMatch(Integer.parseInt(tokens[3]));
    }

}

