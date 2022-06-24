package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.MemberPlayerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Frank
 */
@Repository
public class MemPlayerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_PLAYER = " SELECT PLY_GUID,PLY_Point,PLY_PointUpdatetime,MCT_GUID,AGT_Agent1,AGT_Agent2,AGT_Agent3," +
            " PLY_AccountID,AGT_AccountID,PLY_Tester,PLY_Lock,PLY_Locker,PLY_LockDatetime, " +
            " PLY_NickName,PLY_Kick,PLY_LastTicket,PLY_CreateDatetime   " +
            " FROM MEM_Player ";


    public int getPlayerListCount(String agtGuid, int level, String playerAccountID, int status) {
        String sql = " SELECT COUNT(1) FROM MEM_Player ";
        String fieldName = "AGT_Agent" + level;
        sql += " WHERE " + fieldName + " = " + " :AgentGuid ";
        if (status == 1) {
            sql += " AND PLY_Lock=0";
        }

        if (status == 2) {
            sql += " AND PLY_Lock=1 ";
        }

        Map<String, Object> parameters = new HashMap<>(2);
        if (playerAccountID.trim().length() > 0) {
            sql += " AND PLY_AccountID=:PLY_AccountID ";
            parameters.put("PLY_AccountID", playerAccountID);
        }

        parameters.put("AgentGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }


    /**
     * @param agtGuid
     * @param status  0 全部 1正常 2上鎖
     * @return
     */
    public SqlRowSet getPlayerList(String agtGuid, int level, String playerAccountID, int status, int pageSize, int pageIndex) {
        String sql = " SELECT PLY_GUID, PLY_Point, PLY_NickName," +
                " PLY_AccountID, AGT_AccountID, PLY_Lock,PLY_Locker, " +
                " PLY_NickName, AGT_Agent3 " +
                " FROM MEM_Player ";
        String fieldName = "AGT_Agent" + level;
        sql += " WHERE " + fieldName + " = " + " :AgentGuid ";
        if (status == 1) {
            sql += " AND PLY_Lock=0 ";
        }

        if (status == 2) {
            sql += " AND PLY_Lock=1 ";
        }

        Map<String, Object> parameters = new HashMap<>(4);
        if (playerAccountID.trim().length() > 0) {
            sql += " AND PLY_AccountID=:PLY_AccountID ";
            parameters.put("PLY_AccountID", playerAccountID);
        }

        sql += " LIMIT :pos,:size";
        parameters.put("AgentGuid", agtGuid);
        parameters.put("pos", (pageIndex - 1) * pageSize);
        parameters.put("size", pageSize);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    /**
     * 取得玩家 GUID
     */
    public Optional<String> getPlayerGuid(String agtGuid, String accountId) {

        String sql = "SELECT PLY_GUID FROM MEM_Player WHERE AGT_Agent3 = :AgtGuid AND PLY_AccountId = :AccountId ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("AccountId", accountId);

        List<String> data = namedParameterJdbcTemplate.queryForList(sql, parameters, String.class);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(data.get(0));
    }

    public Optional<List<String>> getPlayerGuidByAgtguidAndLevel(String agtGuid, String accountId, int level) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" SELECT PLY_GUID FROM MEM_Player WHERE PLY_AccountId = :AccountId ");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("AccountId", accountId);
        parameters.put("AgtGuid", agtGuid);

        stringBuilder.append("AND AGT_Agent" + level);
        stringBuilder.append("= :AgtGuid");
        String sql = stringBuilder.toString();

        List<String> data = namedParameterJdbcTemplate.queryForList(sql, parameters, String.class);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(data);
    }


    /**
     * 判斷玩家是否存在
     *
     * @param agtGuid
     * @param accountId
     * @return
     */
    public boolean checkUserExist(String agtGuid, String accountId) {
        String sql = "SELECT COUNT(1) FROM MEM_Player WHERE AGT_Agent3 = :AgtGuid AND PLY_AccountId = :AccountId ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("AccountId", accountId);
        int count = (int) namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count > 0;
    }

    /**
     * 藉由代理層級判斷玩家是否存在
     *
     * @param agtGuid
     * @param accountId
     * @param level
     * @return
     */
    public boolean checkUserExistByAgent(String agtGuid, String accountId, int level) {
        String sql = "SELECT COUNT(1) FROM MEM_Player WHERE PLY_AccountId = :AccountId";
        if (level == 1) {
            sql += " AND AGT_Agent1=:AgtGuid";
        }
        if (level == 2) {
            sql += " AND AGT_Agent2=:AgtGuid";
        }
        if (level == 3) {
            sql += " AND AGT_Agent3=:AgtGuid";
        }

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("AccountId", accountId);
        int count = (int) namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count > 0;
    }

    /**
     * 建立玩家
     *
     * @param model
     * @return
     */
    public boolean createPlayer(MemberPlayerModel model) {
        String sql = "INSERT Into MEM_Player(PLY_GUID,PLY_Point,PLY_PointUpdatetime,MCT_GUID,AGT_Agent1,AGT_Agent2,AGT_Agent3,AGT_AccountID,PLY_AccountID,"
                + "PLY_Tester,PLY_Lock,PLY_Locker,PLY_LockDatetime,"
                + "PLY_CreateDatetime)"
                + "VALUES(:PlyGuid,:PlyPoint,:PlyPointUpdatetime,:MctGuid,:AgtAgent1,:AgtAgent2,:AgtAgent3,:AgtAccountID,:PlyAccountID,"
                + ":PlyTester,:PlyLock,:PlyLocker,:PlyLockDatetime,"
                + "NOW())";
        Map<String, Object> parameters = new HashMap<>(14);
        parameters.put("PlyGuid", model.getPLY_GUID());
        parameters.put("PlyPoint", model.getPLY_Point());
        parameters.put("PlyPointUpdatetime", model.getPLY_PointUpdatetime());
        parameters.put("MctGuid", model.getMCT_GUID());
        parameters.put("AgtAgent1", model.getAGT_Agent1());
        parameters.put("AgtAgent2", model.getAGT_Agent2());
        parameters.put("AgtAgent3", model.getAGT_Agent3());
        parameters.put("AgtAccountID", model.getAGT_AccountID());
        parameters.put("PlyAccountID", model.getPLY_AccountID());
        parameters.put("PlyTester", model.getPLY_Tester());
        parameters.put("PlyLock", model.isPLY_Lock());
        parameters.put("PlyLocker", model.getPLY_Locker());
        parameters.put("PlyLockDatetime", model.getPLY_LockDatetime());
        parameters.put("PlyNickName", model.getPLY_NickName());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 取得玩家資料
     *
     * @param agentGuid
     * @param playerId
     * @return
     */
    public Optional<MemberPlayerModel> getPlayerInfo(String agentGuid, String playerId) {
        String sql = SELECT_PLAYER + " WHERE AGT_Agent3 = :AgtGuid AND PLY_AccountID = :PlayerId";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agentGuid);
        parameters.put("PlayerId", playerId);
        List<MemberPlayerModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                                                                        new BeanPropertyRowMapper<>(MemberPlayerModel.class));

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<MemberPlayerModel> getPlayerListByPlayerID(String playerId) {
        String sql = SELECT_PLAYER + " WHERE PLY_AccountID = :PlayerId";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlayerId", playerId);
        List<MemberPlayerModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(MemberPlayerModel.class));

        return list;
    }

    /**
     * 取得多筆玩家點數
     *
     * @param agentGuid
     * @param playerIdList
     * @return
     */
    public SqlRowSet getPlayersPoint(String agentGuid, List<String> playerIdList) {
        String sql = " SELECT PLY_Point, PLY_AccountID FROM MEM_Player WHERE AGT_Agent3 = :AgtGuid AND PLY_AccountID IN (:PlayerId) ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agentGuid);
        parameters.put("PlayerId", playerIdList);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public Optional<List<MemberPlayerModel>> getPlayerInfoByAgtGuidAndLevel(String agentGuid, String playerId, int level) {
        String sql = SELECT_PLAYER + " WHERE PLY_AccountID = :PlayerId ";
        sql += " AND AGT_Agent" + level + " = :AgtGuid";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agentGuid);
        parameters.put("PlayerId", playerId);

        List<MemberPlayerModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                                                                        new BeanPropertyRowMapper<>(MemberPlayerModel.class));

        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    public Optional<List<MemberPlayerModel>> getPlayerInfoByAgent3List(String playerId, List<String> agent3) {
        String sql = SELECT_PLAYER + " WHERE PLY_AccountID = :PlayerId ";
        sql += " AND AGT_Agent3 IN (:agent3) ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PlayerId", playerId);
        parameters.put("agent3", agent3);

        List<MemberPlayerModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                                                                        new BeanPropertyRowMapper<>(MemberPlayerModel.class));

        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    public Optional<List<MemberPlayerModel>> getPlayerInfoByAgtGuidAndLevelAndPlayerGuid(String agentGuid, String playerId, int level, String playerGuid) {
        String sql = SELECT_PLAYER + " WHERE PLY_AccountID = :PlayerId ";
        sql += " AND AGT_Agent" + level + " = :AgtGuid ";
        sql += " AND PLY_GUID = :PlayerGuid ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agentGuid);
        parameters.put("PlayerId", playerId);
        parameters.put("PlayerGuid", playerGuid);

        List<MemberPlayerModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                                                                        new BeanPropertyRowMapper<>(MemberPlayerModel.class));

        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }


    /**
     * 取得玩家資料
     *
     * @param plyGuid
     * @return
     */
    public Optional<MemberPlayerModel> getPlayerInfo(String plyGuid) {
        String sql = SELECT_PLAYER + " WHERE PLY_Guid = :PlyGuid";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlyGuid", plyGuid);
        List<MemberPlayerModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                                                                        new BeanPropertyRowMapper<>(MemberPlayerModel.class));

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    /**
     * 鎖定玩家
     *
     * @param plyGuid
     * @param agtGuid
     * @return
     */
    public boolean lockPlayer(String plyGuid, String agtGuid) {
        String updateSql = "UPDATE MEM_Player SET PLY_Lock = 1 , PLY_Locker = :AgtGuid , PLY_LockDatetime = NOW() Where PLY_Guid = :PlyGuid AND AGT_Agent3= :AGT_Agent3";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("AGT_Agent3", agtGuid);
        return namedParameterJdbcTemplate.update(updateSql, parameters) > 0;
    }

    /**
     * 解鎖玩家
     *
     * @param plyGuid
     * @param agtGuid
     * @return
     */
    public boolean unlockPlayer(String plyGuid, String agtGuid) {
        String updateSql = "UPDATE MEM_Player SET PLY_Lock = 0 , PLY_Locker = Null , PLY_LockDateTime = Null Where PLY_Guid = :PlyGuid And PLY_Lock = 1 And PLY_Locker = :AgtGuid";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("PlyGuid", plyGuid);
        return namedParameterJdbcTemplate.update(updateSql, parameters) > 0;
    }


    /**
     * 踢單一玩家
     *
     * @param plyGuid
     * @return
     */
    public boolean kickSinglePlayer(String plyGuid) {
        String updateSql = "UPDATE MEM_Player SET PLY_Kick = 1  Where PLY_Guid = :PlyGuid ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlyGuid", plyGuid);
        return namedParameterJdbcTemplate.update(updateSql, parameters) > 0;
    }

    /**
     * 踢All玩家
     *
     * @param agtGuid
     * @return
     */
    public boolean kickPlayerByAgent(String agtGuid) {

        String updateSql = "UPDATE MEM_Player SET PLY_Kick = 1  Where AGT_Agent3 = :AGT_Agent3  ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_Agent3", agtGuid);
        return namedParameterJdbcTemplate.update(updateSql, parameters) > 0;
    }

    public void kickPlayerBatch(List<String> plyList) {
        List<Object[]> batchArgs = plyList.stream().map(p -> new Object[]{p}).collect(Collectors.toList());
        if (!batchArgs.isEmpty()) {
            String sql = "UPDATE MEM_Player SET PLY_Kick = 1  Where  PLY_GUID=? ";
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
    }

    public List<String> getPlayerGuidListByAgent(String agtGuid) {
        String updateSql = "SELECT PLY_GUID FROM MEM_Player   Where AGT_Agent3 = :AGT_Agent3 AND PLY_Kick = 0  ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_Agent3", agtGuid);
        return namedParameterJdbcTemplate.queryForList(updateSql, parameters, String.class);
    }

    /**
     * 解除踢的狀態
     *
     * @param plyGuid
     */
    public void clearKick(String plyGuid) {
        String sql = "UPDATE MEM_Player SET PLY_Kick = 0 WHERE PLY_Guid = :PlyGuid ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlyGuid", plyGuid);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    /**
     * 更新玩家Token
     *
     * @param plyGuid
     * @param token
     * @return
     */
    public boolean updateToken(String plyGuid, String token) {
        String sql = "UPDATE MEM_Player SET PLY_Token = :Token WHERE PLY_Guid = :PlyGuid ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Token", token);
        parameters.put("PlyGuid", plyGuid);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 當Token到期時，更新Token值為登出
     *
     * @param plyGuid
     * @param token
     * @return
     */
    public boolean tokenExpired(String plyGuid, String token) {
        String text = "LOG_OUT";
        String sql = "UPDATE MEM_Player SET PLY_Token = :Text WHERE PLY_GUID = :PlyGuid AND PLY_Token = :Token";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Text", text);
        parameters.put("PlyGuid", plyGuid);
        parameters.put("Token", token);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }


    /**
     * 取得玩家錢包並等待update !!!!
     *
     * @param plyGuid
     * @return
     */
    public BigDecimal getWalletForUpdate(String plyGuid) {
        String sql = "SELECT PLY_Point FROM MEM_Player WHERE   PLY_GUID = :PLY_GUID FOR UPDATE";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PLY_GUID", plyGuid);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class);
    }

    public boolean updateWallet(String plyGuid, BigDecimal pointAmount) {

        String sql = "UPDATE MEM_Player "
                + " SET"
                + " PLY_Point = (PLY_Point +:PLY_Point) ,"
                + " PLY_PointUpdatetime = NOW() "
                + " WHERE  PLY_GUID=:PLY_GUID   AND (PLY_Point +:PLY_Point)>=0  ";
        Map<String, Object> parameters = new HashMap<>(2);

        parameters.put("PLY_Point", pointAmount);
        parameters.put("PLY_GUID", plyGuid);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 踢玩家並將token改為LOG_OUT
     */
    public boolean kickAndLogoutPlayer(String plyGuid) {
        String sql = " UPDATE MEM_Player SET PLY_Kick = 1, PLY_Token='LOG_OUT' " +
                " WHERE PLY_GUID = :PLY_GUID ";
        Map<String, Object> parameters = new HashMap<>(1);

        parameters.put("PLY_GUID", plyGuid);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 使用玩家plyGUID列表取得玩家帳號列表
     */
    public List<String> getOnlinePlayerAccountIdsByPlayerIdList(List<String> plyGuidList) {
        String sql = " SELECT PLY_AccountID " +
                " FROM MEM_Player " +
                " WHERE PLY_GUID IN (:PlyGuidList)  " +
                " ORDER BY PLY_AccountID ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlyGuidList", plyGuidList);

        return namedParameterJdbcTemplate.queryForList(sql, parameters, String.class);
    }
}
