package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adi
 */
@Repository
public class FishDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    public void writeAnalysis(Date fishGameTime,int fishRoundDown,int fishRoundDownTime,String room,int mode, int shootCount,String plyGuid ,BigDecimal bet,BigDecimal netWin,String json) {
        String sql = " INSERT INTO Fish ("
                + " Fish_GameTime,Fish_RoundDown,Fish_RoundDownTime,Fish_Room,Fish_Mode,Fish_ShootCount,PLY_GUID ,Fish_Bet,Fish_Json,Fish_NetWin"
                + " ) values ("
                + " :Fish_GameTime,:Fish_RoundDown,:Fish_RoundDownTime,:Fish_Room,:Fish_Mode,:Fish_ShootCount,:PLY_GUID,:Fish_Bet,:Fish_Json,:Fish_NetWin "
                + " ) ";
        Map<String, Object> parameters = new HashMap<>(10);
        parameters.put("Fish_GameTime", fishGameTime);
        parameters.put("Fish_RoundDown", fishRoundDown);
        parameters.put("Fish_RoundDownTime", fishRoundDownTime);
        parameters.put("Fish_Room",room);
        parameters.put("Fish_Mode",mode);
        parameters.put("Fish_ShootCount",shootCount);
        parameters.put("PLY_GUID",plyGuid);
        parameters.put("Fish_NetWin",netWin);
        parameters.put("Fish_Bet",bet);
        parameters.put("Fish_Json",json);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

}