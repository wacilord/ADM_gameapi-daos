package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysFunDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int[] addSysFun(BatchPreparedStatementSetter batchPreparedStatementSetter) {
        String sql = " INSERT INTO SYS_Fun(Fun_Code,FunType_Code,Fun_Name) VALUES(?,?,?); ";

        return jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
    }

    public int addSysFun(String funCode, String funType, String funName) {
        String sql = " INSERT INTO SYS_Fun(Fun_Code,FunType_Code,Fun_Name) VALUES(:Fun_Code,:FunType_Code,:Fun_Name) " +
                " ON DUPLICATE KEY UPDATE FunType_Code = :FunType_Code2, Fun_Name = :Fun_Name2; ";
        Map<String, Object> paramerMap = new HashMap<>(5);
        paramerMap.put("Fun_Code", funCode);
        paramerMap.put("FunType_Code", funType);
        paramerMap.put("Fun_Name", funName);
        paramerMap.put("FunType_Code2", funType);
        paramerMap.put("Fun_Name2", funName);

        return namedParameterJdbcTemplate.update(sql, paramerMap);
    }

    public SqlRowSet getSysFun() {
        String sql = " SELECT Fun_Code,FunType_Code,Fun_Name,Fun_Open,Fun_Sort FROM SYS_Fun; ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
    }

    public SqlRowSet getSysFunByFunType(String funType) {
        String sql = " SELECT a.Fun_Code AS Fun_Code,a.FunType_Code AS FunType_Code,a.Fun_Name AS Fun_Name,a.Fun_Open AS Fun_Open,a.Fun_Sort AS Fun_Sort " +
                " FROM SYS_Fun a " +
                " WHERE a.FunType_Code = :FunType_Code" ;
        Map<String, Object> paramerMap = new HashMap<>(1);
        paramerMap.put("FunType_Code", funType);
        return namedParameterJdbcTemplate.queryForRowSet(sql, paramerMap);
    }

    public SqlRowSet getSysFunByFunCodes(List<String> funCodes) {
        String sql = " SELECT a.Fun_Code AS Fun_Code,a.FunType_Code AS FunType_Code,a.Fun_Name AS Fun_Name,a.Fun_Open AS Fun_Open,a.Fun_Sort AS Fun_Sort " +
                " FROM SYS_Fun a" +
                " WHERE a.Fun_Code IN (:Fun_Code) ";
        Map<String, Object> paramerMap = new HashMap<>(1);
        paramerMap.put("Fun_Code", funCodes);
        return namedParameterJdbcTemplate.queryForRowSet(sql, paramerMap);
    }

    public SqlRowSet getAllSysFun() {
        String sql = " SELECT a.Fun_Code AS Fun_Code,a.FunType_Code AS FunType_Code,a.Fun_Name AS Fun_Name,a.Fun_Open AS Fun_Open,a.Fun_Sort AS Fun_Sort " +
                " FROM SYS_Fun a ";
        Map<String, Object> paramerMap = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, paramerMap);
    }



    public SqlRowSet getFunCodeListByFunType(String funType) {
        String sql = " SELECT Fun_Code FROM SYS_Fun WHERE FunType_Code = :FunType_Code ";
        Map<String, Object> paramerMap = new HashMap<>(1);
        paramerMap.put("FunType_Code", funType);
        return namedParameterJdbcTemplate.queryForRowSet(sql, paramerMap);
    }
}
