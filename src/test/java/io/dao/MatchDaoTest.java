package io.dao;

import io.entity.Match;
import io.entity.Player;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchDaoTest {

    private PlayerDao playerDao;
    private MatchDao matchDao;

    @BeforeAll
    void setUp() {
        playerDao = new PlayerDao();
        matchDao = new MatchDao();
    }

    @Test
    void findByPlayerNameReturnsProperMatches() {
        Player serena = playerDao.findByNameExact("Serena Williams");
        Player venus = playerDao.findByNameExact("Venus Williams");

        Match sistersFinal = Match.builder()
                .player1(serena)
                .player2(venus)
                .winner(serena)
                .build();

        matchDao.save(sistersFinal);

        List<Match> result = matchDao.findByPlayerName("Venus");

        assertFalse(result.isEmpty(), "Матч должен быть найден");
        assertEquals("Venus Williams", result.get(0).getPlayer2().getName());
    }


    @Test
    void findWinnerPlayer() {
        Player Aryna = playerDao.findByNameExact("Aryna Sabalenka");
        Player Sharapova = playerDao.findByNameExact("Maria Sharapova");
        Player Anna = playerDao.findByNameExact("Anna Kournikova");


        Match firstMatch = Match.builder()
                .player1(Aryna)
                .player2(Sharapova)
                .winner(Sharapova)
                .build();
        matchDao.save(firstMatch);

        Match secondMatch = Match.builder()
                .player1(Anna)
                .player2(Sharapova)
                .winner(Sharapova)
                .build();
        matchDao.save(secondMatch);

        List<Match> SharapovaWinningMathes = matchDao.findByWinner(Sharapova);
        List<Match> result = List.of(firstMatch, secondMatch);

        assertEquals(SharapovaWinningMathes, result);
    }
}
