package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
import com.icrown.gameapi.models.CamAwardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CampaignAwardDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    DateUtil dateUtil;

    public SqlRowSet getCampaignAwardByPlyGuidAndCampaignCode(int camCode, String plyGuid) {
        String sql = " SELECT CAM_AwardID,DT_SEQ,PLY_GUID,PLY_AccountID,MCT_Domain,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID, " +
                " DT_Currency,CAM_Code,CAM_Stage,CAM_AwardStartTakeTime,CAM_DateLine,CAM_Win, CAM_ImgID,CAM_AwardDescription,CAM_AwardTypeID, CAM_AwardTypeName, CAM_AwardCreateDateTime, CAM_Condition " +
                "FROM CAM_Award WHERE PLY_GUID = :PLY_GUID AND CAM_Code = :CAM_Code ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("CAM_Code", camCode);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardCountByPlyGuidAndCampaignCode(int camCode, String plyGuid) {
        String sql = " SELECT COUNT(1) as count FROM CAM_Award WHERE PLY_GUID = :PLY_GUID AND CAM_Code = :CAM_Code ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("CAM_Code", camCode);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardMaxConditionByPlyGuidAndCampaignCode(int camCode, String plyGuid) {
        String sql = " SELECT IFNull(SUM(IFNull(CAM_Condition,0)),0) as maxCondition FROM CAM_Award WHERE PLY_GUID = :PLY_GUID AND CAM_Code = :CAM_Code ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("CAM_Code", camCode);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardByTime(Date startTime, Date endTime) {
        String sql = " SELECT CAM_AwardID,DT_SEQ,PLY_GUID,PLY_AccountID,MCT_Domain,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID, " +
                " DT_Currency,CAM_Code,CAM_Stage,CAM_AwardStartTakeTime,CAM_DateLine,CAM_Win, CAM_AwardTypeID, CAM_AwardTypeName, CAM_AwardCreateDateTime, CAM_Condition " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime; ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardByTimeAndAgtGuid(Date startTime, Date endTime, int level, String agtGuid, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String agentColumn = "";
        String groupByColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
            groupByColumn = "AGT_Agent2";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
            groupByColumn = "AGT_Agent3";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
            groupByColumn = "AGT_Agent3";
        }

        String sql = " SELECT MCT_Domain, " + groupByColumn + " AS agentGuid, " +
                " DT_Currency,CAM_Code,CAM_Stage,SUM(CAM_Win) AS sumCamWin, count(1) AS items " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " GROUP BY " + groupByColumn + " ORDER BY CAM_AwardCreateDateTime DESC ";
        sql += " LIMIT :Pos , :PageSize ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getCampaignAwardCountByTimeAndAgtGuid(Date startTime, Date endTime, int level, String agtGuid) {
        String agentColumn = "";
        String groupByColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
            groupByColumn = "AGT_Agent2";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
            groupByColumn = "AGT_Agent3";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
            groupByColumn = "AGT_Agent3";
        }

        String sql = " SELECT " + groupByColumn + " AS agentGuid " +
                " FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " GROUP BY " + groupByColumn;

        sql = " SELECT COUNT(0) as count FROM ( " + sql + " ) AS X ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        int count = 0;
        if (rowSet.first()) {
            count = rowSet.getInt("count");
        }
        return count;
    }

    public SqlRowSet getCampaignAwardByTimeAndAgtGuidDownload(Date startTime, Date endTime, int level, String agtGuid) {
        String agentColumn = "";
        String groupByColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
            groupByColumn = "AGT_Agent2";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
            groupByColumn = "AGT_Agent3";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
            groupByColumn = "AGT_Agent3";
        }

        String sql = " SELECT MCT_Domain, " + groupByColumn + " AS agentGuid, " +
                " DT_Currency,CAM_Code,CAM_Stage,SUM(CAM_Win) AS sumCamWin, count(1) AS items " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " GROUP BY " + groupByColumn + " ORDER BY CAM_AwardCreateDateTime DESC ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardTotalByTimeAndAgtGuid(Date startTime, Date endTime, int level, String agtGuid) {
        String agentColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
        }

        String sql = " SELECT SUM(CAM_Win) AS sumCamWin, count(1) AS items " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardSelfByTimeAndAgtGuid(Date startTime, Date endTime, int level, String agtGuid, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String agentColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
        }

        String sql = " SELECT MCT_Domain, " + agentColumn + " AS agentGuid, " +
                " DT_Currency,CAM_Code,CAM_Stage,SUM(CAM_Win) AS sumCamWin, count(1) AS items " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " GROUP BY " + agentColumn + " ORDER BY CAM_AwardCreateDateTime DESC ";
        sql += " LIMIT :Pos , :PageSize ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardSelfByTimeAndAgtGuidForDownload(Date startTime, Date endTime, int level, String agtGuid) {
        String agentColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
        }

        String sql = " SELECT MCT_Domain, " + agentColumn + " AS agentGuid, " +
                " DT_Currency,CAM_Code,CAM_Stage,SUM(CAM_Win) AS sumCamWin, count(1) AS items " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " GROUP BY " + agentColumn + " ORDER BY CAM_AwardCreateDateTime DESC ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignAwardTotalSelfByTimeAndAgtGuid(Date startTime, Date endTime, int level, String agtGuid) {
        String agentColumn = "";
        if (level == 1) {
            agentColumn = "AGT_Agent1";
        }
        if (level == 2) {
            agentColumn = "AGT_Agent2";
        }
        if (level == 3) {
            agentColumn = "AGT_Agent3";
        }

        String sql = " SELECT SUM(CAM_Win) AS sumCamWin, count(1) AS items " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getPlayerCampaignAwardByTimeAndAgent3(Date startTime, Date endTime, String agtGuid, int pageIndex, int pageSize) {
        int pos = (pageIndex - 1) * pageSize;
        String agentColumn = "AGT_Agent3";

        String sql = " SELECT CAM_AwardID,DT_SEQ,PLY_GUID,PLY_AccountID,MCT_Domain,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID, " +
                " DT_Currency,CAM_Code,CAM_Stage,CAM_AwardStartTakeTime,CAM_DateLine,CAM_Win, CAM_AwardTypeID, CAM_AwardTypeName, CAM_AwardCreateDateTime, CAM_Condition " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " ORDER BY CAM_AwardCreateDateTime desc ";
        sql += " LIMIT :Pos , :PageSize ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        parameters.put("Pos", pos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getPlayerCampaignAwarTotalByTimeAndAgent3(Date startTime, Date endTime, String agtGuid) {
        String agentColumn = "AGT_Agent3";

        String sql = " SELECT  SUM(CAM_Win) AS sumCamWin, count(1) AS items  " +
                " FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " ORDER BY CAM_AwardCreateDateTime desc ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getPlayerCampaignAwardByTimeAndAgent3ForDownload(Date startTime, Date endTime, String agtGuid) {
        String agentColumn = "AGT_Agent3";

        String sql = " SELECT CAM_AwardID,DT_SEQ,PLY_GUID,PLY_AccountID,MCT_Domain,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID, " +
                " DT_Currency,CAM_Code,CAM_Stage,CAM_AwardStartTakeTime,CAM_DateLine,CAM_Win, CAM_AwardTypeID, CAM_AwardTypeName, CAM_AwardCreateDateTime, CAM_Condition " +
                "FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " ORDER BY CAM_AwardCreateDateTime desc ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getPlayerCampaignAwardCountByTimeAndAgent3(Date startTime, Date endTime, String agtGuid) {
        String agentColumn = "AGT_Agent3";

        String sql = " SELECT CAM_AwardID,DT_SEQ " +
                " FROM CAM_Award WHERE CAM_AwardCreateDateTime >= :startTime AND CAM_AwardCreateDateTime <= :endTime ";

        sql += " AND " + agentColumn + "= :agtGuid ";
        sql += " ORDER BY CAM_AwardCreateDateTime desc ";

        sql = " SELECT COUNT(0) as count FROM ( " + sql + " ) AS X ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agtGuid", agtGuid);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        int count = 0;
        if (rowSet.first()) {
            count = rowSet.getInt("count");
        }
        return count;
    }

    public boolean addCampaignAwardByModel(CamAwardModel award) {
        String sql = " INSERT INTO CAM_Award (CAM_AwardID,DT_SEQ,PLY_GUID,PLY_AccountID,MCT_Domain,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,DT_Currency,CAM_Code,CAM_Stage,CAM_AwardStartTakeTime,CAM_DateLine,CAM_Win,CAM_AwardTypeID,CAM_AwardTypeName,CAM_AwardCreateDateTime,CAM_Condition,CAM_ImgID,CAM_AwardDescription) " +
                " VALUES (:CAM_AwardID,:DT_SEQ,:PLY_GUID,:PLY_AccountID,:MCT_Domain,:AGT_Agent1,:AGT_Agent2,:AGT_Agent3,:AGT_AccountID,:DT_Currency,:CAM_Code,:CAM_Stage,:CAM_AwardStartTakeTime,:CAM_DateLine,:CAM_Win,:CAM_AwardTypeID,:CAM_AwardTypeName,:CAM_AwardCreateDateTime,:CAM_Condition,:CAM_ImgID,:CAM_AwardDescription); ";
        Map<String, Object> parameters = new HashMap<>(21);
        parameters.put("CAM_AwardID", award.getCamAwardID());
        parameters.put("DT_SEQ", award.getDtSeq());
        parameters.put("PLY_GUID", award.getPlyGuid());
        parameters.put("PLY_AccountID", award.getPlyAccountID());
        parameters.put("MCT_Domain", award.getMctDomain());
        parameters.put("AGT_Agent1", award.getAgtAgent1());
        parameters.put("AGT_Agent2", award.getAgtAgent2());
        parameters.put("AGT_Agent3", award.getAgtAgent3());
        parameters.put("AGT_AccountID", award.getAgtAccountID());
        parameters.put("DT_Currency", award.getDtCurrency());
        parameters.put("CAM_Code", award.getCamCode());
        parameters.put("CAM_Stage", award.getCamStage());
        parameters.put("CAM_AwardStartTakeTime", dateUtil.getDateFormat(award.getCamAwardStartTakeTime(), "yyyy-MM:dd HH:mm:ss.SSS"));
        parameters.put("CAM_DateLine", dateUtil.getDateFormat(award.getCamDateLine(), "yyyy-MM:dd HH:mm:ss.SSS"));
        parameters.put("CAM_Win", award.getCamWin());
        parameters.put("CAM_AwardTypeID", award.getCamAwardTypeID());
        parameters.put("CAM_AwardTypeName", award.getCamAwardTypeName());
        parameters.put("CAM_AwardCreateDateTime", award.getCamAwardCreateDateTime());
        parameters.put("CAM_Condition", award.getCamCondition());
        parameters.put("CAM_ImgID", award.getCamImgID());
        parameters.put("CAM_AwardDescription", award.getCamAwardDescription());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public SqlRowSet getCampaignAwardByIDList(List<String> camAwardIDList) {
        String sql = " SELECT　CAM_AwardID,PLY_GUID,PLY_AccountID,MCT_Domain,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID, " +
                " DT_Currency,CAM_Code,CAM_Stage,CAM_AwardTaked,CAM_AwardTakeTime,CAM_AwardStartTakeTime,CAM_DateLine,CAM_Win " +
                "FROM CAM_Award WHERE CAM_AwardID　IN(:CAM_AwardID); ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("CAM_AwardID", camAwardIDList);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public boolean updateCampaignAwardTakeAndWin(CamAwardModel model) {
        String selectSql = " SELECT COUNT(1) FROM CAM_Award " +
                " WHERE CAM_AwardID = :CAM_AwardID AND CAM_AwardTaked = 0 AND CAM_Win = 0 FOR UPDATE ; ";
        Map<String, Object> parameters1 = new HashMap<>(1);
        parameters1.put("CAM_AwardID", model.getCamAwardID());
        if (namedParameterJdbcTemplate.queryForObject(selectSql, parameters1, int.class) == 0) {
            return false;
        }

        String updateSql = " UPDATE CAM_Award SET CAM_AwardTaked = true, CAM_AwardTakeTime = NOW(), CAM_Win = :CAM_Win, CAM_AwardTypeID = :CAM_AwardTypeID; ";
        Map<String, Object> parameters2 = new HashMap<>(2);
        parameters2.put("CAM_Win", model.getCamWin());
        parameters2.put("CAM_AwardTypeID", model.getCamAwardTypeID());
        return namedParameterJdbcTemplate.update(updateSql, parameters2) > 0;
    }
}
