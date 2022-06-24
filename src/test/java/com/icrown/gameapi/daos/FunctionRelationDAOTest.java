package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.AgentModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FunctionRelationDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class FunctionRelationDAOTest {
    @Autowired
    FunctionRelationDAO functionRelationDAO;
    @Test
    public void getFunctionRelationBySatGuid(){
        String satGuid = "satGuid";
        functionRelationDAO.getFunctionRelationBySatGuid(satGuid);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void deleteAllBySubAccount(){
        String subAccountGuid = "subAccountGuid";
        functionRelationDAO.deleteAllBySubAccount(subAccountGuid);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void batchInsert(){
        String subAccount = "subAccount";
        List<String> funCode = new ArrayList<>();
        funCode.add("1");
        funCode.add("2");
        funCode.add("3");
        functionRelationDAO.batchInsert(subAccount,funCode);
    }
}
