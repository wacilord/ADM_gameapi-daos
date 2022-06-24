package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameReportErrorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Frank
 */
@Repository
public class GameReportErrorDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean insert(GameReportErrorModel model) {
        String sql = " INSERT INTO DT_GameReportError " +
                " (GRE_UniKey,GRE_Time,GRE_DailyTradeCount,GRE_GameReportCount,GRE_DiffCount,GRE_CreateDateTime) " +
                " VALUES " +
                " (:GRE_UniKey,:GRE_Time,:GRE_DailyTradeCount,:GRE_GameReportCount,:GRE_DiffCount,:GRE_CreateDateTime) ";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("GRE_UniKey", model.getGRE_Unikey());
        parameters.put("GRE_Time", model.getGRE_Time());
        parameters.put("GRE_DailyTradeCount", model.getGRE_DailyTradeCount());
        parameters.put("GRE_GameReportCount", model.getGRE_GameReportCount());
        parameters.put("GRE_DiffCount", model.getGRE_DiffCount());
        parameters.put("GRE_CreateDateTime", model.getGRE_CreateDateTime());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean updateForReDo(String uniKey, String key, Date doneTime) {
        String sql = " UPDATE DT_GameReportError " +
                " SET LGR_Key = :Key , GRE_DoneTime = :DoneTime " +
                " WHERE GRE_UniKey = :UniKey ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("Key", key);
        parameters.put("DoneTime", doneTime);
        parameters.put("UniKey", uniKey);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean checkTimeExist(Date time) {
        String sql = " SELECT EXISTS(SELECT * FROM DT_GameReportError WHERE GRE_Time = :Time) ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Time", time);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class) > 0;
    }

    public boolean checkDiffCountExistByDay(Date startTime, Date endTime) {
        String sql = " SELECT EXISTS(SELECT * FROM DT_GameReportError " +
                " WHERE GRE_Time >= :startTime AND GRE_TIME < :endTime " +
                " AND GRE_DiffCount <> 0 )";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class) > 0;
    }

    public SqlRowSet getDiffCount() {
        String sql = " SELECT GRE_Time, GRE_DailyTradeCount, GRE_GameReportCount, GRE_DiffCount, GRE_DoneTime, GRE_CreateDateTime " +
                " FROM DT_GameReportError " +
                " WHERE  TO_DAYS(NOW()) - TO_DAYS(GRE_Time) <= 7 AND GRE_DiffCount != 0 ORDER BY GRE_Time DESC ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getDiffCountAndRedo() {
        String sql = " SELECT o.GRE_Time AS GRE_Time, o.GRE_DailyTradeCount AS GRE_DailyTradeCount, o.GRE_GameReportCount AS GRE_GameReportCount, " +
                " o.GRE_DiffCount AS GRE_DiffCount, o.GRE_DoneTime AS GRE_DoneTime, o.GRE_CreateDateTime AS GRE_CreateDateTime, " +
                " r.LGR_AfterDailyTradeCount AS LGR_AfterDailyTradeCount, r.LGR_AfterGameReportCount AS LGR_AfterGameReportCount, r.LGR_AfterDiffCount AS LGR_AfterDiffCount " +
                " FROM DT_GameReportError o " +
                " LEFT JOIN DT_LogGameReport r ON o.LGR_Key = r.LGR_Key " +
                " WHERE  TO_DAYS(NOW()) - TO_DAYS(GRE_Time) <= 7 AND GRE_DiffCount != 0 ORDER BY GRE_Time DESC ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}
