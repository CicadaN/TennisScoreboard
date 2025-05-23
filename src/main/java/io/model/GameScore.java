package io.model;

import static io.model.Point.LOVE;


public class GameScore {

    private Point scoreFirstPlayer = LOVE;
    private Point scoreSecondPlayer = LOVE;


    public boolean pointWonBy(boolean first) {
        Point winner = first ? scoreFirstPlayer : scoreSecondPlayer;
        Point loser = first ? scoreSecondPlayer : scoreFirstPlayer;

        if (winner.isForty() && loser.value < 40) return true;

        if (scoreFirstPlayer.isForty() && scoreSecondPlayer.isForty()) {
            if (first) {
                scoreFirstPlayer = Point.ADVANTAGE;
            } else {
                scoreSecondPlayer = Point.ADVANTAGE;
            }
            return false;
        }

        if (winner.isAdvantage()) return true;

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
