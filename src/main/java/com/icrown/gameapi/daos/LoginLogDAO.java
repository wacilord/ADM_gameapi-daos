package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dennis
 */
@Repository
public class LoginLogDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得登入紀錄
     *
     * @return 登入紀錄
     */
    public SqlRowSet getLoginLogList(String agtGuid, int pageIndex, int pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        String sql = "SELECT BL_CreateDateTime as createDateTime" +
                ", BL_IP as ip" +
                ", SAT_AccountID as loginID" +
                ", BL_ErrorCode as errorCode" +
                ", BL_ErrorMessage as errorMessage" +
                " FROM LOG_BackendLogin " +
                " WHERE AGT_GUID = :AgtGuid" +
                " ORDER BY BL_CreateDateTime DESC " +
                " LIMIT :offset, :pageSize";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("offset", offset);
        parameters.put("pageSize", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getTGAdminLoginLogList() {
        String sql = "SELECT ACT_AccountID,AL_IP,AL_ErrorCode,AL_CreateDateTime,AL_ErrorMessage FROM LOG_AdminLogin " +
                " WHERE  TO_DAYS(NOW()) - TO_DAYS(AL_CreateDateTime) <= 7  ORDER BY AL_CreateDateTime DESC";

        Map<String, Object> parameters = new HashMap<>(0);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 取得登入記錄總比數
     *
     * @return 總比數
     */
    public int getTotalCount(String agtGuid) {
        String sql = "SELECT COUNT(1) as totalCount" +
                " FROM LOG_BackendLogin " +
                " WHERE AGT_GUID=:AgtGuid";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AgtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    /**
     * 紀錄TGAdmin操作紀錄
     */
    public boolean logAction(String accountID, Date startTime, Date endTime, int roleID,String ip, String requestPath, String requestBody) {
        String sql = " INSERT INTO LOG_AdminAction (ACT_AccountID, AA_StartDateTime, AA_EndDateTime, AA_RoleID, AA_IP, AA_RequestPath, AA_RequestBody)" +
                " VALUES(:ACT_AccountID, :AA_StartDateTime, :AA_EndDateTime, :AA_RoleID, :AA_IP, :AA_RequestPath, :AA_RequestBody) ";

        Map<String, Object> parameters = new HashMap<>(8);
        parameters.put("ACT_AccountID", accountID);
        parameters.put("AA_StartDateTime", startTime);
        parameters.put("AA_EndDateTime", endTime);
        parameters.put("AA_RoleID", roleID);
        parameters.put("AA_IP", ip);
        parameters.put("AA_RequestPath", requestPath);
        parameters.put("AA_RequestBody", requestBody);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 取得TGAdmin操作紀錄
     */
    public SqlRowSet getlogAciton() {
        String sql = " SELECT ACT_AccountID, AA_StartDateTime, AA_EndDateTime, AA_RoleID, AA_IP, AA_RequestPath, AA_RequestBody FROM LOG_AdminAction " +
                " WHERE  TO_DAYS(NOW()) - TO_DAYS(AA_StartDateTime) <= 7  ORDER BY AA_StartDateTime DESC";

        Map<String, Object> parameters = new HashMap<>(0);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

}
