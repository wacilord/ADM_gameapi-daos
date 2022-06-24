package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.FunctionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author Adi
 *
 * */

@Repository
public class AgtFunctionDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public SqlRowSet getFunType() {
        String sql ="SELECT FUN_Code,FUN_Name,FUN_Type,FUN_Level1,FUN_Level2,FUN_Level3,FUN_LevelSubAccount,FUN_ICON,FUN_Sort FROM AGT_Function WHERE FUN_Parent='' ORDER BY FUN_Sort ASC";
        return jdbcTemplate.queryForRowSet(sql);
    }

    public SqlRowSet getFun() {
        String sql ="SELECT FUN_Code,FUN_Name,FUN_Type,FUN_Level1,FUN_Level2,FUN_Level3,FUN_LevelSubAccount,FUN_Parent,FUN_ICON,FUN_Html,FUN_API,FUN_Sort FROM AGT_Function   ORDER BY Fun_Parent,FUN_Sort ASC";
        return jdbcTemplate.queryForRowSet(sql);
    }



    public List<FunctionModel> getAllFunction(){
        String sql = " SELECT FUN_Code,FUN_Name,FUN_Type,FUN_Level1,FUN_Level2,FUN_Level3, " +
                     " FUN_LevelSubAccount,FUN_Parent,FUN_ICON,FUN_Html, " +
                     " IFNULL(FUN_API,'') AS FUN_API,FUN_Sort" +
                     " FROM AGT_Function" ;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FunctionModel.class));
    }
}
