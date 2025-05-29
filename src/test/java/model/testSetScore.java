package model;


import io.model.SetScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class testSetScore {

    private SetScore setScore;


    @BeforeEach
    void setUp() {
        setScore = new SetScore();
    }

    @Test
    void firstPlayerWin() {
        for (int i = 0; i < 7; i++) {
            setScore.setWonBy(true);
        }

        int score = setScore.getScoreFirstPlayer();

        assertEquals(6, score);
        assertTrue(setScore.isFinished());
    }

    @Test
    void hardGameAndSecondPlayerWin() {
        for (int i = 0; i < 5; i++) {
            setScore.setWonBy(true);
            setScore.setWonBy(false);
        }

        assertEquals(setScore.getScoreSecondPlayer(), 5);
        assertEquals(setScore.getScoreFirstPlayer(), 5);
        assertFalse(setScore.isFinished());

        setScore.setWonBy(false);
        setScore.setWonBy(false);

        assertTrue(setScore.isFinished());
    }


    @Test
    void startTieBreakAndFinish() {
        for (int i = 0; i < 7; i++) {
            setScore.setWonBy(true);
            setScore.setWonBy(false);
        }

        assertFalse(setScore.isFinished());
        assertTrue(setScore.isTieBrake());

        for (int i = 0; i < 8; i++) {
            setScore.tieBreak(true);
            setScore.tieBreak(false);
        }

        assertTrue(setScore.isTieBrake());
        assertFalse(setScore.isFinished());

        setScore.tieBreak(true);
        setScore.tieBreak(true);

        assertFalse(setScore.isTieBrake());
    }

}
