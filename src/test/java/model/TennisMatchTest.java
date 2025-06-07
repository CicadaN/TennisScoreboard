package model;

import io.entity.Player;
import io.model.OngoingMatch;
import io.model.Point;
import io.service.OngoingMatchesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TennisMatchTest {

    UUID uuid;
    OngoingMatch tennisMatch;
    Player firstPlayer = new Player("Ivan");
    Player secondPlayer = new Player("Step");

    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @BeforeEach
    void setUp() {
        uuid = ongoingMatchesService.startMatch(firstPlayer, secondPlayer);
        tennisMatch = ongoingMatchesService.get(uuid);
    }

    @Test
    void startGame() {
        assertEquals(Point.LOVE, tennisMatch.getCurrentGame().getScoreFirstPlayer());
        assertEquals(Point.LOVE, tennisMatch.getCurrentGame().getScoreFirstPlayer());
    }

    @Test
    void firstPlayerWonFirstSet() {

        for (int i = 0; i < 3; i++) {
            ongoingMatchesService.Point(uuid, true);
        }

        assertEquals(Point.FORTY, tennisMatch.getCurrentGame().getScoreFirstPlayer());

        ongoingMatchesService.Point(uuid, true);

        assertEquals(1, tennisMatch.getSetScore().getScoreFirstPlayer());
        assertEquals(0, tennisMatch.getSetScore().getScoreSecondPlayer());

        for (int i = 0; i < 4; i++) {
            ongoingMatchesService.Point(uuid, true);
        }

        assertEquals(2, tennisMatch.getSetScore().getScoreFirstPlayer());
        assertEquals(0, tennisMatch.getSetScore().getScoreSecondPlayer());

        for (int i = 0; i < 4; i++) {
            ongoingMatchesService.Point(uuid, true);
        }

        assertEquals(3, tennisMatch.getSetScore().getScoreFirstPlayer());
        assertEquals(0, tennisMatch.getSetScore().getScoreSecondPlayer());

        for (int i = 0; i < 4; i++) {
            ongoingMatchesService.Point(uuid, true);
        }

        assertEquals(4, tennisMatch.getSetScore().getScoreFirstPlayer());
        assertEquals(0, tennisMatch.getSetScore().getScoreSecondPlayer());

        for (int i = 0; i < 4; i++) {
            ongoingMatchesService.Point(uuid, true);
        }

        assertEquals(5, tennisMatch.getSetScore().getScoreFirstPlayer());
        assertEquals(0, tennisMatch.getSetScore().getScoreSecondPlayer());

        for (int i = 0; i < 4; i++) {
            ongoingMatchesService.Point(uuid, true);
        }

        assertEquals(0, tennisMatch.getSetScore().getScoreFirstPlayer());
        assertEquals(0, tennisMatch.getSetScore().getScoreSecondPlayer());


        assertEquals(1, tennisMatch.getScores().size());


        System.out.println("Debug information:");
        System.out.println("Current set score: " + tennisMatch.getSetScore().getScoreFirstPlayer() + "-" + tennisMatch.getSetScore().getScoreSecondPlayer());
        System.out.println("Number of completed sets: " + tennisMatch.getScores().size());
        System.out.println("Winner: " + tennisMatch.getWinner());
        System.out.println("First player: " + tennisMatch.getPlayerFirst());
        System.out.println("Second player: " + tennisMatch.getPlayerSecond());
        System.out.println("Match stage: " + tennisMatch.getStage());

    }

    @Test
    void SecondPlayerWonMatch() {

        for (int i = 0; i < 24; i++) {
            ongoingMatchesService.Point(uuid, false);
        }

        assertEquals(1, tennisMatch.getScores().size());

        for (int i = 0; i < 24; i++) {
            ongoingMatchesService.Point(uuid, false);
        }

        assertEquals(2, tennisMatch.getScores().size());
        assertEquals(tennisMatch.playerSecond, tennisMatch.getWinner());


    }

    @Test
    void tieBreakAndSecondPlayerWinsMatch() {
        for (int i = 0; i < 6; i++) {
            winGame(false);
            winGame(true);
        }
        assertTrue(tennisMatch.getSetScore().isTieBreak());

        for (int i = 0; i < 5; i++) ongoingMatchesService.Point(uuid, false);
        for (int i = 0; i < 5; i++) ongoingMatchesService.Point(uuid, true);
        ongoingMatchesService.Point(uuid, false);
        ongoingMatchesService.Point(uuid, false);

        assertEquals(1, tennisMatch.getScores().size());

        for (int i = 0; i < 6; i++) winGame(false);

        assertEquals(tennisMatch.getPlayerSecond(), tennisMatch.getWinner());
    }




    private void winGame(boolean first) {
        for (int p = 0; p < 4; p++) ongoingMatchesService.Point(uuid, first);
    }

    private void winSet(int gamesFirst, int gamesSecond, boolean firstLeads) {
        for (int i = 0; i < gamesFirst;  i++) winGame(firstLeads);
        for (int i = 0; i < gamesSecond; i++) winGame(!firstLeads);
    }

    private void winTieBreak(int ptsFirst, int ptsSecond, boolean firstLeads) {
        int total = ptsFirst + ptsSecond;
        for (int i = 0; i < total; i++) ongoingMatchesService.Point(uuid, firstLeads);
    }

}
