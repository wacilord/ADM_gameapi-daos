package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.LogGameReportModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest(classes = LogGameReportDAOTest.class)
@ComponentScan(basePackages = "com.icrown")
@EnableAutoConfiguration
public class LogGameReportDAOTest {
    @Autowired
    LogGameReportDAO logGameReportDAO;

    @Test
    @Transactional
    @Rollback(true)
    public void insertData(){
        LogGameReportModel model = new LogGameReportModel();
        model.setLGR_Count(1);
        model.setLGR_DoneTime(new Date());
        model.setLGR_EndTime(new Date());
        model.setLGR_StartTime(new Date());
        model.setLGR_AgentID("");
        model.setLGR_Type(0);
        model.setLGR_ExecuteTime(new Date());
        model.setLGR_Executor("ABC");
        model.setLGR_UsedTime(100);
        model.setLGR_AfterDailyTradeCount(1);
        model.setLGR_AfterGameReportCount(1);
        model.setLGR_AfterDiffCount(0);
        logGameReportDAO.insertData(model);
    }

}
