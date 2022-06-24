package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FishRoundDownDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getFishRoundDownByTime(Date startTime, Date endTime) {
        String sql = " SELECT Fish_Room, Fish_RoundDown, Fish_ShootCount, Fish_Bet, Fish_NetWin, Fish_Json " +
                " FROM Fish " +
                " WHERE Fish_GameTime >= :startTime AND Fish_GameTime < :endTime ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

    }

    public SqlRowSet getFishRoundDownByTime(Date startTime, Date endTime, List<String> agent1) {
        String sql = " SELECT Fish_Room, Fish_RoundDown, Fish_ShootCount, Fish_Bet, Fish_NetWin, Fish_Json " +
                " FROM Fish " +
                " WHERE Fish_GameTime >= :startTime AND Fish_GameTime < :endTime AND " +
                " PLY_GUID NOT IN (SELECT PLY_GUID FROM MEM_Player WHERE AGT_Agent1 in (:AGT_Agent1) ) ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("AGT_Agent1", agent1);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

    }

}

