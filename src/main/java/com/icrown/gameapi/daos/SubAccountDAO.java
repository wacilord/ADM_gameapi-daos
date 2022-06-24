package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
import com.icrown.gameapi.models.SubAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.*;

/***
 * @author David
 *
 * */

@Repository
public class SubAccountDAO {

    @Autowired
    DateUtil dateUtil;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_SUB_ACCOUNT = " SELECT SAT_GUID,AGT_GUID,SAT_Type,MCT_Domain,SAT_AccountID," +
            " SAT_Password,SAT_NickName,SAT_Memo,SAT_Enable,SAT_LastIP," +
            " SAT_ErrorCount,SAT_IsFirstLogin,SAT_LastErrorLoginDatetime," +
            " SAT_RegainDatetime,SAT_CreateDatetime,SAT_LastLoginDatetime" +
            " FROM AGT_SubAccount ";

    /***
     * 只比對帳號
     *
     * @param accountID
     * @param domain
     * @return
     */
    public Map<String, Object> login(String accountID, String domain) {
        String sql = " SELECT a.SAT_GUID,a.SAT_Password,a.SAT_IsFirstLogin,a.SAT_RegainDatetime,a.SAT_Enable,a.SAT_ErrorCount,b.AGT_Lock, c.MCT_Enable,IFNULL(c.MCT_BackendAllowIP,'') AS MCT_BackendAllowIP,a.AGT_GUID,b.AGT_Level,a.SAT_Type,b.MCT_Guid,c.LAG_Code AS LAG_Code   " +
                " FROM AGT_SubAccount AS a  " +
                " LEFT JOIN AGT_Agent as b on a.AGT_GUID=b.AGT_GUID  " +
                " LEFT JOIN AGT_Merchant AS c on b.MCT_GUID=c.MCT_GUID " +
                " WHERE a.SAT_AccountID = :SAT_AccountID AND a.MCT_Domain=:MCT_Domain";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("SAT_AccountID", accountID);
        parameters.put("MCT_Domain", domain);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

        if (!rowSet.next()) {
            return Collections.emptyMap();
        }
        Map<String, Object> result = new HashMap<>(12);
        result.put("SAT_GUID", rowSet.getString("SAT_GUID"));
        result.put("SAT_IsFirstLogin", rowSet.getBoolean("SAT_IsFirstLogin"));
        result.put("SAT_RegainDatetime", rowSet.getDate("SAT_RegainDatetime"));
        result.put("SAT_Enable", rowSet.getBoolean("SAT_Enable"));
        result.put("SAT_ErrorCount", rowSet.getInt("SAT_ErrorCount"));
        result.put("AGT_Lock", rowSet.getBoolean("AGT_Lock"));
        result.put("MCT_Enable", rowSet.getBoolean("MCT_Enable"));
        result.put("MCT_BackendAllowIP", rowSet.getString("MCT_BackendAllowIP"));
        result.put("SAT_Password", rowSet.getString("SAT_Password"));
        result.put("AGT_GUID", rowSet.getString("AGT_GUID"));
        result.put("SAT_Type", rowSet.getInt("SAT_Type"));
        result.put("AGT_Level", rowSet.getInt("AGT_Level"));
        result.put("MCT_Guid",rowSet.getString("MCT_Guid"));
        result.put("LAG_Code",rowSet.getString("LAG_Code"));
        return result;
    }


    public boolean loginError(String accountID, String domain, String ipaddress, Date loginTime, Date regainTime) {
        String sql = "UPDATE AGT_SubAccount SET SAT_ErrorCount = SAT_ErrorCount+1 " +
                " , SAT_RegainDatetime=:SAT_RegainDatetime " +
                " , SAT_LastErrorLoginDatetime= :SAT_LastErrorLoginDatetime " +
                " , SAT_LastIP= :SAT_LastIP " +
                " WHERE SAT_AccountID = :SAT_AccountID AND MCT_Domain=:MCT_Domain";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("SAT_AccountID", accountID);
        parameters.put("MCT_Domain", domain);
        parameters.put("SAT_LastIP", ipaddress);
        parameters.put("SAT_RegainDatetime", regainTime);
        parameters.put("SAT_LastErrorLoginDatetime", loginTime);
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    public boolean loginErrorWithLock(String accountID, String domain, String ipaddress, Date loginTime, Date regainTime) {
        String sql = "UPDATE AGT_SubAccount SET SAT_ErrorCount = SAT_ErrorCount+1 " +
                " , SAT_RegainDatetime=:SAT_RegainDatetime " +
                " , SAT_LastErrorLoginDatetime= :SAT_LastErrorLoginDatetime " +
                " , SAT_Enable= 0 " +
                " , SAT_LastIP= :SAT_LastIP " +
                " WHERE SAT_AccountID = :SAT_AccountID AND MCT_Domain=:MCT_Domain";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SAT_AccountID", accountID);
        parameters.put("MCT_Domain", domain);
        parameters.put("SAT_RegainDatetime", regainTime);
        parameters.put("SAT_LastErrorLoginDatetime", loginTime);
        parameters.put("SAT_LastIP", ipaddress);
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    public void loginOk(String satGuid, String token, Date expiredTime, String accountID, String domain, String ipaddress, Date loginTime) {
        String sql = "UPDATE AGT_SubAccount SET SAT_ErrorCount = 0 " +
                " , SAT_RegainDatetime=NOW() " +
                " , SAT_LastLoginDatetime=:SAT_LastLoginDatetime " +
                " , SAT_LastIP= :SAT_LastIP " +
                " WHERE SAT_AccountID = :SAT_AccountID AND MCT_Domain=:MCT_Domain";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("SAT_AccountID", accountID);
        parameters.put("MCT_Domain", domain);
        parameters.put("SAT_LastIP", ipaddress);
        parameters.put("SAT_LastLoginDatetime", loginTime);
        namedParameterJdbcTemplate.update(sql, parameters);


        sql = "DELETE FROM AGT_Session WHERE SAT_GUID=:SAT_GUID ";
        parameters.clear();
        parameters.put("SAT_GUID", satGuid);
        namedParameterJdbcTemplate.update(sql, parameters);


        sql = " INSERT INTO AGT_Session " +
                " (SAT_GUID,SEN_LoginToken,TKS_IP,TKS_Expireddatetime)VALUES" +
                " (:SAT_GUID,:SEN_LoginToken,:TKS_IP,:TKS_Expireddatetime)";
        parameters.clear();
        parameters.put("SAT_GUID", satGuid);
        parameters.put("SEN_LoginToken", token);
        parameters.put("TKS_IP", ipaddress);
        parameters.put("TKS_Expireddatetime", expiredTime);
        namedParameterJdbcTemplate.update(sql, parameters);


    }

    //TODO DAO 要符合規則
    public void loginLog(String accountID, String domain, String ipAddress, String agtGuid, String clientType, int errorCode, String errorMessage) {
        String sql = "INSERT INTO LOG_BackendLogin" +
                " (BL_ClientType,BL_IP,AGT_GUID,MCT_Domain,SAT_AccountID,BL_ErrorCode,BL_ErrorMessage) VALUES" +
                " (:BL_ClientType,:BL_IP,:AGT_GUID,:MCT_Domain,:SAT_AccountID,:BL_ErrorCode,:BL_ErrorMessage) " +
                "";


        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("BL_ClientType", clientType);
        parameters.put("BL_IP", ipAddress);
        parameters.put("AGT_GUID", agtGuid);
        parameters.put("MCT_Domain", domain);
        parameters.put("SAT_AccountID", accountID);
        parameters.put("BL_ErrorCode", errorCode);
        parameters.put("BL_ErrorMessage", errorMessage);
        namedParameterJdbcTemplate.update(sql, parameters);

    }

    //TODO DAO 要符合規則
    /**
     * 設定過期讓他失效
     *
     * @param token
     */
    public void logout(String token) {
        String sql = "UPDATE AGT_Session SET TKS_Expireddatetime = NOW() " +
                " WHERE SEN_LoginToken = :SEN_LoginToken ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SEN_LoginToken", token);
        namedParameterJdbcTemplate.update(sql, parameters);
    }


    /**
     * 找出某位代理所有的子帳號
     **/
    public SqlRowSet findSubAccountListByAGT_GUID(String AGT_GUID) {

        String sql = "SELECT SAT_GUID AS satGuid " +
                "	,SAT_AccountID AS accountID " +
                "	,SAT_NickName AS nickName " +
                "   ,SAT_Enable AS enable " +
                "	,SAT_LastLoginDatetime AS lastLoginDateTime" +
                "	,SAT_LastIP AS lastIP " +
                "	,SAT_CreateDatetime AS createDateTime " +
                "FROM AGT_SubAccount " +
                "WHERE AGT_GUID = :AgentGuid " +
                "	AND SAT_Type = 0 " +
                "ORDER BY SAT_AccountID";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AgentGuid", AGT_GUID);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public boolean add(String subAccountGuid, String agentGuid, int type, String domain, String accountID, String password,String nickName, String memo) {
        String postTime = dateUtil.getString(dateUtil.getDate());
        String sql = "INSERT INTO AGT_SubAccount ( " +
                "	SAT_GUID " +
                "	,AGT_GUID " +
                "	,SAT_Type " +
                "	,MCT_Domain " +
                "	,SAT_AccountID " +
                 "	,SAT_Password " +
                "	,SAT_NickName " +
                "	,SAT_Memo " +
                "	,SAT_Enable " +
                "	,SAT_ErrorCount " +
                "	,SAT_IsFirstLogin " +
                "	,SAT_RegainDatetime " +
                "	,SAT_CreateDatetime " +
                "	) " +
                "VALUES ( " +
                "	:SubAccountGuid " +
                "	,:AgentGuid " +
                "	,:AccountType " +
                "	,:DomainName " +
                "	,:AccountID " +
                 "	,:SAT_Password " +
                "	,:NickName " +
                "	,:Memo" +
                "	,1 " +
                "	,0 " +
                "	,1 " +
                "	, :PostDate " +
                "	, :PostDate " +
                "	); ";

        Map<String, Object> parameters = new HashMap<>(8);
        parameters.put("SubAccountGuid", subAccountGuid);
        parameters.put("AgentGuid", agentGuid);
        parameters.put("AccountType", type);
        parameters.put("DomainName", domain);
        parameters.put("AccountID", accountID);
        parameters.put("SAT_Password", password);
        parameters.put("NickName", nickName);
        parameters.put("Memo", memo);
        parameters.put("PostDate", postTime);
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    public void update(String subAccountGuid, String nickName, String memo) {

        String sql = "UPDATE AGT_SubAccount " +
                "SET SAT_NickName = :NickName " +
                "	,SAT_Memo = :Memo " +
                "WHERE SAT_GUID = :SubAccountGuid ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("NickName", nickName);
        parameters.put("Memo", memo);
        parameters.put("SubAccountGuid", subAccountGuid);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    /**
     * 更新代理的備註欄位
     */
    public void updateAgentMemo(String satGuid, String memo) {

        String sql = "UPDATE AGT_SubAccount " +
                "SET SAT_Memo = :Memo " +
                "WHERE SAT_GUID = :SAT_GUID ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Memo", memo);
        parameters.put("SAT_GUID", satGuid);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void lock(String agtGuid,String satGuid) {

        String sql = "UPDATE AGT_SubAccount SET SAT_Enable = 0 WHERE SAT_GUID = :SAT_GUID AND AGT_GUID = :AGT_GUID AND SAT_Type=0 ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AGT_GUID", agtGuid);
        parameters.put("SAT_GUID", satGuid);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void unlock(String agtGuid,String satGuid) {

        String sql = "UPDATE AGT_SubAccount SET SAT_Enable = 1 WHERE SAT_GUID = :SAT_GUID  AND AGT_GUID = :AGT_GUID AND SAT_Type=0 ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AGT_GUID", agtGuid);
        parameters.put("SAT_GUID", satGuid);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    /***
     * 變更密碼(下層或子帳號)
     * @param password 需先自行加密, 本處不處理加密邏輯
     *
     * */
    public boolean updatePassword(String satGuid, String password) {

        String sql = "UPDATE AGT_SubAccount SET SAT_Password = :Password, SAT_IsFirstLogin = 1 WHERE SAT_GUID = :SubAccountGuid ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Password", password);
        parameters.put("SubAccountGuid", satGuid);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 更新自己的密碼
     * @param satGuid
     * @param password
     * @return
     */
    public boolean updateSelfPassword(String satGuid,String password){
        String sql = "UPDATE AGT_SubAccount SET SAT_Password = :Password, SAT_IsFirstLogin = 0 WHERE SAT_GUID = :SubAccountGuid ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Password", password);
        parameters.put("SubAccountGuid", satGuid);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 驗證帳號是否存在
     */
    public int findSubAccountGuidByAccountID(String accountID, String domainName) {
        String sql = "SELECT  COUNT(1)  " +
                " FROM AGT_SubAccount " +
                " WHERE SAT_AccountID = :AccountID " +
                " AND MCT_Domain = :DomainName ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AccountID", accountID);
        parameters.put("DomainName", domainName);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    /**
     * 依帳號和域名獲取子帳號資訊
     */
    public Optional<SubAccountModel> getSubAccountGuidByAccountID(String accountID, String domainName) {
        String sql = SELECT_SUB_ACCOUNT +
                " WHERE SAT_AccountID = :AccountID " +
                " AND MCT_Domain = :DomainName ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AccountID", accountID);
        parameters.put("DomainName", domainName);

         List<SubAccountModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(SubAccountModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public int findSubAccountGuidByGuid(String satGuid) {
        String sql = "SELECT COUNT(1) as number " +
                "FROM AGT_SubAccount " +
                "WHERE SAT_GUID = :SAT_GUID ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SAT_GUID", satGuid);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public Optional<SubAccountModel> getSubAccountByGuid(String satGuid) {

        String sql = SELECT_SUB_ACCOUNT +
                " WHERE SAT_GUID = :SatGuid ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("SatGuid", satGuid);
        List<SubAccountModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(SubAccountModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    /**
     * 取得代理在SubAccount裡的資料
     *
     * @param agentGuid
     * @return
     */
    public Optional<SubAccountModel> getSubAccountByAgentGuid(String agentGuid) {

        String sql = SELECT_SUB_ACCOUNT +
                " WHERE AGT_GUID = :AgtGuid AND SAT_Type = 1 ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AgtGuid", agentGuid);
        List<SubAccountModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(SubAccountModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }


}
