package io.model;

import lombok.Getter;

import static java.lang.Math.abs;

@Getter
public class SetScore {

    private int scoreFirstPlayer;
    private int scoreSecondPlayer;
    private int tieBreakScoreFirstPlayer;
    private int tieBreakScoreSecondPlayer;
    private boolean isFinished = false;
    private boolean isTieBrake = false;

    private final int SET_GAMES_TO_WIN = 6;
    private final int TIE_BREAK_POINT_TO_WIN = 7;
    private final int MIN_WIN_DIFF = 2;

    public boolean setWonBy(boolean first) {

        if (first) {
            scoreFirstPlayer++;
        } else {
            scoreSecondPlayer++;
        }

        if (scoreFirstPlayer == SET_GAMES_TO_WIN && scoreSecondPlayer == SET_GAMES_TO_WIN) {
            isTieBrake = true;
            return false;
        }

        if ((scoreFirstPlayer >= SET_GAMES_TO_WIN || scoreSecondPlayer >= SET_GAMES_TO_WIN)
                && (abs(scoreFirstPlayer - scoreSecondPlayer) >= MIN_WIN_DIFF)) {
            isFinished = true;
            return true;
        }

        return false;
    }

    public boolean tieBreak(boolean first) {
        if (!isTieBrake) return false;

        if (first) {
            tieBreakScoreFirstPlayer++;
        } else {
            tieBreakScoreSecondPlayer++;
        }

        if ( (tieBreakScoreSecondPlayer >= TIE_BREAK_POINT_TO_WIN || tieBreakScoreFirstPlayer >= TIE_BREAK_POINT_TO_WIN)
                && abs(tieBreakScoreFirstPlayer - tieBreakScoreSecondPlayer) >= MIN_WIN_DIFF) {
            isTieBrake = false;
            resetTieBreak();
            return true;
        }

        return false;
    }

    public void resetTieBreak() {
        tieBreakScoreFirstPlayer = 0;
        tieBreakScoreSecondPlayer = 0;
    }

    private void reset() {
        scoreFirstPlayer = 0;
        scoreSecondPlayer = 0;
        resetTieBreak();
        isFinished = false;
        isTieBrake = false;
    }

}
