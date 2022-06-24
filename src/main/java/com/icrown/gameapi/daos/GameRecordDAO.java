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
import java.util.List;
import java.util.Map;

/**
 * @author Frank
 */
@Repository
public class GameRecordDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DateUtil dateUtil;

    /**
     * 依代理取得老虎機遊戲紀錄 GameType = 1
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getSlotGameByAgent(String agtGuid, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT 1 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as playerID,"
                + " DATE_FORMAT(GR_EndTime, '%Y-%m-%d %H:%i:%s.%f') as gameTime,GR_Currency as currency,GR_Bets as bet,"
                + " GR_GameBleBets as gamebleBets, GR_Win as win, GR_ValidBets as validBets, GR_NetWin as netWin,"
                + " GR_jackpot as jackpot, GR_JackpotContribute as jackpotContribute, GR_JackpotType as jackpotType, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip,"
                + " GR_ClientType as clientType "
                + " FROM GR_SlotGame"
                + " WHERE AGT_Agent3 = :AgtGuid AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    /**
     * 依代理取得捕魚機遊戲紀錄 GameType = 2
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getFishingGameByAgent(String agtGuid, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT 2 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as playerID,"
                + " DATE_FORMAT(GR_EndTime, '%Y-%m-%d %H:%i:%s.%f') as gameTime,GR_Currency as currency, GR_Bets as bet,"
                + " GR_Win as win, GR_ValidBets as validBets, GR_NetWin as netWin,"
                + " GR_jackpot as jackpot, GR_JackpotContribute as jackpotContribute, GR_JackpotType as jackpotType, "
                + " GR_IPAddress as ip ,GR_ClientType as clientType,GR_RoomName as roomName  "
                + " FROM GR_FishingGame "
                + " WHERE AGT_Agent3 = :AgtGuid AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 依代理取得棋牌類遊戲紀錄 GameType = 3
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getCardGameByAgent(String agtGuid, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT 3 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as playerID,"
                + " DATE_FORMAT(GR_EndTime, '%Y-%m-%d %H:%i:%s.%f') as gameTime,GR_Currency as currency,GR_Bets as bet,"
                + " GR_GameBleBets as gamebleBets, GR_Win as win, GR_ValidBets as validBets, GR_NetWin as netWin,"
                + " GR_jackpot as jackpot, GR_JackpotContribute as jackpotContribute, GR_JackpotType as jackpotType, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip,"
                + " GR_ClientType as clientType, IFNULL(GR_RoomName,'') as roomName "
                + " FROM GR_CardGame"
                + " WHERE AGT_Agent3 = :AgtGuid AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 依代理取得街機遊戲紀錄 GameType = 4
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getMiniGameByAgent(String agtGuid, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT 4 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as playerID,"
                + " DATE_FORMAT(GR_EndTime, '%Y-%m-%d %H:%i:%s.%f') as gameTime,GR_Currency as currency,GR_Bets as bet,"
                + " GR_GameBleBets as gamebleBets, GR_Win as win, GR_ValidBets as validBets, GR_NetWin as netWin,"
                + " GR_jackpot as jackpot, GR_JackpotContribute as jackpotContribute, GR_JackpotType as jackpotType, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip,"
                + " GR_ClientType as clientType, "
                + " GR_BetType AS betType,GR_ExtraType AS extraType "
                + " FROM GR_MiniGame"
                + " WHERE AGT_Agent3 = :AgtGuid AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 依代理取得街機遊戲紀錄 GameType = 5
     *
     * @param agtGuid
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public SqlRowSet getRoomGameByAgent(String agtGuid, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT 5 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as playerID,"
                + " DATE_FORMAT(GR_EndTime, '%Y-%m-%d %H:%i:%s.%f') as gameTime,GR_Currency as currency,GR_Bets as bet,"
                + " GR_GameBleBets as gamebleBets, GR_Win as win, GR_ValidBets as validBets, GR_NetWin as netWin,"
                + " GR_jackpot as jackpot, GR_JackpotContribute as jackpotContribute, GR_JackpotType as jackpotType,"
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip,"
                + " GR_ClientType as clientType,"
                + " GR_BetType AS betType,GR_ExtraType AS extraType,"
                + " GR_RoomName AS roomName"
                + " FROM GR_RoomGame"
                + " WHERE AGT_Agent3 = :AgtGuid AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("AgtGuid", agtGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    //TODO 新的遊戲類別

    /**
     * 依玩家取得老虎機遊戲紀錄 GameType = 1
     *
     * @param plyGUID
     * @param gameCode
     * @param beginDateTime
     * @param endDateTime
     * @param startPos
     * @param pageSize
     * @return
     */
    public SqlRowSet getSlotGameByPlayerGameCode(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = " SELECT 1 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute,"
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance "
                + " FROM GR_SlotGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime  ORDER BY GR_EndTime  DESC limit :pos,:size";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    /**
     * 依玩家取得老虎機遊戲紀錄 GameType = 1
     *
     * @param plyGUID
     * @param gameCode
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public int getSlotGameByPlayerGameCodeCount(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT COUNT(1) FROM GR_SlotGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }


    public SqlRowSet getSlotGameDetail(String plyGUID, String gameTurn) {
        String sql = " SELECT 1 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, "
                + " GR_jackpot as jackpot, GR_JackpotType as jackpotType,GR_JackpotContribute as jackpotContribute, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_Record as gameDetail "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance "
                + " FROM GR_SlotGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GR_SEQ=:GR_SEQ ";
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GR_SEQ", gameTurn);


        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public boolean insertFish(long grSeq, String startTime, String endTime, String gameCode, String agtGuid1, String agtGuid2, String agtGuid3, String plyGuid, String agtAccountID, String plyAccountID, String currency, BigDecimal bets, BigDecimal gamebleBets, BigDecimal validBets, BigDecimal win, BigDecimal netWin, int jackpotType, BigDecimal jackpot, BigDecimal jackpotContribute, int flagFreeGame, int flatGameble, int flagInterrupt, String ipAddress, String gsVersion, String gsName, BigDecimal beforeBalace, BigDecimal afterBalance, String json, int clientType, String roomName) {

        String sql = " INSERT INTO GR_FishingGame("
                + " GR_SEQ,GM_GameCode,AGT_Agent1,AGT_Agent2,AGT_Agent3,"
                + " PLY_GUID,AGT_AccountID,PLY_AccountID,GR_Currency,GR_Bets,GR_GamebleBets,GR_Win, "
                + " GR_ValidBets, GR_NetWin, GR_JackpotType,GR_Jackpot,GR_Record,GR_JackpotContribute,GR_FlagFreeGame,"
                + " GR_FlagGamble,GR_FlagInterrupt,GR_StartTime,GR_EndTime,GR_IPAddress,GameServer_Version,"
                + " GameServer_Name,GR_BeforeBalance,GR_AfterBalance,GR_ClientType,GR_RoomName "
                + " )values("
                + " :GR_SEQ,:GM_GameCode,:AGT_Agent1,:AGT_Agent2,:AGT_Agent3,"
                + " :PLY_GUID,:AGT_AccountID,:PLY_AccountID,:GR_Currency,:GR_Bets,:GR_GamebleBets,:GR_Win, "
                + " :GR_ValidBets, :GR_NetWin, :GR_JackpotType,:GR_Jackpot,:GR_Record,:GR_JackpotContribute,:GR_FlagFreeGame,"
                + " :GR_FlagGamble,:GR_FlagInterrupt,CAST(:GR_StartTime AS DATETIME(6)),CAST(:GR_EndTime AS DATETIME(6)),:GR_IPAddress,:GameServer_Version,"
                + " :GameServer_Name,:GR_BeforeBalance,:GR_AfterBalance ,:GR_ClientType,:GR_RoomName "
                + " )";

        Map<String, Object> parameters = new HashMap<>(29);
        parameters.put("GR_SEQ", grSeq);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("AGT_Agent1", agtGuid1);
        parameters.put("AGT_Agent2", agtGuid2);
        parameters.put("AGT_Agent3", agtGuid3);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("AGT_AccountID", agtAccountID);
        parameters.put("PLY_AccountID", plyAccountID);
        parameters.put("GR_Currency", currency);
        parameters.put("GR_Bets", bets);
        parameters.put("GR_GamebleBets", gamebleBets);
        parameters.put("GR_Win", win);
        parameters.put("GR_ValidBets", validBets);
        parameters.put("GR_NetWin", netWin);
        parameters.put("GR_Jackpot", jackpot);
        parameters.put("GR_JackpotType", jackpotType);
        parameters.put("GR_Record", json);
        parameters.put("GR_JackpotContribute", jackpotContribute);
        parameters.put("GR_FlagFreeGame", flagFreeGame);
        parameters.put("GR_FlagGamble", flatGameble);
        parameters.put("GR_FlagInterrupt", flagInterrupt);
        parameters.put("GR_StartTime", startTime);
        parameters.put("GR_EndTime", endTime);
        parameters.put("GR_IPAddress", ipAddress);
        parameters.put("GameServer_Version", gsVersion);
        parameters.put("GameServer_Name", gsName);
        parameters.put("GR_BeforeBalance", beforeBalace);
        parameters.put("GR_AfterBalance", afterBalance);
        parameters.put("GR_ClientType", clientType);
        parameters.put("GR_RoomName", roomName);
        return namedParameterJdbcTemplate.update(sql, parameters) == 1;
    }

    /**
     * 依玩家取得捕魚機遊戲紀錄 GameType = 2
     *
     * @param plyGUID
     * @param gameCode
     * @param beginDateTime
     * @param endDateTime
     * @param startPos
     * @param pageSize
     * @return
     */
    public SqlRowSet getFishingGameByPlayerGameCode(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = " SELECT 2 as gameType, GM_GameCode as gameCode, GR_SEQ as gameTurn, PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime, GR_Currency as currency, GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute,"
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_IPAddress as ip, GR_BeforeBalance as beforeBalance, GR_AfterBalance as afterBalance,GR_RoomName as roomName "
                + " FROM GR_FishingGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime  ORDER BY GR_EndTime  DESC limit :pos,:size";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    /**
     * 依玩家取得捕魚機遊戲總比數 GameType = 2
     *
     * @param plyGUID
     * @param gameCode
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public int getFishingGameByPlayerGameCodeCount(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT COUNT(1) FROM GR_FishingGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getFishingGameDetail(String plyGUID, String gameTurn) {

        String sql = " SELECT 2 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, "
                + " GR_jackpot as jackpot,  GR_JackpotType as jackpotType,GR_JackpotContribute as jackpotContribute, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_Record as gameDetail "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance, IFNULL(GR_RoomName,'') as roomName "
                + " FROM GR_FishingGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GR_SEQ=:GR_SEQ ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GR_SEQ", gameTurn);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }


    public SqlRowSet getCardGameByPlayerGameCode(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = " SELECT 3 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute,"
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance, IFNULL(GR_RoomName,'') as roomName "
                + " FROM GR_CardGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime  ORDER BY GR_EndTime  DESC limit :pos,:size";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getCardGameByPlayerGameCodeCount(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT COUNT(1) FROM GR_CardGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getCardGameDetail(String plyGUID, String gameTurn) {

        String sql = " SELECT 3 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, "
                + " GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_Record as gameDetail "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance, IFNULL(GR_RoomName,'') as roomName "
                + " FROM GR_CardGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GR_SEQ=:GR_SEQ ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GR_SEQ", gameTurn);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public long generateFishSEQ() {
        Map<String, Object> parameters = new HashMap<>(0);
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT NEXTVAL(SEQ_Game)+2001000000000000000");
        return namedParameterJdbcTemplate.queryForObject(sql.toString(), parameters, long.class);
    }

    public int getMiniGameByPlayerGameCodeCount(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT COUNT(1) FROM GR_MiniGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getMiniGameByPlayerGameCode(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = " SELECT 4 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute,"
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip, "
                + " GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance, "
                + " GR_Parent AS mainTurn,IFNULL(GR_BetType,0) AS betType,IFNULL(GR_ExtraType,0) AS extraType, GR_Record AS gameRecord "
                + " FROM GR_MiniGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime "
                + " ORDER BY GR_EndTime  DESC limit :pos,:size ";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getMiniGameDetail(String plyGUID, String gameTurn) {

        String sql = " SELECT 4 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, "
                + " GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_Record as gameDetail "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance "
                + ", IFNULL(GR_BetType, 0) AS GR_BetType , IFNULL(GR_ExtraType, 0) AS GR_ExtraType "
                + " FROM GR_MiniGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GR_SEQ = :GR_SEQ ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GR_SEQ", gameTurn);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getRoomGameByPlayerGameCodeCount(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT COUNT(1) FROM GR_RoomGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getRoomGameByPlayerGameCode(String plyGUID, String gameCode, Date beginDateTime, Date endDateTime, int startPos, int pageSize) {
        String sql = " SELECT 5 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute,"
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip, "
                + " GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance, "
                + " GR_Parent AS mainTurn,IFNULL(GR_BetType,0) AS betType,IFNULL(GR_ExtraType,0) AS extraType, GR_Record AS gameRecord, IFNULL(GR_RoomName,'') as roomName "
                + " FROM GR_RoomGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime "
                + " ORDER BY GR_EndTime  DESC limit :pos,:size ";
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);
        parameters.put("pos", startPos);
        parameters.put("size", pageSize);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getRoomGameDetail(String plyGUID, String gameTurn) {

        String sql = " SELECT 5 as gameType , GM_GameCode as gameCode,GR_SEQ as gameTurn,PLY_AccountID as clientID,"
                + " GR_EndTime as gameTime,GR_Currency as currency,GR_Bets as gameBets,"
                + " GR_GameBleBets as gamebleBets,GR_NetWin as netWin,GR_Win as win,GR_ValidBets as validGameBets, "
                + " GR_jackpot as jackpot, GR_JackpotType as jackpotType, GR_JackpotContribute as jackpotContribute, "
                + " GR_FlagFreeGame as freeGame,GR_FlagGamble as gamble,"
                + " GR_FlagInterrupt as interrupt,GR_IPAddress as ip "
                + " ,GR_Record as gameDetail "
                + " ,GR_BeforeBalance as beforeBalance,GR_AfterBalance as afterBalance, IFNULL(GR_RoomName,'') as roomName "
                + ", IFNULL(GR_BetType, 0) AS GR_BetType , IFNULL(GR_ExtraType, 0) AS GR_ExtraType "
                + " FROM GR_RoomGame "
                + " WHERE  PLY_GUID = :PlyGuid AND GR_SEQ = :GR_SEQ ";

        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("PlyGuid", plyGUID);
        parameters.put("GR_SEQ", gameTurn);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    /**
     * 利用遊戲局號取得遊戲紀錄歷程
     *
     * @param seq
     * @return
     */
    public String getJsonBySeq(long seq) {
        char gameType = String.valueOf(seq).charAt(0);
        String tableName = "";
        switch (gameType) {
            case '1':
                tableName = "GR_SlotGame";
                break;
            case '2':
                tableName = "GR_FishingGame";
                break;
            case '3':
                tableName = "GR_CardGame";
                break;
            case '4':
                tableName = "GR_MiniGame";
                break;
            default:
                return "";
        }
        String sql = " SELECT GR_Record FROM " + tableName + " WHERE GR_SEQ = :Seq ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Seq", seq);
        SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
        if (rowSet.next()) {
            return rowSet.getString("GR_Record");
        }
        return "";
    }

    public SqlRowSet getLastFish() {
        String sql = " SELECT Fish_GameTime  FROM Fish  ORDER BY Fish_ID  DESC limit 0,1 ";
        Map<String, Object> parameters = new HashMap<>(0);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getFishingForAnalysis(String gameCode, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT GM_GameCode as gameCode,PLY_GUID,"
                + " GR_EndTime as gameTime, GR_Currency as currency, GR_Bets as gameBets,"
                + " GR_RoomName as roomName ,GR_Record as json "
                + " FROM GR_FishingGame "
                + " WHERE  GM_GameCode=:GM_GameCode AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime  ORDER BY GR_EndTime  DESC ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getFishingByPlayerForAnalysis(String gameCode, String plyGuid, Date beginDateTime, Date endDateTime) {
        String sql = " SELECT GM_GameCode as gameCode,PLY_GUID,"
                + " GR_EndTime as gameTime, GR_Currency as currency, GR_Bets as gameBets,"
                + " GR_RoomName as roomName ,GR_Record as json "
                + " FROM GR_FishingGame "
                + " WHERE GM_GameCode=:GM_GameCode AND PLY_GUID =:PLY_GUID AND GR_EndTime >= :BeginDateTime AND GR_EndTime < :EndDateTime "
                + " ORDER BY GR_EndTime DESC ";
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("GM_GameCode", gameCode);
        parameters.put("PLY_GUID", plyGuid);
        parameters.put("BeginDateTime", beginDateTime);
        parameters.put("EndDateTime", endDateTime);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getSlotJackpotCount(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT COUNT(1) FROM GR_SlotGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getSlotJackpot(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT GR_SEQ, GM_GameCode, AGT_Agent3, AGT_AccountID, PLY_AccountID, GR_Currency, GR_Jackpot, GR_JackpotType, GR_Record, GR_EndTime " +
                " FROM GR_SlotGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 " +
                " ORDER BY GR_EndTime  DESC";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getFishJackpotCount(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT COUNT(1) FROM GR_FishingGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getFishJackpot(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT GR_SEQ, GM_GameCode, AGT_Agent3, AGT_AccountID, PLY_AccountID, GR_Currency, GR_Jackpot, GR_JackpotType, GR_Record, GR_EndTime " +
                " FROM GR_FishingGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 " +
                " ORDER BY GR_EndTime  DESC ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getCardJackpotCount(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT COUNT(1) FROM GR_CardGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getCardJackpot(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT GR_SEQ, GM_GameCode, AGT_Agent3, AGT_AccountID, PLY_AccountID, GR_Currency, GR_Jackpot, GR_JackpotType, GR_Record, GR_EndTime " +
                " FROM GR_CardGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 " +
                " ORDER BY GR_EndTime  DESC ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public int getMiniJackpotCount(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT COUNT(1) FROM GR_MiniGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 ";
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, int.class);
    }

    public SqlRowSet getMiniJackpot(Date startTime, Date endTime, List<String> agnetGuidList) {
        String sql = " SELECT GR_SEQ, GM_GameCode, AGT_Agent3, AGT_AccountID, PLY_AccountID, GR_Currency, GR_Jackpot, GR_JackpotType, GR_Record, GR_EndTime " +
                " FROM GR_MiniGame " +
                " WHERE  AGT_Agent3 IN (:agent3List) and GR_EndTime >= :startTime AND  GR_EndTime < :endTime AND  GR_JackpotType = 2 " +
                " ORDER BY GR_EndTime  DESC ";

        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("agent3List", agnetGuidList);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }

    public SqlRowSet getSlotGameBaseWinAndFreeWinAndJackpot(Date startTime, Date endTime, List<String> agent1List) {
        String sql = " SELECT GM_GameCode, GR_FlagFreeGame, GR_JackpotType, SUM(GR_Bets) AS sumBet, SUM(GR_Win) AS sumWin, SUM(GR_Jackpot) AS sumJackpot, COUNT(1) AS games " +
                " FROM GR_SlotGame " +
                " WHERE GR_EndTime >= :startTime AND  GR_EndTime < :endTime " +
                " AND AGT_Agent1 NOT IN (:ExcludeAgent1List) " +
                " GROUP BY GM_GameCode, GR_FlagFreeGame, GR_JackpotType ";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("endTime", endTime);
        parameters.put("ExcludeAgent1List", agent1List);

        return namedParameterJdbcTemplate.queryForRowSet(sql, parameters);
    }
}