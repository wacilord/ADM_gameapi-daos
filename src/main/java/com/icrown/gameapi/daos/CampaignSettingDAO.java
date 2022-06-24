package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CampaignSettingDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getCurrentCampaignSetting() {
        String sql = " SELECT CAM_Code, CAM_Name, CAM_StartTime, CAM_EndTime, CAM_DateLine, CAM_Stage, CAM_Condition, CAM_ExchangeType FROM CAM_Setting " +
                " ORDER BY CAM_EndTime DESC LIMIT 1; ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap(0));
    }

    public SqlRowSet getCurrentCampaignSettingByDateTime(Date startTime, Date endTime) {
        String sql = " SELECT CAM_Code, CAM_Name, CAM_StartTime, CAM_EndTime, CAM_DateLine, CAM_Stage, CAM_Condition, CAM_ExchangeType FROM CAM_Setting " +
                " WHERE CAM_StartTime >= :CAM_StartTime AND CAM_EndTime < :CAM_EndTime ; ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap(0));
    }

    public SqlRowSet getCurrentCampaignSettingByCamCode(int camCode) {
        String sql = " SELECT CAM_Code, CAM_Name, CAM_StartTime, CAM_EndTime, CAM_DateLine, CAM_Stage, CAM_Condition, CAM_ExchangeType FROM CAM_Setting " +
                " WHERE CAM_Code = :CAM_Code AND NOW() >= CAM_StartTime AND NOW() < CAM_EndTime; ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("CAM_Code", camCode);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getCampaignSettingByCamCode(int camCode) {
        String sql = " SELECT CAM_Code, CAM_Name, CAM_StartTime, CAM_EndTime, CAM_DateLine, CAM_Stage, CAM_Condition, CAM_ExchangeType FROM CAM_Setting " +
                " WHERE CAM_Code = :CAM_Code; ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("CAM_Code", camCode);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

}
