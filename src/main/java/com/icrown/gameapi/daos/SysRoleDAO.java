package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class SysRoleDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getRoleList(){
        String sql = " SELECT Role_ID,Role_Name FROM SYS_Role ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
    }
}
