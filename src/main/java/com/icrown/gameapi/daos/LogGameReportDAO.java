package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.LogGameReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Frank
 */
@Repository
public class LogGameReportDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean insertData(LogGameReportModel model){
        String sql = " INSERT INTO DT_LogGameReport" +
                " (LGR_Executor,LGR_ExecuteTime,LGR_Type,LGR_AgentID,LGR_StartTime,LGR_EndTime,LGR_DoneTime,LGR_Count,LGR_UsedTime," +
                "  LGR_AfterDailyTradeCount,LGR_AfterGameReportCount,LGR_AfterDiffCount,LGR_Key) " +
                " VALUES " +
                " (:LGR_Executor,:LGR_ExecuteTime,:LGR_Type,:LGR_AgentID,:LGR_StartTime,:LGR_EndTime,:LGR_DoneTime,:LGR_Count,:LGR_UsedTime," +
                "  :LGR_AfterDailyTradeCount,:LGR_AfterGameReportCount,:LGR_AfterDiffCount,:LGR_Key)";
        Map<String,Object> parameters = new HashMap<>(9);
        parameters.put("LGR_Executor",model.getLGR_Executor());
        parameters.put("LGR_ExecuteTime",model.getLGR_ExecuteTime());
        parameters.put("LGR_Type",model.getLGR_Type());
        parameters.put("LGR_AgentID",model.getLGR_AgentID());
        parameters.put("LGR_StartTime",model.getLGR_StartTime());
        parameters.put("LGR_EndTime",model.getLGR_EndTime());
        parameters.put("LGR_DoneTime",model.getLGR_DoneTime());
        parameters.put("LGR_Count",model.getLGR_Count());
        parameters.put("LGR_UsedTime",model.getLGR_UsedTime());
        parameters.put("LGR_AfterDailyTradeCount",model.getLGR_AfterDailyTradeCount());
        parameters.put("LGR_AfterGameReportCount",model.getLGR_AfterGameReportCount());
        parameters.put("LGR_AfterDiffCount",model.getLGR_AfterDiffCount());
        parameters.put("LGR_Key",model.getLGR_Key());
        return namedParameterJdbcTemplate.update(sql,parameters) > 0;
    }
}
