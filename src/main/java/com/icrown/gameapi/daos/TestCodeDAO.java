package com.icrown.gameapi.daos;

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
public class TestCodeDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public SqlRowSet getDailyTradeSumByDateRange(String agent3,String accountID,String gameCode,Date dateStart, Date dateEnd){
        String sql = " select AGT_Agent3 as myAgent3,PLY_AccountID as myAccountID,count(1) as myItems, GM_GameCode as myGameCode,Sum(GM_Bets) as myBets," +
                     " Sum(GM_ValidBets) as myValidBets,  " +
                     " Sum(GM_Win) as myWin,Sum(GM_Jackpot) as myJackpot,Sum(GM_JackpotContribute) as myJackpotContribute, " +
                     " Sum(GM_Commission) as myCommission,Sum(GM_NetWin) as myNetWin " +
                     " From DT_DailyTrade " +
                     " where AGT_Agent3 = :agent3 AND PLY_AccountID = :accountID AND GM_GameCode = :gameCode AND " +
                     " DT_UpdateTime >= :dateStart AND DT_UpdateTime < :dateEnd " +
                     " Group By AGT_Agent3,PLY_AccountID,GM_GameCode ";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("agent3",agent3);
        parameters.put("accountID",accountID);
        parameters.put("gameCode",gameCode);
        parameters.put("dateStart",dateStart);
        parameters.put("dateEnd",dateEnd);
        return namedParameterJdbcTemplate.queryForRowSet(sql,parameters);
    }

    public SqlRowSet getGameReportSumByDateRange(String agent3,String accountID,String gameCode,Date dateStart,Date dateEnd){
        String sql = " select AGT_Agent3 as myAgent3,PLY_AccountID as myAccountID,count(AGG_Items) as myItems,GR_GameCode as myGameCode, " +
                     " Sum(AG_SumBets) as myBets,Sum(AG_SumValidBets) as myValidBets,Sum(AG_SumWin) as myWin, " +
                     " Sum(AG_SumJackpot) as myJackpot,Sum(AG_SumJackpotContribute) as myJackpotContribute," +
                     " Sum(AG_Commission) as myCommission,Sum(AG_NetWin) as myNetWin " +
                     " From DT_GameReport " +
                     " where AGT_Agent3 = :agent3 AND PLY_AccountID = :accountID AND GR_GameCode = :gameCode AND " +
                     " AGG_Time >= :dateStart AND AGG_Time < :dateEnd " +
                     " Group By AGT_Agent3,PLY_AccountID,GR_GameCode ";
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("agent3",agent3);
        parameters.put("accountID",accountID);
        parameters.put("gameCode",gameCode);
        parameters.put("dateStart",dateStart);
        parameters.put("dateEnd",dateEnd);
        return namedParameterJdbcTemplate.queryForRowSet(sql,parameters);
    }
}
