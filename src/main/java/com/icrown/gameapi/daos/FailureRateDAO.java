package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FailureRateDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public long getFailureCountLastTimeByType(String failureSelectType) {
        String sql = " SELECT FR_LastReportTime FROM FR_LastReportDate " +
                " WHERE FR_FailureRateType =:FR_FailureRateType ";

        Map<String, Object> parameters = new HashMap(1);
        parameters.put("FR_FailureRateType", failureSelectType);

        SqlRowSet rs = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        if (rs.first()) {
            return rs.getLong("FR_LastReportTime");
        }

        return 0L;
    }

    public boolean updateFailureCountLastTimeByType(long lastTime, String failureSelectType) {
        String sql = " UPDATE FR_LastReportDate SET FR_LastReportTime =:FR_LastReportTime " +
                " WHERE FR_FailureRateType =:FR_FailureRateType ";
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("FR_LastReportTime", lastTime);
        parameters.put("FR_FailureRateType", failureSelectType);

        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean addFailureCountTypeCount(int failureRateType, int count) {
        String sql = " INSERT INTO FR_FailureRateTypeCount " +
                " SET FR_FailureRateType =:FR_FailureRateType, FR_FailureRateCount =:FR_FailureRateCount ; ";

        Map<String, Object> parameters = new HashMap(2);
        parameters.put("FR_FailureRateType", failureRateType);
        parameters.put("FR_FailureRateCount", count);


        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public boolean addFailureTypeCountByInsertValues(String insertValues) {
        String sql = " INSERT INTO FR_FailureRateTypeCount (FR_FailureRateType, FR_FailureRateCount) " +
                " VALUES " + insertValues + " ; ";

        return namedParameterJdbcTemplate.update(sql, new HashMap<>(0)) > 0;
    }

    public String buildFailureCountTypeCountInsertValues(int failureRateType, int count) {
        return "(" + failureRateType + "," + count + "),";
    }

    public SqlRowSet getFailureCountTypeCount(int beforeMinutes) {
        String sql = " SELECT FR_FailureRateType, FR_FailureRateCount, FR_ReportTime FROM FR_FailureRateTypeCount " +
                " WHERE FR_ReportTime >= DATE_ADD(NOW(), INTERVAL " + (-beforeMinutes) + " MINUTE) ";
        return namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<>());
    }

}
