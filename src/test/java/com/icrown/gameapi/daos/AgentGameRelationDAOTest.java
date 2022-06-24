package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = AgentGameRelationDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class AgentGameRelationDAOTest {
    @Autowired
    AgentGameRelationDAO agentGameRelationDAO;

    @Test
    public void getEnableGameTypeByMctGuid(){
        agentGameRelationDAO.getAllGameTypeByMctGuid("mctGuid");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void initGameTypeByMctGuid(){
        agentGameRelationDAO.initGameTypeByMctGuid("mctGuid,", List.of(1,2));
    }
}
