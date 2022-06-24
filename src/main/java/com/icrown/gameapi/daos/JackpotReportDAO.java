package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Tetsu
 */
@Repository
public class JackpotReportDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getJackpotAgent1Report(String agtGuid, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,SUM(GM_Jackpot) AS GM_Jackpot,GM_JackpotPoolType " +
                " FROM DT_Jackpot " +
                " WHERE AGT_Agent1= :AGT_Agent AND DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND GM_JackpotType=2 " +
                " GROUP BY AGT_Agent1,GM_JackpotPoolType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent", agtGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotAgent1ReportByAgent1List(List<String> agtGuids, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,SUM(GM_Jackpot) AS GM_Jackpot,GM_JackpotPoolType " +
                " FROM DT_Jackpot " +
                " WHERE AGT_Agent1 IN (:AGT_Agent) AND DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND GM_JackpotType=2 " +
                " GROUP BY AGT_Agent1,GM_JackpotPoolType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent", agtGuids);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotAgent2Report(String agtGuid, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,SUM(GM_Jackpot) AS GM_Jackpot,GM_JackpotPoolType " +
                " FROM DT_Jackpot " +
                " WHERE AGT_Agent2= :AGT_Agent AND DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND GM_JackpotType=2 " +
                " GROUP BY AGT_Agent2,GM_JackpotPoolType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent", agtGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotAgent3Report(String agtGuid, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,SUM(GM_Jackpot) AS GM_Jackpot,GM_JackpotPoolType " +
                " FROM DT_Jackpot " +
                " WHERE AGT_Agent3= :AGT_Agent AND DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND GM_JackpotType=2 " +
                " GROUP BY AGT_Agent3,GM_JackpotPoolType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent", agtGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotAgent2ReportByAgent1(String agtGuid, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,SUM(GM_Jackpot) AS GM_Jackpot,GM_JackpotPoolType " +
                " FROM DT_Jackpot " +
                " WHERE AGT_Agent1= :AGT_Agent AND DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND GM_JackpotType=2 " +
                " GROUP BY AGT_Agent2,GM_JackpotPoolType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent", agtGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotPlayerRecord(Date startDate, Date endDate, String agentGuid) {
        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3, DT_SEQ AS gameTurn, GM_JackpotID AS jackpotID,DT_UpdateTime AS gameTime, DT_Currency AS currency, AGT_AccountID AS agtAccountID, PLY_AccountID AS accountID" +
                ", GM_GameCode AS gameCode, GM_JackpotPoolType AS jackpotPoolType, GM_Jackpot AS jackpot2" +
                " FROM DT_Jackpot" +
                " WHERE DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND AGT_Agent3 = :AgentGUID" +
                " ORDER BY DT_UpdateTime DESC";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgentGUID", agentGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotPlayerRecordByPlyGuidList(Date startDate, Date endDate, List<String> plyGuids) {
        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3, DT_SEQ AS gameTurn, GM_JackpotID AS jackpotID,DT_UpdateTime AS gameTime, DT_Currency AS currency, AGT_AccountID AS agtAccountID, PLY_AccountID AS accountID" +
                ", GM_GameCode AS gameCode, GM_JackpotPoolType AS jackpotPoolType, GM_Jackpot AS jackpot2" +
                " FROM DT_Jackpot" +
                " WHERE DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND PLY_GUID in (:PLY_GUID)" +
                " ORDER BY DT_UpdateTime DESC";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("PLY_GUID", plyGuids);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotPlayerRecordByAgent1(Date startDate, Date endDate, String agentGuid) {
        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3, DT_SEQ AS gameTurn, GM_JackpotID AS jackpotID,DT_UpdateTime AS gameTime, DT_Currency AS currency, AGT_AccountID AS agtAccountID, PLY_AccountID AS accountID" +
                ", GM_GameCode AS gameCode, GM_JackpotPoolType AS jackpotPoolType, GM_Jackpot AS jackpot2" +
                " FROM DT_Jackpot" +
                " WHERE DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND AGT_Agent1 = :AgentGUID" +
                " ORDER BY DT_UpdateTime DESC";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgentGUID", agentGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotPlayerRecordByGameType(Date startDate, Date endDate, String agentGuid, List<Integer> gameTypeList) {
        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3, DT_SEQ AS gameTurn, GM_JackpotID AS jackpotID,DT_UpdateTime AS gameTime, DT_Currency AS currency, AGT_AccountID AS agtAccountID, PLY_AccountID AS accountID" +
                ", GM_GameCode AS gameCode, GM_JackpotPoolType AS jackpotPoolType, GM_Jackpot AS jackpot2" +
                " FROM DT_Jackpot" +
                " WHERE DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND AGT_Agent3 = :AgentGUID AND GM_GameType IN (:GM_GameType) " +
                " ORDER BY DT_UpdateTime DESC";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("AgentGUID", agentGuid);
        parameters.put("GM_GameType", gameTypeList);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotPlayerRecordByAgent1ByGameType(Date startDate, Date endDate, String agentGuid, List<Integer> gameTypeList) {
        String sql = "SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3, DT_SEQ AS gameTurn, GM_JackpotID AS jackpotID,DT_UpdateTime AS gameTime, DT_Currency AS currency, AGT_AccountID AS agtAccountID, PLY_AccountID AS accountID" +
                ", GM_GameCode AS gameCode, GM_JackpotPoolType AS jackpotPoolType, GM_Jackpot AS jackpot2 " +
                " FROM DT_Jackpot" +
                " WHERE DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND AGT_Agent1 = :AgentGUID AND GM_GameType IN (:GM_GameType) " +
                " ORDER BY DT_UpdateTime DESC";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("AgentGUID", agentGuid);
        parameters.put("GM_GameType", gameTypeList);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getJackpotAgent3ReportByAgent2(String agtGuid, Date startDate, Date endDate) {
        String sql = " SELECT AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,SUM(GM_Jackpot) AS GM_Jackpot,GM_JackpotPoolType " +
                " FROM DT_Jackpot " +
                " WHERE AGT_Agent2= :AGT_Agent AND DT_UpdateTime >= :startDate AND DT_UpdateTime < :endDate AND GM_JackpotType=2 " +
                " GROUP BY AGT_Agent3,GM_JackpotPoolType ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent", agtGuid);
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
