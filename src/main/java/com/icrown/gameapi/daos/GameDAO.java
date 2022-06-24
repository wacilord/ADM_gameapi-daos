package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author David
 */
@Repository
public class GameDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 新增遊戲
     */
    public boolean addSysGame(String gameCode, int gameType, String name, String version, String gameUrl) {

        String sql = " Insert into SYS_Games(GM_GameCode,GM_GameType,GM_Name,GM_Version,GM_GameUrl,GM_zh_CN_Name,GM_zh_CN_Version) " +
                " Value(:GM_GameCode,:GM_GameType,:GM_Name,:GM_Version,:GM_GameUrl,:GM_zh_CN_Name,:GM_zh_CN_Version) ";

        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("GM_GameType", gameType);
        parameters.put("GM_Name", name);
        parameters.put("GM_Version", version);
        parameters.put("GM_GameUrl", gameUrl);
        parameters.put("GM_zh_CN_Name", name);
        parameters.put("GM_zh_CN_Version", version);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 取得所有遊戲列表
     */
    public SqlRowSet getAllGameList() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT GM_GameCode AS gameCode ");
        sql.append(",SYS_Games.GM_GameType AS gameType ");
        sql.append(",GM_Name AS gameName ");
        sql.append(",GM_Version AS gameVersion ");
        sql.append(",GM_EventVersion AS eventVersion ");
        sql.append(",GM_Maintain AS isMaintain ");
        sql.append("FROM SYS_Games ");

        return jdbcTemplate.queryForRowSet(sql.toString());
    }

    public SqlRowSet getAllGameListWithLanguage(List<String> languageList) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT GM_GameCode AS gameCode ");
        sql.append(",SYS_Games.GM_GameType AS gameType ");
        sql.append(",GM_Name AS gameName ");
        sql.append(",GM_Version AS gameVersion ");
        languageList.forEach(lang -> {
            String lanTemp = lang.replace("-", "_");
            sql.append(",GM_").append(lanTemp).append("_Version ");
        });

        sql.append(",GM_EventVersion AS eventVersion ");
        sql.append(",GM_Maintain AS isMaintain ");
        sql.append("FROM SYS_Games ");

        return jdbcTemplate.queryForRowSet(sql.toString());
    }

    /**
     * 取得所有已開啟遊戲列表
     */
    public SqlRowSet getAllEnableGameList() {
        String sql = "SELECT GM_GameCode AS gameCode" +
                ", GM_GameType AS gameType" +
                ", GM_Enable AS enable" +
                ", GM_Maintain AS maintain" +
                ", GM_Name AS gameName" +
                ", GM_Version AS gameVersion" +
                " FROM SYS_Games WHERE GM_Enable = 1";

        return jdbcTemplate.queryForRowSet(sql);
    }

    /**
     * 取得所有已開啟遊戲列表
     */
    public SqlRowSet getAllEnableGameListBylanguage(String language) {
        if (language.contains("-")) {
            language = language.replace("-", "_");
        }
        String sql = "SELECT GM_GameCode AS gameCode" +
                ", GM_GameType AS gameType" +
                ", GM_Enable AS enable" +
                ", GM_Maintain AS maintain" +
                ", GM_" + language + "_Name AS gameName" +
                ", GM_Version AS gameVersion" +
                " FROM SYS_Games WHERE GM_Enable = 1";

        return jdbcTemplate.queryForRowSet(sql);
    }

    /**
     * 取得所有維護的遊戲列表
     */
    public SqlRowSet getAllMaintainGameList() {
        String sql = "SELECT GM_GameCode AS gameCode" +
                ", GM_GameType AS gameType" +
                ", GM_Enable AS enable" +
                ", GM_Maintain AS maintain" +
                ", GM_Name AS gameName" +
                ", GM_Version AS gameVersion" +
                " FROM SYS_Games WHERE GM_Maintain = 1";

        return jdbcTemplate.queryForRowSet(sql);
    }

    /**
     * 依照代理商取得遊戲列表
     */
    public SqlRowSet findGameListByMerchant(String mctGuid) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT GM_GameCode AS gameCode ");
        sql.append(",SYS_Games.GM_GameType AS gameType ");
        sql.append(",GM_Name AS gameName ");
        sql.append("FROM SYS_Games ");
        sql.append("INNER JOIN AGT_AgentGameRelation R ON SYS_Games.GM_GameType = R.GM_GameType ");
        sql.append("WHERE R.MCT_Guid = :MCT_GUID ");
        sql.append("AND R.AGG_Enable = 1 ");
        sql.append("AND GM_Enable = 1 ");
        sql.append("AND GM_Maintain = 0 ");

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MCT_GUID", mctGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql.toString(), parameters);
    }

    /**
     * 依照代理商,語言取得遊戲列表
     */
    public SqlRowSet findGameListByMerchantAndLanguage(String mctGuid, String language) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT GM_GameCode AS gameCode ");
        sql.append(",SYS_Games.GM_GameType AS gameType ");
        sql.append(",").append("GM_").append(language).append("_Name").append(" AS gameName ");
        sql.append("FROM SYS_Games ");
        sql.append("INNER JOIN AGT_AgentGameRelation R ON SYS_Games.GM_GameType = R.GM_GameType ");
        sql.append("WHERE R.MCT_Guid = :MCT_GUID ");
        sql.append("AND R.AGG_Enable = 1 ");
        sql.append("AND GM_Enable = 1 ");
        sql.append("AND GM_Maintain = 0 ");

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MCT_GUID", mctGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql.toString(), parameters);
    }

    public SqlRowSet getGameUrl(String gameCode) {
        SqlRowSet rowSet = null;
        StringBuilder sql = new StringBuilder();
        try {
            sql.append(" SELECT GM_GameUrl,GM_Enable,GM_Maintain,GM_Version,GM_en_Version,GM_zh_CN_Version,GM_th_Version,IFNULL(GM_EventVersion,'') AS GM_EventVersion,IFNULL(GM_zh_CN_Name,'') as GM_zh_CN_Name,IFNULL(GM_en_Name,'') as GM_en_Name,IFNULL(GM_th_Name,'') as GM_th_Name  FROM SYS_Games ");
            sql.append(" WHERE GM_GameCode = :GM_GameCode ");
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("GM_GameCode", gameCode);
            rowSet = namedParameterJdbcTemplate.queryForRowSet(sql.toString(), parameters);
        } catch (Exception e) {
            sql = new StringBuilder();
            sql.append(" SELECT GM_GameUrl,GM_Enable,GM_Maintain,GM_Version,IFNULL(GM_EventVersion,'') AS GM_EventVersion,IFNULL(GM_zh_CN_Name,'') as GM_zh_CN_Name,IFNULL(GM_en_Name,'') as GM_en_Name,IFNULL(GM_th_Name,'') as GM_th_Name  FROM SYS_Games ");
            sql.append(" WHERE GM_GameCode = :GM_GameCode ");
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("GM_GameCode", gameCode);
            rowSet = namedParameterJdbcTemplate.queryForRowSet(sql.toString(), parameters);
        }

        return rowSet;
    }

    public Optional<GameModel> getGameByGameCode(String gameCode) {
        String sql = "SELECT GM_GameCode,GM_GameType,GM_Enable,GM_Maintain," +
                " GM_Name,GM_GameUrl " +
                " FROM SYS_Games WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("GameCode", gameCode);
        List<GameModel> list = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public Optional<GameModel> getGameByGameCodeAndLanguage(String gameCode, String language) {
        String gameNameLang = "GM_" + language.replace("-", "_") + "_Name";
        String sql = "SELECT GM_GameCode,GM_GameType,GM_Enable,GM_Maintain," +
                gameNameLang + " AS GM_Name ,GM_GameUrl " +
                " FROM SYS_Games WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("GameCode", gameCode);
        List<GameModel> list = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public boolean changeGameVersion(String gameCode, String version) {
        String sql = "UPDATE SYS_Games SET GM_Version = :Version WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Version", version);
        parameters.put("GameCode", gameCode);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean changeGameVersionWithLanguage(String gameCode, String version, String language) {
        String sql = " UPDATE SYS_Games SET GM_" + language + "_Version = :Version ";
        if (language.equals("zh_CN")) {
            sql += ", GM_Version = :Version ";
        }
        sql += " WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Version", version);
        parameters.put("GameCode", gameCode);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean changeEventVersion(String gameCode, String version) {
        String sql = "UPDATE SYS_Games SET GM_EventVersion = :Version WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("Version", version);
        parameters.put("GameCode", gameCode);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean changeGameVersion(String gameCode, String version, String eventVersion) {
        String sql = "UPDATE SYS_Games SET GM_Version = :Version, GM_EventVersion = :EventVersion WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("Version", version);
        parameters.put("EventVersion", eventVersion);
        parameters.put("GameCode", gameCode);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }


    public boolean changeGameMaintain(String gameCode, boolean isMaintain) {
        String sql = "UPDATE SYS_Games SET GM_Maintain = :GM_Maintain WHERE GM_GameCode = :GameCode ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("GM_Maintain", isMaintain);
        parameters.put("GameCode", gameCode);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public SqlRowSet getLobbyUrl() {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT GM_GameUrl,GM_Enable,GM_Maintain,GM_Version,IFNULL(GM_EventVersion,'') AS GM_EventVersion FROM SYS_Games ");
        sql.append(" WHERE GM_GameCode = :GM_GameCode ");
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("GM_GameCode", "lobby");
        return namedParameterJdbcTemplate.queryForRowSet(sql.toString(), parameters);
    }
}

