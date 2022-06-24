package com.icrown.gameapi.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icrown.gameapi.models.GameSettingModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = GameSettingDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameSettingDAOTest {
    @Autowired
    GameSettingDAO gameSettingDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    @Rollback(true)
    void addGameSetting() {
        GameSettingModel model = new GameSettingModel();
        model.setAGT_GUID("agtGuid");
        model.setSYS_GameSetting_Key("key");
        model.setSYS_GameSetting_Value("{}");
        model.setSYS_GameSetting_Note("node");
        gameSettingDAO.addGameSettingKeyAndValue(model);
    }

}
