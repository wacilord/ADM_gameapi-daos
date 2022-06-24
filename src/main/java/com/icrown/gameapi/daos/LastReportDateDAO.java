package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.LastReportDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Frank
 */
@Repository
public class LastReportDateDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得最新的資料by Date
     *
     * @return
     */
    public Optional<LastReportDateModel> getLatestDataByDate(Date dtStart, Date dtEnd) {
        String sql = " SELECT LRD_ID,LRD_MinDate,LRD_MaxDate,LRD_MinIndex,LRD_MaxIndex,LRD_StartDate,LRD_AfterSelect,LRD_DoneDate," +
                " LRD_Count,LRD_QueryTime,LRD_UpdateTime,LRD_Duration ,DT_CreateDateTime " +
                " FROM DT_LastReportDate" +
                " WHERE LRD_MaxDate >= :dtStart and LRD_MaxDate < :dtEnd  ORDER BY LRD_ID DESC LIMIT 1 ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("dtStart", dtStart);
        parameters.put("dtEnd", dtEnd);
        List<LastReportDateModel> list = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(LastReportDateModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public Optional<LastReportDateModel> getLatestDataWithoutDate() {
        String sql = " SELECT LRD_ID,LRD_MinDate,LRD_MaxDate,LRD_MinIndex,LRD_MaxIndex,LRD_StartDate,LRD_AfterSelect,LRD_DoneDate," +
                " LRD_Count,LRD_QueryTime,LRD_UpdateTime,LRD_Duration ,DT_CreateDateTime " +
                " FROM DT_LastReportDate" +
                " ORDER BY LRD_ID DESC LIMIT 1 ";
        Map<String, Object> parameters = new HashMap<>();
        List<LastReportDateModel> list = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(LastReportDateModel.class));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    /**
     * 新增資料
     *
     * @param model
     * @return
     */
    public boolean insertData(LastReportDateModel model) {
        String sql = " INSERT INTO DT_LastReportDate " +
                " (LRD_MinDate,LRD_MaxDate,LRD_MinIndex,LRD_MaxIndex,LRD_StartDate,LRD_AfterSelect,LRD_DoneDate,LRD_Count,LRD_Count2,LRD_QueryTime,LRD_UpdateTime,LRD_Duration,DT_CreateDateTime) " +
                " VALUES " +
                " (:LRD_MinDate,:LRD_MaxDate,:LRD_MinIndex,:LRD_MaxIndex,:LRD_StartDate,:LRD_AfterSelect,:LRD_DoneDate,:LRD_Count,:LRD_Count2,:LRD_QueryTime,:LRD_UpdateTime,:LRD_Duration,:DT_CreateDateTime) ";
        Map<String, Object> parameters = new HashMap<>(13);
        parameters.put("LRD_MinDate", model.getLRD_MinDate());
        parameters.put("LRD_MaxDate", model.getLRD_MaxDate());
        parameters.put("LRD_MinIndex", model.getLRD_MinIndex());
        parameters.put("LRD_MaxIndex", model.getLRD_MaxIndex());
        parameters.put("LRD_StartDate", model.getLRD_StartDate());
        parameters.put("LRD_AfterSelect", model.getLRD_AfterSelect());
        parameters.put("LRD_DoneDate", model.getLRD_DoneDate());
        parameters.put("LRD_Count", model.getLRD_Count());
        parameters.put("LRD_Count2", model.getLRD_Count2());
        parameters.put("LRD_QueryTime", model.getLRD_QueryTime());
        parameters.put("LRD_UpdateTime", model.getLRD_UpdateTime());
        parameters.put("LRD_Duration", model.getLRD_Duration());
        parameters.put("DT_CreateDateTime", model.getDT_CreateDateTime());
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public Date getLatestCreateDatetime() {
        String sql = " SELECT DT_CreateDateTime " +
                " FROM DT_LastReportDate" +
                " ORDER BY LRD_ID DESC LIMIT 1 ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Date.class);
    }
}
