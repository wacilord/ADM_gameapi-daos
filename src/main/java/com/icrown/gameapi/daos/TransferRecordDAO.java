package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.ACC_TransferRecordModel;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Frank
 */
@Repository
public class TransferRecordDAO {
    private static final String SELECT_TRANSFER_SQL = " SELECT TFR_SEQ AS paySerialno, TFR_TransferID AS transferID,PLY_AccountID AS playerID,"
            + " TFR_CreateDatetime AS tradeTime,AGT_AccountID AS agentName,TFR_BeforeBalance as beforeBalance,"
            + " Acc_Code AS tradeType,AGP_Point AS tradePoint,TFR_AfterBalance AS afterBalance"
            + " FROM ACC_TransferRecord "
            + " WHERE AGT_Agent3 = :AgtGuid AND TFR_CreateDatetime >= :BeginDateTime AND TFR_CreateDatetime < :EndDateTime "
            + " AND AGP_Status = 1 ";
    private static final String SELECT_TRANSFER_SQL_BY_PLAYERID = SELECT_TRANSFER_SQL + "AND PLY_AccountID = :PlayerID";

    private static final String SELECT_TRANSFER_SQL_QUERY = "SELECT TFR_TransferID AS transferID, Acc_Code AS code, TFR_Currency AS currency,  " +
            " AGP_Point AS point, AGP_Status AS status, TFR_BeforeBalance AS beforeBalance, AGP_Point AS balance, " +
            " TFR_AfterBalance AS afterBalance,PLY_AccountID AS accountID, TFR_CreateDatetime AS createDateTime, AGT_Agent3 AS agent" +
            " FROM ACC_TransferRecord";

    private static final String SELECT_TRANSFER_RECORD = "SELECT TFR_TransferID AS transferID,PLY_AccountID AS playerID,"
            + " TFR_CreateDatetime AS tradeTime,AGT_AccountID AS agentName,TFR_BeforeBalance as beforeBalance,"
            + " Acc_Code AS tradeType,AGP_Point AS tradePoint,TFR_AfterBalance AS afterBalance"
            + " FROM ACC_TransferRecord ";

    private static final String SELECT_TRANSFER_RECORD_COUNT = "SELECT COUNT(1) FROM ACC_TransferRecord ";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得出入金紀錄
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getTradeRecordByTime(String agtGuid, Date beginDateTime, Date endDateTime) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForRowSet(SELECT_TRANSFER_SQL, parameters);
    }

    /**
     * 取得出入金紀錄
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @param playerID
     * @return
     */
    public SqlRowSet getTradeRecordByTimeAndPlayer(String agtGuid, Date beginDateTime, Date endDateTime, String playerID) {
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("PlayerID", playerID);
        return namedParameterJdbcTemplate.queryForRowSet(SELECT_TRANSFER_SQL_BY_PLAYERID, parameters);
    }


    public int getTradeRecordCountByTimeAndPlayer(String plyGUID, Date beginDateTime, Date endDateTime) {
        String sql = SELECT_TRANSFER_RECORD_COUNT
                + " WHERE  TFR_CreateDatetime >= :BeginDateTime AND TFR_CreateDatetime < :EndDateTime AND PLY_GUID = :PLY_GUID "
                + " AND AGP_Status = 1  ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("PLY_GUID", plyGUID);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getTradeRecordByTimeAndPlayer(String plyGUID, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = SELECT_TRANSFER_RECORD
                + " WHERE  TFR_CreateDatetime >= :BeginDateTime AND TFR_CreateDatetime < :EndDateTime AND PLY_GUID = :PLY_GUID "
                + " AND AGP_Status = 1  "
                + " ORDER BY TFR_CreateDatetime DESC "
                + " LIMIT :pos,:size";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("PLY_GUID", plyGUID);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public int getTradeRecordCountByTimeAndPlayerAndAccCode(String plyGUID, String accCode, Date beginDateTime, Date endDateTime) {
        String sql = SELECT_TRANSFER_RECORD_COUNT
                + " WHERE  TFR_CreateDatetime >= :BeginDateTime AND TFR_CreateDatetime < :EndDateTime AND PLY_GUID = :PLY_GUID "
                + " AND AGP_Status = 1  "
                + " AND Acc_Code = :Acc_Code  ";


        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PLY_GUID", plyGUID);
        parameters.put("Acc_Code", accCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getTradeRecordByTimeAndPlayerAndAccCode(String plyGUID, String accCode, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = SELECT_TRANSFER_RECORD
                + " WHERE  TFR_CreateDatetime >= :BeginDateTime AND TFR_CreateDatetime < :EndDateTime AND PLY_GUID = :PLY_GUID "
                + " AND AGP_Status = 1  "
                + " AND Acc_Code = :Acc_Code  "
                + " ORDER BY TFR_CreateDatetime DESC "
                + " LIMIT :pos,:size";

        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PLY_GUID", plyGUID);
        parameters.put("Acc_Code", accCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public SqlRowSet getTradeRecordByQuery(String agentGuid, String code, Date startDate, Date endDate, String plyGuid, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String sql = " SELECT TFR_TransferID AS transferID, Acc_Code AS code, TFR_Currency AS currency,  " +
                " AGP_Point AS point, AGP_Status AS status, TFR_BeforeBalance AS beforeBalance, AGP_Point AS balance, " +
                " TFR_AfterBalance AS afterBalance,PLY_AccountID AS accountID, TFR_CreateDatetime AS createDateTime " +
                " FROM ACC_TransferRecord" +
                " WHERE TFR_CreateDatetime >= :StartDate And TFR_CreateDatetime < :EndDate AND AGT_Agent3 = :AgentGuid ";
        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);
        if (!StringUtil.isNullOrEmpty(code)) {
            sql += " AND ACC_Code =:Code ";
            parameters.put("Code", code);
        }
        if (!StringUtil.isNullOrEmpty(plyGuid)) {
            sql += " AND PLY_GUID =:PlyGuid";
            parameters.put("PlyGuid", plyGuid);
        }
        sql += " ORDER BY TFR_CreateDatetime DESC ";
        sql += " limit :Pos , :PageSize ";
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getTradeRecordByQueryAndLevel(String agentGuid, String code, Date startDate, Date endDate, List<String> plyGuidList, int pageIndex, int pageSize, int level) {
        int pos = (pageIndex - 1) * pageSize;
        String sql = SELECT_TRANSFER_SQL_QUERY + " WHERE TFR_CreateDatetime >= :StartDate And TFR_CreateDatetime < :EndDate ";
        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        if (!StringUtil.isNullOrEmpty(code)) {
            sql += " AND ACC_Code =:Code ";
            parameters.put("Code", code);
        }
        if (!plyGuidList.isEmpty()) {
            sql += " AND PLY_GUID in (:PlyGuid)";
            parameters.put("PlyGuid", plyGuidList);
        }

        sql += " AND AGT_Agent" + level + " = :AgentGuid ";
        sql += " ORDER BY TFR_CreateDatetime DESC ";
        sql += " limit :Pos , :PageSize ";
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getTradeRecordByQueryCount(String agentGuid, String code, Date startDate, Date endDate, String plyGuid) {
        String sql = " SELECT count(1) " +
                " FROM ACC_TransferRecord " +
                " WHERE TFR_CreateDatetime >= :StartDate And TFR_CreateDatetime < :EndDate AND AGT_Agent3 = :AgentGuid ";
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);
        if (!StringUtil.isNullOrEmpty(code)) {
            sql += " AND ACC_Code =:Code ";
            parameters.put("Code", code);
        }
        if (!StringUtil.isNullOrEmpty(plyGuid)) {
            sql += " AND PLY_GUID =:PlyGuid";
            parameters.put("PlyGuid", plyGuid);
        }
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public int getTradeRecordByQueryCountAndLevel(String agentGuid, String code, Date startDate, Date endDate, List<String> plyGuidList, int level) {
        String sql = " SELECT count(1) " +
                " FROM ACC_TransferRecord " +
                " WHERE TFR_CreateDatetime >= :StartDate And TFR_CreateDatetime < :EndDate ";
        sql += " AND AGT_Agent" + level + " = :AgentGuid ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);
        parameters.put("AgentGuid", agentGuid);

        if (!StringUtil.isNullOrEmpty(code)) {
            sql += " AND ACC_Code =:Code ";
            parameters.put("Code", code);
        }
        if (!plyGuidList.isEmpty()) {
            sql += " AND PLY_GUID in (:PlyGuid)";
            parameters.put("PlyGuid", plyGuidList);
        }

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    /**
     * 看是否要跟 checkTransfer 合併
     *
     * @param agentGuid
     * @param transferID
     * @return
     */
    public SqlRowSet getTradeRecordByTransferID(String agentGuid, int level, String transferID) {
        String sql = SELECT_TRANSFER_SQL_QUERY + " WHERE TFR_TransferID = :TransferID";

        String fieldName = "AGT_Agent" + level;
        sql += " AND " + fieldName + " = :AgentGuid ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgentGuid", agentGuid);
        parameters.put("TransferID", transferID);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public Optional<ACC_TransferRecordModel> checkTransfer(String aGTGUID, String tFRTransferID) {
        String sql = "SELECT TFR_SEQ,TFR_TransferID,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGP_Status,AGP_Type,AGP_Point,TFR_AfterBalance,TFR_BeforeBalance,TFR_ErrorCode,TFR_FalseReason FROM ACC_TransferRecord WHERE AGT_Agent3=:AGT_Agent3 AND TFR_TransferID=:TFR_TransferID";

        Map<String, Object> parameters = new HashMap<>(2);

        parameters.put("TFR_TransferID", tFRTransferID);
        parameters.put("AGT_Agent3", aGTGUID);

        List<ACC_TransferRecordModel> acc_transferRecordModels = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(ACC_TransferRecordModel.class));


        if (acc_transferRecordModels.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(acc_transferRecordModels.get(0));
        }
    }


    public boolean isTransferRecordExist(String aGTGUID, String tFRTransferID) {
        Map<String, Object> parameters = new HashMap<>(2);
        String sql = "SELECT count(1) FROM ACC_TransferRecord WHERE AGT_Agent3=:AGT_Agent3 AND TFR_TransferID=:TFR_TransferID";
        parameters.put("TFR_TransferID", tFRTransferID);
        parameters.put("AGT_Agent3", aGTGUID);
        int count = (int) namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
        return count > 0;
    }

    /**
     * 取得 transfer SEQ
     *
     * @return
     */
    public long generateTransferSEQ() {
        Map<String, Object> parameters = new HashMap<>(0);
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT NEXTVAL(SEQ_Transfer)");
        return namedParameterJdbcTemplate.queryForObject(sql.toString(), parameters, long.class);
    }

    private Map<String, Object> getMapForInsertTransfer(long transferSEQ, String transferID, String agt_GUID1, String agt_GUID2, String agt_GUID3, BigDecimal pointAmount, BigDecimal beforeBalance, BigDecimal afterBalance, String ply_GUID, String aGT_Account_ID, String ply_AccountID, int accCode, int agpType, String currency) {
        Map<String, Object> parameters = new HashMap<>(16);
        parameters.put("TFR_TransferID", transferID);
        parameters.put("TFR_SEQ", transferSEQ);
        parameters.put("AGT_Agent1", agt_GUID1);
        parameters.put("AGT_Agent2", agt_GUID2);
        parameters.put("AGT_Agent3", agt_GUID3);
        parameters.put("AGP_Status", 1);
        parameters.put("AGP_Type", agpType);
        parameters.put("AGP_Point", pointAmount);
        parameters.put("TFR_AfterBalance", afterBalance);
        parameters.put("TFR_CallDatetime", new Date());
        parameters.put("PLY_GUID", ply_GUID);
        parameters.put("AGT_AccountID", aGT_Account_ID);
        parameters.put("PLY_AccountID", ply_AccountID);
        parameters.put("Acc_Code", accCode);
        parameters.put("TFR_BeforeBalance", beforeBalance);
        parameters.put("TFR_Currency", currency);
        return parameters;
    }

    public boolean insertTransfer(long transferSEQ, String agt_GUID1, String agt_GUID2, String agt_GUID3, String aGT_Account_ID, String ply_GUID, String ply_AccountID, String transferID, BigDecimal pointAmount, String currency, BigDecimal beforeBalance, int accCode, int agpType, BigDecimal afterBalance) {
        String sql = "INSERT INTO ACC_TransferRecord (TFR_TransferID,TFR_SEQ,"
                + "AGT_Agent1,"
                + "AGT_Agent2,"
                + "AGT_Agent3,"
                + "AGP_Status,"
                + "AGP_Type,"
                + "AGP_Point,"
                + "TFR_AfterBalance,"
                + "TFR_CallDatetime,"
                + "PLY_GUID,AGT_AccountID,PLY_AccountID,Acc_Code,TFR_BeforeBalance,TFR_Currency"
                + ") VALUES(:TFR_TransferID,:TFR_SEQ,:AGT_Agent1,:AGT_Agent2,:AGT_Agent3,:AGP_Status,:AGP_Type,:AGP_Point,:TFR_AfterBalance,:TFR_CallDatetime,:PLY_GUID,:AGT_AccountID,:PLY_AccountID,:Acc_Code,:TFR_BeforeBalance,:TFR_Currency)";
        Map<String, Object> parameters = getMapForInsertTransfer(transferSEQ, transferID, agt_GUID1, agt_GUID2, agt_GUID3, pointAmount, beforeBalance, afterBalance, ply_GUID, aGT_Account_ID, ply_AccountID, accCode, agpType, currency);
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }
}
