package io.dao;

import io.entity.Match;
import io.entity.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MatchDao extends AbstractHibernateDAO<Match> {

    public MatchDao() {
        super(Match.class);
    }

    public List<Match> findPage(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("The page number and size must be positive.");
        }
        return super.findAll(page, size);
    }

    public List<Match> findByPlayerName(String name) {
        return execute(session ->
                session.createQuery(
                        "FROM Match m WHERE m.player1.name LIKE :name OR m.player2.name LIKE :name",
                        Match.class
                ).setParameter("name", "%" + name + "%").getResultList()
        );
    }

    public List<Match> findByPlayerName(String name, int page, int size) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name");
        return execute(session ->
                session.createQuery("""
                    FROM Match m
                    WHERE lower(m.player1.name) LIKE :n
                       OR lower(m.player2.name) LIKE :n
                    """, Match.class)
                        .setParameter("n", "%" + name.toLowerCase() + "%")
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList());
    }

    public List<Match> findByWinner(Player winner) {
        return execute(session ->
                session.createQuery(
                        "FROM Match m WHERE m.winner = :winner",
                        Match.class
                ).setParameter("winner", winner).getResultList()
        );
    }

    public List<Match> findMatchesBetweenPlayers(Player player1, Player player2) {
        return execute(session ->
                session.createQuery(
                                "FROM Match m WHERE (m.player1 = :p1 AND m.player2 = :p2) OR (m.player1 = :p2 AND m.player2 = :p1)",
                                Match.class
                        )
                        .setParameter("p1", player1)
                        .setParameter("p2", player2)
                        .getResultList()
        );
    }

    public long countMatchesByPlayer(Player player) {
        return execute(session ->
                session.createQuery(
                        "SELECT COUNT(*) FROM Match m WHERE m.player1 = :player OR m.player2 = :player",
                        Long.class
                ).setParameter("player", player).getSingleResult()
        );
    }

    public long countWinsByPlayer(Player player) {
        return execute(session ->
                session.createQuery(
                        "SELECT COUNT(*) FROM Match m WHERE m.winner = :player",
                        Long.class
                ).setParameter("player", player).getSingleResult()
        );
    }
}
