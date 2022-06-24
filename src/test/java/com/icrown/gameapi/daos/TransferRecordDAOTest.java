package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TransferRecordDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class TransferRecordDAOTest {
    @Autowired
    TransferRecordDAO transferRecordDAO;

    @Test
    public void getTradeRecordByTime() throws Exception {
        String agtGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-01 00:00:00");
        Date endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-31 00:00:00");
        transferRecordDAO.getTradeRecordByTime(agtGuid, beginDateTime, endDateTime);
    }

    @Test
    public void getTradeRecordByTimeAndPlayer() throws Exception {
        String agtGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-01 00:00:00");
        Date endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-31 00:00:00");
        String playerId = "0e3e7f1d-9812-4926-8748-f70c57a3dfc2";
        transferRecordDAO.getTradeRecordByTimeAndPlayer(agtGuid, beginDateTime, endDateTime, playerId);
    }

    @Test
    public void getTradeRecordByTimeAndPlayerGuid() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-01 00:00:00");
        Date endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-31 00:00:00");

        transferRecordDAO.getTradeRecordByTimeAndPlayer(plyGuid, beginDateTime, endDateTime, 0, 10);
    }

    @Test
    public void getTradeRecordByQuery() {
        String agentGuid = "agentGuid";
        String code = "1";
        Date startDate = new Date();
        Date endDate = new Date();
        String plyGuid = "plyGuid";
        int pageIndex = 1;
        int pageSize = 10;
        transferRecordDAO.getTradeRecordByQuery(agentGuid, code, startDate, endDate, plyGuid, pageIndex, pageSize);
    }

    @Test
    public void getTradeRecordByQueryCount() {
        String agentGuid = "agentGuid";
        String code = "1";
        Date startDate = new Date();
        Date endDate = new Date();
        String plyGuid = "plyGuid";
        transferRecordDAO.getTradeRecordByQueryCount(agentGuid, code, startDate, endDate, plyGuid);
    }

    @Test
    public void getTradeRecordByTransferID() {
        String agentGuid = "agentGuid";
        String transferID = "transferID";
        int level = 1;
        transferRecordDAO.getTradeRecordByTransferID(agentGuid, level, transferID);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void insertTransfer() {
        String playerGuid = "playerGuid";
        String agtGuid1 = "agtGuid1";
        String agtGuid2 = "agtGuid2";
        String agtGuid3 = "agtGuid3";
        String agtAccountID = "agtAccountID";
        String plyAccountID = "plyAccountID";
        String transferID = "transferID";
        BigDecimal beforeBalance = BigDecimal.valueOf(10);
        BigDecimal pointAmount = BigDecimal.valueOf(10);
        BigDecimal afterBalance = BigDecimal.valueOf(20);
        int accCode = 3101;
        int agpType = 1;
        String currency = "RMB";

        Long transferSEQ = (long) 1000;
        transferRecordDAO.insertTransfer(transferSEQ, agtGuid1, agtGuid2, agtGuid3, agtAccountID, playerGuid, plyAccountID, transferID, pointAmount, currency, beforeBalance, accCode, agpType, afterBalance);
    }


    @Test
    void getTradeRecordCountByTimeAndPlayer() throws ParseException {
        String plyGUID = "00205bb3-b82b-480d-b80f-eed61d130ead";
        Date beginDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-01-07 00:00:00");
        Date endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-01-09 00:00:00");

        int count = transferRecordDAO.getTradeRecordCountByTimeAndPlayer(plyGUID, beginDateTime, endDateTime);
//        assertTrue(count != 0);
    }


    @Test
    void getTradeRecordCountByTimeAndPlayerAndAccCode() throws ParseException {
        String plyGUID = "00205bb3-b82b-480d-b80f-eed61d130ead";
        String accCode = "3101";
        Date beginDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-01-07 00:00:00");
        Date endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-01-09 00:00:00");
        int count = transferRecordDAO.getTradeRecordCountByTimeAndPlayerAndAccCode(plyGUID, accCode, beginDateTime, endDateTime);
//        assertTrue(count != 0);
    }


    @Test
    void getTradeRecordByTimeAndPlayerAndAccCode() throws ParseException {
        String plyGUID = "00205bb3-b82b-480d-b80f-eed61d130ead";
        String accCode = "3101";
        Date beginDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-01-07 00:00:00");
        Date endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-01-09 00:00:00");
        int startPos = 0;
        int pageSize = 1;
        SqlRowSet rowset = transferRecordDAO.getTradeRecordByTimeAndPlayerAndAccCode(plyGUID, accCode, beginDateTime, endDateTime, startPos, pageSize);
//        assertTrue(rowset.next());
    }


    @Test
    void checkTransfer() {
        String agtGUID = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String tfrTransferID = "14EA027B0BFD4538B2C64054B21E3100";

        Optional trfOp = transferRecordDAO.checkTransfer(agtGUID, tfrTransferID);
//        assertTrue(trfOp.isPresent());
    }

    @Test
    void isTransferRecordExist() {
        String plyGUID = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String tfrTransferID = "14EA027B0BFD4538B2C64054B21E3100";

        boolean isExist = transferRecordDAO.isTransferRecordExist(plyGUID, tfrTransferID);
//        assertTrue(isExist);
    }


    //SEQ無法Rollback
    @Transactional
    @Rollback(true)
    @Test
    void generateTransferSEQ() {
//        long seq = transferRecordDAO.generateTransferSEQ();
//        System.out.println(seq);
//        assertNotNull(seq);
    }


}
