package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
import com.icrown.gameapi.models.LastReportDateModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DailyTradeDAO.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class DailyTradeDAOTest {
    @Autowired
    DailyTradeDAO dailyTradeDAO;
    @Autowired
    LastReportDateDAO lastReportDateDAO;

    @Autowired
    DateUtil dateUtil;
//    @Test
//    public void getDailyTradeByIndexAndDateRecover(){
//        dailyTradeDAO.getDailyTradeByIndexAndDateRecover(100,new Date(),new Date());
//    }


    @Test
    public void getDailyTradeByIndexAndUpdateTime() {
        Date startTime = dateUtil.addTime(new Date(), Calendar.DATE,-1);
        Date endTime = new Date();
        long index=100;
        int size = 10;

        Optional<LastReportDateModel> lastReportDate = lastReportDateDAO.getLatestDataByDate(startTime, endTime);
        if (!lastReportDate.isEmpty()) {
            index = lastReportDate.get().getLRD_MinIndex();
        }

        dailyTradeDAO.getDailyTradeByIndexAndDate(index, startTime, endTime, 1, size);
    }

    @Test
    public void getRecordBySeq() {
        String agentGuid = "agentGuid";
        long seq = 1000000;
        dailyTradeDAO.getRecordBySeq(agentGuid, seq);
    }

    @Test
    public void getDailyTradeList() {
        String playerGuid = "playerGuid";
        List<String> playerGuids = Arrays.asList(playerGuid);
        Date startDate = new Date();
        Date endDate = new Date();
        Date partitionStart = new Date();
        Date partitionEnd = new Date();
        int pageIndex = 1;
        int pageSize = 10;
        dailyTradeDAO.getDailyTradeList(playerGuids, startDate, endDate, partitionStart, partitionEnd, pageIndex, pageSize);
    }

    @Test
    public void getDailyTradeListCount() {
        String playerGuid = "playerGuid";
        List<String> playerGuids = Arrays.asList(playerGuid);
        Date startDate = new Date();
        Date endDate = new Date();
        Date partitionStart = new Date();
        Date partitionEnd = new Date();
        dailyTradeDAO.getDailyTradeListCount(playerGuids, startDate, endDate, partitionStart, partitionEnd);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void insertDailyTrade() {
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
        String currency = "RMB";

        Long transferSEQ = (long) 1000;

        dailyTradeDAO.insertDailyTrade(transferSEQ, agtGuid1, agtGuid2, agtGuid3, agtAccountID, playerGuid, plyAccountID, transferID, pointAmount, currency, beforeBalance, afterBalance, accCode, 3);
    }

    @Transactional
    @Rollback(true)
    @Test
    void insertFish() {
        long dtSeq = 123456789L;
        String plyGuid = "4b70781e-2955-4a99-a842-249e21a325b8";
        String plyAccountID = "test2493";
        Date dtUpdateTime = dateUtil.strToDate("2020-01-30 12:00:00", "yyyy-MM-dd HH:mm:ss");
        int accCode = 2001;
        int dtWorkType = 2;
        String agtGuid1 = "644fba76-f945-11e9-911c-000c294fac45";
        String agtGuid2 = "f5c41be2-f945-11e9-911c-000c294fac45";
        String agtGuid3 = "5f36e12c-f946-11e9-911c-000c294fac45";
        int gameType = 1;
        String gameCode = "1002";
        BigDecimal beforeBalance = new BigDecimal(100000.000);
        BigDecimal tradePoint = new BigDecimal(0.005);
        BigDecimal afterBalance = new BigDecimal(100000.005);
        String ipAddress = "192.168.5.1";
        BigDecimal bets = new BigDecimal(0.025);
        BigDecimal validBets = new BigDecimal(0.025);
        BigDecimal win = new BigDecimal(0.030);
        BigDecimal netWin = new BigDecimal(0.005);
        BigDecimal jackpot = new BigDecimal(0);
        BigDecimal jackpotContribute = new BigDecimal(0);
        BigDecimal commision = new BigDecimal(0);
        String currency = "RMB";
        int jackpotType = 1;

        boolean result = dailyTradeDAO.insertFish(dtSeq, plyGuid, plyAccountID, dateUtil.getString(dtUpdateTime), accCode, dtWorkType, agtGuid1, agtGuid2, agtGuid3, "agtAccountID", gameType, gameCode, beforeBalance, tradePoint, afterBalance, ipAddress, bets, validBets, win, netWin, jackpotType, jackpot, jackpotContribute, commision, currency);

//        assertTrue(result);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void getDailyTradeByHour() {
        String agentID = "abc";
        Date startTime = new Date();
        Date endTime = new Date();
        dailyTradeDAO.getDailyTradeByHour(agentID, startTime, endTime);
    }

    @Test
    public void getDailyTradeCountByHour() {
        dailyTradeDAO.getDailyTradeCountByRange("", new Date(), new Date());
    }

    @Test
    public void isSeqExist() {
        boolean isExist = dailyTradeDAO.isSeqExist(111);
        assertTrue(isExist == false);
    }
}
