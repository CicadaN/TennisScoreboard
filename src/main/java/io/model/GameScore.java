package io.model;

import lombok.Getter;

import static io.model.Point.LOVE;

@Getter
public class GameScore {

    private Point scoreFirstPlayer = LOVE;
    private Point scoreSecondPlayer = LOVE;
    private boolean isFinished = false;


    public boolean pointWonBy(boolean first) {
        Point winner = first ? scoreFirstPlayer : scoreSecondPlayer;
        Point loser = first ? scoreSecondPlayer : scoreFirstPlayer;

        if (winner.isForty() && loser.value < 40) {
            isFinished = true;
            reset();
            return true;
        }

        if (winner.isAdvantage()) {
            isFinished = true;
            reset();
            return true;
        }

        if (scoreFirstPlayer.isForty() && scoreSecondPlayer.isForty()) {
            if (first) {
                scoreFirstPlayer = Point.ADVANTAGE;
            } else {
                scoreSecondPlayer = Point.ADVANTAGE;
            }
            return false;
        }

        if (loser.isAdvantage()) {
            scoreFirstPlayer = scoreSecondPlayer = Point.FORTY;
            return false;
        }

        if (first) {
            scoreFirstPlayer = scoreFirstPlayer.next();
        } else {
            scoreSecondPlayer = scoreSecondPlayer.next();
        }
        return false;
    }

    public void reset() {
        scoreFirstPlayer = scoreSecondPlayer = LOVE;
    }
}
