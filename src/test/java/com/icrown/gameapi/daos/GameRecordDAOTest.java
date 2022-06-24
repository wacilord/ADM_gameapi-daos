package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = GameRecordDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameRecordDAOTest {
    @Autowired
    GameRecordDAO gameRecordDAO;

    @Autowired
    DateUtil dateUtil;


    @Test
    public void test() {

    }

    @Test
    public void getArcadeGameByAgent() throws Exception {
        String agentGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getMiniGameByAgent(agentGuid, beginTime, endTime);
    }

    @Test
    public void getSlotGameByAgent() throws Exception {
        String agentGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getSlotGameByAgent(agentGuid, beginTime, endTime);
    }

    @Test
    public void getFishingByAgent() throws Exception {
        String agentGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getFishingGameByAgent(agentGuid, beginTime, endTime);
    }

    @Test
    public void getChessGameByAgent() throws Exception {
        String agentGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getCardGameByAgent(agentGuid, beginTime, endTime);
    }

    @Test
    public void getSlotGameByPlayerGameCodeCount() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String gameCode = "1001";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getSlotGameByPlayerGameCodeCount(plyGuid, gameCode, beginTime, endTime);
    }


    //@Test
    public void getSlotGameDetail() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String gameTurn = "1001";

        gameRecordDAO.getSlotGameDetail(plyGuid, gameTurn);
    }


    @Test
    public void getSlotGameByPlayerGameCode() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String gameCode = "1001";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getSlotGameByPlayerGameCode(plyGuid, gameCode, beginTime, endTime, 0, 10);
    }

    @Test
    void getFishingGameByPlayerGameCode() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String gameCode = "1001";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getFishingGameByPlayerGameCode(plyGuid, gameCode, beginTime, endTime, 0, 10);
    }

    @Test
    public void getFishingGameByPlayerGameCodeCount() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String gameCode = "1001";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameRecordDAO.getFishingGameByPlayerGameCodeCount(plyGuid, gameCode, beginTime, endTime);
    }

    @Test
    void getFishingGameDetail() {
        String plyGUID = "3a3aa9e6-81a2-41a3-952f-c3549a724efa";
        String gameTurn = "2001100000006437807";
        SqlRowSet rowSet = gameRecordDAO.getFishingGameDetail(plyGUID, gameTurn);

//        assertTrue(rowSet.next());
    }

    @Test
    void getFishSEQ() {
        gameRecordDAO.generateFishSEQ();
    }

    @Test
    void getFishingForAnalysis() {
        gameRecordDAO.getFishingForAnalysis("2001", new Date(), new Date());
    }
/*
    @Transactional
    @Rollback(true)
    @Test
    void insertFish() {
        long grSeq = 123456789L;
        Date startTime =  dateUtil.strToDate("2020-01-30 12:00:00", "yyyy-MM-dd HH:mm:ss");
        Date endTime =  dateUtil.strToDate("2020-01-30 12:00:00", "yyyy-MM-dd HH:mm:ss");
        String gameCode = "2001";
        String agtGuid1 = "4c04a6aa-bcd4-4191-8da1-45db3727e3c5";
        String agtGuid2 = "eaf00889-5c7d-4a15-b348-4f18c47fc153";
        String agtGuid3 = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String plyGuid = "2d23fea7-088a-477e-bbc7-af95dc8b9186";
        String agtAccountID = "frank001";
        String plyAccountID = "ABC8179";
        String currency = "RMB";
        BigDecimal bets = new BigDecimal(0.02);
        BigDecimal gamebleBets = new BigDecimal(0);
        BigDecimal validBets = new BigDecimal(0.02);
        BigDecimal betsResult = new BigDecimal(0.04);
        BigDecimal betsWin = new BigDecimal(0.02) ;
        BigDecimal jackpot = new BigDecimal(0);
        BigDecimal jackpotContribute = new BigDecimal(0);
        int flagFreeGame = 0;
        int flatGameble = 0;
        int flagInterrupt = 0;
        String ipAddress = "192.168.5.42";
        String gsVersion = "0.5.0";
        String gsName = "192.168.5.211";
        BigDecimal beforeBalace = new BigDecimal(100000.00);
        BigDecimal afterBalance = new BigDecimal(100000.02);
        String json = "{\"totalBet\":2,\"totalWin\":4,\"fishId\":\"n_2\",\"seq\":\"123456789\"}";
        boolean result = gameRecordDAO.insertFish(grSeq,dateUtil.getString(startTime),dateUtil.getString(endTime),gameCode,agtGuid1,agtGuid2,agtGuid3,plyGuid,agtAccountID,plyAccountID,
                currency,bets,gamebleBets,validBets,betsResult,betsWin,jackpot,jackpotContribute,flagFreeGame,flatGameble,
                flagInterrupt,ipAddress,gsVersion,gsName,beforeBalace,afterBalance,json);

    }

 */
}
