package model;

import io.model.GameScore;
import io.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class testGameScore {

    private GameScore gameScore;

    @BeforeEach
    void setUp() {
        gameScore = new GameScore();
    }

    @Test
    void startGame() {
        assertEquals(Point.LOVE, gameScore.getScoreFirstPlayer());
        assertEquals(Point.LOVE, gameScore.getScoreSecondPlayer());
        assertFalse(gameScore.isFinished());
    }

    @Test
    void firstPlayerWin() {

        gameScore.pointWonBy(true);
        assertEquals(Point.FIFTEEN, gameScore.getScoreFirstPlayer());

        gameScore.pointWonBy(true);
        assertEquals(Point.THIRTY, gameScore.getScoreFirstPlayer());

        gameScore.pointWonBy(true);
        assertEquals(Point.FORTY, gameScore.getScoreFirstPlayer());

        boolean gameFinished = gameScore.pointWonBy(true);
        assertTrue(gameFinished);
        assertEquals(Point.LOVE, gameScore.getScoreSecondPlayer());
        assertEquals(Point.LOVE, gameScore.getScoreFirstPlayer());

    }

    @Test
    void advantageAndDeuce() {
        for (int i = 0; i < 3; i++) {
            gameScore.pointWonBy(true);
            gameScore.pointWonBy(false);
        }
        assertEquals(Point.FORTY, gameScore.getScoreFirstPlayer());
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        // First player получает Advantage
        boolean finished = gameScore.pointWonBy(true);
        assertFalse(finished);
        assertEquals(Point.ADVANTAGE, gameScore.getScoreFirstPlayer());
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        // Второй игрок отыгрывает Advantage (снова 40-40)
        finished = gameScore.pointWonBy(false);
        assertFalse(finished);
        assertEquals(Point.FORTY, gameScore.getScoreFirstPlayer());
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        finished = gameScore.pointWonBy(true);
        assertFalse(finished);
        assertEquals(Point.ADVANTAGE, gameScore.getScoreFirstPlayer());
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        finished = gameScore.pointWonBy(false);
        assertFalse(finished);
        assertEquals(Point.FORTY, gameScore.getScoreFirstPlayer());
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        finished = gameScore.pointWonBy(true);
        assertFalse(finished);
        assertEquals(Point.ADVANTAGE, gameScore.getScoreFirstPlayer());
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        finished = gameScore.pointWonBy(true);
        assertTrue(finished);

    }

    @Test
    void longAndHardGame() {

        gameScore.pointWonBy(true);
        assertEquals(Point.FIFTEEN, gameScore.getScoreFirstPlayer());

        gameScore.pointWonBy(true);
        assertEquals(Point.THIRTY, gameScore.getScoreFirstPlayer());

        gameScore.pointWonBy(false);
        assertEquals(Point.FIFTEEN, gameScore.getScoreSecondPlayer());

        gameScore.pointWonBy(false);
        assertEquals(Point.THIRTY, gameScore.getScoreSecondPlayer());

        gameScore.pointWonBy(false);
        assertEquals(Point.FORTY, gameScore.getScoreSecondPlayer());

        boolean finished = gameScore.pointWonBy(false);
        assertTrue(finished);

        assertEquals(Point.LOVE, gameScore.getScoreSecondPlayer());
        assertEquals(Point.LOVE, gameScore.getScoreFirstPlayer());

    }
}
