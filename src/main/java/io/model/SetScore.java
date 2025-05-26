package io.model;

import lombok.Getter;

import static java.lang.Math.abs;

@Getter
public class SetScore {

    private int scoreFirstPlayer;
    private int scoreSecondPlayer;
    private int taiBreakScoreFirstPlayer;
    private int taiBreakScoreSecondPlayer;

    private boolean tiebreak;
    private boolean finished;

    public boolean gameWonBy(boolean first) {
        if (finished) return false;

        if (first) scoreFirstPlayer++;
        else scoreSecondPlayer++;

        if (scoreFirstPlayer == 6 && scoreSecondPlayer == 6) {
            tiebreak = true;
            return false;
        }

        if (wonSet()) {
            finished = true;
        }
        return wonSet();
    }

    public boolean wonSet() {
        return (scoreSecondPlayer >= 6 || scoreFirstPlayer >= 6)
                && (abs(scoreFirstPlayer - scoreSecondPlayer) >= 2);
    }

    public boolean tiePointBy(boolean first) {
        if (finished || !tiebreak) return false;

        if (first) taiBreakScoreFirstPlayer++;
        else taiBreakScoreSecondPlayer++;

        if ((taiBreakScoreFirstPlayer >= 7 || taiBreakScoreSecondPlayer >= 7)
                && (abs(taiBreakScoreFirstPlayer - taiBreakScoreSecondPlayer) >= 2)) {
            if (first) scoreFirstPlayer++;
            else scoreSecondPlayer++;
            tiebreak = false;
            finished = true;
            taiBreakScoreFirstPlayer = taiBreakScoreSecondPlayer = 0;
            return true;
        }
        return false;
    }

}
