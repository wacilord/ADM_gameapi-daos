package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TGAccountDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 以帳號取得會員的所有資料
     *
     * @param accountId
     * @return
     */
    public SqlRowSet getAccountById(String accountId) {

        String sql = " SELECT SYS_Account_ID, Role_ID, SYS_Password, SYS_Name, SYS_IsFirstLogin, " +
                " SYS_LastIP, SYS_LastLoginDatetime, SYS_CreateDatetime, SYS_RegainDatetime, " +
                " SYS_LoginErrorCount, SYS_Enable " +
                " FROM SYS_Account " +
                " WHERE SYS_Account_ID = :SYS_Account_ID";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SYS_Account_ID", accountId);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getAllAccount() {
        String sql = " SELECT SYS_Account_ID, Role_ID, SYS_Password, SYS_Name, SYS_IsFirstLogin, " +
                " SYS_LastIP, SYS_LastLoginDatetime, SYS_CreateDatetime, SYS_RegainDatetime, " +
                " SYS_LoginErrorCount, SYS_Enable " +
                " FROM SYS_Account ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
    }

    public boolean addSysAccount(String accountID, String accountName, String password, int roleID) {
        String sql = " INSERT INTO SYS_Account (SYS_Account_ID, SYS_Password, SYS_Name, Role_ID, SYS_IsFirstLogin, SYS_CreateDatetime, SYS_Enable) " +
                " VALUES (:SYS_Account_ID, :SYS_Password, :SYS_Name, :Role_ID, 1, NOW(), 1) ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("SYS_Account_ID", accountID);
        parameters.put("SYS_Password", password);
        parameters.put("SYS_Name", accountName);
        parameters.put("Role_ID", roleID);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 紀錄可再登入時間
     *
     * @param regainDateTime
     */
    public void updateRegainTime(String accountId, Date regainDateTime) {
        String sql = " UPDATE SYS_Account SET SYS_RegainDatetime = :SYS_RegainDatetime WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("SYS_RegainDatetime", regainDateTime);
        parameters.put("SYS_Account_ID", accountId);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    //TODO DAO 要符合規則

    /**
     * 登入Log
     */
    public void loginLog(Date createDateTime, String clientType, String ip, String accountId, int code, String message) {

        String sql = " INSERT INTO LOG_AdminLogin (AL_CreateDateTime, AL_ClientType, AL_IP, ACT_AccountID, AL_ErrorCode, AL_ErrorMessage) " +
                " VALUES(:AL_CreateDateTime, :AL_ClientType, :AL_IP, :ACT_AccountID, :AL_ErrorCode, :AL_ErrorMessage) ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("AL_CreateDateTime", createDateTime);
        parameters.put("AL_ClientType", clientType);
        parameters.put("AL_IP", ip);
        parameters.put("ACT_AccountID", accountId);
        parameters.put("AL_ErrorCode", code);
        parameters.put("AL_ErrorMessage", message);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    //TODO DAO 要符合規則

    /**
     * 取出所有role
     */
    public List<Map<String, Object>> getAllRoles() {
        String sql = " SELECT Role_ID, Role_Name FROM SYS_Role ";

        return namedParameterJdbcTemplate.queryForList(sql, new HashMap<>(2));
    }

    //TODO DAO 要符合規則

    /**
     * 以roleId取得role資料
     */
    public SqlRowSet getRoleByRoleId(int roleId) {
        String sql = " SELECT Role_ID, Role_Name FROM SYS_Role " +
                " WHERE Role_ID = :Role_ID ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Role_ID", roleId);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    //TODO DAO 要符合規則

    /**
     * 以accountId取得session
     */
    public SqlRowSet getSessionByAccountId(String accountId) {
        String sql = " SELECT SYS_Account_ID, SYS_Session_Token, SYS_Session_IP, SYS_Session_Expireddatetime, SYS_Session_Createdatetime " +
                " FROM SYS_Session " +
                " WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SYS_Account_ID", accountId);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    //TODO DAO 要符合規則
    /**
     * 更新session
     */
    public void updateSession(String accountId, String token, String ip, Date expiredDateTime, Date createDateTime) {
        String sql = " UPDATE SYS_Session SET SYS_Session_Token = :SYS_Session_Token, SYS_Session_IP = :SYS_Session_IP, " +
                " SYS_Session_Expireddatetime = :SYS_Session_Expireddatetime, SYS_Session_Createdatetime = :SYS_Session_Createdatetime " +
                " WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("SYS_Account_ID", accountId);
        parameters.put("SYS_Session_Token", token);
        parameters.put("SYS_Session_IP", ip);
        parameters.put("SYS_Session_Expireddatetime", expiredDateTime);
        parameters.put("SYS_Session_Createdatetime", createDateTime);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    //TODO DAO 要符合規則
    /**
     * 新增session
     */
    public void newSession(String accountId, String token, String ip, Date expiredDateTime, Date createDateTime) {
        String sql = " INSERT INTO SYS_Session " + " (SYS_Account_ID, SYS_Session_Token, SYS_Session_IP, SYS_Session_Expireddatetime, SYS_Session_Createdatetime) " +
                " VALUES(:SYS_Account_ID, :SYS_Session_Token, :SYS_Session_IP, :SYS_Session_Expireddatetime, :SYS_Session_Createdatetime) ";
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("SYS_Account_ID", accountId);
        parameters.put("SYS_Session_Token", token);
        parameters.put("SYS_Session_IP", ip);
        parameters.put("SYS_Session_Expireddatetime", expiredDateTime);
        parameters.put("SYS_Session_Createdatetime", createDateTime);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    /**
     * 更新最後登入時間和IP
     */
    public void updateLastLogin(String accountId, String ip, Date lastLoginDateTime) {
        String sql = " UPDATE SYS_Account SET SYS_LastIP = :SYS_LastIP, SYS_LastLoginDatetime = :SYS_LastLoginDatetime, SYS_RegainDatetime = 0, SYS_LoginErrorCount = 0 " +
                " WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("SYS_Account_ID", accountId);
        parameters.put("SYS_LastIP", ip);
        parameters.put("SYS_LastLoginDatetime", lastLoginDateTime);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    /**
     * 更新登入錯誤次數
     */
    public void updateErrorCount(String accountId, int errorCount) {
        String sql = " UPDATE SYS_Account SET SYS_LoginErrorCount = :SYS_LoginErrorCount " +
                " WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("SYS_LoginErrorCount", errorCount);
        parameters.put("SYS_Account_ID", accountId);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    //TODO DAO 要符合規則
    /**
     * 登入錯誤
     */
    public SqlRowSet loginError(String accountId) {
        String sql = " SELECT SYS_LoginErrorCount + 1 " +
                " FROM SYS_Account " +
                " WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SYS_Account_ID", accountId);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public boolean updateSelfPassword(String accountID, String password) {
        String sql = "UPDATE SYS_Account SET SYS_Password = :SYS_Password, SYS_IsFirstLogin = 0 WHERE SYS_Account_ID = :SYS_Account_ID ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("SYS_Password", password);
        parameters.put("SYS_Account_ID", accountID);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean lockAccountById(String accountID) {
        String sql = " UPDATE SYS_Account SET SYS_Enable = 0 WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SYS_Account_ID", accountID);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean unlockAccountById(String accountID) {
        String sql = " UPDATE SYS_Account SET SYS_Enable = 1 WHERE SYS_Account_ID = :SYS_Account_ID ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SYS_Account_ID", accountID);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

}
