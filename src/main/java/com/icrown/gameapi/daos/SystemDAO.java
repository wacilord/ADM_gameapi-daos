package com.icrown.gameapi.daos;

import com.icrown.gameapi.commons.utils.DateUtil;
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
public class SystemDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private DateUtil dateUtil;
    /**
     * 取得資料庫時間含毫秒6位(讓程式時間保持一致)
     * @return
     */
    public Date getDbDate(){
        String sql = "SELECT CURRENT_TIMESTAMP(6) as CurrentDate ";
        Map<String,Object> parameters = new HashMap<>();
        return namedParameterJdbcTemplate.queryForObject(sql,parameters,Date.class);
    }

    /**
     * 取得資料庫日期不包含時間(讓程式時間保持一致)
     * @return
     */
    public Date getDbDatePart(){
        String sql = "SELECT DATE(NOW()) as CurrentDate ";
        Map<String,Object> parameters = new HashMap<>();
        return namedParameterJdbcTemplate.queryForObject(sql,parameters,Date.class);
    }

    /**
     * 取得資料庫時間依照格式化(讓程式時間保持一致)
     * @return
     */
    public Date getDbDateByFormat(String format){
        String sql = "SELECT CURRENT_TIMESTAMP(6) as CurrentDate ";
        Map<String,Object> parameters = new HashMap<>();
        Date currentDate = namedParameterJdbcTemplate.queryForObject(sql,parameters,Date.class);
        return dateUtil.getDateWithFormat(currentDate,format);
    }

    /**
     * 取得當前資料庫叢集數量
     * @return
     */
    public int getClusterSize(){
        String sql = "SHOW STATUS LIKE 'wsrep_cluster_size'";
        Map<String,Object> parameters = new HashMap<>(1);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql,parameters);
        if(!rowSet.next()){
            return 0;
        }
        return rowSet.getInt("Value");
    }
}
