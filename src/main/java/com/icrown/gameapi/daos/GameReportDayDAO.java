package com.icrown.gameapi.daos;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.icrown.gameapi.models.GameReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Repository
public class GameReportDayDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得玩家曾經玩過的遊戲
     **/
    public SqlRowSet getPlayedGames(String playerGuid, String beginDateTime, String endDatetime, int gameType) {
        String sql = "SELECT GR_GameCode as gameCode, DATE_FORMAT(MAX(AGG_Time), '%Y-%m-%d') AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :BeginDate AND AGG_Time <= :EndDate " +
                "	    AND PLY_GUID = :PlayerGuid " +
                "	    AND GR_GameType = :GameType " +
                " GROUP BY GR_GameCode";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PlayerGuid", playerGuid);
        parameters.put("GameType", gameType);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 加總
     */
    public SqlRowSet getPlayerBetsReportbyGametype(String playerGuid, String beginDateTime, String endDatetime) {
        String sql = "SELECT GR_GameType AS gameType" +
                "	,IFNULL(SUM(AG_SumBets), 0) AS bets " +
                "	,IFNULL(SUM(AG_SumNetWin), 0) AS netWin " +
                "FROM DT_GameReportDay " +
                "WHERE AGG_Time >= :BeginDate " +
                "	AND AGG_Time <= :EndDate " +
                "	AND PLY_GUID = :PlayerGuid " +
                "GROUP BY GR_GameType";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PlayerGuid", playerGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public SqlRowSet getPlayerDailyReport(List<String> playerGuidList, Date beginDateTime, Date endDatetime) {
        String sql = "SELECT PLY_AccountID, Date(AGG_Time) AS accountDate , GR_Currency, SUM(AG_SumBets) AS AG_SumBets, " +
                " SUM(AG_SumValidBets) AS AG_SumValidBets, SUM(AG_SumWin) AS AG_SumWin, SUM(AG_SumJackpotContribute) AS AG_SumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS AG_SumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS AG_SumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS AG_SumJackpot3, " +
                " SUM(AG_Commission) AS AG_Commission, SUM(AG_SumNetWin) AS AG_SumNetWin, SUM(AGG_Items) As AGG_Items " +
                " ,AGT_Agent3 " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID in (:PLY_GUID) AND AGG_Time >= :BeginDate AND AGG_Time < :EndDate ";
        sql += " GROUP BY AGT_Agent3, Date(AGG_Time) ";
        sql += " ORDER BY Date(AGG_Time) ASC";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PLY_GUID", playerGuidList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得玩家每日下注紀錄
     **/
    public SqlRowSet getPlayerDailyBetHistory(String playerGuid, String beginDateTime, String endDatetime) {
        String sql = "SELECT DATE_FORMAT(AGG_Time, '%Y-%m-%d') AS playDate " +
                "	,GR_GameType AS gameType " +
                "	,GR_GameCode AS gameCode " +
                "	,SUM(AG_SumBets) AS bets " +
                "	,SUM(AG_SumNetWin) AS netWin " +
                "FROM DT_GameReportDay " +
                "WHERE AGG_Time >= :BeginDate " +
                "	AND AGG_Time <= :EndDate " +
                "	AND PLY_GUID = :PlayerGuid " +
                "GROUP BY playDate, GR_GameCode ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PlayerGuid", playerGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

    }

    public SqlRowSet getGameReportDayByAgent(List<String> agtGuids, List<Integer> gameTypes, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1 AS agent1, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets,  SUM(AG_SumValidBets) AS sumValidBets, " +
                " SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute,  SUM(AG_SumJackpot) AS sumJackpot, " +
                " SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin ,  SUM(AGG_Items) AS items " +
                " FROM DT_GameReportDay  " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate ";


        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);

        if (agtGuids.size() == 1) {
            sql += " AND AGT_Agent1 = :AGT_Agent1 ";
            parameters.put("AGT_Agent1", agtGuids.get(0));
        }

        if (agtGuids.size() > 1 && agtGuids.size() != 0) {
            sql += " AND AGT_Agent1 IN (:AGT_Agent1) ";
            parameters.put("AGT_Agent1", agtGuids);
        }

        if (gameTypes.size() == 1) {
            sql += " AND GR_GameType =  :GR_GameType";
            parameters.put("GR_GameType", gameTypes.get(0));
        }

        if (gameTypes.size() > 1 && gameTypes.size() != 0) {
            sql += " AND GR_GameType IN (:GR_GameType) ";
            parameters.put("GR_GameType", gameTypes);
        }

        sql += " GROUP BY AGT_Agent1 ";

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportDayByAgent3(String agentGuid, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType , GR_GameCode AS gameCode , GR_Currency AS currency, " +
                " SUM(AG_SumBets) AS sumBets , SUM(AG_SumValidBets) AS sumValidBets , SUM(AG_SumWin) AS sumWin ," +
                " SUM(AG_SumJackpotContribute) AS sumJackpotContribute , SUM(AG_SumJackpot) AS sumJackpot ," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, SUM(AG_SumNetWin) AS sumNetWin, " +
                " SUM(AGG_Items) AS items , SUM(AG_Commission) AS commission  " +
                " FROM DT_GameReportDay" +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate AND AGT_Agent3 = :AgentGuid " +
                " GROUP BY GR_GameCode";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportDayByGameType(List<Integer> gameTypes, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType, GR_GameCode AS gameCode, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_SumNetWin) AS sumNetWin, SUM(AGG_Items) AS items, SUM(AG_Commission) AS commission " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate AND GR_GameType IN (:GR_GameType) " +
                " GROUP BY GR_GameCode ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("GR_GameType", gameTypes);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportDayByGameType(List<Integer> gameTypes, Date startDate, Date endDate, List<String> agent1s) {
        String sql = " SELECT GR_GameType AS gameType, GR_GameCode AS gameCode, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_SumNetWin) AS sumNetWin, SUM(AGG_Items) AS items, SUM(AG_Commission) AS commission " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate AND GR_GameType IN (:GR_GameType) AND AGT_Agent1 NOT IN (:AGT_Agent1) " +
                " GROUP BY GR_GameCode ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("GR_GameType", gameTypes);
        parameters.put("AGT_Agent1", agent1s);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getDailyReportByDate(List<Integer> gameType, Date startDate, Date endDate, String agentGuid, int level) {
        String agentWhereField = "AGT_Agent" + level;
        String sql = "SELECT Date(AGG_Time) AS accountDate , GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin , SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission ," +
                " SUM(AG_SumNetWin) AS sumNetWin , SUM(AGG_Items) AS items " +
                " FROM DT_GameReportDay " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND " + agentWhereField + " = :AgentGuid ";
        sql += " GROUP BY Date(AGG_Time),GR_Currency ";
        sql += " ORDER BY Date(AGG_Time) ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getPlayerDailyReportByAgent(List<Integer> gameType, Date startDate, Date endDate, String agentGuid, int level) {
        String agentWhereField = "AGT_Agent" + level;
        String sql = "SELECT PLY_AccountID, Date(AGG_Time) AS accountDate , GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin , SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin , SUM(AGG_Items) AS items " +
                " ,AGT_Agent3 " +
                " FROM DT_GameReportDay " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND " + agentWhereField + " = :AgentGuid ";
        sql += " GROUP BY AGT_Agent3, PLY_AccountID, GR_Currency ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public ArrayNode getPlayerDailyReportByAgent(String gameDate, String agtGuid) {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        String sql = "SELECT DATE_FORMAT(AGG_Time, '%Y-%m-%d') AS gameDate " +
                "	,PLY_AccountID AS playerID " +
                "	,GR_GameCode AS gameCode " +
                "	,AG_SumBets AS bet " +
                "	,AG_SumNetWin AS netWin " +
                "	,AG_SumValidBets AS validBets " +
                "	,AG_SumWin AS win " +
                "	,AG_SumJackpot AS jackpot " +
                "	,IFNULL(AG_SumJackpot2,0) AS jackpot2 " +
                "	,IFNULL(AG_SumJackpot3,0) AS jackpot3 " +
                "	,AG_SumJackpotContribute AS jackpotContribute " +
                "	,AG_Commission AS commission " +
                "	,GR_Currency AS currency " +
                "	,AGG_Items AS recordCount " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time = :gameDate " +
                "	AND AGT_Agent3 = :agent3 ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("gameDate", gameDate);
        parameters.put("agent3", agtGuid);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        //decimal小數位設定到第4位
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_DOWN);

        while (rowSet.next()) {
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            objectNode.put("playerID", rowSet.getString("playerID"));
            objectNode.put("gameCode", rowSet.getString("gameCode"));
            objectNode.put("bet", decimalFormat.format(rowSet.getBigDecimal("bet")));
            objectNode.put("netWin", decimalFormat.format(rowSet.getBigDecimal("netWin")));
            objectNode.put("validBets", decimalFormat.format(rowSet.getBigDecimal("validBets")));
            objectNode.put("win", decimalFormat.format(rowSet.getBigDecimal("win")));
            objectNode.put("jackpot", decimalFormat.format(rowSet.getBigDecimal("jackpot")));
            objectNode.put("jackpot2", decimalFormat.format(rowSet.getBigDecimal("jackpot2")));
            objectNode.put("jackpot3", decimalFormat.format(rowSet.getBigDecimal("jackpot3")));
            objectNode.put("jackpotContribute", decimalFormat.format(rowSet.getBigDecimal("jackpotContribute")));
            objectNode.put("commission", decimalFormat.format(rowSet.getBigDecimal("commission")));
            objectNode.put("currency", rowSet.getString("currency"));
            objectNode.put("recordCount", rowSet.getString("recordCount"));
            arrayNode.add(objectNode);
        }
        return arrayNode;
    }

    public void insert0ThenUpdateDay(GameReportModel model) {
        String sql = "SELECT EXISTS((SELECT * FROM DT_GameReportDay WHERE PLY_GUID=:PLY_GUID AND GR_GameCode=:GR_GameCode AND AGG_Time=:Time))";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PLY_GUID", model.getPLY_GUID());
        parameters.put("GR_GameCode", model.getGR_GameCode());
        parameters.put("Time", model.getAGG_Time());
        int count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);

        if (count == 0) {
            insertDay(model);
        }
        else {
            updateDay(model);
        }

    }

    public int insertDay(GameReportModel model) {
        String sql =
                " INSERT INTO DT_GameReportDay(AGT_Agent1,AGT_Agent2,AGT_Agent3,PLY_GUID,PLY_AccountID,AGG_Items," +
                        " GR_GameType,GR_GameCode,AGG_Time,GR_Currency,AG_SumBets,AG_SumValidBets,AG_SumWin," +
                        " AG_SumJackpot," +
                        " AG_SumJackpot2," +
                        " AG_SumJackpot3," +
                        " AG_SumJackpotContribute,AG_Commission,AG_SumNetWin)" +
                        " VALUES(:Agent1,:Agent2,:Agent3,:GUID,:AccountID,:Items," +
                        " :GameType,:GameCode,:Time,:Currency,:SumBets,:SumValidBets,:SumWin," +
                        " :SumJackpot," +
                        " :SumJackpot2," +
                        " :SumJackpot3," +
                        " :SumJackpotContribute,:Commission,:SumNetWin) ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Agent1", model.getAGT_Agent1());
        parameters.put("Agent2", model.getAGT_Agent2());
        parameters.put("Agent3", model.getAGT_Agent3());
        parameters.put("GUID", model.getPLY_GUID());
        parameters.put("AccountID", model.getPLY_AccountID());
        parameters.put("Items", model.getAGG_Items());
        parameters.put("GameType", model.getGR_GameType());
        parameters.put("GameCode", model.getGR_GameCode());
        parameters.put("Time", model.getAGG_Time());
        parameters.put("Currency", model.getGR_Currency());
        parameters.put("SumBets", model.getAG_SumBets());
        parameters.put("SumValidBets", model.getAG_SumValidBets());
        parameters.put("SumWin", model.getAG_SumWin());
        parameters.put("SumJackpot", model.getAG_SumJackpot());
        parameters.put("SumJackpot2", model.getAG_SumJackpot2());
        parameters.put("SumJackpot3", model.getAG_SumJackpot3());
        parameters.put("SumJackpotContribute", model.getAG_SumJackpotContribute());
        parameters.put("Commission", model.getAG_Commission());
        parameters.put("SumNetWin", model.getAG_SumNetWin());
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void updateDay(GameReportModel model) {
        String sql = "UPDATE DT_GameReportDay SET " +
                " AGG_Items =AGG_Items + :Items,AG_SumBets = AG_SumBets + :SumBets, " +
                " AG_SumValidBets = AG_SumValidBets + :SumValidBets, AG_SumWin= AG_SumWin + :SumWin," +
                " AG_SumJackpot = AG_SumJackpot + :SumJackpot, " +
                " AG_SumJackpot2 = AG_SumJackpot2 + :SumJackpot2, " +
                " AG_SumJackpot3 = AG_SumJackpot3 + :SumJackpot3, " +
                " AG_SumJackpotContribute = AG_SumJackpotContribute + :SumJackpotContribute," +
                " AG_Commission = AG_Commission + :Commission,AG_SumNetWin = AG_SumNetWin + :SumNetWin " +
                " WHERE PLY_GUID=:GUID AND GR_GameCode=:GameCode AND AGG_Time=:Time ";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Agent1", model.getAGT_Agent1());
        parameters.put("Agent2", model.getAGT_Agent2());
        parameters.put("Agent3", model.getAGT_Agent3());
        parameters.put("GUID", model.getPLY_GUID());
        parameters.put("AccountID", model.getPLY_AccountID());
        parameters.put("Items", model.getAGG_Items());
        parameters.put("GameType", model.getGR_GameType());
        parameters.put("GameCode", model.getGR_GameCode());
        parameters.put("Time", model.getAGG_Time());
        parameters.put("Currency", model.getGR_Currency());
        parameters.put("SumBets", model.getAG_SumBets());
        parameters.put("SumValidBets", model.getAG_SumValidBets());
        parameters.put("SumWin", model.getAG_SumWin());
        parameters.put("SumJackpot", model.getAG_SumJackpot());
        parameters.put("SumJackpot2", model.getAG_SumJackpot2());
        parameters.put("SumJackpot3", model.getAG_SumJackpot3());
        parameters.put("SumJackpotContribute", model.getAG_SumJackpotContribute());
        parameters.put("Commission", model.getAG_Commission());
        parameters.put("SumNetWin", model.getAG_SumNetWin());


        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void clearGameReportDayByDate(Date gameDate) {
        String sql = "DELETE FROM DT_GameReportDay WHERE AGG_Time = :GameDate ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("GameDate", gameDate);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void clearGameReportDayByDateAndAgent3(String agentID, Date gameDate) {
        String sql = "DELETE FROM DT_GameReportDay WHERE AGT_Agent3=:AgentID AND AGG_Time = :GameDate ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("AgentID", agentID);
        parameters.put("GameDate", gameDate);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public SqlRowSet getSummaryAgentReportDay(List<String> agent3List, List<Integer> gameTypes, Date startDate, Date endDate) {

        String sql = " SELECT AGT_Agent3 AS agent3, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets,  SUM(AG_SumValidBets) AS sumValidBets, " +
                " SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute,  SUM(AG_SumJackpot) AS sumJackpot, " +
                " SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin ,  SUM(AGG_Items) AS items " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);

        if (agent3List.size() == 1) {
            sql += " AND AGT_Agent3 = :AGT_Agent3 ";
            parameters.put("AGT_Agent3", agent3List.get(0));
        }

        if (agent3List.size() > 1) {
            sql += " AND AGT_Agent3 IN (:AGT_Agent3) ";
            parameters.put("AGT_Agent3", agent3List);
        }

        if (gameTypes.size() == 1) {
            sql += " AND GR_GameType =  :GR_GameType";
            parameters.put("GR_GameType", gameTypes.get(0));
        }

        if (gameTypes.size() > 1) {
            sql += " AND GR_GameType IN (:GR_GameType) ";
            parameters.put("GR_GameType", gameTypes);
        }

        sql += " GROUP BY AGT_Agent3 ";

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public BigDecimal getHistorySumBets(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT IFNull(SUM(AG_SumBets),0) AS SumBets " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class);
    }

    public BigDecimal getHistorySumValidBets(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT IFNull(SUM(AG_SumValidBets),0) AS SumValidBets " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class);
    }

    public BigDecimal getHistorySumNetWin(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT IFNull(SUM(AG_SumNetWin),0) AS SumNetWin " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class);
    }

    public SqlRowSet getNetWinGroupByGameType(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType,SUM(AG_SumNetWin) AS NetWin " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " Group By GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryByDay(Date startDate, Date endDate) {

        String sql = " SELECT SUM(AG_SumValidBets) AS sumValidBets, SUM(AGG_Items) AS items " +
                " ,COUNT(1) AS ccu, AGG_Time AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate GROUP BY AGG_Time ORDER BY AGG_Time ASC";

        Map<String, Object> parameters = new HashMap<>(2);

        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryByDay(Date startDate, Date endDate, List<String> excludeAgent1) {

        String sql = " SELECT SUM(AG_SumValidBets) AS sumValidBets, SUM(AGG_Items) AS items " +
                " ,COUNT(1) AS ccu, AGG_Time AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " AND AGT_Agent1 NOT IN (:ExcludeAgent1) " +
                " GROUP BY AGG_Time ORDER BY AGG_Time ASC ";

        Map<String, Object> parameters = new HashMap<>(3);

        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("ExcludeAgent1", excludeAgent1);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getNetWinGroupByDate(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT AGG_Time AS date, SUM(AG_SumNetWin) AS netWin " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " GROUP BY AGG_Time " +
                " ORDER BY AGG_Time ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryByDayByGameCode(Date startDate, Date endDate) {

        String sql = " SELECT GR_GameCode AS gameCode,SUM(AG_SumValidBets) AS sumValidBets, SUM(AGG_Items) AS items " +
                " ,COUNT(1) AS ccu, AGG_Time AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate GROUP BY AGG_Time,GR_GameCode ORDER BY AGG_Time ASC";

        Map<String, Object> parameters = new HashMap<>(2);

        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryByDayByGameCode(Date startDate, Date endDate, List<String> agent1List) {

        String sql = " SELECT GR_GameCode AS gameCode,SUM(AG_SumValidBets) AS sumValidBets, SUM(AGG_Items) AS items " +
                " ,COUNT(1) AS ccu, AGG_Time AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate  AND AGT_Agent1 NOT IN (:AGT_Agent1) " +
                " GROUP BY AGG_Time,GR_GameCode ORDER BY AGG_Time ASC";

        Map<String, Object> parameters = new HashMap<>(3);

        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AGT_Agent1", agent1List);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getBetsGroupByGame(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType,SUM(AG_SumBets) AS bets " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " Group By GR_GameType " +
                " Order By GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryByDayByGameType(Date startDate, Date endDate) {

        String sql = " SELECT GR_GameType AS gameType,SUM(AG_SumValidBets) AS sumValidBets, SUM(AGG_Items) AS items " +
                " ,COUNT(1) AS ccu, AGG_Time AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate GROUP BY AGG_Time,GR_GameType ORDER BY AGG_Time ASC";

        Map<String, Object> parameters = new HashMap<>(2);

        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryByDayByGameType(Date startDate, Date endDate, List<String> agent1List) {

        String sql = " SELECT GR_GameType AS gameType,SUM(AG_SumValidBets) AS sumValidBets, SUM(AGG_Items) AS items " +
                " ,COUNT(1) AS ccu, AGG_Time AS gameDate " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate  AND AGT_Agent1 NOT IN (:AGT_Agent1) " +
                " GROUP BY AGG_Time,GR_GameType ORDER BY AGG_Time ASC ";

        Map<String, Object> parameters = new HashMap<>(3);

        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AGT_Agent1", agent1List);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public List<Integer> getGameTypeByTimeAndAgent1(String agent1, Date startTime, Date endTime) {
        String sql = " SELECT GR_GameType " +
                "FROM DT_GameReportDay " +
                " WHERE AGT_Agent1 = :Agent1 AND AGG_Time >= :StartTime AND AGG_Time < :EndTime " +
                " GROUP BY GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("Agent1", agent1);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<Integer> list = new ArrayList<>();
        while (rowSet.next()) {
            list.add(Integer.valueOf(rowSet.getString("GR_GameType")));
        }
        return list;
    }

    public List<GameReportModel> getPlayerIdListByDate(String startTime, String endTime) {
        String sql = " SELECT AGG_Time AS AGG_DATE, PLY_GUID, AGT_Agent1 " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime " +
                " GROUP BY AGG_Time, PLY_GUID, AGT_Agent1 ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<GameReportModel> list = new ArrayList<>();
        while (rowSet.next()) {
            GameReportModel model = new GameReportModel();
            model.setAGG_Time(rowSet.getDate("AGG_DATE"));
            model.setPLY_GUID(rowSet.getString("PLY_GUID"));
            model.setAGT_Agent1(rowSet.getString("AGT_Agent1"));

            list.add(model);
        }
        return list;
    }

    public List<GameReportModel> getPlayerIdListByDateAndGameCode(String startTime, String endTime) {
        String sql = " SELECT AGG_Time AS AGG_DATE, PLY_GUID, GR_GameCode, AGT_Agent1 " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime " +
                " GROUP BY AGG_Time, PLY_GUID, GR_GameCode, AGT_Agent1 ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<GameReportModel> list = new ArrayList<>();
        while (rowSet.next()) {
            GameReportModel model = new GameReportModel();
            model.setAGG_Time(rowSet.getDate("AGG_DATE"));
            model.setPLY_GUID(rowSet.getString("PLY_GUID"));
            model.setGR_GameCode(rowSet.getString("GR_GameCode"));
            model.setAGT_Agent1(rowSet.getString("AGT_Agent1"));

            list.add(model);
        }
        return list;
    }

    public List<Integer> get2MonthGameTypeByAgent3List(List<String> agent3List) {
        String sql = " SELECT GR_GameType " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= ADDDATE(CURRENT_DATE(), -60) AND AGG_Time < CURRENT_DATE() " +
                " AND AG_SumBets > 0 AND AGT_Agent3 IN (:AGT_Agent3)" +
                " GROUP BY GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_Agent3", agent3List);

        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<Integer> gameTypeList = new ArrayList<>();
        while (rowSet.next()) {
            gameTypeList.add(rowSet.getInt("GR_GameType"));
        }
        return gameTypeList;
    }

    public List<Integer> get2MonthGameCodeByAgent3List(List<String> agent3List) {
        String sql = " SELECT GR_GameCode " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= ADDDATE(CURRENT_DATE(), -60) AND AGG_Time < CURRENT_DATE() " +
                " AND AG_SumBets > 0 AND AGT_Agent3 IN (:AGT_Agent3)" +
                " GROUP BY GR_GameCode ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_Agent3", agent3List);

        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<Integer> gameCodeList = new ArrayList<>();
        while (rowSet.next()) {
            gameCodeList.add(rowSet.getInt("GR_GameCode"));
        }
        return gameCodeList;
    }

    public int getAggItemsByPlyGuidAdnGameCode(String plyGuid, String gameCode, Date startDateTime, Date endDateTime) {
        String sql = " SELECT IFNULL(SUM(AGG_Items),0) FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PLY_GUID AND GR_GameCode = :GR_GameCode AND AGG_Time >= :startTime AND AGG_Time <= :endTime ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("GR_GameCode", gameCode);
        parameters.put("startTime", startDateTime);
        parameters.put("endTime", endDateTime);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getRententionCountInRangeByPlayerGuid(String startDate, String endDate, String oneWeekStartDate, String oneWeekEndDate, String twoWeekStartDate, String twoWeekEndDate, String oneMonthStartDate, String oneMonthEndDate) {
        String sql = " SELECT LOG.PLY_GUID AS logUser, IFNULL(firstDay.PLY_GUID,'') AS firstDayUser, IFNULL(oneWeek.PLY_GUID,'') AS oneWeekUser, IFNULL(twoWeek.PLY_GUID,'') AS twoWeekUser, IFNULL(oneMonth.PLY_GUID,'') AS oneMonthUser " +
                " FROM (SELECT PLY_GUID FROM LOG_GameTicket WHERE LGT_Createdatetime >= :startDate AND LGT_Createdatetime < :endDate GROUP BY PLY_GUID) AS LOG " +
                " LEFT JOIN (SELECT PLY_GUID FROM DT_GameReportDay WHERE AGG_Time >= :startDate         AND AGG_Time < :endDate         GROUP BY PLY_GUID) AS firstDay  ON LOG.PLY_GUID=firstDay.PLY_GUID " +
                " LEFT JOIN (SELECT PLY_GUID FROM DT_GameReportDay WHERE AGG_Time >= :oneWeekStartDate  AND AGG_Time < :oneWeekEndDate  GROUP BY PLY_GUID ) AS oneWeek  ON firstDay.PLY_GUID=oneWeek.PLY_GUID " +
                " LEFT JOIN (SELECT PLY_GUID FROM DT_GameReportDay WHERE AGG_Time >= :twoWeekStartDate  AND AGG_Time < :twoWeekEndDate  GROUP BY PLY_GUID ) AS twoWeek  ON firstDay.PLY_GUID=twoWeek.PLY_GUID " +
                " LEFT JOIN (SELECT PLY_GUID FROM DT_GameReportDay WHERE AGG_Time >= :oneMonthStartDate AND AGG_Time < :oneMonthEndDate GROUP BY PLY_GUID ) AS oneMonth ON firstDay.PLY_GUID=oneMonth.PLY_GUID ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("oneWeekStartDate", oneWeekStartDate);
        parameters.put("oneWeekEndDate", oneWeekEndDate);
        parameters.put("twoWeekStartDate", twoWeekStartDate);
        parameters.put("twoWeekEndDate", twoWeekEndDate);
        parameters.put("oneMonthStartDate", oneMonthStartDate);
        parameters.put("oneMonthEndDate", oneMonthEndDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getBankerAdvantagePerAgent3(Date startTime, Date endTime) {
        String sql = " SELECT (SELECT AGT_AccountID FROM AGT_Agent WHERE AGT_GUID=grd.AGT_Agent1) AS agt1Account, " +
                " (SELECT AGT_AccountID FROM AGT_Agent WHERE AGT_GUID=grd.AGT_Agent3) AS agt3Account, AGT_Agent3, " +
                " GR_GameType, GR_GameCode, " +
                " ROUND(100-((SUM(AG_SumWin)-SUM(AG_SumJackpot2))/SUM(AG_SumBets)*100),4) AS bankerAdvantage, " +
                " SUM(AGG_Items) AS sumItems " +
                " FROM DT_GameReportDay grd " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime " +
                " GROUP BY AGT_Agent1, AGT_Agent3, GR_GameType, GR_GameCode" +
                " ORDER BY AGT_Agent1, AGT_Agent3, GR_GameType, GR_GameCode; ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public SqlRowSet getRententionCountInRangeByPlayerGuidAndGameCode(String startDate, String endDate, String oneWeekStartDate, String oneWeekEndDate, String twoWeekStartDate, String twoWeekEndDate, String oneMonthStartDate, String oneMonthEndDate) {
        String sql = " SELECT LOG.PLY_GUID AS logUser, LOG.LGL_GameCode AS gameCode, IFNULL(firstDay.PLY_GUID,'') AS firstDayUser, IFNULL(oneWeek.PLY_GUID,'') AS oneWeekUser, IFNULL(twoWeek.PLY_GUID,'') AS twoWeekUser, IFNULL(oneMonth.PLY_GUID,'') AS oneMonthUser " +
                " FROM (SELECT PLY_GUID, LGL_GameCode FROM LOG_GameTicket WHERE LGT_Createdatetime >= :startDate AND LGT_Createdatetime < :endDate GROUP BY PLY_GUID, LGL_GameCode) AS LOG " +
                " LEFT JOIN (SELECT PLY_GUID, GR_GameCode FROM DT_GameReportDay WHERE AGG_Time >= :startDate         AND AGG_Time < :endDate         GROUP BY PLY_GUID, GR_GameCode) AS firstDay ON LOG.PLY_GUID=firstDay.PLY_GUID AND LOG.LGL_GameCode=firstDay.GR_GameCode " +
                " LEFT JOIN (SELECT PLY_GUID, GR_GameCode FROM DT_GameReportDay WHERE AGG_Time >= :oneWeekStartDate  AND AGG_Time < :oneWeekEndDate  GROUP BY PLY_GUID, GR_GameCode) AS oneWeek  ON firstDay.PLY_GUID=oneWeek.PLY_GUID  AND firstDay.GR_GameCode=oneWeek.GR_GameCode " +
                " LEFT JOIN (SELECT PLY_GUID, GR_GameCode FROM DT_GameReportDay WHERE AGG_Time >= :twoWeekStartDate  AND AGG_Time < :twoWeekEndDate  GROUP BY PLY_GUID, GR_GameCode) AS twoWeek  ON firstDay.PLY_GUID=twoWeek.PLY_GUID  AND firstDay.GR_GameCode=twoWeek.GR_GameCode " +
                " LEFT JOIN (SELECT PLY_GUID, GR_GameCode FROM DT_GameReportDay WHERE AGG_Time >= :oneMonthStartDate AND AGG_Time < :oneMonthEndDate GROUP BY PLY_GUID, GR_GameCode) AS oneMonth ON firstDay.PLY_GUID=oneMonth.PLY_GUID AND firstDay.GR_GameCode=oneMonth.GR_GameCode ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("oneWeekStartDate", oneWeekStartDate);
        parameters.put("oneWeekEndDate", oneWeekEndDate);
        parameters.put("twoWeekStartDate", twoWeekStartDate);
        parameters.put("twoWeekEndDate", twoWeekEndDate);
        parameters.put("oneMonthStartDate", oneMonthStartDate);
        parameters.put("oneMonthEndDate", oneMonthEndDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getPlayerBankerAdvantageByAgent3AndGameCode(Date startTime, Date endTime, String agent3, String gameCode) {
        String sql = " SELECT (SELECT AGT_AccountID FROM AGT_Agent WHERE AGT_GUID=grd.AGT_Agent1) AS agt1Account, " +
                " (SELECT AGT_AccountID FROM AGT_Agent WHERE AGT_GUID=grd.AGT_Agent3) AS agt3Account, AGT_Agent3, PLY_AccountID, " +
                " GR_GameType, GR_GameCode, ROUND((SUM(AG_SumWin)-SUM(AG_SumJackpot2))/SUM(AG_SumBets)*100,4) AS RTP, SUM(AGG_Items) AS sumItems " +
                " FROM DT_GameReportDay grd " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime AND AGT_Agent3 = :Agent3 AND GR_GameCode = :GameCode " +
                " GROUP BY AGT_Agent1, AGT_Agent3, PLY_AccountID, GR_GameType, GR_GameCode; ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        parameters.put("Agent3", agent3);
        parameters.put("GameCode", gameCode);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getDailySummaryJackpotContributeByAgent3(Date startTime, Date endTime) {
        String sql = "SELECT AGG_Time, AGT_Agent1, AGT_Agent2, AGT_Agent3, (SELECT AGT_AccountID FROM AGT_Agent WHERE AGT_GUID=reportDay.AGT_Agent3) AS AGT_AccountID, " +
                " SUM(AG_SumJackpotContribute) AS AG_SumJackpotContribute, GR_Currency, GR_GameCode " +
                " FROM DT_GameReportDay reportDay " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime " +
                " GROUP BY AGG_Time, AGT_Agent1, AGT_Agent2, AGT_Agent3, GR_Currency, GR_GameCode ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
