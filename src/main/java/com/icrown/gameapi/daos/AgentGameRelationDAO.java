package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.GameTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank
 */
@Repository
public class AgentGameRelationDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    GameTypeDAO gameTypeDAO;

    /**
     * 取得總代可玩的遊戲
     *
     * @return
     */
    public SqlRowSet getAllGameTypeByMctGuid(String mctGuid) {
        String sql = "SELECT MCT_Guid,GM_GameType,AGG_Enable FROM AGT_AgentGameRelation WHERE MCT_Guid=:MctGuid ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("MctGuid", mctGuid);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public void initGameTypeByMctGuid(String mctGuid, List<Integer> enableGameType) {
        var gameTypeModels = gameTypeDAO.getAllGameType();
        String sql = " INSERT INTO AGT_AgentGameRelation " +
                " ( MCT_GUID," +
                " GM_GameType," +
                " AGG_Enable )" +
                " VALUES" +
                " ( " +
                "  :merchantGUID," +
                " :gameType," +
                " :enable );";

        for (GameTypeModel gameTypeModel : gameTypeModels) {
            int gameType = gameTypeModel.getGM_TypeSerial();
            boolean enable = false;
            if (enableGameType.contains(gameTypeModel.getGM_TypeSerial())) {
                enable = true;
            }
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("merchantGUID", mctGuid);
            parameters.put("gameType", gameType);
            parameters.put("enable", enable);
            namedParameterJdbcTemplate.update(sql, parameters);
        }
    }

    public void updateGameTypeByMctGuid(String mctGuid, List<Map<String, Object>> gameTypeList) {

        String sql = "UPDATE AGT_AgentGameRelation " +
                " SET AGG_Enable =:agg_enable " +
                " WHERE MCT_Guid=:mct_guid AND GM_Gametype=:gm_gametype ; ";

        Map[] batchValues = gameTypeList.toArray(new Map[gameTypeList.size()]);

        namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
    }

    /**
     * 所有總代新增遊戲類別，預設開啟
     */
    public void addGameTypeByMctGuid(List<String> merchantList, int gameType) {

        String sql = " INSERT INTO AGT_AgentGameRelation " +
                " ( MCT_GUID," +
                " GM_GameType," +
                " AGG_Enable )" +
                " VALUES" +
                " ( " +
                "  :merchantGUID," +
                " :gameType," +
                " :enable );";

        List<Map<String, Object>> parameterList = new ArrayList<>();
        for (String mctGuid : merchantList) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("merchantGUID", mctGuid);
            parameters.put("gameType", gameType);
            parameters.put("enable", true);
            parameterList.add(parameters);
        }

        Map[] batchValues = parameterList.toArray(new Map[merchantList.size()]);

        namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
    }
}
