package com.icrown.gameapi.models;

import java.util.Date;

/**
 * @author Frank
 */
public class GameReportErrorModel {
    private long GRE_ID ;
    private String GRE_Unikey;
    private Date GRE_Time;
    private int GRE_DailyTradeCount;
    private int GRE_GameReportCount;
    private int GRE_DiffCount;
    private String LGR_Key;
    private Date GRE_DoneTime;
    private Date GRE_CreateDateTime;

    public long getGRE_ID() {
        return GRE_ID;
    }

    public void setGRE_ID(long GRE_ID) {
        this.GRE_ID = GRE_ID;
    }

    public String getGRE_Unikey() {
        return GRE_Unikey;
    }

    public void setGRE_Unikey(String GRE_Unikey) {
        this.GRE_Unikey = GRE_Unikey;
    }

    public Date getGRE_Time() {
        return GRE_Time;
    }

    public void setGRE_Time(Date GRE_Time) {
        this.GRE_Time = GRE_Time;
    }

    public int getGRE_DailyTradeCount() {
        return GRE_DailyTradeCount;
    }

    public void setGRE_DailyTradeCount(int GRE_DailyTradeCount) {
        this.GRE_DailyTradeCount = GRE_DailyTradeCount;
    }

    public int getGRE_GameReportCount() {
        return GRE_GameReportCount;
    }

    public void setGRE_GameReportCount(int GRE_GameReportCount) {
        this.GRE_GameReportCount = GRE_GameReportCount;
    }

    public int getGRE_DiffCount() {
        return GRE_DiffCount;
    }

    public void setGRE_DiffCount(int GRE_DiffCount) {
        this.GRE_DiffCount = GRE_DiffCount;
    }

    public String getLGR_Key() {
        return LGR_Key;
    }

    public void setLGR_Key(String LGR_Key) {
        this.LGR_Key = LGR_Key;
    }

    public Date getGRE_DoneTime() {
        return GRE_DoneTime;
    }

    public void setGRE_DoneTime(Date GRE_DoneTime) {
        this.GRE_DoneTime = GRE_DoneTime;
    }

    public Date getGRE_CreateDateTime() {
        return GRE_CreateDateTime;
    }

    public void setGRE_CreateDateTime(Date GRE_CreateDateTime) {
        this.GRE_CreateDateTime = GRE_CreateDateTime;
    }
}
