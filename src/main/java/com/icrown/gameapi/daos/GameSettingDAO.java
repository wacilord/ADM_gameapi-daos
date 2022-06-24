package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameSettingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank
 */
@Repository
public class GameSettingDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addGameSettingKeyAndValue(GameSettingModel model) {
        String sql = " Insert into SYS_GameSetting(AGT_GUID,SYS_GameSetting_Key,SYS_GameSetting_Value,SYS_GameSetting_Note) " +
                " Value(:AgtGuid,:GameSettingKey,:GameSettingValue,:GameSettingNode) ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("AgtGuid", model.getAGT_GUID());
        parameters.put("GameSettingKey", model.getSYS_GameSetting_Key());
        parameters.put("GameSettingValue", model.getSYS_GameSetting_Value());
        parameters.put("GameSettingNode", model.getSYS_GameSetting_Note());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean addAgentGameSetting(GameSettingModel model) {
        String sql = " INSERT INTO SYS_GameSetting(AGT_GUID, AGT_Agent1, AGT_Agent2, AGT_Agent3, AGT_Level, AGT_AccountID, MCT_Domain, SYS_GameSetting_Key, SYS_GameSetting_Value, SYS_GameSetting_Note) " +
                " VALUES(:AGT_GUID, :AGT_Agent1, :AGT_Agent2, :AGT_Agent3, :AGT_Level, :AGT_AccountID, :MCT_Domain, :SYS_GameSetting_Key, :SYS_GameSetting_Value, :SYS_GameSetting_Note) ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("AGT_GUID", model.getAGT_GUID());
        parameters.put("AGT_Agent1", model.getAGT_Agent1());
        parameters.put("AGT_Agent2", model.getAGT_Agent2());
        parameters.put("AGT_Agent3", model.getAGT_Agent3());
        parameters.put("AGT_Level", model.getAGT_Level());
        parameters.put("AGT_AccountID", model.getAGT_AccountID());
        parameters.put("MCT_Domain", model.getMCT_Domain());
        parameters.put("SYS_GameSetting_Key", model.getSYS_GameSetting_Key());
        parameters.put("SYS_GameSetting_Value", model.getSYS_GameSetting_Value());
        parameters.put("SYS_GameSetting_Note", model.getSYS_GameSetting_Note());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean updateGameSettingKeyAndValue(GameSettingModel model) {

        String sql = " UPDATE SYS_GameSetting SET SYS_GameSetting_Key = :SYS_GameSetting_Key, " +
                " SYS_GameSetting_Value = :SYS_GameSetting_Value, SYS_GameSetting_Note = :SYS_GameSetting_Note " +
                " WHERE AGT_GUID = :AGT_GUID AND SYS_GameSetting_Key = :SYS_GameSetting_Key ";

        SqlParameterSource ps = new BeanPropertySqlParameterSource(model);
        return namedParameterJdbcTemplate.update(sql, ps) > 0;
    }

    @Transactional(rollbackFor = Exception.class, timeout = 10)
    public int[] batchAddGameSetting(List<GameSettingModel> gameSettingList) {
        String sql = " INSERT INTO SYS_GameSetting(AGT_GUID, AGT_Agent1, AGT_Agent2, AGT_Agent3, AGT_Level, AGT_AccountID, MCT_Domain, SYS_GameSetting_Key, SYS_GameSetting_Value, SYS_GameSetting_Note) " +
                " VALUES(?,?,?,?,?,?,?,?,?,?) ";

        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                GameSettingModel gameSettingModel = gameSettingList.get(i);
                preparedStatement.setString(1, gameSettingModel.getAGT_GUID());
                preparedStatement.setString(2, gameSettingModel.getAGT_Agent1());
                preparedStatement.setString(3, gameSettingModel.getAGT_Agent2());
                preparedStatement.setString(4, gameSettingModel.getAGT_Agent3());
                preparedStatement.setInt(5, gameSettingModel.getAGT_Level());
                preparedStatement.setString(6, gameSettingModel.getAGT_AccountID());
                preparedStatement.setString(7, gameSettingModel.getMCT_Domain());
                preparedStatement.setString(8, gameSettingModel.getSYS_GameSetting_Key());
                preparedStatement.setString(9, gameSettingModel.getSYS_GameSetting_Value());
                preparedStatement.setString(10, gameSettingModel.getSYS_GameSetting_Note());
            }

            @Override
            public int getBatchSize() {
                return gameSettingList.size();
            }
        };

        return jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
    }

    @Transactional(rollbackFor = Exception.class, timeout = 10)
    public int[] batchUpdateGameSetting(List<GameSettingModel> gameSettingList) {
        String sql = " UPDATE SYS_GameSetting SET AGT_GUID = ?, SYS_GameSetting_Value = ?, SYS_GameSetting_Note = ? " +
                " WHERE AGT_GUID = ? AND SYS_GameSetting_Key = ? ";

        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                GameSettingModel gameSettingModel = gameSettingList.get(i);
                preparedStatement.setString(1, gameSettingModel.getAGT_GUID());
                preparedStatement.setString(2, gameSettingModel.getSYS_GameSetting_Value());
                preparedStatement.setString(3, gameSettingModel.getSYS_GameSetting_Note());
                preparedStatement.setString(4, gameSettingModel.getAGT_GUID());
                preparedStatement.setString(5, gameSettingModel.getSYS_GameSetting_Key());
            }

            @Override
            public int getBatchSize() {
                return gameSettingList.size();
            }
        };

        return jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
    }

    public SqlRowSet getGameSetting(String agtGuid, String key) {
        String sql = "SELECT SYS_GameSetting_Value FROM SYS_GameSetting WHERE AGT_GUID=:AGT_GUID AND SYS_GameSetting_Key=:SYS_GameSetting_Key LIMIT 0,1 ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AGT_GUID", agtGuid);
        parameters.put("SYS_GameSetting_Key", key);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getGameSettingCountByAgtGuidAndKey(String agtGuid, String key) {
        String sql = "SELECT COUNT(1) FROM SYS_GameSetting WHERE AGT_GUID=:AGT_GUID AND SYS_GameSetting_Key=:SYS_GameSetting_Key ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("AGT_GUID", agtGuid);
        parameters.put("SYS_GameSetting_Key", key);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    public List<String> getAllGameSettingKeys() {
        String sql = " SELECT CONCAT(AGT_GUID,SYS_GameSetting_Key) AS gameSettingKey From SYS_GameSetting; ";
        Map<String, Object> parameters = new HashMap<>(0);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<String> keys = new ArrayList<>();
        while (rowSet.next()) {
            keys.add(rowSet.getString("gameSettingKey"));
        }
        return keys;
    }

    public SqlRowSet getAllGameSettingByagtGuid(String agtGuid) {
        String sql = " SELECT AGT_GUID,SYS_GameSetting_Key,SYS_GameSetting_Value,SYS_GameSetting_Note " +
                " FROM SYS_GameSetting WHERE AGT_GUID = :AGT_GUID ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_GUID", agtGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public List<GameSettingModel> getAllGameSettingByKey(String key) {

        String sql = " SELECT SYS_GameSetting_ID, AGT_GUID, SYS_GameSetting_Key, SYS_GameSetting_Value, SYS_GameSetting_Note " +
                " FROM SYS_GameSetting " +
                " WHERE SYS_GameSetting_Key=:SYS_GameSetting_Key ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("SYS_GameSetting_Key", key);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameSettingModel.class));
    }

    public SqlRowSet getMultiplierListByGameCode(String gameSettingKey, String jsonPath) {
        String sql = " SELECT AGT_GUID, JSON_Query(SYS_GameSetting_Value, :JsonPath) AS multiplierList " +
                " FROM SYS_GameSetting " +
                " WHERE SYS_GameSetting_Key=:GameSettingKey;";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("JsonPath", jsonPath);
        parameters.put("GameSettingKey", gameSettingKey);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getMultiplierListByAgtGuidAndGameCode(String agtGuid, String gameSettingKey, String jsonPath) {
        String sql = " SELECT AGT_GUID, JSON_Query(SYS_GameSetting_Value, :JsonPath) AS multiplierList " +
                " FROM SYS_GameSetting " +
                " WHERE SYS_GameSetting_Key=:GameSettingKey AND AGT_GUID =:AGT_GUID;";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("JsonPath", jsonPath);
        parameters.put("GameSettingKey", gameSettingKey);
        parameters.put("AGT_GUID", agtGuid);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public boolean updateGameMultiplierList(String agtGuid, String gameSettingKey, String jsonPath, String multiplierList) {

        String sql = " UPDATE SYS_GameSetting " +
                " SET SYS_GameSetting_Value = JSON_REPLACE(SYS_GameSetting_Value, :JsonPath, JSON_COMPACT(:MultiplierList))" +
                " WHERE SYS_GameSetting_Key =:GameSettingKey AND AGT_GUID =:AGT_GUID; ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("JsonPath", jsonPath);
        parameters.put("MultiplierList", multiplierList);
        parameters.put("GameSettingKey", gameSettingKey);
        parameters.put("AGT_GUID", agtGuid);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean updateGameMultiplierListByDomain(String domain, String gameSettingKey, String jsonPath, String multiplierList) {

        String sql = " UPDATE SYS_GameSetting " +
                " SET SYS_GameSetting_Value = JSON_REPLACE(SYS_GameSetting_Value, :JsonPath, JSON_COMPACT(:MultiplierList))" +
                " WHERE SYS_GameSetting_Key =:GameSettingKey AND MCT_Domain =:MCT_Domain; ";

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("JsonPath", jsonPath);
        parameters.put("MultiplierList", multiplierList);
        parameters.put("GameSettingKey", gameSettingKey);
        parameters.put("MCT_Domain", domain);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean updateGameMultiplierListAllAgent(String gameSettingKey, String jsonPath, String multiplierList) {

        String sql = " UPDATE SYS_GameSetting " +
                " SET SYS_GameSetting_Value = JSON_REPLACE(SYS_GameSetting_Value, :JsonPath, JSON_COMPACT(:MultiplierList))" +
                " WHERE SYS_GameSetting_Key =:GameSettingKey; ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("JsonPath", jsonPath);
        parameters.put("MultiplierList", multiplierList);
        parameters.put("GameSettingKey", gameSettingKey);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public SqlRowSet checkJsonIsValid(String multiplierList) {
        String sql = " SELECT JSON_VALID(:MultiplierList) AS valid; ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MultiplierList", multiplierList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public List<GameSettingModel> getJackpotByAgent1(String agent1) {
        String sql = " SELECT * FROM SYS_GameSetting WHERE AGT_Agent1 = :AGT_Agent1 AND SYS_GameSetting_Key ='jackpot' ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("AGT_Agent1", agent1);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameSettingModel.class));
    }

    public List<GameSettingModel> getJackpotByDomain(String domain) {
        String sql = " SELECT AGT_GUID, AGT_Agent1, AGT_Agent2, AGT_Agent3, AGT_Level, AGT_AccountID, MCT_Domain, SYS_GameSetting_Key, SYS_GameSetting_Value, SYS_GameSetting_Note " +
                " FROM SYS_GameSetting " +
                " WHERE MCT_Domain = :MCT_Domain AND SYS_GameSetting_Key ='jackpot' ";

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MCT_Domain", domain);

        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(GameSettingModel.class));
    }
}
