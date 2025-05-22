package io.dao;

import io.entity.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PlayerDao extends AbstractHibernateDAO<Player> {

    public PlayerDao() {
        super(Player.class);
    }

    public List<Player> findByName(String name) {
        return execute(session ->
                session.createQuery(
                        "FROM Player p WHERE p.name LIKE :name",
                        Player.class
                ).setParameter("name", "%" + name + "%").getResultList()
        );
    }

    public List<Player> findTopPlayers(int limit) {
        return execute(session ->
                session.createQuery(
                        "SELECT p FROM Player p " +
                        "LEFT JOIN Match m ON p = m.winner " +
                        "GROUP BY p " +
                        "ORDER BY COUNT(m) DESC",
                        Player.class
                ).setMaxResults(limit).getResultList()
        );
    }

    public Player findByNameExact(String name) {
        return execute(session ->
                session.createQuery(
                        "FROM Player p WHERE p.name = :name",
                        Player.class
                ).setParameter("name", name).uniqueResult()
        );
    }
}
