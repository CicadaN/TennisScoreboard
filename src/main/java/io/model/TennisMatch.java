package io.model;

import io.entity.Player;
import io.exception.ValidationExeption;
import lombok.Getter;

@Getter
public class TennisMatch {

    private final Player player1;
    public final Player player2;
    private int score;
    private boolean gameFinished;

    public TennisMatch(Player player1, Player player2) {
        if (player1 == player2) throw new ValidationExeption("TennisMatch", "The player cannot play with himself", new Throwable());
        this.player1 = player1;
        this.player2 = player2;
    }

}
