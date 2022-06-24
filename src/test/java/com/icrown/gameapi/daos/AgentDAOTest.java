package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.AgentModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AgentDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class AgentDAOTest {
    @Autowired
    AgentDAO agentDAO;

    @Test
    public void getAgentInfoByAgentGUID() {
        String agentGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        agentDAO.getAgentInfoByAgentGUID(agentGuid);
    }

    @Test
    public void getAllAgents() {
        List<AgentModel> agents = agentDAO.getAllAgents();
    }

    @Test
    public void getAgentTreeByAgent3() {
        String agentGuid3 = "agentGuid3";
        agentDAO.getAgentTreeByAgent3(agentGuid3);
    }

    @Test
    public void getAgentTreeByAgent2() {
        String agentGuid2 = "agentGuid2";
        agentDAO.getAgentTreeByAgent2(agentGuid2);
    }

    @Test
    public void getAgentDetail() {
        String agtGuid = "agtGuid";
        agentDAO.getAgentDetail(agtGuid);
    }

    @Test
    public void findAgentByAccountID() {
        String accountID = "accountID";
        String domainName = "domainName";
        agentDAO.findAgentByAccountID(accountID, domainName);
    }

    @Test
    public void findSubAccountByAccountID() {
        String accountID = "accountID";
        String domainName = "domainName";
        agentDAO.findSubAccountByAccountID(accountID, domainName);
    }

    @Test
    public void getAllAgentsByMctGUID() {
        String mctGuid = "mctGuid";
        agentDAO.getAllAgentsByMctGUID(mctGuid);
    }

    @Test
    public void getAgentList() {
        String agtParent = "agtParent";
        String agtAccountID = "agtAccountID";
        int status = 0;
        int pageSize = 1;
        int pageIndex = 10;
        agentDAO.getAgentList(agtParent, agtAccountID, status, pageSize, pageIndex);
    }

    @Test
    public void getAgentListCount() {
        String agtParent = "agtParent";
        String agtAccountID = "agtAccountID";
        int status = 0;
        agentDAO.getAgentListCount(agtParent, agtAccountID, status);
    }

    @Transactional
    @Rollback
    @Test
    public void add() {
        AgentModel model = new AgentModel();
        model.setAGT_CreateDatetime(new Date());
        model.setAGT_AccountID("accountID");
        model.setMCT_GUID("mctGuid");
        model.setAGT_Level(3);
        model.setAGT_Lock(0);
        model.setAGT_Agent1("agent1");
        model.setAGT_Agent2("agent2");
        model.setAGT_Agent3("agent3");
        model.setAGT_GUID("agtGuid");
        model.setAGT_Parent("agtParent");
        model.setMCT_Domain("domain");
        agentDAO.add(model);
    }

    @Test
    void getAllAgentByParent() {
        String guid = "guid";
        int level = 2;
        agentDAO.getAllAgentByParent(guid, level);
    }

    @Test
    void getAllMemberByParent() {
        String guid = "guid";
        int level =2;
        agentDAO.getAllMemberByParent(guid, level);
    }

    @Test
    void getAllAgentByLocker() {
        String agtGuid = "guid";
        String lockerGuid = "lockGuid";
        int level = 2;
        agentDAO.getAllAgentByLocker(lockerGuid, agtGuid, level);
    }

    @Test
    void getAllMemberByLocker() {
        String agtGuid = "guid";
        String lockerGuid = "lockGuid";
        int level = 2;
        agentDAO.getAllMemberByLocker(lockerGuid, agtGuid, level);
    }

    @Test
    void lockAgent() {
        String lockerGuid = "lockGuid";
        List<String> guids = new ArrayList<>();
        guids.add("a");
        agentDAO.lockAgent(lockerGuid, guids);
    }

    @Test
    void lockPlayerByAgent() {
        String lockerGuid = "lockGuid";
        List<String> guids = new ArrayList<>();
        guids.add("a");
        agentDAO.lockPlayerByAgent(lockerGuid, guids);
    }

    @Test
    void unlockAgent() {
        List<String> guids = new ArrayList<>();
        guids.add("a");
        agentDAO.unlockAgent(guids);
    }

    @Test
    void unlockPlayer() {
        List<String> guids = new ArrayList<>();
        guids.add("a");
        agentDAO.unlockPlayer(guids);
    }

    @Test
    void getAllAgent1WithMctGuid(){
        agentDAO.getAllAgent1WithMctGuid();
    }

    @Test
    void getAllAgent3ByMctGuid(){
        agentDAO.getAllAgentByMctGuid("mctGuid");
    }

    @Test
    void getAgentByDomain(){
        String domain = "mctDomain";

        agentDAO.getAgentByDomain(domain);
    }

}
