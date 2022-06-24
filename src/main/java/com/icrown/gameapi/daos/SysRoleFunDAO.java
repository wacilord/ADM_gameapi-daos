package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SysRoleFunDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public SqlRowSet getAllRoleFun() {
        String sql = " SELECT Role_ID,FUN_Code FROM SYS_RoleFun ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
    }

    public SqlRowSet getAllRoleFunWithRoleName() {
        String sql = " SELECT a.Role_ID AS Role_ID,b.Role_Name AS Role_Name,a.FUN_Code AS FUN_Code FROM SYS_RoleFun a JOIN SYS_Role b ON a.Role_ID = b.Role_ID";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
    }

    public int addRoleFun(int roleID, String funCode) {
        String sql = " INSERT INTO SYS_RoleFun(Role_ID,FUN_Code) VALUES(:Role_ID,:FUN_Code) ";
        Map<String, Object> paramerMap = new HashMap<>(2);
        paramerMap.put("Role_ID", roleID);
        paramerMap.put("FUN_Code", funCode);

        return namedParameterJdbcTemplate.update(sql, paramerMap);
    }

    public int deleteRoleFun(int roleID, String funCode) {
        String deleteSql = " DELETE FROM SYS_RoleFun WHERE Role_ID = :Role_ID AND FUN_Code = :FUN_Code ";
        Map<String, Object> deleteMap = new HashMap<>(2);
        deleteMap.put("Role_ID", roleID);
        deleteMap.put("FUN_Code", funCode);
        return namedParameterJdbcTemplate.update(deleteSql, deleteMap);
    }

    public SqlRowSet getRoleFunByFunCode(String funCode) {
        String sql = " SELECT Role_ID, FUN_Code FROM SYS_RoleFun where FUN_Code = :FUN_Code ";
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("FUN_Code", funCode);
        return namedParameterJdbcTemplate.queryForRowSet(sql, paramMap);
    }

}
