package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.CamAwardTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CampaignAwardTypeDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SqlRowSet getCamAwardTypeByCamAwardTypeID(String camAwardTypeID) {
        String sql = " SELECT CAM_AwardTypeID,CAM_AwardTypeName,CAM_Code,CAM_AwardTypeMin,CAM_AwardTypeMax FROM CAM_AwardType WHERE CAM_AwardTypeID = :CAM_AwardTypeID; ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("CAM_AwardTypeID", camAwardTypeID);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public List<CamAwardTypeModel> getAllCamAwardTypeByCamCode(int camCode) {
        String sql = " SELECT CAM_AwardTypeMainID,CAM_AwardTypeMainName,CAM_AwardTypeID,CAM_AwardTypeName,CAM_ImgID,CAM_AwardDescription,CAM_Code,CAM_AwardTypeMin,CAM_AwardTypeMax,CAM_Win FROM CAM_AwardType " +
                " WHERE CAM_Code = :CAM_Code ; ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("CAM_Code", camCode);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        List<CamAwardTypeModel> list = new ArrayList<>();
        CamAwardTypeModel model;
        while(rowSet.next()) {
            model = new CamAwardTypeModel();
            model.setCamAwardTypeMainID(rowSet.getInt("CAM_AwardTypeMainID"));
            model.setCamAwardTypeMainName(rowSet.getString("CAM_AwardTypeMainName"));
            model.setCamAwardTypeID(rowSet.getInt("CAM_AwardTypeID"));
            model.setCamAwardTypeName(rowSet.getString("CAM_AwardTypeName"));
            model.setCamImgID(rowSet.getInt("CAM_ImgID"));
            model.setCamAwardDescription(rowSet.getString("CAM_AwardDescription"));
            model.setCamCode(rowSet.getInt("CAM_Code"));
            model.setCamAwardTypeMin(rowSet.getBigDecimal("CAM_AwardTypeMin"));
            model.setCamAwardTypeMax(rowSet.getBigDecimal("CAM_AwardTypeMax"));
            model.setCamWin(rowSet.getBigDecimal("CAM_Win"));
            list.add(model);
        }
        return list;
    }
}
