package com.icrown.gameapi.daos;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = GameReportDAO.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class GameReportDAOTest {
    @Autowired
    private GameReportDAO gameReportDAO;

    /*
    @Test
    public void getSingleData(){
        String playerGuid = "playerGuid";
        int gameType = 1;
        String gameCode = "1001";
        Date time = new Date();
        gameReportDAO.getSingleData(playerGuid,gameType,gameCode,time);
    }
    */
    private GameReportModel getModel() {
        GameReportModel model = new GameReportModel();
        model.setAGT_Agent1("agent1");
        model.setAGT_Agent2("agent2");
        model.setAGT_Agent3("agent3");
        model.setPLY_GUID("guid");
        model.setPLY_AccountID("accountID");
        model.setAGG_Items(10);
        model.setGR_GameType(1);
        model.setGR_GameCode("1001");
        model.setAGG_Time(new Date());
        model.setGR_Currency("RMB");
        model.setAG_SumBets(new BigDecimal(100));
        model.setAG_SumValidBets(new BigDecimal(100));
        model.setAG_SumWin(new BigDecimal(100));
        model.setAG_SumJackpot(new BigDecimal(0));
        model.setAG_SumJackpot2(new BigDecimal(0));
        model.setAG_SumJackpot3(new BigDecimal(0));
        model.setAG_SumJackpotContribute(new BigDecimal(1));
        model.setAG_Commission(new BigDecimal(0));
        model.setAG_SumNetWin(new BigDecimal(100));
        return model;
    }

    /*
    @Test
    @Transactional
    @Rollback(true)
    public void updateDatas(){
        var model = getModel();
        List<GameReportModel> models = new ArrayList();
        models.add(model);
        gameReportDAO.updateDatas(models);
    }
    */
    /*
    @Test
    @Transactional
    @Rollback(true)
    public void insertDatas(){
        var model = getModel();
        List<GameReportModel> models = new ArrayList();
        models.add(model);
        gameReportDAO.insertDatas(models);
    }
    */
    @Test
    public void testGetPlayerDailyBetHistory() {

        String playerGuid = "426abbc2-cae4-4388-a8e9-57ebec9189d9";
        String beginDate = "2019-10-15";
        String endDate = "2019-11-14";
        gameReportDAO.getPlayerDailyBetHistory(playerGuid, beginDate, endDate);
    }

    @Test
    public void testGetPlayedGames() {

        String playerGuid = "426abbc2-cae4-4388-a8e9-57ebec9189d9";
        String beginDate = "2019-10-15";
        String endDate = "2019-11-14";
        int gameType = 1;
        gameReportDAO.getPlayedGames(playerGuid, beginDate, endDate, gameType);
    }

    @Test
    public void testGetPlayerBetsReportbyGametype() {
        String playerGuid = "426abbc2-cae4-4388-a8e9-57ebec9189d9";
        String beginDate = "2019-10-15";
        String endDate = "2019-11-14";
        gameReportDAO.getPlayerBetsReportbyGametype(playerGuid, beginDate, endDate);
    }

    @Test
    public void getSlotDayList() throws Exception {
        String plyGuid = "dd600fe1-3b2b-49a9-b73b-7bc01a03187c";
        String gameCode = "1001";
        Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-15 14:10:00");
        gameReportDAO.getGameDayList(plyGuid, gameCode, beginTime, endTime);
    }

    @Test
    public void getHistorySumBets() {
        String plyGuid = "plyGuid";
        Date startDate = new Date();
        Date endDate = new Date();
        gameReportDAO.getHistorySumBets(plyGuid, startDate, endDate);
    }

    @Test
    public void getHistorySumResult() {
        String plyGuid = "plyGuid";
        Date startDate = new Date();
        Date endDate = new Date();
        gameReportDAO.getHistorySumNetWin(plyGuid, startDate, endDate);
    }

    @Test
    public void getResultGroupByGameType() {
        String plyGuid = "plyGuid";
        Date startDate = new Date();
        Date endDate = new Date();
        gameReportDAO.getNetWinGroupByGameType(plyGuid, startDate, endDate);
    }

    @Test
    public void getResultGroupByDate() {
        String plyGuid = "plyGuid";
        Date startDate = new Date();
        Date endDate = new Date();
        gameReportDAO.getNetWinGroupByDate(plyGuid, startDate, endDate);
    }

    @Test
    public void getBetsGroupByGame() {
        String plyGuid = "plyGuid";
        Date startDate = new Date();
        Date endDate = new Date();
        gameReportDAO.getBetsGroupByGame(plyGuid, startDate, endDate);
    }

    @Test
    public void getGameReportInfo() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        int level = 3;
        gameReportDAO.getGameReportInfo(gameType, startDate, endDate, agentGuid, level);
    }

    @Test
    public void getGameSummaryReport() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        int level = 2;
        gameReportDAO.getGameSummaryReport(gameType, startDate, endDate, agentGuid, level);
    }

    @Test
    public void getPlayerSummaryReport() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        int pageIndex = 1;
        int pageSize = 10;
        gameReportDAO.getPlayerSummaryReport(gameType, startDate, endDate, agentGuid, pageIndex, pageSize);
    }

    @Test
    public void getPlayerSummaryReportCount() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        gameReportDAO.getPlayerSummaryReportCount(gameType, startDate, endDate, agentGuid);
    }

    @Test
    public void getSinglePlayerSummaryReport() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        String playerID = "PlayerID";
        int level = 2;
        gameReportDAO.getSinglePlayerSummaryReport(agentGuid, level, gameType, startDate, endDate, playerID);
    }

    @Test
    public void getAgentSummaryReport() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        int level = 2;
        int pageIndex = 1;
        int pageSize = 10;
        gameReportDAO.getAgentSummaryReport(gameType, startDate, endDate, agentGuid, level, pageIndex, pageSize);
    }

    @Test
    public void getAgentSummaryReportCount() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        int level = 2;
        gameReportDAO.getAgentSummaryReportCount(gameType, startDate, endDate, agentGuid, level);
    }

    @Test
    public void getDailyReportByDate() {
        List<Integer> gameType = new ArrayList<>();
        gameType.add(1);
        gameType.add(2);
        gameType.add(3);
        gameType.add(4);
        Date startDate = new Date();
        Date endDate = new Date();
        String agentGuid = "agentGuid";
        int level = 2;
        gameReportDAO.getDailyReportByDate(gameType, startDate, endDate, agentGuid, level);
    }

    @Test
    public void getTodayBetsListByHour() {
        String agtGuid = "agtGuid";
        int level = 1;
        Date startTime = new Date();
        Date endTime = new Date();
        gameReportDAO.getTodayBetsListByHour(agtGuid, level, startTime, endTime);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void delete() {
        String agentID = "abc";
        Date startTime = new Date();
        gameReportDAO.delete(agentID, startTime);
    }

    @Test
    public void getCountByRange() {
        gameReportDAO.getCountByRange("1234", new Date(), new Date());
    }

    @Test
    public void testGetSummaryAgentReport() {
        List<String> agent3List = List.of("abcdefg","hijklg");
        List<Integer> gameTypes = List.of(999);

        SqlRowSet sqlRowSet = gameReportDAO.getSummaryAgentReport(agent3List, gameTypes, new Date(), new Date());

        assertFalse(sqlRowSet.next());
    }
}
