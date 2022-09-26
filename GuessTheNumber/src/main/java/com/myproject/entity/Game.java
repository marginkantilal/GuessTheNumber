package com.myproject.entity;

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
public class Game {

   private int gameId;
   private String answer;
    private boolean isGameFinished;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId && isGameFinished == game.isGameFinished && answer.equals(game.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, answer, isGameFinished);
    }
}
