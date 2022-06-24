package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class LogVersionRecordDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void logVersionRecord(String gameCode, String version, String eventVersion, String sysAccountID, String note) {
        String sql = " INSERT INTO LOG_VersionRecord (GM_GameCode,GM_Version,GM_EventVersion,SYS_Account_ID,LGT_Note) " +
                " VALUES(:GM_GameCode,:GM_Version,:GM_EventVersion,:SYS_Account_ID,:LGT_Note) ";
        Map<String, Object> parameterMap = new HashMap<>(5);
        parameterMap.put("GM_GameCode", gameCode);
        parameterMap.put("GM_Version", version);
        parameterMap.put("GM_EventVersion", eventVersion);
        parameterMap.put("SYS_Account_ID", sysAccountID);
        parameterMap.put("LGT_Note", note);
        namedParameterJdbcTemplate.update(sql, parameterMap);
    }

    public void logVersionRecord(String gameCode, Map<String, String> langVersionMap, String eventVersion, String sysAccountID, String note) {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO LOG_VersionRecord ").append(" (GM_GameCode ");
        langVersionMap.entrySet().forEach(lang -> {
            sb.append(",").append(lang.getKey());
        });
        sb.append(" ,GM_EventVersion,SYS_Account_ID,LGT_Note) ");
        sb.append(" VALUES(:GM_GameCode ");
        langVersionMap.entrySet().forEach(lang -> {
            sb.append(",:").append(lang.getKey());
        });
        sb.append(" ,:GM_EventVersion,:SYS_Account_ID,:LGT_Note) ");
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("GM_GameCode", gameCode);
        langVersionMap.entrySet().forEach(lang -> {
            parameterMap.put(lang.getKey(), lang.getValue());
        });
        parameterMap.put("GM_EventVersion", eventVersion);
        parameterMap.put("SYS_Account_ID", sysAccountID);
        parameterMap.put("LGT_Note", note);

        namedParameterJdbcTemplate.update(sb.toString(), parameterMap);
    }

    public void logVersionRecordWithLanguage(String gameCode, String version, String lang, String eventVersion, String sysAccountID, String note) {
        StringBuffer sb = new StringBuffer();
        String versionSql = "GM_" + lang + "_Version";
        boolean flag = lang.equals("zh_CN");

        sb.append(" INSERT INTO LOG_VersionRecord ").append("(GM_GameCode");
        sb.append(",").append(versionSql);
        if (flag) {
            sb.append(",").append("GM_Version");
        }
        sb.append(",GM_EventVersion,SYS_Account_ID,LGT_Note) ");
        sb.append("VALUES(:GM_GameCode");
        sb.append(",:").append(versionSql);
        if (flag) {
            sb.append(",:GM_Version");
        }
        sb.append(",:GM_EventVersion,:SYS_Account_ID,:LGT_Note)");

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("GM_GameCode", gameCode);
        parameterMap.put(versionSql, version);
        if (flag) {
            parameterMap.put("GM_Version", version);
        }
        parameterMap.put("GM_EventVersion", eventVersion);
        parameterMap.put("SYS_Account_ID", sysAccountID);
        parameterMap.put("LGT_Note", note);
        System.out.println(sb.toString());
        namedParameterJdbcTemplate.update(sb.toString(), parameterMap);
    }

    public SqlRowSet getLogVersionRecord(Date startDate, Date endDate) {
        String sql = " SELECT GM_GameCode,GM_Version,GM_zh_CN_Version,GM_th_Version,GM_en_Version,GM_EventVersion,SYS_Account_ID,LGT_CreateDateTime,LGT_Note " +
                " FROM LOG_VersionRecord WHERE LGT_CreateDateTime >= :startDate AND LGT_CreateDateTime < :endDate ORDER BY LGT_CreateDateTime DESC";
        Map<String, Object> parameterMap = new HashMap<>(2);
        parameterMap.put("startDate", startDate);
        parameterMap.put("endDate", endDate);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameterMap);
    }


}
