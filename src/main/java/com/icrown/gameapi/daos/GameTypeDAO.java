package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameTypeDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得所有遊戲類型
     *
     * @return
     */
    public List<GameTypeModel> getAllGameType() {
        String sql = "SELECT GM_GameType,GM_TypeSerial,GM_GameTypeName,GM_TypeIcon,GM_TableName FROM SYS_GameType ";
        System.out.println("sql:");
        System.out.println(sql);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
        while(rowSet.next()) {
            System.out.println("GM_GameType");
            System.out.println(rowSet.getString("GM_GameType"));
            System.out.println("GM_TypeSerial");
            System.out.println(rowSet.getInt("GM_TypeSerial"));
            System.out.println("GM_GameTypeName");
            System.out.println(rowSet.getString("GM_GameTypeName"));
            System.out.println("GM_TableName");
            System.out.println(rowSet.getString("GM_TableName"));
        }
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GameTypeModel.class));
    }

    /**
     * 依語言取得所有遊戲類型
     *
     * @return
     */
    public List<GameTypeModel> getAllGameTypeByLanguage(String langauge) {
        langauge = langauge.replace("-", "_");
        String sql = "SELECT GM_GameType,GM_TypeSerial,GM_" + langauge + "_GameTypeName,GM_TypeIcon,GM_TableName FROM SYS_GameType ";
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
        List<GameTypeModel> list = new ArrayList<>();
        GameTypeModel model = null;
        while(rowSet.next()) {
            model = new GameTypeModel();
            model.setGM_GameType(rowSet.getString("GM_GameType"));
            model.setGM_TypeSerial(rowSet.getInt("GM_TypeSerial"));
            model.setGM_GameTypeName(rowSet.getString("GM_" + langauge + "_GameTypeName"));
            model.setGM_TableName(rowSet.getString("GM_TableName"));
            list.add(model);
        }
        return list;
    }

    /**
     * 新增遊戲類型
     */
    public void addGameType(String gameType, int serial, String gameTypeName, String typeIcon, String tableName) {
        String sql = " INSERT INTO SYS_GameType (GM_GameType, GM_TypeSerial, GM_GameTypeName, GM_TypeIcon, GM_TableName, GM_zh_CN_GameTypeName) " +
                " VALUES(:GM_GameType, :GM_TypeSerial, :GM_GameTypeName, :GM_TypeIcon, :GM_TableName, :GM_zh_CN_GameTypeName) ";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("GM_GameType", gameType);
        parameters.put("GM_TypeSerial", serial);
        parameters.put("GM_GameTypeName", gameTypeName);
        parameters.put("GM_TypeIcon", typeIcon);
        parameters.put("GM_TableName", tableName);
        parameters.put("GM_zh_CN_GameTypeName", gameTypeName);

        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
