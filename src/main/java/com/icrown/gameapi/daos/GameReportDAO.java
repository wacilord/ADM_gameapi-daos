package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameReportModel;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank
 */
@Repository
public class GameReportDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /*
    以 Ply_Guid , Agg_Items , GR_GameCode 為Key值，如果沒存在新增一筆資料，存在則更新資料
     */


    public void insert0ThenUpdate(GameReportModel model) {
        String sql = "SELECT EXISTS((SELECT * FROM DT_GameReport WHERE PLY_GUID=:PLY_GUID AND GR_GameCode=:GR_GameCode AND AGG_Time=:Time ))";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PLY_GUID", model.getPLY_GUID());
        parameters.put("GR_GameCode", model.getGR_GameCode());
        parameters.put("Time", model.getAGG_Time());
        int count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);

        if (count == 0) {
            insert(model);
        }
        else {
            update(model);
        }

    }

    public int insert(GameReportModel model) {
        String sql =
                " INSERT INTO DT_GameReport(AGT_Agent1,AGT_Agent2,AGT_Agent3,PLY_GUID,PLY_AccountID,AGG_Items," +
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
                        ":SumJackpotContribute,:Commission,:SumNetWin) ";
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

    public void update(GameReportModel model) {
        String sql = "UPDATE DT_GameReport SET " +
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

    /**
     * 加總
     */
    public SqlRowSet getPlayerBetsReportbyGametype(String playGuid, String beginDateTime, String endDatetime) {
        String sql = "SELECT GR_GameType AS gameType" +
                "	,IFNULL(SUM(AG_SumBets), 0) AS bets " +
                "	,IFNULL(SUM(AG_SumNetWin), 0) AS netWin " +
                "FROM DT_GameReport " +
                "WHERE AGG_Time >= :BeginDate " +
                "	AND AGG_Time <= :EndDate " +
                "	AND PLY_GUID = :PlayerGuid " +
                "GROUP BY GR_GameType";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PlayerGuid", playGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得玩家曾經玩過的遊戲
     **/
    public SqlRowSet getPlayedGames(String playGuid, String beginDateTime, String endDatetime, int gameType) {
        String sql = "SELECT GR_GameCode as gameCode " +
                "	,DATE_FORMAT(MAX(AGG_Time), '%Y-%m-%d') AS gameDate " +
                "FROM DT_GameReport " +
                "WHERE AGG_Time >= :BeginDate " +
                "	AND AGG_Time <= :EndDate " +
                "	AND PLY_GUID = :PlayerGuid " +
                "	AND GR_GameType = :GameType " +
                "GROUP BY GR_GameCode";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PlayerGuid", playGuid);
        parameters.put("GameType", gameType);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    /**
     * 取得玩家每日下注紀錄
     **/
    public SqlRowSet getPlayerDailyBetHistory(String playGuid, String beginDateTime, String endDatetime) {
        String sql = "SELECT DATE_FORMAT(AGG_Time, '%Y-%m-%d') AS playDate " +
                "	,GR_GameType AS gameType " +
                "	,GR_GameCode AS gameCode " +
                "	,SUM(AG_SumBets) AS bets " +
                "	,SUM(AG_SumNetWin) AS netWin " +
                "FROM DT_GameReport " +
                "WHERE AGG_Time >= :BeginDate " +
                "	AND AGG_Time <= :EndDate " +
                "	AND PLY_GUID = :PlayerGuid " +
                "GROUP BY playDate, GR_GameCode ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("BeginDate", beginDateTime);
        parameters.put("EndDate", endDatetime);
        parameters.put("PlayerGuid", playGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

    }

    /**
     * 前端frontend使用
     *
     * @param plyGUID
     * @param gameCode
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getGameDayList(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT GR_GameCode AS gameCode, DATE_FORMAT(AGG_Time, \"%Y-%m-%d\") AS gameTime,SUM(AG_SumBets) as gameBets, "
                + " SUM(AG_SumWin) as win , "
                + " SUM(AG_SumNetWin) as netWin "
                + " FROM DT_GameReport "
                + " WHERE  PLY_GUID = :PlyGuid AND GR_GameCode=:GM_GameCode AND AGG_Time >= :BeginDateTime AND AGG_Time < :EndDateTime GROUP BY DATE_FORMAT(AGG_Time, \"%Y%m%d\") ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得歷史下注總額
     *
     * @param plyGuid
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getHistorySumBets(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT IFNull(SUM(AG_SumBets),0) AS SumBets " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
//        String sql = " SELECT SUM(IFNULL(subSumBets.AG_SumBets,0)) AS SumBets " +
//                " FROM ( " +
//                " SELECT AG_SumBets " +
//                " FROM DT_GameReport " +
//                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate) AS subSumBets ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class);
    }

    /**
     * 取得歷史總輸贏
     *
     * @param plyGuid
     * @param startDate
     * @param endDate
     * @return
     */
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

    /**
     * 取得歷史總下注和總輸贏
     *
     * @param plyGuid
     * @param startDate
     * @param endDate
     * @return
     */
    public SqlRowSet getHistorySumBetsAndSumNetWin(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT IFNull(SUM(AG_SumBets),0) AS SumBets, IFNull(SUM(AG_SumNetWin),0) AS SumNetWin " +
                " FROM DT_GameReportDay " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得依遊戲類別為群組的輸贏結果
     *
     * @param plyGuid
     * @param startDate
     * @param endDate
     * @return
     */
    public SqlRowSet getNetWinGroupByGameType(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType,SUM(AG_SumNetWin) AS NetWin " +
                " FROM DT_GameReport " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " Group By GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得依日期為群組的輸贏結果
     *
     * @param plyGuid
     * @param startDate
     * @param endDate
     * @return
     */
    public SqlRowSet getNetWinGroupByDate(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT STR_TO_DATE(DATE_FORMAT(AGG_Time, '%Y-%m-%d 00:00:00'),'%Y-%m-%d') AS date,  " +
                " SUM(AG_SumNetWin) AS netWin " +
                " FROM DT_GameReport " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " Group By DAY(AGG_Time) " +
                " Order By DAY(AGG_Time) ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得依遊戲類別為群組的押注結果
     *
     * @param plyGuid
     * @param startDate
     * @param endDate
     * @return
     */
    public SqlRowSet getBetsGroupByGame(String plyGuid, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType,SUM(AG_SumBets) AS bets " +
                " FROM DT_GameReport " +
                " WHERE PLY_GUID = :PlyGuid AND AGG_Time >= :StartDate AND AGG_Time < :EndDate " +
                " Group By GR_GameType " +
                " Order By GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportInfo(List<Integer> gameType, Date startDate, Date endDate, String agentGuid, int level) {
        String sql = " SELECT GR_GameType AS gameType , GR_GameCode AS gameCode , GR_Currency AS currency, " +
                " SUM(AG_SumBets) AS sumBets , SUM(AG_SumValidBets) AS sumValidBets , SUM(AG_SumWin) AS sumWin ," +
                " SUM(AG_SumJackpotContribute) AS sumJackpotContribute , SUM(AG_SumJackpot) AS sumJackpot," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, SUM(AG_SumNetWin) AS sumNetWin, " +
                " SUM(AGG_Items) AS items , SUM(AG_Commission) AS commission  " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        sql += " GROUP BY GR_GameType,GR_GameCode,GR_Currency ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportInfoByGameCode(List<Integer> gameType, List<Integer> gameCode, Date startDate, Date endDate, String agentGuid, int level) {
        String sql = " SELECT GR_GameType AS gameType , GR_GameCode AS gameCode , GR_Currency AS currency, " +
                " SUM(AG_SumBets) AS sumBets , SUM(AG_SumValidBets) AS sumValidBets , SUM(AG_SumWin) AS sumWin ," +
                " SUM(AG_SumJackpotContribute) AS sumJackpotContribute , SUM(AG_SumJackpot) AS sumJackpot ," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_SumNetWin) AS sumNetWin, " +
                " SUM(AGG_Items) AS items , SUM(AG_Commission) AS commission  " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND GR_GameCode IN (:GameCode) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        sql += " GROUP BY GR_GameType,GR_GameCode,GR_Currency ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("GameType", gameType);
        parameters.put("GameCode", gameCode);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportInfoByPlayer(String playerAccount, List<Integer> gameType, List<Integer> gameCode, Date startDate, Date endDate, String agentGuid, int level) {
        String sql = " SELECT GR_GameType AS gameType , GR_GameCode AS gameCode , GR_Currency AS currency, " +
                " SUM(AG_SumBets) AS sumBets , SUM(AG_SumValidBets) AS sumValidBets , SUM(AG_SumWin) AS sumWin ," +
                " SUM(AG_SumJackpotContribute) AS sumJackpotContribute , SUM(AG_SumJackpot) AS sumJackpot ," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_SumNetWin) AS sumNetWin, " +
                " SUM(AGG_Items) AS items , SUM(AG_Commission) AS commission  " +
                " FROM DT_GameReport " +
                " WHERE PLY_AccountID = :PlayerAccount AND GR_GameType In (:GameType) AND GR_GameCode IN (:GameCode) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        sql += " GROUP BY GR_GameType,GR_GameCode,GR_Currency ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlayerAccount", playerAccount);
        parameters.put("GameType", gameType);
        parameters.put("GameCode", gameCode);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportInfoByAgent3(List<Integer> gameType, List<Integer> gameCode, Date startDate, Date endDate, String agentGuid) {
        String sql = " SELECT GR_GameType AS gameType , GR_GameCode AS gameCode , GR_Currency AS currency, " +
                " SUM(AG_SumBets) AS sumBets , SUM(AG_SumValidBets) AS sumValidBets , SUM(AG_SumWin) AS sumWin ," +
                " SUM(AG_SumJackpotContribute) AS sumJackpotContribute , SUM(AG_SumJackpot) AS sumJackpot ," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3," +
                " SUM(AG_SumNetWin) AS sumNetWin, " +
                " SUM(AGG_Items) AS items , SUM(AG_Commission) AS commission  " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND GR_GameCode IN (:GameCode) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        sql += " AND AGT_Agent3 = :AgentGuid ";
        sql += " GROUP BY GR_GameType,GR_GameCode,GR_Currency ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("GameType", gameType);
        parameters.put("GameCode", gameCode);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public SqlRowSet getGameSummaryReport(List<Integer> gameType, Date startDate, Date endDate, String agentGuid, int level) {
        String sql = "SELECT GR_Currency AS currency, GR_GameType AS gameType , SUM(AG_SumBets) AS sumBets , SUM(AG_SumValidBets) AS sumValidBets , " +
                " SUM(AG_SumWin) AS sumWin , SUM(AG_SumJackpotContribute) AS SumJackpotContribute , SUM(AG_SumJackpot) AS sumJackpot," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin ," +
                " SUM(AGG_Items) AS items , GR_Currency AS currency " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        sql += " GROUP BY GR_GameType ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getTotalSummaryReport(List<Integer> gameType, Date startDate, Date endDate, String agentGuid, int level) {
        String sql = "SELECT IFNULL(GR_Currency, \"\") AS currency, IFNULL(SUM(AG_SumBets), 0) AS sumBets," +
                " IFNULL(SUM(AG_SumValidBets), 0) AS sumValidBets, IFNULL(SUM(AG_SumWin), 0) AS sumWin," +
                " IFNULL(SUM(AG_SumJackpotContribute), 0) AS SumJackpotContribute, IFNULL(SUM(AG_SumJackpot), 0) AS sumJackpot," +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3," +
                " IFNULL(SUM(AG_Commission), 0) AS commission," +
                " IFNULL(SUM(AG_SumNetWin), 0) AS sumNetWin , IFNULL(SUM(AGG_Items), 0) AS items" +
                " FROM DT_GameReport" +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate ";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getPlayerSummaryReport(List<Integer> gameType, Date starDate, Date endDate, String agentGuid, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String sql = " SELECT PLY_AccountID AS accountID, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets , " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3," +
                " SUM(AG_Commission) AS commission," +
                " SUM(AG_SumNetWin) AS sumNetWin , SUM(AGG_Items) AS items " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND AGT_Agent3 = :AgentGuid ";
        sql += " GROUP BY PLY_AccountID,GR_Currency ";
        sql += " ORDER BY sumBets DESC ";
        sql += " LIMIT :Pos , :PageSize ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得選擇的遊戲類型及日期區間內，某代理底下以玩家帳號及幣別Group By取得總筆數(分頁用)
     *
     * @param gameType
     * @param starDate
     * @param endDate
     * @param agentGuid
     * @return
     */
    public int getPlayerSummaryReportCount(List<Integer> gameType, Date starDate, Date endDate, String agentGuid) {
        String sql = " SELECT PLY_AccountID,GR_Currency " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND AGT_Agent3 = :AgentGuid ";
        sql += " GROUP BY PLY_AccountID,GR_Currency";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        sql = " SELECT COUNT(1) as count FROM ( " + sql + " ) AS X";
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        int count = 0;
        if (rowSet.next()) {
            count = rowSet.getInt("count");
        }
        return count;
    }

    public SqlRowSet getAgentSummaryReport(List<Integer> gameType, Date starDate, Date endDate, String agentGuid, int level, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String agentSelectField = "AGT_Agent" + (level + 1);
        String agentWhereField = "AGT_Agent" + level;
        String sql = " SELECT " + agentSelectField + " AS agentGuid , GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission ," +
                " SUM(AG_SumNetWin) AS sumNetWin , SUM(AGG_Items) AS items " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND " + agentWhereField + " = :AgentGuid ";
        sql += " GROUP BY " + agentSelectField + ",GR_Currency ";
        sql += " ORDER BY sumBets DESC ";
        sql += " LIMIT :Pos , :PageSize ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得選擇的遊戲類型及日期區間內，某代理底下以代理帳號及幣別Group By取得總筆數(分頁用)
     *
     * @param gameType
     * @param starDate
     * @param endDate
     * @param agentGuid
     * @param level
     * @return
     */
    public int getAgentSummaryReportCount(List<Integer> gameType, Date starDate, Date endDate, String agentGuid, int level) {
        String agentSelectField = "AGT_Agent" + (level + 1);
        String agentWhereField = "AGT_Agent" + level;
        String sql = " SELECT " + agentSelectField + ",GR_Currency " +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND " + agentWhereField + " = :AgentGuid ";
        sql += " GROUP BY " + agentSelectField + ",GR_Currency ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        sql = " SELECT COUNT(0) as count FROM ( " + sql + " ) AS X";
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        int count = 0;
        if (rowSet.next()) {
            count = rowSet.getInt("count");
        }
        return count;
    }

    public SqlRowSet getSinglePlayerSummaryReport(String agentGuid, int level, List<Integer> gameType, Date starDate, Date endDate, String playerID) {
        String sql = "SELECT AGT_Agent3 as agent, GR_GameType as gameType, PLY_AccountID AS accountID, GR_Currency AS currency" +
                ", SUM(AG_SumBets) AS sumBets, SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin" +
                ", SUM(AG_SumJackpotContribute) AS sumJackpotContribute, SUM(AG_SumJackpot) AS sumJackpot" +
                ", IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3" +
                ", SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin, SUM(AGG_Items) AS items" +
                " FROM DT_GameReport" +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND PLY_AccountID = :PlayerID";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        sql += " GROUP BY AGT_Agent3, GR_GameType, GR_Currency ORDER BY sumBets DESC";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("AgentGuid", agentGuid);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("PlayerID", playerID);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSinglePlayerSummaryReportGroupByAgent3(String agentGuid, int level, List<Integer> gameType, Date starDate, Date endDate, String playerID) {
        String sql = "SELECT AGT_Agent3 as agent, GR_GameType as gameType, PLY_AccountID AS accountID, GR_Currency AS currency" +
                ", SUM(AG_SumBets) AS sumBets, SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin" +
                ", SUM(AG_SumJackpotContribute) AS sumJackpotContribute, SUM(AG_SumJackpot) AS sumJackpot" +
                ", IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2 , IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3" +
                ", SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin, SUM(AGG_Items) AS items" +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND PLY_AccountID = :PlayerID";
        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = " + " :AgentGuid ";
        sql += " GROUP BY AGT_Agent3, GR_Currency ORDER BY sumBets DESC";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("AgentGuid", agentGuid);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("PlayerID", playerID);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSinglePlayerSummaryReportByAgent3List(List<String> agentGuidList, List<Integer> gameType, Date starDate, Date endDate, String playerID) {
        String sql = "SELECT AGT_Agent3 as agent, GR_GameType as gameType, PLY_AccountID AS accountID, GR_Currency AS currency" +
                ", SUM(AG_SumBets) AS sumBets, SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin" +
                ", SUM(AG_SumJackpotContribute) AS sumJackpotContribute, SUM(AG_SumJackpot) AS sumJackpot" +
                ", IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2 , IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3" +
                ", SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin, SUM(AGG_Items) AS items" +
                " FROM DT_GameReport " +
                " WHERE GR_GameType In (:GameType) AND AGT_Agent3 IN (:agent3) AND AGG_Time >= :StartDate AND AGG_Time < :EndDate AND PLY_AccountID = :PlayerID";

        sql += " GROUP BY AGT_Agent3, GR_Currency ORDER BY sumBets DESC";
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("agent3", agentGuidList);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("PlayerID", playerID);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getTodayBetsListByHour(String agtGuid, int level, Date startTime, Date endTime) {
        String sql = "SELECT SUM(AG_SumBets) as sumbets,SUM(AG_SumNetWin) as sumNetWin, AGG_Time" +
                " FROM DT_GameReport WHERE  AGG_Time >= :StartDate AND AGG_Time < :EndDate";
        if (level == 1) {
            sql += " AND AGT_Agent1=:agtGuid";
        }
        if (level == 2) {
            sql += " AND AGT_Agent2=:agtGuid";
        }
        if (level == 3) {
            sql += " AND AGT_Agent3=:agtGuid";
        }
        sql += " GROUP BY AGG_Time ASC ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("agtGuid", agtGuid);
        parameters.put("StartDate", startTime);
        parameters.put("EndDate", endTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getDailyReportByDate(List<Integer> gameType, Date startDate, Date endDate, String agentGuid, int level) {
        String agentWhereField = "AGT_Agent" + level;
        String sql = "SELECT Date(AGG_Time) AS accountDate , GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, " +
                " SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin , SUM(AG_SumJackpotContribute) AS sumJackpotContribute, " +
                " SUM(AG_SumJackpot) AS sumJackpot, IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission ," +
                " SUM(AG_SumNetWin) AS sumNetWin , SUM(AGG_Items) AS items " +
                " FROM DT_GameReport " +
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

    public void delete(String agentID, Date startTime) {
        String sql = "DELETE FROM DT_GameReport WHERE AGG_Time = :StartTime ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("StartTime", startTime);
        if (!StringUtil.isNullOrEmpty(agentID)) {
            sql += " AND AGT_Agent1 = :AgentID ";
            parameters.put("AgentID", agentID);
        }
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public int getCountByRange(String agentID, Date startTime, Date endTime) {
        String sql = " SELECT IfNull(SUM(AGG_Items),0) as dataCount FROM DT_GameReport " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        if (!StringUtil.isNullOrEmpty(agentID)) {
            sql += " AND AGT_Agent1 = :AgentID ";
            parameters.put("AgentID", agentID);
        }
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }


    public List<GameReportModel> getPlayerDataByDateAndAgent3(String agentID, Date startTime, Date endTime) {

        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,PLY_GUID,PLY_AccountID,SUM(AGG_Items) as AGG_Items, " +
                " GR_GameType,GR_GameCode, DATE_FORMAT(AGG_Time, '%Y-%m-%d') as AGG_Time,GR_Currency,SUM(AG_SumBets) as AG_SumBets,SUM(AG_SumValidBets) as AG_SumValidBets, SUM(AG_SumWin) as AG_SumWin," +
                " SUM(AG_SumJackpot) as AG_SumJackpot, " +
                " SUM(AG_SumJackpot2) as AG_SumJackpot2, " +
                " SUM(AG_SumJackpot3) as AG_SumJackpot3, " +
                " SUM(AG_SumJackpotContribute) as AG_SumJackpotContribute, SUM(AG_Commission) as AG_Commission, SUM(AG_SumNetWin) as AG_SumNetWin " +
                " FROM DT_GameReport " +
                " WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime AND AGT_Agent3=:AgentID " +
                " GROUP BY PLY_GUID,GR_GameCode";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        parameters.put("AgentID", agentID);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameReportModel.class));
    }

    public List<GameReportModel> getPlayerDataByDate(Date startTime, Date endTime) {

        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,PLY_GUID,PLY_AccountID,SUM(AGG_Items) as AGG_Items, " +
                "  GR_GameType,GR_GameCode, DATE_FORMAT(AGG_Time, '%Y-%m-%d') as AGG_Time,GR_Currency,SUM(AG_SumBets) as AG_SumBets,SUM(AG_SumValidBets) as AG_SumValidBets, SUM(AG_SumWin) as AG_SumWin," +
                "  SUM(AG_SumJackpot) as AG_SumJackpot, " +
                "  SUM(AG_SumJackpot2) as AG_SumJackpot2," +
                "  SUM(AG_SumJackpot3) as AG_SumJackpot3," +
                "  SUM(AG_SumJackpotContribute) as AG_SumJackpotContribute, SUM(AG_Commission) as AG_Commission, SUM(AG_SumNetWin) as AG_SumNetWin " +
                "  FROM DT_GameReport " +
                "  WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime " +
                "  GROUP BY PLY_GUID,GR_GameCode";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameReportModel.class));
    }

    public SqlRowSet getGameReportByAgent(List<String> agtGuids, List<Integer> gameTypes, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1 AS agent1, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets,  SUM(AG_SumValidBets) AS sumValidBets, " +
                " SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute, SUM(AG_SumJackpot) AS sumJackpot, " +
                " IFNULL(SUM(AG_SumJackpot2), 0.00) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0.00) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission, SUM(AG_SumNetWin) AS sumNetWin,  SUM(AGG_Items) AS items " +
                " FROM DT_GameReport " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate AND AGT_Agent1 IN (:AGT_Agent1) AND GR_GameType IN (:GR_GameType) " +
                " GROUP BY AGT_Agent1 ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AGT_Agent1", agtGuids);
        parameters.put("GR_GameType", gameTypes);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSummaryAgentReport(List<String> agent3List, List<Integer> gameTypes, Date startDate, Date endDate) {

        String sql = " SELECT AGT_Agent3 AS agent3, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets, SUM(AG_SumValidBets) AS sumValidBets, " +
                " SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute, SUM(AG_SumJackpot) AS sumJackpot, " +
                " IFNULL(SUM(AG_SumJackpot2), 0.00) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0.00) AS sumJackpot3, " +
                " SUM(AG_Commission) AS commission ,SUM(AG_SumNetWin) AS sumNetWin ,SUM(AGG_Items) AS items " +
                " FROM DT_GameReport " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate  AND AGT_Agent3 IN (:AGT_Agent3) " +
                " AND GR_GameType IN (:GR_GameType) " +
                " GROUP BY AGT_Agent3 ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AGT_Agent3", agent3List);
        parameters.put("GR_GameType", gameTypes);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getAgentGameReport(List<String> agent3List, List<Integer> gameTypes, Date startDate, Date endDate) {

        String sql = " SELECT AGT_Agent3 AS agent3,GR_GameCode AS gameCode, GR_Currency AS currency, SUM(AG_SumBets) AS sumBets,  SUM(AG_SumValidBets) AS sumValidBets, " +
                " SUM(AG_SumWin) AS sumWin, SUM(AG_SumJackpotContribute) AS sumJackpotContribute,  SUM(AG_SumJackpot) AS sumJackpot, " +
                " IFNULL(SUM(AG_SumJackpot2), 0.00) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0.00) AS sumJackpot3,  " +
                " SUM(AG_Commission) AS commission , SUM(AG_SumNetWin) AS sumNetWin ,  SUM(AGG_Items) AS items " +
                " FROM DT_GameReportDay " +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate  AND AGT_Agent3 IN (:AGT_Agent3) " +
                " AND GR_GameType IN (:GR_GameType) " +
                " GROUP BY GR_GameCode, AGT_Agent3, GR_Currency";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AGT_Agent3", agent3List);
        parameters.put("GR_GameType", gameTypes);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getGameReportDayByAgent3(String agentGuid, Date startDate, Date endDate) {
        String sql = " SELECT GR_GameType AS gameType, GR_GameCode AS gameCode, GR_Currency AS currency, " +
                " SUM(AG_SumBets) AS sumBets, SUM(AG_SumValidBets) AS sumValidBets, SUM(AG_SumWin) AS sumWin, " +
                " SUM(AG_SumJackpotContribute) AS sumJackpotContribute, SUM(AG_SumJackpot) AS sumJackpot, " +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, SUM(AG_SumNetWin) AS sumNetWin, " +
                " SUM(AGG_Items) AS items, SUM(AG_Commission) AS commission  " +
                " FROM DT_GameReportDay" +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate AND AGT_Agent3 = :AgentGuid " +
                " GROUP BY GR_GameCode";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotReportByAgent3(String agentGuid, Date startDate, Date endDate) {
        String sql = " SELECT IFNULL(SUM(AG_SumJackpotContribute), 0) AS sumJackpotContribute, IFNULL(SUM(AG_SumJackpot),0) AS sumJackpot, " +
                " IFNULL(SUM(AG_SumJackpot2), 0) AS sumJackpot2, IFNULL(SUM(AG_SumJackpot3), 0) AS sumJackpot3, SUM(AGG_Items) AS items " +
                " FROM DT_GameReport" +
                " WHERE AGG_Time >= :StartDate AND AGG_Time < :EndDate AND AGT_Agent3 = :AgentGuid ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
