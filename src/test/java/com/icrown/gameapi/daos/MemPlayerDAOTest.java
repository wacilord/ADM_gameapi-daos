package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.MemberPlayerModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MemPlayerDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class MemPlayerDAOTest {
    @Autowired
    MemPlayerDAO memPlayerDAO;

    @Test
    public void getPlayerGuid() {
        String agtGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String accountId = "A1003";
        memPlayerDAO.getPlayerGuid(agtGuid, accountId);
    }

    @Test
    public void checkUserExist() {
        String agtGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String accountId = "A1003";
        memPlayerDAO.checkUserExist(agtGuid, accountId);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createPlayer() {
        MemberPlayerModel model = new MemberPlayerModel();
        model.setPLY_CreateDatetime(new Date());
        model.setPLY_NickName("NickName");
        model.setPLY_Tester(false);
        model.setMCT_GUID("17b1927a-7b9b-4b02-9845-0e57d73a7b1c");
        model.setAGT_Agent1("4c04a6aa-bcd4-4191-8da1-45db3727e3c5");
        model.setAGT_Agent2("eaf00889-5c7d-4a15-b348-4f18c47fc153");
        model.setAGT_Agent3("dd600fe1-3b2b-49a9-b73b-7bc01a03187c");
        model.setPLY_AccountID("KKKKAAAA");
        model.setAGT_AccountID("DDDDDDDD");
        model.setPLY_PointUpdatetime(new Date());
        model.setPLY_Point(new BigDecimal(100));
        model.setPLY_GUID("17b1927a-7b9b-4b02-9845-0e57d73a7b1s");
        model.setPLY_Lock(false);
        memPlayerDAO.createPlayer(model);
    }

    @Test
    public void getPlayerInfo() {
        String agtGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String accountId = "A1003";
        memPlayerDAO.getPlayerInfo(agtGuid, accountId);
    }

    @Test
    public void getPlayerInfo2() {
        String plyGuid = "b53e5f72-40d8-42bd-855e-14fb6a56257d";
        memPlayerDAO.getPlayerInfo(plyGuid);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void lockPlayer() {
        memPlayerDAO.lockPlayer("b53e5f72-40d8-42bd-855e-14fb6a56257d", "dd600fe1-3b2b-49a9-b73b-7bc01a03187c");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void unlockPlayer() {
        memPlayerDAO.unlockPlayer("b53e5f72-40d8-42bd-855e-14fb6a56257d", "dd600fe1-3b2b-49a9-b73b-7bc01a03187c");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void kickSinglePlayer() {
        memPlayerDAO.kickSinglePlayer("b53e5f72-40d8-42bd-855e-14fb6a56257d");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void kickPlayerByAgent() {
        memPlayerDAO.kickPlayerByAgent("dd600fe1-3b2b-49a9-b73b-7bc01a03187c");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateToken(){
        memPlayerDAO.updateToken("plyGuid","token");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void tokenExpired(){
        String plyGuid = "plyGuid";
        String token = "token";
        memPlayerDAO.tokenExpired(plyGuid,token);
    }

    @Test
    public void getPlayerListCount(){
        String agtGuid = "agtGuid";
        String playerAccountID = "playerAccountID";
        int level =1;
        int status = 0;
        memPlayerDAO.getPlayerListCount(agtGuid,level,playerAccountID,status);
    }

    @Test
    public void getPlayerList(){
        String agtGuid = "agtGuid";
        String playerAccountID = "playerAccountID";
        int level =1;
        int status = 0;
        int pageSize = 10;
        int pageIndex = 1;
        memPlayerDAO.getPlayerList(agtGuid,level,playerAccountID,status,pageSize, pageIndex);
    }

    @Test
    public void clearKick(){
        String plyGuid = "plyGuid";
        memPlayerDAO.clearKick(plyGuid);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void updateWallet(){
        String plyGuid="plyGuid";
        BigDecimal pointAmount = BigDecimal.valueOf(10);


        memPlayerDAO.updateWallet(plyGuid,pointAmount);
    }

    @Transactional
    @Rollback(true)
    @Test
    void kickPlayerBatch() {
        List<String> plyList = Arrays.asList("b53e5f72-40d8-42bd-855e-14fb6a56257d", "15be8820-9e23-45ce-81b5-b0b9209451a7");
        memPlayerDAO.kickPlayerBatch(plyList);
    }


    @Test
    void getPlayerGuidListByAgent() {
        String agtGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        List<String> plyGuid = memPlayerDAO.getPlayerGuidListByAgent(agtGuid);
//        assertTrue(!plyGuid.isEmpty());
    }


    @Test
    void getWalletForUpdate() {
        String plyGUID = "b53e5f72-40d8-42bd-855e-14fb6a56257d";
        BigDecimal plyPoint = memPlayerDAO.getWalletForUpdate(plyGUID);
//        assertNotNull(plyPoint);
    }


}
