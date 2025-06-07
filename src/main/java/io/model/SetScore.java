package io.model;

import io.exception.ValidationExeption;
import lombok.Getter;

import static java.lang.Math.abs;

@Getter
public class SetScore {

    private int scoreFirstPlayer;
    private int scoreSecondPlayer;
    private int tieBreakScoreFirstPlayer;
    private int tieBreakScoreSecondPlayer;

    private SetStatus state = SetStatus.REGULAR;

    private static final int SET_GAMES_TO_WIN = 6;
    private static final int TIE_BREAK_POINT_TO_WIN = 7;
    private static final int MIN_WIN_DIFF = 2;

    public boolean isTieBreak() {
        return state == SetStatus.TIE_BREAK;
    }

    public boolean isSetFinished() {
        return state == SetStatus.FINISHED;
    }

    public boolean setWonBy(boolean first) {
        if (state != SetStatus.REGULAR) {
            throw new ValidationExeption("SetScore","Tie-break is currently active", new Throwable());
        }

        if (first) {
            scoreFirstPlayer++;
        } else {
            scoreSecondPlayer++;
        }

        if (scoreFirstPlayer == SET_GAMES_TO_WIN && scoreSecondPlayer == SET_GAMES_TO_WIN) {
            state = SetStatus.TIE_BREAK;
            return false;
        }

        if ((scoreFirstPlayer >= SET_GAMES_TO_WIN || scoreSecondPlayer >= SET_GAMES_TO_WIN)
                && (abs(scoreFirstPlayer - scoreSecondPlayer) >= MIN_WIN_DIFF)) {
            state = SetStatus.FINISHED;
            return true;
        }

        return false;
    }

    public boolean tieBreak(boolean first) {
        if (state != SetStatus.TIE_BREAK ) {
            throw new ValidationExeption("SetScore", "The tie-break is not active right now", new Throwable());
        }

        if (first) {
            tieBreakScoreFirstPlayer++;
        } else {
            tieBreakScoreSecondPlayer++;
        }

        if ( (tieBreakScoreSecondPlayer >= TIE_BREAK_POINT_TO_WIN || tieBreakScoreFirstPlayer >= TIE_BREAK_POINT_TO_WIN)
                && abs(tieBreakScoreFirstPlayer - tieBreakScoreSecondPlayer) >= MIN_WIN_DIFF) {
            if (first) {
                scoreFirstPlayer = 7;
                scoreSecondPlayer = 6;
            } else {
                scoreFirstPlayer = 6;
                scoreSecondPlayer = 7;
            }

            state = SetStatus.FINISHED;
            return true;
        }

        return false;
    }
}
