package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameReportErrorModel;
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

@SpringBootTest(classes = GameReportErrorDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameReportErrorDAOTest {
    @Autowired
    GameReportErrorDAO gameReportErrorDAO;

    @Test
    @Transactional
    @Rollback(true)
    public void insert(){
        GameReportErrorModel model = new GameReportErrorModel();
        model.setGRE_Unikey("uniKey");
        model.setGRE_CreateDateTime(new Date());
        model.setGRE_DiffCount(1);
        model.setGRE_GameReportCount(1);
        model.setGRE_DailyTradeCount(1);
        model.setGRE_Time(new Date());
        model.setGRE_DoneTime(new Date());
        model.setLGR_Key("key");
        gameReportErrorDAO.insert(model);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateForReDo(){
        gameReportErrorDAO.updateForReDo("uniKey","key",new Date());
    }

    @Test
    public void checkTimeExist(){
        gameReportErrorDAO.checkTimeExist(new Date());
    }


}
