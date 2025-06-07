package io.service;

import io.model.MatchStatus;
import io.model.OngoingMatch;
import io.model.SetScore;

public class TennisScoreCalculator {

    private static final int SETS_TO_WIN = 2;

    public boolean pointWonBy(OngoingMatch match, boolean first) {

        if (match.getSetScore().isTieBreak()) {
            boolean tbFinished = match.getSetScore().tieBreak(first);

            if (tbFinished) {
                match.getScores().add(match.getSetScore());
                match.setSetScore(new SetScore());
                match.getCurrentGame().reset();
            }

            return tbFinished && isMatchFinished(match);
        }

        boolean gameFinished = match.getCurrentGame().pointWonBy(first);

        if (gameFinished) {
            boolean setFinished = match.getSetScore().setWonBy(first);

            if (setFinished) {
                match.getScores().add(match.getSetScore());
                match.setSetScore(new SetScore());
                match.getCurrentGame().reset();

                return isMatchFinished(match);
            }
        }

        return false;
    }

    private boolean isMatchFinished(OngoingMatch match) {

        int setFirstPlayer = 0;
        int setSecondPlayer = 0;

        for (SetScore setScore : match.getScores()) {
            if (setScore.getScoreFirstPlayer() > setScore.getScoreSecondPlayer()) {
                setFirstPlayer++;
            } else {
                setSecondPlayer++;
            }
        }

        if (setFirstPlayer >= SETS_TO_WIN || setSecondPlayer >= SETS_TO_WIN) {
            if (setFirstPlayer > setSecondPlayer) {
                match.setWinner(match.getPlayerFirst());
            } else {
                match.setWinner(match.getPlayerSecond());
            }
            match.setStage(MatchStatus.FINISHED);
            return true;
        }

        return false;
    }
}
