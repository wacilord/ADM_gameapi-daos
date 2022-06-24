package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
//import com.icrown.gameapi.dtos.SubAccountListDTO;
import com.icrown.gameapi.models.SubAccountModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author David
 */
@SpringBootTest(classes = SubAccountDAOTest.class)
@ComponentScan(basePackages = "com.icrown")
@EnableAutoConfiguration
public class SubAccountDAOTest {

    @Autowired
    SubAccountDAO subAccountDAO;
    @Autowired
    DateUtil dateUtil;


    public SubAccountDAOTest() {
    }


    /**
     * Test of login method, of class SubAccountDAO.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testLogin() {
        System.out.println("login");
        String subAccountGuid = "testGUID";
        String agentGuid = "testGUID";
        int type = 0;
        String domain = "test";
        String accountID = "testID";
        String password = "password";
        String nickName = "";
        String memo = "";

        subAccountDAO.add(subAccountGuid, agentGuid, type, domain, accountID, password, nickName, memo);
        Map<String, Object> result = subAccountDAO.login(accountID, domain);
    }

    /**
     * Test of LoginError method, of class SubAccountDAO.
     */
    @Test
    public void testLoginError() {
        System.out.println("LoginError");
        String accountID = "";
        String domain = "";
        String ipaddress = "";
        Date loginTime = null;
        Date regainTime = null;

        boolean expResult = false;
        boolean result = subAccountDAO.loginError(accountID, domain, ipaddress, loginTime, regainTime);
        assertEquals(expResult, result);

    }

    /**
     * Test of LoginErrorWithLock method, of class SubAccountDAO.
     */
    @Test
    public void testLoginErrorWithLock() {
        System.out.println("LoginErrorWithLock");
        String accountID = "";
        String domain = "";
        String ipaddress = "";
        Date loginTime = null;
        Date regainTime = null;

        boolean expResult = false;
        boolean result = subAccountDAO.loginErrorWithLock(accountID, domain, ipaddress, loginTime, regainTime);
        assertEquals(expResult, result);
    }

    /**
     * Test of loginOk method, of class SubAccountDAO.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testLoginOk() throws ParseException {
        System.out.println("loginOk");
        String SatGuid = "testIDGuid";
        String token = "testtoken";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expiredTime = simpleDateFormat.parse(dateUtil.getForwardDate(1));
        String accountID = "testID";
        String domain = "test";
        String ipaddress = "127.0.0.1";
        Date loginTime = simpleDateFormat.parse(dateUtil.getForwardDate(0));
        ;

        subAccountDAO.loginOk(SatGuid, token, expiredTime, accountID, domain, ipaddress, loginTime);

    }

    /**
     * Test of loginLog method, of class SubAccountDAO.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testLoginLog() {
        System.out.println("loginLog");
        String accountID = "testID";
        String domain = "test";
        String ipAddress = "127.0.0.1";
        String agtGuid = "testGUID";
        String clientType = "web";
        int errorCode = 0;
        String errorMessage = "";

        subAccountDAO.loginLog(accountID, domain, ipAddress, agtGuid, clientType, errorCode, errorMessage);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of logout method, of class SubAccountDAO.
     */
    @Test
    public void testLogout() {
        System.out.println("logout");
        String token = "testtoken";
        subAccountDAO.logout(token);
    }

    /**
     * Test of findSubAccountListByAGT_GUID method, of class SubAccountDAO.
     */
    @Test
    public void testFindSubAccountListByAGT_GUID() {
        System.out.println("findSubAccountListByAGT_GUID");
        String AGT_GUID = "5f36e12c-f946-11e9-911c-000c294fac45";
        subAccountDAO.findSubAccountListByAGT_GUID(AGT_GUID);
    }

    /**
     * Test of add method, of class SubAccountDAO.
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testAdd() {
        System.out.println("add");
        String subAccountGuid = "testGUID";
        String agentGuid = "testGUID";
        int type = 0;
        String domain = "test";
        String accountID = "testID";
        String password = "password";
        String nickName = "";
        String memo = "";

        subAccountDAO.add(subAccountGuid, agentGuid, type, domain, accountID, password, nickName, memo);
        Optional<SubAccountModel> result = subAccountDAO.getSubAccountByGuid(agentGuid);
    }

    /**
     * Test of update method, of class SubAccountDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String subAccountGuid = "testGUID";
        String nickName = "nickName";
        String memo = "memo";

        subAccountDAO.update(subAccountGuid, nickName, memo);

    }

    /**
     * Test of lock method, of class SubAccountDAO.
     */
    @Test
    public void testLock() {
        System.out.println("lock");
        String subAccountGuid = "testGUID";
        subAccountDAO.lock("agtGuid", subAccountGuid);
    }

    /**
     * Test of unlock method, of class SubAccountDAO.
     */
    @Test
    public void testUnlock() {
        System.out.println("unlock");
        String subAccountGuid = "testGUID";
        subAccountDAO.unlock("agtGuid", subAccountGuid);
    }

    /**
     * Test of updatePassword method, of class SubAccountDAO.
     */
    @Test
    public void testUpdatePassword() {
        String subAccountGuid = "testGUID";
        String password = "passsword123";
        subAccountDAO.updatePassword(subAccountGuid, password);
    }

    @Test
    public void testUpdateSelfPassword(){
        String subAccountGuid = "testGUID";
        String password = "passsword123";
        subAccountDAO.updateSelfPassword(subAccountGuid, password);
    }

    /**
     * Test of findSubAccountGuidByAccountID method, of class SubAccountDAO.
     */
    @Test
    public void testFindSubAccountGuidByAccountID() {
        System.out.println("findSubAccountGuidByAccountID");
        String accountID = "testGUID";
        String domainName = "test";
        int expResult = 0;
        int result = subAccountDAO.findSubAccountGuidByAccountID(accountID, domainName);
        assertEquals(expResult, result);
    }

    /**
     * Test of findSubAccountGuidByGuid method, of class SubAccountDAO.
     */
    @Test
    public void testFindSubAccountGuidByGuid() {
        System.out.println("findSubAccountGuidByGuid");
        String satGuid = "testGUID";

        int expResult = 0;
        int result = subAccountDAO.findSubAccountGuidByGuid(satGuid);
        assertEquals(expResult, result);
    }

    /**
     * 資料不存在測試
     */
    @Test
    public void testNodataGetSubAccountByGuid() {
        System.out.println("getSubAccountByGuid");
        String satGuid = "testNoRecord";
        Optional<SubAccountModel> result = subAccountDAO.getSubAccountByGuid(satGuid);
        assertEquals(result, Optional.empty());
    }

    @Test
    public void updateAgentMemo() {
        String agentGuid = "agentGuid";
        String memo = "memo";
        subAccountDAO.updateAgentMemo(agentGuid, memo);
    }

    @Test
    public void getSubAccountByAgentGuid() {
        String agentGuid = "agentGuid";
        subAccountDAO.getSubAccountByAgentGuid(agentGuid);
    }
}
