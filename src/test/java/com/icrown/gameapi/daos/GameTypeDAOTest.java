package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = GameTypeDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameTypeDAOTest {
    @Autowired
    GameTypeDAO gameTypeDAO;
    @Test
    public void getAllGameType(){
        gameTypeDAO.getAllGameType();
    }
}
