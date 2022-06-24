package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SystemDAOTest.class)
@ComponentScan(basePackages = "com.icrown")
@EnableAutoConfiguration
public class SystemDAOTest {
    @Autowired
    SystemDAO systemDAO;

    @Test
    public void getDbDate(){
        var date = systemDAO.getDbDate();
    }

    @Test
    public void getDbDatePart(){
        var date = systemDAO.getDbDatePart();
    }

    @Test
    public void getDbDateByFormat(){
        var date = systemDAO.getDbDateByFormat("yyyy-MM-dd HH:mm");
    }

    @Test
    public void getClusterSize(){
        systemDAO.getClusterSize();
    }
}
