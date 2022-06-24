package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class SysSettingDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<String> getValueByName(String name) {
        String sql = " Select SET_Value From SYS_Setting WHERE SET_Name = :Name ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Name", name);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        if (rowSet.next()) {
            String value = rowSet.getString("SET_Value");
            return Optional.of(value);
        }
        return Optional.empty();
    }
}
