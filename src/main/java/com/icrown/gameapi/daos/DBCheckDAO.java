package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.ChekPartitionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DBCheckDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean chechDBPartition(String tableName) throws Exception {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            String dataBaseName = conn.getCatalog();

            List<SqlParameter> parameters = Arrays.asList(
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR));
            Map<String, Object> result = jdbcTemplate.call(con -> {
                CallableStatement callableStatement = con.prepareCall("{CALL check_partition(?,?)}");
                callableStatement.setString(1, dataBaseName);
                callableStatement.setString(2, tableName);
                return callableStatement;
            }, parameters);

            return Integer.parseInt(result.get("#update-count-1").toString()) > 0;
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    public SqlRowSet getMaxPartition(List<String> tableNames) throws Exception {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            String tableSchema = conn.getCatalog();

            String sql = " SELECT table_name as tableName, MAX(DATE(REPLACE(partition_name,'p',''))) as maxPartition, MAX(CREATE_TIME) as createDateTime " +
                    " FROM information_schema.partitions " +
                    " WHERE table_schema = :table_schema AND table_name IN (:table_names) GROUP BY table_name; ";

            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("table_schema", tableSchema);
            parameters.put("table_names", tableNames);

            return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    public SqlRowSet getPartitionInfo(List<String> tableNames, int days) throws Exception {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            String tableSchema = conn.getCatalog();

            String sql = " SELECT table_name as tableName, MAX(DATE(REPLACE(partition_name,'p',''))) as maxPartition, MIN(FROM_DAYS(partition_description)) AS minPartition," +
                    " MAX(CREATE_TIME) as createDateTime, " +
                    " DATEDIFF(DATE_SUB(current_date, INTERVAL :days day), MIN(FROM_DAYS(partition_description))) AS removableDays " +
                    "FROM information_schema.partitions " +
                    " WHERE table_schema = :table_schema AND table_name IN (:table_names) GROUP BY table_name; ";

            Map<String, Object> parameters = new HashMap<>(3);
            parameters.put("table_schema", tableSchema);
            parameters.put("days", days);
            parameters.put("table_names", tableNames);

            return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    public boolean createPartition(String tableName, int days) throws Exception {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            String tableSchema = conn.getCatalog();

            List<SqlParameter> parameters = Arrays.asList(
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER));
            Map<String, Object> result = jdbcTemplate.call(con -> {
                CallableStatement callableStatement = con.prepareCall("{CALL create_partition_by_number(?,?,?,?,?)}");
                callableStatement.setString(1, tableSchema);
                callableStatement.setString(2, tableName);
                callableStatement.setInt(3, days);
                callableStatement.setInt(4, 0);
                callableStatement.setInt(5, 1);
                return callableStatement;
            }, parameters);

            return Integer.parseInt(result.get("#update-count-1").toString()) > 0;
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    public boolean removeLimit60Days(String tableName, int days) throws Exception {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            String tableSchema = conn.getCatalog();

            List<SqlParameter> parameters = Arrays.asList(
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER));
            Map<String, Object> result = jdbcTemplate.call(con -> {
                CallableStatement callableStatement = con.prepareCall("{CALL remove_partition(?,?,?,?)}");
                callableStatement.setString(1, tableSchema);
                callableStatement.setString(2, tableName);
                callableStatement.setInt(3, 60);
                callableStatement.setInt(4, days);
                return callableStatement;
            }, parameters);

            return Integer.parseInt(result.get("#update-count-1").toString()) > 0;
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    public boolean removeLimit180Days(String tableName, int days) throws Exception {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            String tableSchema = conn.getCatalog();

            List<SqlParameter> parameters = Arrays.asList(
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER));
            Map<String, Object> result = jdbcTemplate.call(con -> {
                CallableStatement callableStatement = con.prepareCall("{CALL remove_partition(?,?,?,?)}");
                callableStatement.setString(1, tableSchema);
                callableStatement.setString(2, tableName);
                callableStatement.setInt(3, 180);
                callableStatement.setInt(4, days);
                return callableStatement;
            }, parameters);

            return Integer.parseInt(result.get("#update-count-1").toString()) > 0;
        }
        catch (Exception exception) {
            throw exception;
        }
    }

}
