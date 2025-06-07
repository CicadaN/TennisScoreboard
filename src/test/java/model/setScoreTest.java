package model;


import io.model.SetScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class setScoreTest {

    private SetScore setScore;

    @BeforeEach
    void setUp() {
        setScore = new SetScore();
    }

    @Test
    void firstPlayerWin() {
        for (int i = 0; i < 6; i++) {
            setScore.setWonBy(true);
        }

        int score = setScore.getScoreFirstPlayer();

        assertEquals(6, score);
        assertTrue(setScore.isSetFinished());
    }

    @Test
    void hardGameAndSecondPlayerWin() {
        for (int i = 0; i < 5; i++) {
            setScore.setWonBy(true);
            setScore.setWonBy(false);
        }

        assertEquals(setScore.getScoreSecondPlayer(), 5);
        assertEquals(setScore.getScoreFirstPlayer(), 5);
        assertFalse(setScore.isSetFinished());

        setScore.setWonBy(false);
        setScore.setWonBy(false);

        assertTrue(setScore.isSetFinished());
    }


    @Test
    void startTieBreakAndFinish() {
        for (int i = 0; i < 6; i++) {
            setScore.setWonBy(true);
            setScore.setWonBy(false);
        }

        assertTrue(setScore.isTieBreak());
        assertFalse(setScore.isSetFinished());

        for (int i = 0; i < 7; i++) {
            setScore.tieBreak(true);
            setScore.tieBreak(false);
        }

        assertTrue(setScore.isTieBreak());
        assertFalse(setScore.isSetFinished());

        setScore.tieBreak(true);
        setScore.tieBreak(true);

        assertFalse(setScore.isTieBreak());
    }

}
