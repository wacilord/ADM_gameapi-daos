package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tetsu
 */
@Repository
public class LanTranslationDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Map<String, String> getTranslationListByLan(String language) {
        if(language.contains("-")) {
            language = language.replace("-","_");
        }
        String targetCulomn = "LAN_" + language + "_Value";
        String sql = "SELECT LAN_zh_CN_Value, " + targetCulomn + " AS transValue FROM LAN_Translation ";
        SqlRowSet rowSet;

        try {
            rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
        } catch (Exception ex) {
            sql = "SELECT LAN_zh_CN_Value, LAN_zh_CN_Value AS transValue FROM LAN_Translation ";
            rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
        }

        Map<String, String> transMap = new HashMap<>();
        while (rowSet.next()) {
            transMap.put(rowSet.getString("LAN_zh_CN_Value"), rowSet.getString("transValue"));
        }
        return transMap;
    }

    public String getTranslationListByLanAndCNValue(String language, String cnvValue) {
        if(language.contains("-")) {
            language = language.replace("-","_");
        }
        String targetCulomn = "LAN_" + language + "_Value";
        String sql = "SELECT LAN_zh_CN_Value, " + targetCulomn + " AS transValue FROM LAN_Translation " +
                " WHERE LAN_zh_CN_Value = " + cnvValue;

        SqlRowSet rowSet;
        try {
            rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
        } catch (Exception ex) {
            sql = "SELECT LAN_zh_CN_Value, '' AS lanValue FROM LAN_Translation ";
            rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>(0));
        }

        String transValue = "";
        while (rowSet.next()) {
            transValue = rowSet.getString("transValue");
        }
        return transValue;
    }

    public void insertTranslation(String zh, String en, String th) {
        String sql = "INSERT INTO LAN_Translation SET LAN_zh_CN_Value=:LAN_zh_CN_Value, LAN_EN_Value=:LAN_EN_Value, LAN_TH_Value=:LAN_TH_Value";
        Map<String,Object> parameterMap = new HashMap<>(3);
        parameterMap.put("LAN_zh_CN_Value", zh);
        parameterMap.put("LAN_EN_Value", en);
        parameterMap.put("LAN_TH_Value", th);

        namedParameterJdbcTemplate.update(sql, parameterMap);
    }

}
