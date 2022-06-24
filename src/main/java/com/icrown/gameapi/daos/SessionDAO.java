package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.SessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.*;


/**
 * @author Frank
 */
@Repository
public class SessionDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<SessionModel> getSessionByToken(String token){
        String sql = " SELECT SAT_GUID,SEN_LoginToken,TKS_IP,TKS_Expireddatetime,TKS_Createdatetime" +
                     " FROM AGT_Session " +
                     " WHERE SEN_LoginToken = :Token ";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("Token",token);
        List<SessionModel> list = namedParameterJdbcTemplate.query(sql, parameters,
                new BeanPropertyRowMapper<>(SessionModel.class));
        if(list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list.get(0));

    }

    public boolean expiredSessionByToken(String token, Date expiredDateTime) {
        String sql = " UPDATE AGT_Session " +
                " SET TKS_Expireddatetime =:TKS_Expireddatetime " +
                " WHERE SEN_LoginToken =:SEN_LoginToken ";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("TKS_Expireddatetime", expiredDateTime);
        parameters.put("SEN_LoginToken",token);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

    public SqlRowSet getAdminSessionByToken(String token) {
        String sql = " SELECT SYS_Account_ID, SYS_Session_Token, SYS_Session_IP, SYS_Session_Expireddatetime, SYS_Session_Createdatetime " +
                " FROM SYS_Session " +
                " WHERE SYS_Session_Token = :SYS_Session_Token ";
        Map<String,Object> parameters = new HashMap<>(1);
        parameters.put("SYS_Session_Token",token);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

}
