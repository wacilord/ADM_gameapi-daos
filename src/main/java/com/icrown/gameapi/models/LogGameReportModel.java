package com.icrown.gameapi.models;

import java.util.Date;

/**
 * @author Frank
 */
public class LogGameReportModel {
    private long LGR_ID;
    private String LGR_Executor;
    private Date LGR_ExecuteTime;
    private int LGR_Type;
    private String LGR_AgentID;
    private Date LGR_StartTime;
    private Date LGR_EndTime;
    private Date LGR_DoneTime;
    private int LGR_Count;
    private int LGR_UsedTime;
    private int LGR_AfterDailyTradeCount;
    private int LGR_AfterGameReportCount;
    private int LGR_AfterDiffCount;
    private String LGR_Key;

    public long getLGR_ID() {
        return LGR_ID;
    }

    public void setLGR_ID(long LGR_ID) {
        this.LGR_ID = LGR_ID;
    }

    public String getLGR_Executor() {
        return LGR_Executor;
    }

    public void setLGR_Executor(String LGR_Executor) {
        this.LGR_Executor = LGR_Executor;
    }

    public Date getLGR_ExecuteTime() {
        return LGR_ExecuteTime;
    }

    public void setLGR_ExecuteTime(Date LGR_ExecuteTime) {
        this.LGR_ExecuteTime = LGR_ExecuteTime;
    }

    public int getLGR_Type() {
        return LGR_Type;
    }

    public void setLGR_Type(int LGR_Type) {
        this.LGR_Type = LGR_Type;
    }

    public String getLGR_AgentID() {
        return LGR_AgentID;
    }

    public void setLGR_AgentID(String LGR_AgentID) {
        this.LGR_AgentID = LGR_AgentID;
    }

    public Date getLGR_StartTime() {
        return LGR_StartTime;
    }

    public void setLGR_StartTime(Date LGR_StartTime) {
        this.LGR_StartTime = LGR_StartTime;
    }

    public Date getLGR_EndTime() {
        return LGR_EndTime;
    }

    public void setLGR_EndTime(Date LGR_EndTime) {
        this.LGR_EndTime = LGR_EndTime;
    }

    public Date getLGR_DoneTime() {
        return LGR_DoneTime;
    }

    public void setLGR_DoneTime(Date LGR_DoneTime) {
        this.LGR_DoneTime = LGR_DoneTime;
    }

    public int getLGR_Count() {
        return LGR_Count;
    }

    public void setLGR_Count(int LGR_Count) {
        this.LGR_Count = LGR_Count;
    }

    public int getLGR_UsedTime() {
        return LGR_UsedTime;
    }

    public void setLGR_UsedTime(int LGR_UsedTime) {
        this.LGR_UsedTime = LGR_UsedTime;
    }

    public int getLGR_AfterDailyTradeCount() {
        return LGR_AfterDailyTradeCount;
    }

    public void setLGR_AfterDailyTradeCount(int LGR_AfterDailyTradeCount) {
        this.LGR_AfterDailyTradeCount = LGR_AfterDailyTradeCount;
    }

    public int getLGR_AfterGameReportCount() {
        return LGR_AfterGameReportCount;
    }

    public void setLGR_AfterGameReportCount(int LGR_AfterGameReportCount) {
        this.LGR_AfterGameReportCount = LGR_AfterGameReportCount;
    }

    public int getLGR_AfterDiffCount() {
        return LGR_AfterDiffCount;
    }

    public void setLGR_AfterDiffCount(int LGR_AfterDiffCount) {
        this.LGR_AfterDiffCount = LGR_AfterDiffCount;
    }

    public String getLGR_Key() {
        return LGR_Key;
    }

    public void setLGR_Key(String LGR_Key) {
        this.LGR_Key = LGR_Key;
    }
}
