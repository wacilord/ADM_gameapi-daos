package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

/**
 *
 * @author David
 */
@SpringBootTest(classes = LogGameTicketDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class LogGameTicketDAOTest {

    @Autowired
    LogGameTicketDAO logGameTicketDAO;

    @Test
    public void playerIsOnline(){
        logGameTicketDAO.playerIsOnline("0e3e7f1d-9812-4926-8748-f70c57a3dfc2");
    }

    @Test
    public void lastOnlineTime(){
        String plyGuid = "plyGuid";
        logGameTicketDAO.lastOnlineTime(plyGuid);
    }

    @Test
    public void getPlayerGuidByToken(){
        String token = "token";
        logGameTicketDAO.getPlayerGuidByToken(token);
    }

    @Test
    public void onlineCount(){
        String agtGuid = "agtGuid";
        int level = 3;
        logGameTicketDAO.onlineCount(agtGuid,level);
    }

    @Test
    public void getOnlinePlayerCountByAgent1(){
        logGameTicketDAO.getOnlinePlayerCountByAgent1();
    }

    @Test
    public void getGameTypeByTimeRangeAndAgent1(){
        logGameTicketDAO.getGameTypeByTimeRangeAndAgent1("agent1", new Date(),new Date());
    }
}
