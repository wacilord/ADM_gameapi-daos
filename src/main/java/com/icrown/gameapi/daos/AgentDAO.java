package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
import com.icrown.gameapi.models.AgentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Frank
 */
@Repository
public class AgentDAO {

    @Autowired
    DateUtil dateUtil;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String getAgentSql() {
        return "SELECT AGT_GUID,MCT_GUID,AGT_Parent,AGT_Level,AGT_Agent1,AGT_Agent2,AGT_Agent3, " +
                " MCT_Domain, AGT_AccountID," +
                " AGT_Lock," +
                " AGT_CreateDatetime" +
                " FROM AGT_Agent ";
    }

    public SqlRowSet getAgentDetail(String agtGUID) {

        String sql = "SELECT a.AGT_GUID AS agentGuid" +
                "	,a.MCT_Domain AS domain" +
                "	,a.AGT_Level AS level" +
                "	,a.AGT_AccountID AS accountID" +
                "	,a.AGT_CreateDatetime AS createDateTime" +
                "	,s.SAT_NickName AS nickName" +
                "	,s.SAT_Memo AS memo" +
                " FROM AGT_Agent a" +
                " INNER JOIN AGT_SubAccount s ON s.SAT_GUID = a.AGT_GUID" +
                " WHERE a.AGT_GUID = :AgentGUID";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AgentGUID", agtGUID);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public Optional<AgentModel> getAgentInfoByAgentGUID(String agtGUID) {
        String sql = getAgentSql() + " WHERE AGT_GUID = :AGT_GUID";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_GUID", agtGUID);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }


    public int findAgentByAccountID(String accountID, String domainName) {
        String sql = "SELECT COUNT(1) FROM AGT_Agent WHERE AGT_AccountID = :AccountID AND MCT_Domain = :DomainName";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AccountID", accountID);
        parameters.put("DomainName", domainName);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    //TODO DAO 要符合規則
    public int findSubAccountByAccountID(String accountID, String domainName) {
        String sql = "SELECT COUNT(1) FROM AGT_SubAccount WHERE SAT_AccountID = :AccountID AND MCT_Domain = :DomainName";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AccountID", accountID);
        parameters.put("DomainName", domainName);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }


    /**
     * 取得所有第3層的代理
     *
     * @return
     */
    public List<AgentModel> getAllAgents() {
        String sql = getAgentSql() + " WHERE AGT_Level = 3 ";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AgentModel.class));
    }

    /**
     * 取得所有第1層的代理
     *
     * @return
     */
    public List<AgentModel> getAllAgent1() {
        String sql = getAgentSql() + " WHERE AGT_Level = 1 ";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AgentModel.class));
    }

    /**
     * 從第3層取得組織樹
     *
     * @param agentGuid3
     * @return
     */
    public Map<String, String> getAgentTreeByAgent3(String agentGuid3) {
        String sql = " SELECT A3.AGT_AccountID as Agent3 , A2.AGT_AccountID as Agent2 , A1.AGT_AccountID as Agent1 " +
                " FROM AGT_Agent A3 " +
                " LEFT JOIN AGT_Agent A2 ON A3.AGT_Agent2 = A2.AGT_GUID " +
                " LEFT JOIN AGT_Agent A1 ON A3.AGT_Agent1 = A1.AGT_GUID " +
                " WHERE A3.AGT_GUID = :AgentGuid3 ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AgentGuid3", agentGuid3);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        Map<String, String> result = new HashMap<>(3);
        if (!rowSet.next()) {
            return result;
        }
        result.put("Agent1", rowSet.getString("Agent1"));
        result.put("Agent2", rowSet.getString("Agent2"));
        result.put("Agent3", rowSet.getString("Agent3"));
        return result;
    }

    public SqlRowSet getAgentTreeListByAgent3(String mctGuid) {
        String sql = " SELECT  A3.AGT_GUID,A3.AGT_AccountID as Agent3 , A2.AGT_AccountID as Agent2 , A1.AGT_AccountID as Agent1 " +
                " FROM AGT_Agent A3 " +
                " LEFT JOIN AGT_Agent A2 ON A3.AGT_Agent2 = A2.AGT_GUID " +
                " LEFT JOIN AGT_Agent A1 ON A3.AGT_Agent1 = A1.AGT_GUID " +
                " WHERE A3.MCT_GUID = :MCT_GUID ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MCT_GUID", mctGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getAllAgentTreeList() {
        String sql = " SELECT  A3.AGT_GUID,A3.AGT_AccountID as Agent3 , A2.AGT_AccountID as Agent2 , A1.AGT_AccountID as Agent1 " +
                " FROM AGT_Agent A3 " +
                " LEFT JOIN AGT_Agent A2 ON A3.AGT_Agent2 = A2.AGT_GUID " +
                " LEFT JOIN AGT_Agent A1 ON A3.AGT_Agent1 = A1.AGT_GUID ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 從第2層取得組織樹
     *
     * @param agentGuid2
     * @return
     */
    public Map<String, String> getAgentTreeByAgent2(String agentGuid2) {
        String sql = " SELECT A2.AGT_AccountID as Agent2 , A1.AGT_AccountID as Agent1 " +
                " FROM AGT_Agent A2 " +
                " LEFT JOIN AGT_Agent A1 ON A2.AGT_Agent1 = A1.AGT_GUID " +
                " WHERE A2.AGT_GUID = :AgentGuid2 ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AgentGuid2", agentGuid2);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        Map<String, String> result = new HashMap<>(2);
        if (!rowSet.next()) {
            return result;
        }
        result.put("Agent1", rowSet.getString("Agent1"));
        result.put("Agent2", rowSet.getString("Agent2"));
        return result;
    }


    /**
     * 取得所有代理
     *
     * @return
     */
    public List<AgentModel> getAllAgentsByMctGUID(String mctGuid) {
        String sql = getAgentSql() + " WHERE MCT_GUID = :mctGuid ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("mctGuid", mctGuid);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(AgentModel.class));
    }

    public List<AgentModel> getAllAgent3ByMctGUID(String mctGuid) {

        String sql =  " SELECT AGT_GUID,MCT_GUID,AGT_Parent,AGT_Level,AGT_Agent1,AGT_Agent2,AGT_Agent3, " +
                " MCT_Domain, AGT_AccountID," +
                " AGT_Lock," +
                " AGT_CreateDatetime" +
                " FROM AGT_Agent " +
                " WHERE MCT_GUID = :mctGuid AND AGT_Level = 3 ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("mctGuid", mctGuid);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(AgentModel.class));
    }



    public SqlRowSet getAgentList(String agtParent, String agtAccountID, int status, int pageSize, int pageIndex) {

        String sql = "SELECT AGT_GUID,AGT_AccountID,AGT_Lock,AGT_Locker,AGT_Level FROM AGT_Agent  WHERE AGT_Parent = :AGT_Parent ";

        if (status == 1) {
            sql += " AND AGT_Lock =0";
        }

        if (status == 2) {
            sql += " AND AGT_Lock =1";
        }


        Map<String, Object> parameters = new HashMap<>(4);

        if (!agtAccountID.trim().equals("")) {
            sql += " AND AGT_AccountID =:AGT_AccountID ";
            parameters.put("AGT_AccountID", agtAccountID);
        }
        sql += " limit :start,:size ";
        parameters.put("AGT_Parent", agtParent);
        parameters.put("start", (pageIndex - 1) * pageSize);
        parameters.put("size", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getManagerList(String agtParent, String agtAccountID, int status) {

        String sql = " SELECT AGT_GUID,AGT_AccountID,AGT_Lock,AGT_Locker,AGT_Level FROM AGT_Agent  WHERE AGT_Parent = :AGT_Parent AND AGT_Level = 2 ";

        if (status == 1) {
            sql += " AND AGT_Lock =0";
        }

        if (status == 2) {
            sql += " AND AGT_Lock =1";
        }

        Map<String, Object> parameters = new HashMap<>(4);

        if (!agtAccountID.trim().equals("")) {
            sql += " AND AGT_AccountID =:AGT_AccountID ";
            parameters.put("AGT_AccountID", agtAccountID);
        }

        parameters.put("AGT_Parent", agtParent);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getAgentListCount(String agtParent, String agtAccountID, int status) {

        String sql = "SELECT COUNT(1) FROM AGT_Agent  WHERE AGT_Parent = :AGT_Parent ";

        if (status == 1) {
            sql += " AND AGT_Lock =0";
        } else if (status == 2) {
            sql += " AND AGT_Lock =1";
        }

        Map<String, Object> parameters = new HashMap<>(2);

        if (!agtAccountID.trim().equals("")) {
            sql += " AND AGT_AccountID =:AGT_AccountID ";
            parameters.put("AGT_AccountID", agtAccountID);
        }

        parameters.put("AGT_Parent", agtParent);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public List<AgentModel> getAgentByAccountIDAndParentGuid(String agtParent, String agtAccountID, int status) {
        String sql = getAgentSql();
        sql += " WHERE AGT_Parent = :AGT_Parent ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AGT_Parent", agtParent);

        if (status == 1) {
            sql += " AND AGT_Lock =0";
        } else if (status == 2) {
            sql += " AND AGT_Lock =1";
        }

        if (!agtAccountID.trim().equals("")) {
            sql += " AND AGT_AccountID =:AGT_AccountID ";
            parameters.put("AGT_AccountID", agtAccountID);
        }

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(AgentModel.class));
    }

    public int getManagerListCount(String agtParent, String agtAccountID, int status) {

        String sql = "SELECT COUNT(1) FROM AGT_Agent  WHERE AGT_Parent = :AGT_Parent AND AGT_Level = 2 ";

        if (status == 1) {
            sql += " AND AGT_Lock =0";
        } else if (status == 2) {
            sql += " AND AGT_Lock =1";
        }

        Map<String, Object> parameters = new HashMap<>(2);

        if (!agtAccountID.trim().equals("")) {
            sql += " AND AGT_AccountID =:AGT_AccountID ";
            parameters.put("AGT_AccountID", agtAccountID);
        }

        parameters.put("AGT_Parent", agtParent);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }


    /**
     * 新增代理
     */
    public boolean add(AgentModel model) {
        String sql = "INSERT INTO AGT_Agent" +
                "	(AGT_GUID," +
                "	MCT_GUID," +
                "	AGT_Parent," +
                "	AGT_Level," +
                "	AGT_Agent1," +
                "	AGT_Agent2," +
                "	AGT_Agent3," +
                "   MCT_Domain," +
                "	AGT_AccountID," +
                "	AGT_Lock," +
                "	AGT_CreateDatetime" +
                "	) VALUES" +
                "	(:AentGUID," +
                "	:MctGUID," +
                "	:Parent," +
                "	:AgentLevel," +
                "	:Agent1," +
                "	:Agent2," +
                "	:Agent3," +
                "   :DomainName, " +
                "	:AccountID," +
                "	0," +
                "	:CreateDatetime" +
                "	);";

        Map<String, Object> parameters = new HashMap<>(10);
        parameters.put("AentGUID", model.getAGT_GUID());
        parameters.put("MctGUID", model.getMCT_GUID());
        parameters.put("Parent", model.getAGT_Parent());
        parameters.put("AgentLevel", model.getAGT_Level());
        parameters.put("Agent1", model.getAGT_Agent1());
        parameters.put("Agent2", model.getAGT_Agent2());
        parameters.put("Agent3", model.getAGT_Agent3());
        parameters.put("DomainName", model.getMCT_Domain());
        parameters.put("AccountID", model.getAGT_AccountID());
        parameters.put("CreateDatetime", model.getAGT_CreateDatetime());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;

    }

    /**
     * 取得代理底下的所有代理
     */
    public List<String> getAllAgentByParent(String agentGuid, int agtLevel) {
        String sql = "SELECT AGT_GUID FROM AGT_Agent WHERE AGT_Lock = 0 AND";
        Map<String, Object> parameters = new HashMap<>(1);
        if (agtLevel == 2) {
            sql += " AGT_Agent2 = :agentGuid";
        }
        if (agtLevel == 3) {
            sql += " AGT_Agent3 = :agentGuid";
        }
        parameters.put("agentGuid", agentGuid);

        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<String> agentGuidList = new ArrayList<>();
        while (sqlRowSet.next()) {
            agentGuidList.add(sqlRowSet.getString("AGT_GUID"));
        }
        return agentGuidList;
    }

    //TODO DAO 要符合規則

    /**
     * 取得代理底下的所有玩家
     */
    public List<String> getAllMemberByParent(String agentGuid, int agtLevel) {
        String sql = "SELECT PLY_GUID FROM MEM_Player WHERE PLY_Lock = 0 AND ";

        Map<String, Object> parameters = new HashMap<>(1);
        if (agtLevel == 2) {
            sql += " AGT_Agent2 = :agentGuid";
        }
        if (agtLevel == 3) {
            sql += " AGT_Agent3 = :agentGuid";
        }
        parameters.put("agentGuid", agentGuid);

        SqlRowSet playerRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<String> playerGuidList = new ArrayList<>();
        while (playerRowSet.next()) {
            playerGuidList.add(playerRowSet.getString("PLY_GUID"));
        }
        return playerGuidList;
    }

    /**
     * 取得代理底下的所有被鎖代理
     */
    public List<String> getAllAgentByLocker(String lockerGuid, String agentGuid, int agtLevel) {
        String sql = "SELECT AGT_GUID FROM AGT_Agent WHERE AGT_Lock = 1 AND AGT_Locker = :lockerGuid AND";
        Map<String, Object> parameters = new HashMap<>(2);
        if (agtLevel == 2) {
            sql += " AGT_Agent2 = :agentGuid";
        }
        if (agtLevel == 3) {
            sql += " AGT_Agent3 = :agentGuid";
        }
        parameters.put("lockerGuid", lockerGuid);
        parameters.put("agentGuid", agentGuid);

        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<String> agentGuidList = new ArrayList<>();
        while (sqlRowSet.next()) {
            agentGuidList.add(sqlRowSet.getString("AGT_GUID"));
        }
        return agentGuidList;
    }

    //TODO DAO 要符合規則

    /**
     * 取得代理底下的所有被鎖玩家
     */
    public List<String> getAllMemberByLocker(String lockerGuid, String agentGuid, int agtLevel) {
        String sql = "SELECT PLY_GUID FROM MEM_Player WHERE PLY_Lock = 1 AND PLY_Locker = :lockerGuid AND";

        Map<String, Object> parameters = new HashMap<>(1);
        if (agtLevel == 2) {
            sql += " AGT_Agent2 = :agentGuid";
        }
        if (agtLevel == 3) {
            sql += " AGT_Agent3 = :agentGuid";
        }
        parameters.put("lockerGuid", lockerGuid);
        parameters.put("agentGuid", agentGuid);

        SqlRowSet playerRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<String> playerGuidList = new ArrayList<>();
        while (playerRowSet.next()) {
            playerGuidList.add(playerRowSet.getString("PLY_GUID"));
        }
        return playerGuidList;
    }

    /**
     * 上鎖代理
     */
    public void lockAgent(String lockerGuid, List<String> guidList) {
        if (guidList.isEmpty()) {
            return;
        }

        String updateSql = "UPDATE AGT_Agent SET AGT_Lock = 1 , AGT_Locker = :lockerGuid " +
                ", AGT_LockDatetime = NOW() WHERE AGT_Lock = 0 AND AGT_GUID = :guid";
        List<Map<String, String>> parameterList = guidList.stream().map(guid -> {
            Map<String, String> parameters = new HashMap<>(2);
            parameters.put("lockerGuid", lockerGuid);
            parameters.put("guid", guid);
            return parameters;
        }).collect(Collectors.toList());

        namedParameterJdbcTemplate.batchUpdate(updateSql, parameterList.toArray(new Map[parameterList.size()]));
    }

    //TODO DAO 要符合規則

    /**
     * 根據上層代理上鎖玩家
     */
    public void lockPlayerByAgent(String lockerGuid, List<String> guidList) {
        if (guidList.isEmpty()) {
            return;
        }

        String updateSql = "UPDATE MEM_Player SET PLY_Lock = 1 , PLY_Locker = :lockerGuid " +
                ", PLY_LockDatetime = NOW() WHERE PLY_GUID = :guid";

        guidList.parallelStream().forEach(guid -> {
            Map<String, String> parameters = new HashMap<>(2);
            parameters.put("lockerGuid", lockerGuid);
            parameters.put("guid", guid);
            namedParameterJdbcTemplate.update(updateSql, parameters);
        });
    }

    /**
     * 解鎖代理
     */
    public void unlockAgent(List<String> guidList) {
        if (guidList.isEmpty()) {
            return;
        }

        String updateSql = "UPDATE AGT_Agent SET AGT_Lock = 0 , AGT_Locker = Null " +
                ", AGT_LockDatetime = Null WHERE AGT_GUID = :guid";

        List<Map<String, String>> parameterList = guidList.stream().map(guid -> {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put("guid", guid);
            return parameters;
        }).collect(Collectors.toList());

        namedParameterJdbcTemplate.batchUpdate(updateSql, parameterList.toArray(new Map[parameterList.size()]));
    }

    //TODO DAO 要符合規則

    /**
     * 解鎖玩家
     */
    public void unlockPlayer(List<String> guidList) {
        if (guidList.isEmpty()) {
            return;
        }

        String updateSql = "UPDATE MEM_Player SET PLY_Lock = 0 , PLY_Locker = Null " +
                ", PLY_LockDateTime = Null WHERE PLY_GUID = :guid";
        guidList.parallelStream().forEach(guid -> {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put("guid", guid);
            namedParameterJdbcTemplate.update(updateSql, parameters);
        });


    }

    /**
     * 取得所有Agent1並對應總代名稱
     *
     * @return
     */
    public SqlRowSet getAllAgent1WithMctGuid() {
        String sql = " SELECT a1.AGT_Agent1,a2.MCT_FriendlyName " +
                " FROM AGT_Agent a1 " +
                " LEFT JOIN AGT_Merchant a2 ON a1.MCT_Guid = a2.MCT_Guid " +
                " WHERE a1.Agt_Level = 1 AND a2.MCT_Enable = 1 ";
        Map<String, Object> parameters = new HashMap<>(1);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getAllAgentByMctGuid(String mctGuid) {
        String sql = " SELECT AGT_GUID, AGT_AccountID, AGT_Level, AGT_Parent, MCT_Domain " +
                " FROM AGT_Agent " +
                " WHERE MCT_GUID = :MctGuid ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MctGuid", mctGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getAgentListByMctGuidAndLevel(String mctGuid, String level) {
        String sql = " SELECT AGT_GUID,AGT_AccountID " +
                " FROM AGT_Agent " +
                " WHERE MCT_GUID = :MctGuid ";
        if (!level.equals("")) {
            sql = sql + "AND AGT_Level =" + level;
        }
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MctGuid", mctGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public Optional<AgentModel> getAgentByAccountAndDomain(String accountID, String domain) {
        String sql = getAgentSql() + " WHERE AGT_AccountID = :AccountID AND MCT_Domain = :Domain ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AccountID", accountID);
        parameters.put("Domain", domain);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public Optional<AgentModel> getAgent3ByAccountAndDomain(String accountID, String domain) {
        String sql = getAgentSql() + " WHERE AGT_AccountID = :AccountID AND MCT_Domain = :Domain AND AGT_Level = 3 ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AccountID", accountID);
        parameters.put("Domain", domain);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public Optional<List<AgentModel>> getAgentByDomain(String domain) {
        String sql = getAgentSql() + " WHERE MCT_Domain = :Domain ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Domain", domain);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list);
    }

    public Optional<List<AgentModel>> getAgentByDomain(List<String> domainList) {
        String sql = getAgentSql() + " WHERE MCT_Domain IN (:Domain) ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Domain", domainList);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list);
    }

    public Optional<List<AgentModel>> getAgent3ByDomain(String domain) {
        String sql = getAgentSql() + " WHERE MCT_Domain = :Domain AND AGT_Level = 3";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Domain", domain);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list);
    }

    public List<AgentModel> getAgentByDomainAndPlayerID(String domain, String playerID) {
        String sql = " SELECT a.AGT_GUID as AGT_GUID, a.MCT_GUID as MCT_GUID, a.AGT_Parent as AGT_Parent, " +
                " a.AGT_Level as AGT_Level, a.AGT_Agent1 as AGT_Agent1, a.AGT_Agent2 as AGT_Agent2, a.AGT_Agent3 as AGT_Agent3, " +
                " a.MCT_Domain as MCT_Domain, a.AGT_AccountID as AGT_AccountID, a.AGT_Lock as AGT_Lock," +
                " a.AGT_CreateDatetime as AGT_CreateDatetime " +
                " FROM AGT_Agent a LEFT JOIN MEM_Player p ON a.AGT_Agent3 = p.AGT_Agent3 WHERE a.MCT_Domain = :Domain AND p.PLY_AccountID = :playerID ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Domain", domain);
        parameters.put("playerID", playerID);
        List<AgentModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(AgentModel.class));

        return list;
    }

    public SqlRowSet getAgentAndPlayerByDomainAndPlayerID(String domain, String playerID) {
        String sql = " SELECT a.AGT_GUID as AGT_GUID, a.MCT_GUID as MCT_GUID, a.AGT_Parent as AGT_Parent, " +
                " a.AGT_Level as AGT_Level, a.AGT_Agent1 as AGT_Agent1, a.AGT_Agent2 as AGT_Agent2, a.AGT_Agent3 as AGT_Agent3, " +
                " a.MCT_Domain as MCT_Domain, a.AGT_AccountID as AGT_AccountID, a.AGT_Lock as AGT_Lock," +
                " a.AGT_CreateDatetime as AGT_CreateDatetime, " +
                " p.PLY_GUID as PLY_GUID, p.PLY_AccountID as PLY_AccountID " +
                " FROM AGT_Agent a " +
                " LEFT JOIN MEM_Player p ON a.AGT_Agent3 = p.AGT_Agent3 " +
                " WHERE a.MCT_Domain = :Domain AND p.PLY_AccountID = :playerID ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Domain", domain);
        parameters.put("playerID", playerID);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getAgent3ListByAgentGuidAndLevel(String agentGuid, int level) {
        String agent = "AGT_Agent" + level;
        String sql = " SELECT AGT_Agent3 FROM AGT_Agent WHERE " + agent + " = :agent AND AGT_Level = 3";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("agent", agentGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
