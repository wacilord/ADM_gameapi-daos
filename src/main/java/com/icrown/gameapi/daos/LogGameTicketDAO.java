package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.LogGameTicketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LogGameTicketDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 判斷玩家是否在玩遊戲
     *
     * @param plyGuid
     * @return
     */
    public boolean playerIsOnline(String plyGuid) {
        String sql = "SELECT count(1) FROM LOG_GameTicket WHERE PLY_GUID = :PlyGuid And LGT_Closedatetime Is Null AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY) ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlyGuid", plyGuid);
        int count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count > 0;
    }

    /**
     * 以token判斷玩家是否在玩遊戲
     *
     * @param token
     * @return
     */
    public boolean playerIsOnlineByToken(String token) {
        String sql = "SELECT COUNT(1) AS count FROM LOG_GameTicket WHERE LGT_Createdatetime > DATE_SUB(NOW(), INTERVAL 2 DAY) AND LGT_Token= :token AND LGT_Closedatetime IS NULL;";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("token", token);
        int count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count > 0;
    }

    /**
     * 取得玩家最後在線時間
     *
     * @param plyGuid
     * @return
     */
    public Optional<Date> lastOnlineTime(String plyGuid) {
        String sql = "SELECT LGT_Createdatetime AS maxDate FROM LOG_GameTicket WHERE PLY_GUID = :PlyGuid ORDER BY LGT_Createdatetime DESC LIMIT 1";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PlyGuid", plyGuid);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        if (rowSet.next()) {
            Date date = rowSet.getDate("maxDate");
            return Optional.of(date);
        }
        return Optional.empty();
    }

    /**
     * 利用Token取得玩家Guid
     *
     * @param token
     * @return
     */
    public Optional<String> getPlayerGuidByToken(String token) {
        String sql = "SELECT PLY_GUID FROM LOG_GameTicket WHERE LGT_Token = :Token ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Token", token);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        if (!rowSet.next()) {
            return Optional.empty();
        }
        String playerGuid = rowSet.getString("PLY_GUID");
        return Optional.of(playerGuid);
    }

    public int onlineCount(String agtGuid, int level) {
        String sql = " SELECT COUNT(1) FROM LOG_GameTicket WHERE LGT_Closedatetime IS NULL " +
                " AND LGT_Createdatetime <= NOW() AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY) ";

        if (level == 1) {
            sql += " AND AGT_Agent1 =:agtGuid ";
        }
        if (level == 2) {
            sql += " AND AGT_Agent2 =:agtGuid ";
        }
        if (level == 3) {
            sql += " AND AGT_Agent3 =:agtGuid ";
        }
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("agtGuid", agtGuid);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);

    }

    /**
     * 取得線上玩家資料
     *
     * @return
     */
    public List<LogGameTicketModel> getOnlinePlayer() {
        String sql = " SELECT " +
                " LGT_Ticket,GameServer_Name,LGL_Work,AGT_Agent1,AGT_Agent2,LGT_Token,AGT_Agent3,PLY_GUID,LGT_IsMember,LGT_IP," +
                " LGL_GameCode,LGT_Createdatetime,LGT_Closedatetime,LGT_Host" +
                " FROM LOG_GameTicket  " +
                " WHERE LGT_Closedatetime IS NULL AND LGT_Createdatetime <= NOW() AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY) ";

        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(LogGameTicketModel.class));
    }

    /**
     * 取得指定代理線上玩家資料
     *
     * @return
     */
    public List<LogGameTicketModel> getOnlinePlayerByAgnet3(String agtGuid, int startPos, int pageSize) {
        String sql = " SELECT " +
                " LGT_Ticket,GameServer_Name,LGL_Work,AGT_Agent1,AGT_Agent2,LGT_Token,AGT_Agent3,PLY_GUID,LGT_IsMember,LGT_IP," +
                " LGL_GameCode,LGT_Createdatetime,LGT_Closedatetime,LGT_Host" +
                " FROM LOG_GameTicket  " +
                " WHERE AGT_Agent3= :AGT_Agent3 AND LGT_Closedatetime IS NULL AND LGT_Createdatetime <= NOW() AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY) " +
                " LIMIT :StartPos, :PageSize ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AGT_Agent3", agtGuid);
        parameters.put("StartPos", startPos);
        parameters.put("PageSize", pageSize);
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(LogGameTicketModel.class));
    }

    /**
     * 查詢每個總代線上玩家數
     *
     * @return
     */
    public SqlRowSet getOnlinePlayerCountByAgent1() {
        String sql = " SELECT " +
                " COUNT(1) as playerCount,AGT_Agent1 as agent1 " +
                " FROM LOG_GameTicket " +
                " WHERE LGT_Closedatetime IS NULL AND LGT_Createdatetime <= NOW() AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY)  " +
                " GROUP BY AGT_Agent1 ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public List<Integer> getGameTypeByTimeRangeAndAgent1(String agent1, Date startTime, Date endTime) {
        String sql = " SELECT SUBSTR(LGL_GameCode,1,1) AS GameType " +
                " FROM LOG_GameTicket " +
                " WHERE AGT_Agent1 = :Agent1 AND LGT_Createdatetime >= :StartTime AND LGT_Createdatetime < :EndTime " +
                " GROUP BY SUBSTR(LGL_GameCode,1,1) ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("Agent1", agent1);
        parameters.put("StartTime", startTime);
        parameters.put("EndTime", endTime);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<Integer> list = new ArrayList<>();
        while (rowSet.next()) {
            list.add(Integer.valueOf(rowSet.getString("GameType")));
        }
        return list;
    }


    public List<String> getPlyGuidByGameCodeAndAgent1List(int gameCode, List<String> agent1List, Date createDateTime) {
        String sql = " SELECT PLY_GUID FROM LOG_GameTicket " +
                " WHERE LGL_GameCode = :LGL_GameCode AND LGT_Createdatetime >= :LGT_Createdatetime AND " +
                " LGT_Closedatetime IS NULL AND AGT_Agent1 in (:AGT_Agent1) ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("LGL_GameCode", gameCode);
        parameters.put("LGT_Createdatetime", createDateTime);
        parameters.put("AGT_Agent1", agent1List);

        List<String> playGuidList = new ArrayList<>();
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        while (rowSet.next()) {
            playGuidList.add(rowSet.getString("PLY_GUID"));
        }
        return playGuidList;
    }

    public List<LogGameTicketModel> getLogDateAndLogPlayerIdList(String startDate, String endDate) {
        String sql = " SELECT STR_TO_DATE(DATE_FORMAT(LGT_Createdatetime,'%Y-%m-%d 00:00:00'),'%Y-%m-%d %T') AS LGT_Date, PLY_GUID, AGT_Agent1 " +
                " FROM LOG_GameTicket " +
                " WHERE LGT_Createdatetime >= :StartDate AND LGT_Createdatetime < :EndDate " +
                " GROUP BY DATE_FORMAT(LGT_Createdatetime,'%Y-%m-%d 00:00:00'), PLY_GUID, AGT_Agent1 ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);

        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<LogGameTicketModel> playGuidList = new ArrayList<>();
        while (rowSet.next()) {
            LogGameTicketModel model = new LogGameTicketModel();
            model.setPLY_GUID(rowSet.getString("PLY_GUID"));
            model.setLGT_Createdatetime(rowSet.getDate("LGT_Date"));
            model.setAGT_Agent1(rowSet.getString("AGT_Agent1"));

            playGuidList.add(model);
        }
        return playGuidList;
    }

    public List<LogGameTicketModel> getLogDateAndAndGameCodeAndLogPlayerIdList(String startDate, String endDate) {
        String sql = " SELECT STR_TO_DATE(DATE_FORMAT(LGT_Createdatetime,'%Y-%m-%d 00:00:00'),'%Y-%m-%d %T') AS LGT_Date, PLY_GUID, LGL_GameCode, AGT_Agent1 " +
                " FROM LOG_GameTicket " +
                " WHERE LGT_Createdatetime >= :StartDate AND LGT_Createdatetime < :EndDate " +
                " GROUP BY DATE_FORMAT(LGT_Createdatetime,'%Y-%m-%d 00:00:00'), PLY_GUID, LGL_GameCode, AGT_Agent1 ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("StartDate", startDate);
        parameters.put("EndDate", endDate);

        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<LogGameTicketModel> playGuidList = new ArrayList<>();
        while (rowSet.next()) {
            LogGameTicketModel model = new LogGameTicketModel();
            model.setPLY_GUID(rowSet.getString("PLY_GUID"));
            model.setLGT_Createdatetime(rowSet.getDate("LGT_Date"));
            model.setLGL_GameCode(rowSet.getString("LGL_GameCode"));
            model.setAGT_Agent1(rowSet.getString("AGT_Agent1"));

            playGuidList.add(model);
        }
        return playGuidList;
    }

    public SqlRowSet getPlayingGameCode(String playGuid) {
        String sql = " SELECT LGL_GameCode FROM LOG_GameTicket " +
                " WHERE PLY_GUID = :PLY_GUID AND " +
                " LGT_Closedatetime IS NULL AND " +
                " LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY) " +
                " ORDER BY LGT_Createdatetime DESC LIMIT 1 ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("PLY_GUID", playGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getOnlinePlayerByAgent(String agent1) {
        String sql = "SELECT LGL_GameCode as gameCode,COUNT(1) as number" +
                " FROM LOG_GameTicket WHERE LGT_Closedatetime IS NULL AND LGT_Createdatetime <= NOW()" +
                " AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY) AND AGT_Agent1 = :agtGuid" +
                " GROUP BY LGL_GameCode";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("agtGuid", agent1);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getOnlinePlayerCountList(String agentGuid, int gameCode) {
        String sql = "SELECT PLY_GUID as guid" +
                " FROM LOG_GameTicket WHERE LGT_Closedatetime IS NULL AND LGT_Createdatetime <= NOW()" +
                " AND LGT_Createdatetime > DATE_ADD(NOW(), INTERVAL -2 DAY)" +
                " AND AGT_Agent1 = :agtGuid AND LGL_GameCode = :gameCode";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("agtGuid", agentGuid);
        parameters.put("gameCode", gameCode);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
