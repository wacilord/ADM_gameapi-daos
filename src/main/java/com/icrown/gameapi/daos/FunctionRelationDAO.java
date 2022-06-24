package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.FunctionRelationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FunctionRelationDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteAllBySubAccount(String subAccountGuid) {

        String deleteSQL = "DELETE FROM AGT_FunctionRelation WHERE SAT_GUID = :SubAccountGuid";
        Map<String, String> parameters = new HashMap<>(1);
        parameters.put("SubAccountGuid", subAccountGuid);
        namedParameterJdbcTemplate.update(deleteSQL, parameters);
    }

    public void batchInsert(String satGuid, List<String> funcode) {
        List<Object[]> batchArgs = funcode.stream().map(f -> new Object[]{f, satGuid}).collect(Collectors.toList());
        if (!batchArgs.isEmpty()) {
            String sql = "INSERT INTO AGT_FunctionRelation VALUES (?,?)";
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
    }

    public List<FunctionRelationModel> getFunctionRelationBySatGuid(String satGuid) {
        String sql = " SELECT FUN_Code,SAT_GUID FROM AGT_FunctionRelation WHERE SAT_GUID = :SatGuid ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SatGuid", satGuid);
        return namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(FunctionRelationModel.class));
    }

}