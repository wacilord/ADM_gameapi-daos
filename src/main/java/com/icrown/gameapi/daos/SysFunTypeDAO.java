package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class SysFunTypeDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getFunTypeList() {
        String sql = " SELECT FunType_Code, FunType_Name, FunType_Enable, FunType_Sort " +
                " FROM SYS_FunType ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
    }


}
