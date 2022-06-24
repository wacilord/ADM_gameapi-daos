package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class LanInfoDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getAllLanguageData() {
        String sql = " SELECT LAN_Code, LAN_Name, date_format, price_format FROM LAN_Info; ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap(0));
    }
}
