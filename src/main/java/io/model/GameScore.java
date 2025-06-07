package io.model;

import lombok.Getter;

import static io.model.Point.*;

@Getter
public class GameScore {

    private Point scoreFirstPlayer = LOVE;
    private Point scoreSecondPlayer = LOVE;

    public boolean pointWonBy(boolean first) {

        if (scoreFirstPlayer.isForty() && scoreSecondPlayer.isForty()) {
            if (first) {
                scoreFirstPlayer = ADVANTAGE;
            } else {
                scoreSecondPlayer = ADVANTAGE;
            }
            return false;
        }

        if (scoreFirstPlayer == ADVANTAGE && !first
                || scoreSecondPlayer == ADVANTAGE && first) {
            scoreFirstPlayer = scoreSecondPlayer = FORTY;
            return false;
        }

        if (scoreFirstPlayer == ADVANTAGE || scoreSecondPlayer == ADVANTAGE) {
            reset();
            return true;
        }

        if (first) {
            if (scoreFirstPlayer.isForty() && scoreSecondPlayer.value < 40) {
                reset();
                return true;
            }
            scoreFirstPlayer = scoreFirstPlayer.next();
        } else {
            if (scoreSecondPlayer.isForty() && scoreFirstPlayer.value < 40) {
                reset();
                return true;
            } else {
                scoreSecondPlayer = scoreSecondPlayer.next();
            }
        }

        return false;
    }

    public void reset() {
        scoreFirstPlayer = scoreSecondPlayer = LOVE;
    }
}
