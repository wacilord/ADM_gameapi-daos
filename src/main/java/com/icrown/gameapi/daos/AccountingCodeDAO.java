package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


/**
 * @author David
 */
@Repository
public class AccountingCodeDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得會計項目
     */
    public SqlRowSet getAllAccountingCode() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ACC_Code AS accCode ");
        sql.append(",ACC_Name AS accName ");
        sql.append("FROM ACC_AccountingCode ");

        return jdbcTemplate.queryForRowSet(sql.toString());
    }


}

