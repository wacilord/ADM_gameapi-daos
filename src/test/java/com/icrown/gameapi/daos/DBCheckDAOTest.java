package com.icrown.gameapi.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DBCheckDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
class DBCheckDAOTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void chechDBPartition() throws Exception {
        String dataBaseNames = jdbcTemplate.getDataSource().getConnection().getCatalog();

        ResultSet proceduresResultSet = jdbcTemplate.getDataSource().getConnection().getMetaData().getProcedures(null, null, "check_partition");
        if (proceduresResultSet.first()){
            assertTrue(proceduresResultSet.getString("PROCEDURE_NAME").equals("check_partition"));
        }

        assertTrue(!dataBaseNames.equals(""));
    }
}