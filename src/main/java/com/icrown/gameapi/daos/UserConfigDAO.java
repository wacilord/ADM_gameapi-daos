package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Frank
 */
@Repository
public class UserConfigDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<String> getValueByName(String name) {
        String sql = " Select UserConfig_Value From SYS_UserConfig Where UserConfig_Name = :Name ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Name", name);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        if (rowSet.next()) {
            String value = rowSet.getString("UserConfig_Value");
            return Optional.of(value);
        }
        return Optional.empty();
    }

    /**
     * 取得所有鍵值
     *
     * @return
     */
    public List<String> getAllKeys() {
        String sql = " Select UserConfig_Name From SYS_UserConfig ";
        Map<String, Object> parameters = new HashMap<>();
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<String> keys = new ArrayList<>();
        while (rowSet.next()) {
            keys.add(rowSet.getString("UserConfig_Name"));
        }
        return keys;
    }

    /**
     * 更新userConfig
     */
    public boolean updateUserConfig(String userConfigName, String userConfigValue) {
        String sql = " UPDATE SYS_UserConfig SET UserConfig_Value = :UserConfig_Value WHERE UserConfig_Name = :UserConfig_Name ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("UserConfig_Name", userConfigName);
        parameters.put("UserConfig_Value", userConfigValue);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    /**
     * 取得所有userConfig
     */
    public SqlRowSet getAllUserConfig() {
        String sql = " SELECT UserConfig_Name, UserConfig_Value, UserConfig_Comment FROM SYS_UserConfig ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
