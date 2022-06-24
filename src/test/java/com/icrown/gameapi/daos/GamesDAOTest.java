package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameReportModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = GameReportDAO.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GamesDAOTest {
    @Autowired
    private
    GameDAO gameDAO;

    @Test
    @Transactional
    @Rollback(true)
    public void changeGameVersion(){
        String gameCode = "99999";
        String version = "12345";
        gameDAO.changeGameVersion(gameCode,version);
    }
}
