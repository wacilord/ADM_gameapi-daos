package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.AGT_MerchantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Frank
 */
@Repository
public class MerchantDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Optional<AGT_MerchantModel> getMerchant(String mctGuid) {
        String sql = "SELECT MCT_GUID,MCT_FriendlyName,MCT_Enable,MCT_Domain,MCT_Currency,LAG_Code," +
                "IFNULL(MCT_AllowIP,'') as MCT_AllowIP,IFNULL(MCT_BackendAllowIP,'') AS MCT_BackendAllowIP , " +
                " IFNULL(MCT_FrontendAllowIP,'') AS MCT_FrontendAllowIP,MCT_MerchantCode, IFNULL(MCT_IsMaintain,0) AS MCT_IsMaintain " +
                "FROM AGT_Merchant WHERE MCT_GUID=:MCT_GUID";
        Map<String, Object> parameters = new HashMap<>(1);

        parameters.put("MCT_GUID", mctGuid);
        List<AGT_MerchantModel> agtMerchantModelList = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(AGT_MerchantModel.class));

        if (agtMerchantModelList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(agtMerchantModelList.get(0));
        }
    }

    public boolean addMerchant(AGT_MerchantModel model) {
        String sql = "INSERT INTO AGT_Merchant" +
                " ( MCT_GUID," +
                " MCT_FriendlyName," +
                " MCT_Enable," +
                " MCT_Domain," +
                " MCT_Currency," +
                " LAG_Code," +
                " MCT_AllowIP," +
                " MCT_BackendAllowIP," +
                " MCT_FrontendAllowIP," +
                " MCT_MerchantCode, " +
                " MCT_IsMaintain )" +
                " VALUES" +
                " ( :merchantGUID," +
                " :name," +
                " :enable," +
                " :domain," +
                " :currency," +
                " :code," +
                " :gameAllowIP," +
                " :backendAllowIP," +
                " :frontendAllowIP," +
                " :merchantCode, " +
                " :isMaintain );";

        Map<String, Object> parameters = new HashMap<>(13);
        parameters.put("merchantGUID", model.getMCT_GUID());
        parameters.put("name", model.getMCT_FriendlyName());
        parameters.put("enable", model.isMCT_Enable());
        parameters.put("domain", model.getMCT_Domain());
        parameters.put("currency", model.getMCT_Currency());
        parameters.put("code", model.getLAG_Code());
        parameters.put("gameAllowIP", model.getMCT_AllowIP());
        parameters.put("backendAllowIP", model.getMCT_BackendAllowIP());
        parameters.put("frontendAllowIP", model.getMCT_FrontendAllowIP());
        parameters.put("merchantCode", model.getMCT_MerchantCode());
        parameters.put("isMaintain", model.isMCT_IsMaintain());
        return (namedParameterJdbcTemplate.update(sql, parameters) == 1);
    }

    public SqlRowSet getALLMerchantAndAgentInfo() {
        String sql = " SELECT a.MCT_FriendlyName,b.AGT_Agent1 " +
                " FROM AGT_Merchant a " +
                " LEFT JOIN AGT_Agent b ON a.MCT_GUID = b.MCT_GUID ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);

    }

    public SqlRowSet getAllMerchant() {
        String sql = " SELECT a1.MCT_GUID as MCT_GUID, a1.MCT_FriendlyName as MCT_FriendlyName, a1.MCT_Domain as MCT_Domain ,a1.MCT_IsMaintain as MCT_IsMaintain " +
                ", a2.AGT_AccountID as AGT_AccountID, a2.AGT_GUID as AGT_GUID " +
                " FROM AGT_Merchant a1 " +
                " LEFT JOIN AGT_Agent a2 ON a1.MCT_GUID = a2.MCT_GUID " +
                " WHERE a2.AGT_Level = 1 ";
        Map<String, Object> parameters = new HashMap<>(0);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 檢查Domain是否存在
     *
     * @param domain
     * @return
     */
    public boolean checkDomainExist(String domain) {
        String sql = " SELECT EXISTS( SELECT * FROM AGT_Merchant WHERE MCT_Domain = :Domain ) ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Domain", domain);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class) > 0;
    }

    /**
     * 以domain取得總代
     */
    public Optional<AGT_MerchantModel> getMerchantByDomain(String domain) {
        String sql = " SELECT MCT_GUID,MCT_FriendlyName,MCT_Enable,MCT_Domain,MCT_Currency,LAG_Code," +
                " IFNULL(MCT_AllowIP,'') as MCT_AllowIP,IFNULL(MCT_BackendAllowIP,'') AS MCT_BackendAllowIP , " +
                " IFNULL(MCT_FrontendAllowIP,'') AS MCT_FrontendAllowIP,MCT_MerchantCode, IFNULL(MCT_IsMaintain,0) AS MCT_IsMaintain " +
                " FROM AGT_Merchant WHERE MCT_Domain = :Domain ";
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("Domain", domain);

        List<AGT_MerchantModel> agtMerchantModelList = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(AGT_MerchantModel.class));

        if (agtMerchantModelList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(agtMerchantModelList.get(0));
        }
    }

    /**
     * 更新merchantIP
     */
    public boolean updateMerchantIP(String mctGuid, int ipTpye, String ip) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE AGT_Merchant SET ");
        if (ipTpye == 1) {
            stringBuilder.append(" MCT_AllowIP = ");
            stringBuilder.append(":allowIP ");
        }
        if (ipTpye == 2) {
            stringBuilder.append(" MCT_FrontendAllowIP = ");
            stringBuilder.append(":allowIP ");
        }
        if (ipTpye == 3) {
            stringBuilder.append(" MCT_BackendAllowIP = ");
            stringBuilder.append(":allowIP ");
        }

        stringBuilder.append(" WHERE MCT_GUID = :MCT_GUID ");

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("allowIP", ip);
        parameters.put("MCT_GUID", mctGuid);

        return namedParameterJdbcTemplate.update(stringBuilder.toString(), parameters) > 0;
    }

    public boolean updateIsMaintainByDomain(String domain, int isMaintain) {
        String sql = " UPDATE AGT_Merchant SET MCT_IsMaintain = :MCT_IsMaintain WHERE MCT_Domain = :MCT_Domain";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("MCT_IsMaintain", isMaintain);
        parameters.put("MCT_Domain", domain);
        return namedParameterJdbcTemplate.update(sql, parameters) > 0;
    }

}
