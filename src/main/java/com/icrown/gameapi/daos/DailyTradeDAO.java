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
public class DailyTradeDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得DailyTrade資料GroupBy後變成GameRepot資料
     *
     * @param index
     * @param startTime
     * @param endTime
     * @param size
     * @return
     */
    public List<GameReportModel> getDailyTradeByIndexAndDate(long index, Date startTime, Date endTime, int delaySecond, int size) {
        String sql = "SELECT CONCAT(PLY_Guid,GM_GameCode,DATE_FORMAT(DT_UpdateTime, '%Y-%m-%d %H:00:00')) AS CheckKey, " +
                " CONCAT(PLY_Guid,GM_GameCode,DATE_FORMAT(DT_UpdateTime, '%Y-%m-%d 00:00:00')) AS CheckKeyByDate, " +
                " DT_UpdateTime, " +
                " DT_Index, " +
                " AGT_Agent1, " +
                " AGT_Agent2, " +
                " AGT_Agent3, " +
                " PLY_Guid, " +
                " PLY_AccountID, " +
                " 1 AS AGG_Items, " +
                " GM_GameType AS GR_GameType, " +
                " GM_GameCode AS GR_GameCode, " +
                " DATE_FORMAT(DT_UpdateTime, '%Y-%m-%d %H:00:00') AS AGG_Time, " +
                " DT_Currency AS GR_Currency, " +
                " IfNull(GM_Bets,0) AS AG_SumBets, " +
                " IfNull(GM_ValidBets,0) AS AG_SumValidBets, " +
                " IfNull(GM_Win,0) AS AG_SumWin, " +
                " IfNull(GM_Jackpot,0) AS AG_SumJackpot, " +
                " 0 AS AG_SumJackpot2 , " +
                " 0 AS AG_SumJackpot3 , " +
                " IfNull(GM_JackpotType,0) AS AG_JackpotType, " +
                " IfNull(GM_JackpotContribute,0) AS AG_SumJackpotContribute, " +
                " IfNull(GM_Commission,0) AS AG_Commission, " +
                " IfNull(GM_NetWin,0) AS AG_SumNetWin, " +
                " DT_CreateDateTime,DT_WorkType " +
                " FROM DT_DailyTrade " +
                " WHERE TIMEDIFF(DT_CreateDateTime,:StartTime) > 0 AND DT_CreateDateTime < :EndTime AND  DT_CreateDateTime < (CURRENT_TIMESTAMP(6) - INTERVAL :delaySecond SECOND)  " +
                " AND DT_Index > :Index " +
                " AND DT_WorkType = 2 " +
                " ORDER BY DT_Index ASC " +
                " LIMIT :size";
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("Index", index);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        parameters.put("size", size);
        parameters.put("delaySecond", delaySecond);
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameReportModel.class));
    }

    /**
     * 利用單號及代理Guid取得資料
     *
     * @param agentGuid
     * @param seq
     * @return
     */
    public SqlRowSet getRecordBySeq(String agentGuid, long seq) {
        String sql = " SELECT DT_SEQ as seq ,PLY_GUID , PLY_AccountID as accountID , GM_GameType as gameType, " +
                " GM_GameCode as gameCode, DT_Currency as currency , GM_Bets as bets , DT_WorkType as workType, " +
                " GM_ValidBets as validBets , GM_NetWin as netWin, DT_UpdateTime as startTime " +
                " FROM DT_DailyTrade " +
                " WHERE DT_SEQ = :Seq AND AGT_Agent3 = :AgentGuid ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Seq", seq);
        parameters.put("AgentGuid", agentGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getRecordBySeqAndLevel(String agentGuid, long seq, int level) {
        String sql = " SELECT DT_SEQ as seq ,PLY_GUID , PLY_AccountID as accountID , GM_GameType as gameType, " +
                " GM_GameCode as gameCode, DT_Currency as currency , GM_Bets as bets , DT_WorkType as workType, " +
                " GM_ValidBets as validBets , GM_NetWin as netWin, DT_UpdateTime as startTime " +
                " FROM DT_DailyTrade " +
                " WHERE DT_SEQ = :Seq ";
        sql += " AND AGT_Agent" + level + " =:AgentGuid ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Seq", seq);
        parameters.put("AgentGuid", agentGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getRecordByOnlySeq(long seq) {
        String sql = " SELECT DT_SEQ as seq ,PLY_GUID , PLY_AccountID as accountID , AGT_Agent3 as agent3,GM_GameType as gameType, " +
                " GM_GameCode as gameCode, DT_Currency as currency , GM_Bets as bets , DT_WorkType as workType, " +
                " GM_ValidBets as validBets , GM_NetWin as netWin, DT_UpdateTime as startTime " +
                " FROM DT_DailyTrade " +
                " WHERE DT_SEQ = :Seq ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Seq", seq);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得帳變紀錄
     *
     * @param playerGuidList
     * @param startDate
     * @param endDate
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public SqlRowSet getDailyTradeList(List<String> playerGuidList, Date startDate, Date endDate, Date partitionStart, Date partitionEnd, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String sql = " SELECT DT_SEQ AS seq ,IFNULL(TFR_TransferID,'') AS transferID, DT_UpdateTime AS updateDate, ACC_Code AS code, GM_GameType AS gameType , GM_GameCode AS gameCode , " +
                " DT_Currency AS currency, DT_TradePoint AS tradePoint , DT_AfterBalance AS afterBalance, PLY_GUID , AGT_Agent3 " +
                " FROM DT_DailyTrade " +
                " WHERE PLY_GUID IN (:PlyGuid) AND DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND DT_CreateDatetime > :PartitionStart AND DT_CreateDatetime <:PartitionEnd  " +
                " ORDER BY DT_CreateDatetime desc " +
                " LIMIT :Pos , :PageSize ";
        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("PlyGuid", playerGuidList);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("PartitionStart", partitionStart);
        parameters.put("PartitionEnd", partitionEnd);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getDailyTradeListWithAccCode(List<String> playerGuidList, Date startDate, Date endDate, Date partitionStart, Date partitionEnd, int pageIndex, int pageSize, List<Integer> queryType) {
        int pos = (pageIndex - 1) * pageSize;

        String sql = " SELECT DT_SEQ AS seq ,IFNULL(TFR_TransferID,'') AS transferID, DT_UpdateTime AS updateDate, ACC_Code AS code, GM_GameType AS gameType , GM_GameCode AS gameCode , " +
                " DT_Currency AS currency, DT_TradePoint AS tradePoint , DT_AfterBalance AS afterBalance, PLY_AccountID, PLY_GUID , AGT_Agent3 " +
                " FROM DT_DailyTrade " +
                " WHERE PLY_GUID IN (:PlyGuid) AND DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND DT_CreateDatetime > :PartitionStart AND DT_CreateDatetime <:PartitionEnd  " +
                " AND ACC_Code IN (:ACC_Code) " +
                " ORDER BY DT_CreateDatetime desc " +
                " LIMIT :Pos , :PageSize ";

        Map<String, Object> parameters = new HashMap<>(8);

        parameters.put("PlyGuid", playerGuidList);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("PartitionStart", partitionStart);
        parameters.put("PartitionEnd", partitionEnd);
        parameters.put("ACC_Code", queryType);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getDailyTradeListWithAccCode(List<String> playerGuidList, BigDecimal tradePoint, Date startDate, Date endDate, Date partitionStart, Date partitionEnd, int pageIndex, int pageSize, List<Integer> queryType) {
        int pos = (pageIndex - 1) * pageSize;
        String sql = "SELECT DT_SEQ, IFNULL(TFR_TransferID,'') AS TFR_TransferID, PLY_GUID, PLY_AccountID,DT_UpdateTime, " +
                "ACC_Code, DT_WorkType, AGT_Agent1, AGT_Agent2, AGT_Agent3,AGT_AccountID, DT_Currency, GM_GameType, GM_GameCode, " +
                " GM_Bets, GM_ValidBets, DT_BeforeBalance, DT_TradePoint, DT_AfterBalance, GM_Win, GM_JackpotType, GM_Jackpot, GM_JackpotContribute, " +
                " GM_Commission, GM_NetWin, DT_ClientType, DT_IPAddress, DT_CreateDatetime " +
                " FROM DT_DailyTrade " +
                " WHERE PLY_GUID IN (:PlyGuid) AND DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND DT_CreateDatetime > :PartitionStart " +
                " AND DT_CreateDatetime <:PartitionEnd AND ACC_Code IN (:ACC_Code) ";

        Map<String, Object> parameters = new HashMap<>(9);

        parameters.put("PlyGuid", playerGuidList);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("PartitionStart", partitionStart);
        parameters.put("PartitionEnd", partitionEnd);
        parameters.put("ACC_Code", queryType);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);

        if (tradePoint.compareTo(BigDecimal.ZERO) > 0) {
            sql += " AND DT_TradePoint >= :tradePoint ";
            parameters.put("tradePoint", tradePoint);
        }

        sql += " ORDER BY DT_CreateDatetime DESC " +
                " LIMIT :Pos, :PageSize ";

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSinglePlayerSummaryReportByAgent3List(List<String> agentGuidList, List<String> plyGuidList, List<Integer> gameType, Date starDate, Date endDate, Date partitionStartDate, Date partitionEndDate) {
        String sql = " SELECT AGT_Agent3 AS agent, GM_GameType AS gameType, PLY_AccountID AS accountID, DT_Currency AS currency, " +
                " SUM(GM_Bets) AS sumBets, SUM(GM_ValidBets) AS sumValidBets, SUM(GM_Win) AS sumWin, " +
                " SUM(GM_JackpotContribute) AS sumJackpotContribute, " +
                " SUM(IF(GM_JackpotType=1,IFNULL(GM_Jackpot,0),0)) AS sumJackpot, " +
                " SUM(IF(GM_JackpotType=2,IFNULL(GM_Jackpot,0),0)) AS sumJackpot2, " +
                " SUM(IF(GM_JackpotType=3,IFNULL(GM_Jackpot,0),0)) AS sumJackpot3, " +
                " SUM(GM_Commission) AS commission , SUM(GM_NetWin) AS sumNetWin, COUNT(1) AS items " +
                " FROM DT_DailyTrade " +
                " WHERE DT_CreateDatetime >= :partitionStartDate AND DT_CreateDatetime < :partitionEndDate AND DT_WorkType = 2  AND " +
                " DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND" +
                " AGT_Agent3 IN (:agent3) AND PLY_GUID IN (:PLY_GUID) AND GM_GameType IN (:GameType) " +
                " GROUP BY AGT_Agent3, DT_Currency ORDER BY sumBets DESC ";
        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("agent3", agentGuidList);
        parameters.put("GameType", gameType);
        parameters.put("StartDate", starDate);
        parameters.put("EndDate", endDate);
        parameters.put("partitionStartDate", partitionStartDate);
        parameters.put("partitionEndDate", partitionEndDate);
        parameters.put("PLY_GUID", plyGuidList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    /**
     * 取得帳變紀錄總筆數
     *
     * @param playerGuidList
     * @param startDate
     * @param endDate
     * @return
     */
    public int getDailyTradeListCount(List<String> playerGuidList, Date startDate, Date endDate, Date partitionStart, Date partitionEnd) {
        String sql = " SELECT COUNT(1) " +
                " FROM DT_DailyTrade " +
                " WHERE PLY_GUID IN (:PlyGuid) AND DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND DT_CreateDatetime > :PartitionStart AND DT_CreateDatetime <:PartitionEnd   ";
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("PlyGuid", playerGuidList);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("PartitionStart", partitionStart);
        parameters.put("PartitionEnd", partitionEnd);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public int getDailyTradeListCount2(List<String> playerGuidList, Date startDate, Date endDate, Date partitionStart, Date partitionEnd, List<Integer> queryType) {
        String sql = " SELECT COUNT(1) " +
                " FROM DT_DailyTrade " +
                " WHERE PLY_GUID IN (:PlyGuid) AND DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND DT_CreateDatetime > :PartitionStart AND DT_CreateDatetime <:PartitionEnd  " +
                " AND ACC_Code IN (:ACC_Code) ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlyGuid", playerGuidList);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("PartitionStart", partitionStart);
        parameters.put("PartitionEnd", partitionEnd);
        parameters.put("ACC_Code", queryType);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public int getDailyTradeListCount2(List<String> playerGuidList, BigDecimal tradePoint, Date startDate, Date endDate, Date partitionStart, Date partitionEnd, List<Integer> queryType) {
        String sql = " SELECT COUNT(1) " +
                " FROM DT_DailyTrade " +
                " WHERE PLY_GUID IN (:PlyGuid) AND DT_UpdateTime >= :StartDate AND DT_UpdateTime < :EndDate AND DT_CreateDatetime > :PartitionStart AND DT_CreateDatetime <:PartitionEnd  " +
                " AND ACC_Code IN (:ACC_Code) ";

        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("PlyGuid", playerGuidList);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("PartitionStart", partitionStart);
        parameters.put("PartitionEnd", partitionEnd);
        parameters.put("ACC_Code", queryType);
        if (tradePoint.compareTo(BigDecimal.ZERO) > 0) {
            sql += " AND DT_TradePoint >= :tradePoint ";
            parameters.put("tradePoint", tradePoint);
        }

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    private Map<String, Object> getMapForInsertDailytrade(long transferSEQ, String ply_GUID, String agt_GUID1, String agt_GUID2, String agt_GUID3, String agtAccountID, BigDecimal beforeBalance, BigDecimal pointAmount, BigDecimal afterBalance, String transferID, int accCode, String currency, String PlyAccountID, int dtWorkType) {
        Map<String, Object> parameters = new HashMap<>(15);
        parameters.put("DT_SEQ", transferSEQ);
        parameters.put("TFR_TransferID", transferID);
        parameters.put("PLY_GUID", ply_GUID);
        parameters.put("DT_UpdateTime", new Date());
        parameters.put("AGT_Agent1", agt_GUID1);
        parameters.put("AGT_Agent2", agt_GUID2);
        parameters.put("AGT_Agent3", agt_GUID3);
        parameters.put("AGT_AccountID", agtAccountID);
        parameters.put("DT_BeforeBalance", beforeBalance);
        parameters.put("DT_TradePoint", pointAmount);
        parameters.put("DT_AfterBalance", afterBalance);
        parameters.put("ACC_Code", accCode);
        parameters.put("DT_Currency", currency);
        parameters.put("PLY_AccountID", PlyAccountID);
        parameters.put("DT_WorkType", dtWorkType);
        return parameters;
    }

    public boolean insertDailyTrade(long transferSEQ, String agt_GUID1, String agt_GUID2, String agt_GUID3, String aGTAccountID, String ply_GUID, String ply_AccountID, String transferID, BigDecimal pointAmount, String currency, BigDecimal beforeBalance, BigDecimal afterBalance, int accCode, int dtWorkType) {
        String sql = " INSERT INTO DT_DailyTrade "
                + " 	(DT_SEQ,TFR_TransferID, "
                + " 	PLY_GUID, "
                + " 	DT_UpdateTime, "
                + " 	ACC_Code, "
                + " 	DT_WorkType, "
                + " 	AGT_Agent1, "
                + " 	AGT_Agent2, "
                + " 	AGT_Agent3, AGT_AccountID,"
                + " 	DT_BeforeBalance, "
                + " 	DT_TradePoint, "
                + " 	DT_AfterBalance ,DT_Currency,PLY_AccountID"
                + " 	)"
                + " 	VALUES"
                + " 	("
                + " 	:DT_SEQ,"
                + "      :TFR_TransferID, "
                + " 	:PLY_GUID, "
                + " 	:DT_UpdateTime, "
                + " 	:ACC_Code, "
                + " 	:DT_WorkType, "
                + " 	:AGT_Agent1, "
                + " 	:AGT_Agent2, "
                + " 	:AGT_Agent3, "
                + "     :AGT_AccountID,"
                + " 	:DT_BeforeBalance, "
                + " 	:DT_TradePoint, "
                + " 	:DT_AfterBalance ,:DT_Currency,:PLY_AccountID"
                + " 	)";

        Map<String, Object> parameters = getMapForInsertDailytrade(transferSEQ, ply_GUID, agt_GUID1, agt_GUID2, agt_GUID3, aGTAccountID, beforeBalance, pointAmount, afterBalance, transferID, accCode, currency, ply_AccountID, dtWorkType);

        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    public boolean insertFish(long dtSeq, String plyGuid, String plyAccountID, String dtUpdateTime, int accCode, int dtWorkType, String agtGuid1, String agtGuid2, String agtGuid3, String agtAccountID, int gameType, String gameCode, BigDecimal beforeBalance, BigDecimal tradePoint, BigDecimal afterBalance, String ipAddress, BigDecimal bets, BigDecimal validBets, BigDecimal win, BigDecimal netWin, int jackpotType, BigDecimal jackpot, BigDecimal jackpotContribute, BigDecimal commision, String currency) {
        String sql = "INSERT INTO DT_DailyTrade (DT_SEQ,PLY_GUID,PLY_AccountID,DT_UpdateTime,ACC_Code,DT_WorkType,"
                + " AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,GM_GameType,GM_GameCode,DT_BeforeBalance,DT_TradePoint,DT_AfterBalance,"
                + " DT_IPAddress,GM_Bets,GM_ValidBets,GM_Win,GM_NetWin,GM_JackpotType,GM_Jackpot,GM_JackpotContribute,"
                + " GM_Commission,DT_Currency)values("
                + " :DT_SEQ,:PLY_GUID,:PLY_AccountID,CAST(:DT_UpdateTime AS DATETIME(6)),:ACC_Code,:DT_WorkType,"
                + " :AGT_Agent1,:AGT_Agent2,:AGT_Agent3,:AGT_AccountID,:GM_GameType,:GM_GameCode,:DT_BeforeBalance,:DT_TradePoint,:DT_AfterBalance"
                + " ,:DT_IPAddress,:GM_Bets,:GM_ValidBets,:GM_Win,:GM_NetWin,:GM_JackpotType,:GM_Jackpot,:GM_JackpotContribute,"
                + " :GM_Commission,:DT_Currency"
                + " )";
        Map<String, Object> parameters = new HashMap<>(25);
        parameters.put("DT_SEQ", dtSeq);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("PLY_AccountID", plyAccountID);
        parameters.put("AGT_AccountID", agtAccountID);
        parameters.put("DT_UpdateTime", dtUpdateTime);
        parameters.put("ACC_Code", accCode);
        parameters.put("DT_WorkType", dtWorkType);
        parameters.put("AGT_Agent1", agtGuid1);
        parameters.put("AGT_Agent2", agtGuid2);
        parameters.put("AGT_Agent3", agtGuid3);
        parameters.put("GM_GameType", gameType);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("DT_BeforeBalance", beforeBalance);
        parameters.put("DT_TradePoint", tradePoint);
        parameters.put("DT_AfterBalance", afterBalance);
        parameters.put("DT_IPAddress", ipAddress);
        parameters.put("GM_Bets", bets);
        parameters.put("GM_ValidBets", validBets);
        parameters.put("GM_Win", win);
        parameters.put("GM_NetWin", netWin);
        parameters.put("GM_JackpotType", jackpotType);
        parameters.put("GM_Jackpot", jackpot);
        parameters.put("GM_JackpotContribute", jackpotContribute);
        parameters.put("GM_Commission", commision);
        parameters.put("DT_Currency", currency);
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    public boolean insertCampaignRecord(long dtSeq, String plyGuid, String plyAccountID, Date dtUpdateTime, int accCode, int dtWorkType, String agtGuid1, String agtGuid2, String agtGuid3, String agtAccountID, int gameType, String gameCode, BigDecimal beforeBalance, BigDecimal tradePoint, BigDecimal afterBalance, String ipAddress, BigDecimal bets, BigDecimal validBets, BigDecimal win, BigDecimal netWin, int jackpotType, BigDecimal jackpot, BigDecimal jackpotContribute, BigDecimal commision, String currency) {
        String sql = "INSERT INTO DT_DailyTrade (DT_SEQ,PLY_GUID,PLY_AccountID,DT_UpdateTime,ACC_Code,DT_WorkType,"
                + " AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,GM_GameType,GM_GameCode,DT_BeforeBalance,DT_TradePoint,DT_AfterBalance,"
                + " DT_IPAddress,GM_Bets,GM_ValidBets,GM_Win,GM_NetWin,GM_JackpotType,GM_Jackpot,GM_JackpotContribute,"
                + " GM_Commission,DT_Currency)values("
                + " :DT_SEQ,:PLY_GUID,:PLY_AccountID,CAST(:DT_UpdateTime AS DATETIME(6)),:ACC_Code,:DT_WorkType,"
                + " :AGT_Agent1,:AGT_Agent2,:AGT_Agent3,:AGT_AccountID,:GM_GameType,:GM_GameCode,:DT_BeforeBalance,:DT_TradePoint,:DT_AfterBalance"
                + " ,:DT_IPAddress,:GM_Bets,:GM_ValidBets,:GM_Win,:GM_NetWin,:GM_JackpotType,:GM_Jackpot,:GM_JackpotContribute,"
                + " :GM_Commission,:DT_Currency"
                + " )";
        Map<String, Object> parameters = new HashMap<>(25);
        parameters.put("DT_SEQ", dtSeq);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("PLY_AccountID", plyAccountID);
        parameters.put("AGT_AccountID", agtAccountID);
        parameters.put("DT_UpdateTime", dtUpdateTime);
        parameters.put("ACC_Code", accCode);
        parameters.put("DT_WorkType", dtWorkType);
        parameters.put("AGT_Agent1", agtGuid1);
        parameters.put("AGT_Agent2", agtGuid2);
        parameters.put("AGT_Agent3", agtGuid3);
        parameters.put("GM_GameType", gameType);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("DT_BeforeBalance", beforeBalance);
        parameters.put("DT_TradePoint", tradePoint);
        parameters.put("DT_AfterBalance", afterBalance);
        parameters.put("DT_IPAddress", ipAddress);
        parameters.put("GM_Bets", bets);
        parameters.put("GM_ValidBets", validBets);
        parameters.put("GM_Win", win);
        parameters.put("GM_NetWin", netWin);
        parameters.put("GM_JackpotType", jackpotType);
        parameters.put("GM_Jackpot", jackpot);
        parameters.put("GM_JackpotContribute", jackpotContribute);
        parameters.put("GM_Commission", commision);
        parameters.put("DT_Currency", currency);
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    /**
     * 補GameReport資料
     *
     * @param agentID
     * @param startTime
     * @param endTime
     * @return
     */
    public List<GameReportModel> getDailyTradeByHour(String agentID, Date startTime, Date endTime) {
        String sql = "SELECT " +
                " AGT_Agent1, " +
                " AGT_Agent2, " +
                " AGT_Agent3, " +
                " PLY_Guid, " +
                " PLY_AccountID, " +
                " Count(1) AS Agg_Items , " +
                " GM_GameType AS GR_GameType, " +
                " GM_GameCode AS GR_GameCode, " +
                " DATE_FORMAT(DT_UpdateTime, '%Y-%m-%d %H:00:00') AS AGG_Time, " +
                " DT_Currency AS GR_Currency, " +
                " Sum(IfNull(GM_Bets,0)) AS AG_SumBets, " +
                " Sum(IfNull(GM_ValidBets,0)) AS AG_SumValidBets, " +
                " Sum(IfNull(GM_Win,0)) AS AG_SumWin, " +
                " Sum(IF(GM_JackpotType<2,IFNULL(GM_Jackpot,0),0)) AS AG_SumJackpot, " +
                " Sum(IF(GM_JackpotType=2,IFNULL(GM_Jackpot,0),0)) AS AG_SumJackpot2, " +
                " Sum(IF(GM_JackpotType=3,IFNULL(GM_Jackpot,0),0)) AS AG_SumJackpot3, " +
                " GM_JackpotType AS AG_JackpotType, " +
                " Sum(IfNull(GM_JackpotContribute,0)) AS AG_SumJackpotContribute, " +
                " Sum(IfNull(GM_Commission,0)) AS AG_Commission, " +
                " Sum(IfNull(GM_NetWin,0)) AS AG_SumNetWin " +
                " FROM DT_DailyTrade " +
                " WHERE DT_UpdateTime >= :StartTime AND DT_UpdateTime < :EndTime AND DT_WorkType = 2 ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        if (!StringUtil.isNullOrEmpty(agentID)) {
            sql += " AND AGT_Agent1 = :AgentID ";
            parameters.put("AgentID", agentID);
        }
        sql += " Group By PLY_Guid,AGG_Time,GR_GameCode ";
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameReportModel.class));
    }

    /**
     * 取得某時間區間裡筆數合
     *
     * @param agentID
     * @param startTime
     * @param endTime
     * @return
     */
    public int getDailyTradeCountByRange(String agentID, Date startTime, Date endTime) {
        String sql = " SELECT COUNT(1) FROM DT_DailyTrade " +
                " WHERE DT_UpdateTime >= :StartTime AND DT_UpdateTime < :EndTime AND DT_WorkType = 2 ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        if (!StringUtil.isNullOrEmpty(agentID)) {
            sql += " AND AGT_Agent1 = :AgentID ";
            parameters.put("AgentID", agentID);
        }
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public boolean isSeqExist(long seq) {
        String sql = " SELECT COUNT(1) FROM DT_DailyTrade WHERE DT_SEQ=:DT_SEQ ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("DT_SEQ", seq);
        return (namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class) > 0);
    }

    public SqlRowSet getJackpotReport(Date startTime, Date endTime, Date partitionStart, Date partitionEnd, List<String> agentGuidList, List<Integer> gameType) {
        String sql = " SELECT DT_SEQ, PLY_AccountID, AGT_Agent3, AGT_AccountID, DT_Currency, GM_GameCode, GM_JackpotType, GM_Jackpot, IFNULL(GM_JackpotID,'') AS GM_JackpotID, IFNULL(GM_JackpotPoolType,'') AS GM_JackpotPoolType, DT_UpdateTime " +
                " FROM DT_DailyTrade WHERE AGT_Agent3 IN (:agent3List) " +
                " AND DT_CreateDatetime >= :PartitionStartTime AND  DT_CreateDatetime < :PartitionEndTime " +
                " AND DT_UpdateTime >= :StartTime AND  DT_UpdateTime < :EndTime AND DT_WorkType =2 AND GM_JackpotType = 2 AND GM_GameType IN (:GameType) ";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        parameters.put("PartitionStartTime", partitionStart);
        parameters.put("PartitionEndTime", partitionEnd);
        parameters.put("agent3List", agentGuidList);
        parameters.put("GameType", gameType);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getWinOverSpecificWinPoint(Date StartTime, Date EndTime, Date partitionStart, Date partitionEnd, int specificWinPoint, int gameType) {
        String sql = " SELECT DT_SEQ, DT_CreateDatetime, (SELECT AGT_AccountID FROM AGT_Agent WHERE AGT_GUID=dt.AGT_Agent1) AS agtAccount, AGT_Agent1, GM_GameCode, (SELECT PLY_AccountID FROM MEM_Player WHERE PLY_GUID=dt.PLY_GUID) AS plyAccount, PLY_GUID, GM_Bets, GM_Win " +
                " FROM DT_DailyTrade dt " +
                " WHERE DT_CreateDatetime >= :PartitionStartTime AND DT_CreateDatetime < :PartitionEndTime " +
                " AND GM_Win > :WinPoint AND GM_JackpotType = 0  AND DT_WorkType = 2  AND GM_GameType= :GameType " +
                " AND DT_UpdateTime >= :StartTime AND DT_UpdateTime < :EndTime ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PartitionStartTime", partitionStart);
        parameters.put("PartitionEndTime", partitionEnd);
        parameters.put("WinPoint", specificWinPoint);
        parameters.put("GameType", gameType);
        parameters.put("StartTime", StartTime);
        parameters.put("EndTime", EndTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSumWinByGameCodeUsingGameType(Date StartTime, Date EndTime, Date partitionStart, Date partitionEnd, List<Integer> gameType, List<String> agent1List) {
        String sql = " SELECT GM_GameType, GM_GameCode, SUM(GM_Bets) AS sumBet " +
                " FROM DT_DailyTrade " +
                " WHERE DT_CreateDatetime >= :PartitionStartTime AND DT_CreateDatetime < :PartitionEndTime " +
                " AND DT_WorkType = 2  AND GM_GameType IN (:GameType) " +
                " AND DT_UpdateTime >= :StartTime AND DT_UpdateTime < :EndTime " +
                " AND AGT_Agent1 NOT IN (:ExcludeAgent1List) " +
                " GROUP BY GM_GameType, GM_GameCode ";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PartitionStartTime", partitionStart);
        parameters.put("PartitionEndTime", partitionEnd);
        parameters.put("GameType", gameType);
        parameters.put("StartTime", StartTime);
        parameters.put("EndTime", EndTime);
        parameters.put("ExcludeAgent1List", agent1List);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
