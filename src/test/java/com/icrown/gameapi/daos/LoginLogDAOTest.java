package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author David
 */
@SpringBootTest(classes = LoginLogDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class LoginLogDAOTest {
    @Autowired
    LoginLogDAO loginLogDAO;

    @Test
    public void getLoginLogList(){
        String agtGuid = "4c04a6aa-bcd4-4191-8da1-45db3727e3c5";
        int pageIndex = 1;
        int pageSize = 1;
        SqlRowSet rowSet = loginLogDAO.getLoginLogList(agtGuid, pageIndex, pageSize);

//        assertTrue(rowSet.next());
    }

    @Test
    public void getTotalCount(){
        String agtGuid = "4c04a6aa-bcd4-4191-8da1-45db3727e3c5";
        int count = loginLogDAO.getTotalCount(agtGuid);

//        assertTrue(count != 0);
    }

}
