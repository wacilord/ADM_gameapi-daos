package com.icrown.gameapi.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Frank
 */
public class GameReportModel {
    private String CheckKey;
    private String CheckKeyByDate;
    private long DT_Index;
    private Date DT_UpdateTime;
    private int GR_ID;
    private String AGT_Agent1;
    private String AGT_Agent2;
    private String AGT_Agent3;
    private String PLY_GUID;
    private String PLY_AccountID;
    private int AGG_Items;
    private int GR_GameType;
    private String GR_GameCode;
    private Date AGG_Time;
    private String GR_Currency;
    private BigDecimal AG_SumBets;
    private BigDecimal AG_SumValidBets;
    private BigDecimal AG_SumWin;
    private BigDecimal AG_SumJackpot;
    private BigDecimal AG_SumJackpot2;
    private BigDecimal AG_SumJackpot3;
    private int AG_JackpotType;
    private BigDecimal AG_SumJackpotContribute;
    private BigDecimal AG_Commission;
    private BigDecimal AG_SumNetWin;
    private Date DT_CreateDateTime;
    private int DT_WorkType;



    public Date getDT_CreateDateTime() {
        return DT_CreateDateTime;
    }

    public void setDT_CreateDateTime(Date DT_CreateDateTime) {
        this.DT_CreateDateTime = DT_CreateDateTime;
    }

    public String getCheckKeyByDate() {
        return CheckKeyByDate;
    }

    public void setCheckKeyByDate(String checkKeyByDate) {
        CheckKeyByDate = checkKeyByDate;
    }

    public String getCheckKey() {
        return CheckKey;
    }

    public void setCheckKey(String checkKey) {
        CheckKey = checkKey;
    }

    public long getDT_Index() {
        return DT_Index;
    }

    public void setDT_Index(long DT_Index) {
        this.DT_Index = DT_Index;
    }

    public Date getDT_UpdateTime() {
        return DT_UpdateTime;
    }

    public void setDT_UpdateTime(Date DT_UpdateTime) {
        this.DT_UpdateTime = DT_UpdateTime;
    }

    public int getGR_ID() {
        return GR_ID;
    }

    public void setGR_ID(int GR_ID) {
        this.GR_ID = GR_ID;
    }

    public String getAGT_Agent1() {
        return AGT_Agent1;
    }

    public void setAGT_Agent1(String AGT_Agent1) {
        this.AGT_Agent1 = AGT_Agent1;
    }

    public String getAGT_Agent2() {
        return AGT_Agent2;
    }

    public void setAGT_Agent2(String AGT_Agent2) {
        this.AGT_Agent2 = AGT_Agent2;
    }

    public String getAGT_Agent3() {
        return AGT_Agent3;
    }

    public void setAGT_Agent3(String AGT_Agent3) {
        this.AGT_Agent3 = AGT_Agent3;
    }

    public String getPLY_GUID() {
        return PLY_GUID;
    }

    public void setPLY_GUID(String PLY_GUID) {
        this.PLY_GUID = PLY_GUID;
    }

    public String getPLY_AccountID() {
        return PLY_AccountID;
    }

    public void setPLY_AccountID(String PLY_AccountID) {
        this.PLY_AccountID = PLY_AccountID;
    }

    public int getAGG_Items() {
        return AGG_Items;
    }

    public void setAGG_Items(int AGG_Items) {
        this.AGG_Items = AGG_Items;
    }

    public int getGR_GameType() {
        return GR_GameType;
    }

    public void setGR_GameType(int GR_GameType) {
        this.GR_GameType = GR_GameType;
    }

    public String getGR_GameCode() {
        return GR_GameCode;
    }

    public void setGR_GameCode(String GR_GameCode) {
        this.GR_GameCode = GR_GameCode;
    }

    public Date getAGG_Time() {
        return AGG_Time;
    }

    public void setAGG_Time(Date AGG_Time) {
        this.AGG_Time = AGG_Time;
    }

    public String getGR_Currency() {
        return GR_Currency;
    }

    public void setGR_Currency(String GR_Currency) {
        this.GR_Currency = GR_Currency;
    }

    public BigDecimal getAG_SumBets() {
        return AG_SumBets;
    }

    public void setAG_SumBets(BigDecimal AG_SumBets) {
        this.AG_SumBets = AG_SumBets;
    }

    public BigDecimal getAG_SumValidBets() {
        return AG_SumValidBets;
    }

    public void setAG_SumValidBets(BigDecimal AG_SumValidBets) {
        this.AG_SumValidBets = AG_SumValidBets;
    }

    public BigDecimal getAG_SumWin() {
        return AG_SumWin;
    }

    public void setAG_SumWin(BigDecimal AG_SumWin) {
        this.AG_SumWin = AG_SumWin;
    }

    public BigDecimal getAG_SumJackpot() {
        return AG_SumJackpot;
    }

    public void setAG_SumJackpot(BigDecimal AG_SumJackpot) {
        this.AG_SumJackpot = AG_SumJackpot;
    }

    public BigDecimal getAG_SumJackpot2() {
        return AG_SumJackpot2;
    }

    public void setAG_SumJackpot2(BigDecimal AG_SumJackpot2) {
        this.AG_SumJackpot2 = AG_SumJackpot2;
    }

    public BigDecimal getAG_SumJackpot3() {
        return AG_SumJackpot3;
    }

    public void setAG_SumJackpot3(BigDecimal AG_SumJackpot3) {
        this.AG_SumJackpot3 = AG_SumJackpot3;
    }

    public int getAG_JackpotType() {
        return AG_JackpotType;
    }

    public void setAG_JackpotType(int AG_JackpotType) {
        this.AG_JackpotType = AG_JackpotType;
    }

    public BigDecimal getAG_SumJackpotContribute() {
        return AG_SumJackpotContribute;
    }

    public void setAG_SumJackpotContribute(BigDecimal AG_SumJackpotContribute) {
        this.AG_SumJackpotContribute = AG_SumJackpotContribute;
    }

    public BigDecimal getAG_Commission() {
        return AG_Commission;
    }

    public void setAG_Commission(BigDecimal AG_Commission) {
        this.AG_Commission = AG_Commission;
    }

    public BigDecimal getAG_SumNetWin() {
        return AG_SumNetWin;
    }

    public void setAG_SumNetWin(BigDecimal AG_SumNetWin) {
        this.AG_SumNetWin = AG_SumNetWin;
    }

    public int getDT_WorkType() {
        return DT_WorkType;
    }

    public void setDT_WorkType(int DT_WorkType) {
        this.DT_WorkType = DT_WorkType;
    }
}
