package com.icrown.gameapi.daos;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.icrown.gameapi.commons.utils.DateUtil;
import com.icrown.gameapi.models.GameReportModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GameReportDayDAO.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameReportDayDAOTest {
    @Autowired
    private GameReportDayDAO gameReportDayDAO;

    @Autowired
    DateUtil dateUtil;

    @Test
    public void testGetPlayedGames() {
        String playerGuid = "playerGuid";
        String beginDateTime = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        String endDatetime = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        int gameType = 1;

        SqlRowSet sqlRowSet = gameReportDayDAO.getPlayedGames(playerGuid, beginDateTime, endDatetime, gameType);

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetPlayerBetsReportByGameType() {
        String playerGuid = "playerGuid";
        String beginDateTime = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        String endDatetime = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");

        SqlRowSet sqlRowSet = gameReportDayDAO.getPlayerBetsReportbyGametype(playerGuid, beginDateTime, endDatetime);

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetPlayerDailyBetHistory() {
        String playerGuid = "playerGuid";
        String beginDateTime = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        String endDatetime = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");

        SqlRowSet sqlRowSet = gameReportDayDAO.getPlayerDailyBetHistory(playerGuid, beginDateTime, endDatetime);

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetGameReportDayByAgent() {
        List<String> agtGuids = List.of("agtGuid1", "agtGuids2");
        List<Integer> gameTypes = List.of(1, 2);
        Date startDate = dateUtil.getDatePart(new Date());
        Date endDate = dateUtil.getDatePart(new Date());

        SqlRowSet sqlRowSet = gameReportDayDAO.getGameReportDayByAgent(agtGuids, gameTypes, startDate, endDate);

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetGameReportDayByGameType() {
        List<Integer> gameTypes = List.of(999999);
        Date startDate = dateUtil.getDatePart(new Date());
        Date endDate = dateUtil.getDatePart(new Date());

        SqlRowSet sqlRowSet = gameReportDayDAO.getGameReportDayByGameType(gameTypes, startDate, endDate);

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void getDailyReportByDate() {
        List<Integer> gameType = List.of(777);
        Date startDate = dateUtil.getDatePart(new Date());
        Date endDate = dateUtil.getDatePart(new Date());

        String agentGuid = "agentGuid";
        int level = 3;

        SqlRowSet sqlRowSet = gameReportDayDAO.getDailyReportByDate(gameType, startDate, endDate, agentGuid, level);

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetPlayerDailyReportByAgent() {
        String gameDate = dateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        String agtGuid = "agent3";

        ArrayNode arrayNode = gameReportDayDAO.getPlayerDailyReportByAgent(gameDate, agtGuid);

        assertTrue(arrayNode.isEmpty());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsert0ThenUpdateDay() {
        GameReportModel model = new GameReportModel();
        model.setPLY_GUID("playerGuid");
        model.setGR_GameCode("6001");
        model.setAGG_Time(dateUtil.getDatePart(new Date()));
        model.setAGT_Agent1("agent1");
        model.setAGT_Agent2("agent2");
        model.setAGT_Agent3("agent3");
        model.setPLY_AccountID("plyAccountID");
        model.setAGG_Items(123456);
        model.setGR_GameType(9);
        model.setGR_Currency("NTD");
        model.setAG_SumBets(new BigDecimal(Math.random()));
        model.setAG_SumValidBets(new BigDecimal(Math.random()));
        model.setAG_SumWin(new BigDecimal(Math.random()));
        model.setAG_SumJackpot(new BigDecimal(Math.random()));
        model.setAG_SumJackpot2(new BigDecimal(Math.random()));
        model.setAG_SumJackpot3(new BigDecimal(Math.random()));
        model.setAG_SumJackpotContribute(new BigDecimal(Math.random()));
        model.setAG_Commission(new BigDecimal(Math.random()));
        model.setAG_SumNetWin(new BigDecimal(Math.random()));

        gameReportDayDAO.insert0ThenUpdateDay(model);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsertDay() {
        GameReportModel model = new GameReportModel();
        model.setAGT_Agent1("agent1");
        model.setAGT_Agent2("agent2");
        model.setAGT_Agent3("agent3");
        model.setPLY_GUID("playerGuid");
        model.setPLY_AccountID("plyAccountID");
        model.setAGG_Items(123456);
        model.setGR_GameType(9);
        model.setGR_GameCode("9001");
        model.setAGG_Time(dateUtil.getDatePart(new Date()));
        model.setGR_Currency("NTD");
        model.setAG_SumBets(new BigDecimal(Math.random()));
        model.setAG_SumValidBets(new BigDecimal(Math.random()));
        model.setAG_SumWin(new BigDecimal(Math.random()));
        model.setAG_SumJackpot(new BigDecimal(Math.random()));
        model.setAG_SumJackpot2(new BigDecimal(Math.random()));
        model.setAG_SumJackpot3(new BigDecimal(Math.random()));
        model.setAG_SumJackpotContribute(new BigDecimal(Math.random()));
        model.setAG_Commission(new BigDecimal(Math.random()));
        model.setAG_SumNetWin(new BigDecimal(Math.random()));

        int affectedRows = gameReportDayDAO.insertDay(model);

        assertEquals(1, affectedRows);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateDay() {
        GameReportModel model = new GameReportModel();
        model.setAGT_Agent1("agent1");
        model.setAGT_Agent2("agent2");
        model.setAGT_Agent3("agent3");
        model.setPLY_GUID("playerGuid");
        model.setPLY_AccountID("plyAccountID");
        model.setAGG_Items(123456);
        model.setGR_GameType(9);
        model.setGR_GameCode("9001");
        model.setAGG_Time(dateUtil.getDatePart(new Date()));
        model.setGR_Currency("NTD");
        model.setAG_SumBets(new BigDecimal(Math.random()));
        model.setAG_SumValidBets(new BigDecimal(Math.random()));
        model.setAG_SumWin(new BigDecimal(Math.random()));
        model.setAG_SumJackpot(new BigDecimal(Math.random()));
        model.setAG_SumJackpot2(new BigDecimal(Math.random()));
        model.setAG_SumJackpot3(new BigDecimal(Math.random()));
        model.setAG_SumJackpotContribute(new BigDecimal(Math.random()));
        model.setAG_Commission(new BigDecimal(Math.random()));
        model.setAG_SumNetWin(new BigDecimal(Math.random()));

        int affectedRows = gameReportDayDAO.insertDay(model);

        assertEquals(1, affectedRows);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testClearGameReportDayByDate() {
        Date gameDate = dateUtil.getDatePart(new Date());

        gameReportDayDAO.clearGameReportDayByDate(gameDate);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testClearGameReportDayByDateAndAgent3() {
        String agentID = "agentID";
        Date gameDate = dateUtil.getDatePart(new Date());

        gameReportDayDAO.clearGameReportDayByDateAndAgent3(agentID, gameDate);
    }

    @Test
    public void testGetSummaryAgentReportDay() {
        List<String> agent3List = List.of("abcdefg", "hijklg");
        List<Integer> gameTypes = List.of(999);

        SqlRowSet sqlRowSet = gameReportDayDAO.getSummaryAgentReportDay(agent3List, gameTypes, new Date(), new Date());

        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetSummaryByDay() {
        SqlRowSet sqlRowSet = gameReportDayDAO.getSummaryByDay(new Date(), new Date());
        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetSummaryByDayByGameCode() {
        SqlRowSet sqlRowSet = gameReportDayDAO.getSummaryByDayByGameCode(new Date(), new Date());
        assertFalse(sqlRowSet.next());
    }

    @Test
    public void testGetSummaryByDayByGameType() {
        SqlRowSet sqlRowSet = gameReportDayDAO.getSummaryByDayByGameType(new Date(), new Date());
        assertFalse(sqlRowSet.next());
    }
}
