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

@SpringBootTest(classes = TGAccountDAOTest.class)
@ComponentScan(basePackages = "com.icrown")
@EnableAutoConfiguration
class TGAccountDAOTest {

    @Autowired
    TGAccountDAO tgAccountDAO;

    @Test
    void getAccountById() {
        String accountId = "abcdefg123456789";
        SqlRowSet sqlRowSet = tgAccountDAO.getAccountById(accountId);
        assertFalse(sqlRowSet.next());
    }

    @Test
    @Transactional
    @Rollback(true)
    void updateRegainTime() {
        String accountId = "abcdefg123456789";
        Date regainDateTime = new Date();
        tgAccountDAO.updateRegainTime(accountId, regainDateTime);
    }

    @Test
    void loginLog() {
        Date createDateTime = new Date();
        String clientType = "1";
        String ip = "127.0.0.1";
        String accountId = "abcdefg123456789";
        int code = 1001;
        String message = "message";

        tgAccountDAO.loginLog(createDateTime, clientType, ip, accountId, code, message);
    }

    @Test
    void getAllRoles() {
        List<Map<String, Object>> list = tgAccountDAO.getAllRoles();
//        assertTrue(!list.isEmpty());
    }

    @Test
    void getRoleByRoleId() {
        int roleId = 123456789;
        SqlRowSet sqlRowSet = tgAccountDAO.getRoleByRoleId(roleId);
        assertFalse(sqlRowSet.next());
    }

    @Test
    void getSessionByAccountId() {
        String accountId = "abcdefg123456789";
        SqlRowSet sqlRowSet = tgAccountDAO.getSessionByAccountId(accountId);
        assertFalse(sqlRowSet.next());
    }

    @Test
    @Transactional
    @Rollback(true)
    void updateSession() {
        String accountId = "abcdefg123456789";
        String token = "token";
        String ip = "127.0.0.1";
        Date expiredDateTime = new Date();
        Date createDateTime = new Date();
        tgAccountDAO.updateSession(accountId, token, ip, expiredDateTime, createDateTime);
    }

    @Test
    @Transactional
    @Rollback(true)
    void newSession() {
        String accountId = "abcdefg123456789";
        String token = "token";
        String ip = "127.0.0.1";
        Date expiredDateTime = new Date();
        Date createDateTime = new Date();
        tgAccountDAO.newSession(accountId, token, ip, expiredDateTime, createDateTime);
    }

    @Test
    @Transactional
    @Rollback(true)
    void updateLastLogin() {
        String AccountId = "abcdefg123456789";
        String ip = "127.0.0.1";
        Date lastLoginDateTime = new Date();
        tgAccountDAO.updateLastLogin(AccountId, ip, lastLoginDateTime);
    }

    @Test
    @Transactional
    @Rollback(true)
    void updateErrorCount() {
        String accountId = "abcdefg123456789";
        int errorCount = 0;
        tgAccountDAO.updateErrorCount(accountId, errorCount);

    }

    @Test
    @Transactional
    @Rollback(true)
    void loginError() {
        String accountId = "abcdefg123456789";
        SqlRowSet sqlRowSet = tgAccountDAO.loginError(accountId);
        assertFalse(sqlRowSet.next());
    }
}