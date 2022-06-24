package com.icrown.gameapi.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SeqGameDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 取得 game SEQ
     *
     * @return
     */
    public long generateTransferSEQ() {
        Map<String, Object> parameters = new HashMap<>(0);
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT NEXTVAL(SEQ_Game)");
        return namedParameterJdbcTemplate.queryForObject(sql.toString(), parameters, long.class);
    }
}
