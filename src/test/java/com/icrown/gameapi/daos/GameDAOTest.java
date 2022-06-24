package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author David
 */
@SpringBootTest(classes = GameDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameDAOTest {

    @Autowired
    GameDAO gameDAO;


    public GameDAOTest() {
    }


    /**
     * Test of findGameListByMerchant method, of class GameDAO.
     */
    @Test
    public void testFindGameListByMerchant() {
        System.out.println("findGameListByMerchant");
        String MCT_GUID = "17b1927a-7b9b-4b02-9845-0e57d73a7b1c";

        gameDAO.findGameListByMerchant(MCT_GUID);
    }

    /**
     * Test of getGameUrl method, of class GameDAO.
     */
    @Test
    public void testGetGameUrlSucceed() {
        String gameCode = "1001";
        SqlRowSet result = gameDAO.getGameUrl(gameCode);
        assertNotNull(result);
    }

    @Test
    public void getGameByGameCode() {
        String gameCode = "1001";
        Optional<GameModel> opt = gameDAO.getGameByGameCode(gameCode);

    }
}
