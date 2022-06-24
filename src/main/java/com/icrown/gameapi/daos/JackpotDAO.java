package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dennis
 */
@Repository
public class JackpotDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Map<String, BigDecimal>> getJackpotValue(String jackpotId) {
        String sql = "SELECT MiniValue,MiniMax,MiniMin" +
                ",MinorValue,MinorMax,MinorMin" +
                ",MajorValue,MajorMax,MajorMin" +
                ",GrandValue,GrandMax,GrandMin" +
                " FROM SYS_Jackpot WHERE Jackpot_ID = :Jackpot_ID";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Jackpot_ID", jackpotId);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        Map<String, Map<String, BigDecimal>> result = new HashMap<>(4);
        if (!rowSet.next()) {
            return result;
        }

        Map<String, BigDecimal> mini = new HashMap<>(3);
        Map<String, BigDecimal> minor = new HashMap<>(3);
        Map<String, BigDecimal> major = new HashMap<>(3);
        Map<String, BigDecimal> grand = new HashMap<>(3);

        mini.put("val", rowSet.getBigDecimal("MiniValue"));
        mini.put("max", rowSet.getBigDecimal("MiniMax"));
        mini.put("min", rowSet.getBigDecimal("MiniMin"));

        minor.put("val", rowSet.getBigDecimal("MinorValue"));
        minor.put("max", rowSet.getBigDecimal("MinorMax"));
        minor.put("min", rowSet.getBigDecimal("MinorMin"));

        major.put("val", rowSet.getBigDecimal("MajorValue"));
        major.put("max", rowSet.getBigDecimal("MajorMax"));
        major.put("min", rowSet.getBigDecimal("MajorMin"));

        grand.put("val", rowSet.getBigDecimal("GrandValue"));
        grand.put("max", rowSet.getBigDecimal("GrandMax"));
        grand.put("min", rowSet.getBigDecimal("GrandMin"));

        result.put("mini", mini);
        result.put("minor", minor);
        result.put("major", major);
        result.put("grand", grand);

        return result;
    }
    
    public Integer checkDailySumJackpotContributeExist(Date startTime, Date endTime) {
        String sql = " SELECT EXISTS(SELECT * FROM DT_JackpotContribute WHERE AGG_Time >= :StartTime AND AGG_Time < :EndTime LIMIT 1) " +
                " FROM DUAL; ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public int insertToDTJackpotContribute(Date aggTime, String agent1, String agent2, String agent3, String agtAccountID, BigDecimal sumJackpotContribute, String currencyy, String jackpotID, String gameCode) {
        String sql = " INSERT INTO DT_JackpotContribute(AGT_Agent1, AGT_Agent2, AGT_Agent3, AGT_AccountID, AGG_Time, AG_JackpotContribute," +
                " GM_JackpotID, DT_Currency, GR_GameCode) " +
                " VALUES(:Agent1, :Agent2, :Agent3, :AccountID, :Time, :SumJackpotContribute, :JackpotID, :Currency, :GameCode) ";
        Map<String, Object> parameters = new HashMap<>(8);
        parameters.put("Agent1", agent1);
        parameters.put("Agent2", agent2);
        parameters.put("Agent3", agent3);
        parameters.put("AccountID", agtAccountID);
        parameters.put("Time", aggTime);
        parameters.put("SumJackpotContribute", sumJackpotContribute);
        parameters.put("JackpotID", jackpotID);
        parameters.put("Currency", currencyy);
        parameters.put("GameCode", gameCode);

        return this.namedParameterJdbcTemplate.update(sql, parameters);
    }

    public SqlRowSet getJackpotInfo(String jackpotId) {
        String sql = "SELECT IFNULL (SUM(GM_Jackpot), 0) as totalJackpot, COUNT(0) as jackpotWinner" +
                " FROM DT_Jackpot WHERE GM_JackpotID = :jackpotId";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("jackpotId", jackpotId);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

    }
}
