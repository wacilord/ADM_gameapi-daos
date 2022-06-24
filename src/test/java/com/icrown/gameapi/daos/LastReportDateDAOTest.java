package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.LastReportDateModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest(classes = LastReportDateDAO.class)
@ComponentScan(basePackages = "com.icrown")
@EnableAutoConfiguration
public class LastReportDateDAOTest {
    @Autowired
    LastReportDateDAO lastReportDateDAO;

    @Test
    @Transactional
    @Rollback(true)
    public void insertData(){
        LastReportDateModel model = new LastReportDateModel();
        model.setLRD_MinDate(new Date());
        model.setLRD_MaxDate(new Date());
        model.setLRD_MinIndex(1);
        model.setLRD_MaxIndex(100);
        model.setLRD_StartDate(new Date());
        model.setLRD_AfterSelect(new Date());
        model.setLRD_DoneDate(new Date());
        model.setLRD_QueryTime(1);
        model.setLRD_UpdateTime(1);
        model.setLRD_Duration(1);
        model.setDT_CreateDateTime(new Date());
        lastReportDateDAO.insertData(model);
    }

    @Test
    public void getLatestDataByDate(){
        lastReportDateDAO.getLatestDataByDate(new Date(),new Date());
    }

//    @Test
//    public void getForCheck(){
//        lastReportDateDAO.getForCheck(new Date(),100);
//    }
//
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void updateForCheck(){
//        lastReportDateDAO.updateForCheck(1,1,new Date(),1,new Date(),new Date());
//    }
}
