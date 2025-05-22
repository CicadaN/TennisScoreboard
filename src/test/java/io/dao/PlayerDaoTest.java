package io.dao;

import io.entity.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerDaoTest {

    private PlayerDao playerDao;

    @BeforeAll
    void init() {
        playerDao = new PlayerDao();
    }

    @Test
    void saveAndFindById() {
        Player serena = playerDao.findByNameExact("Serena Williams");

        int id = serena.getId();
        Player fromDb = playerDao.findById(id);

        assertNotNull(fromDb);
        assertEquals("Serena Williams", fromDb.getName());
    }

    @Test
    void deleteRemovesRow() {
        Player temp = new Player("Temp User");
        playerDao.save(temp);
        int id = temp.getId();

        playerDao.delete(temp);

        Player shouldBeNull = playerDao.findById(id);
        assertNull(shouldBeNull);
    }
}
