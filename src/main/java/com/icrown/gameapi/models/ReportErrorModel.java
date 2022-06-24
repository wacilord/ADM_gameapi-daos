package com.icrown.gameapi.models;

import java.util.Date;

/**
 * @author Frank
 */
public class ReportErrorModel {
    private long RE_ID;
    private long RE_MinIndex;
    private long RE_MaxIndex;
    private int RE_ClusterCount;
    private int RE_MaxClusterCount;
    private Date RE_MinDate;
    private Date RE_MaxDate;
    private int RE_Count;
    private Date RE_CreateDateTime;

    public long getRE_ID() {
        return RE_ID;
    }

    public void setRE_ID(long RE_ID) {
        this.RE_ID = RE_ID;
    }

    public long getRE_MinIndex() {
        return RE_MinIndex;
    }

    public void setRE_MinIndex(long RE_MinIndex) {
        this.RE_MinIndex = RE_MinIndex;
    }

    public long getRE_MaxIndex() {
        return RE_MaxIndex;
    }

    public void setRE_MaxIndex(long RE_MaxIndex) {
        this.RE_MaxIndex = RE_MaxIndex;
    }

    public int getRE_ClusterCount() {
        return RE_ClusterCount;
    }

    public void setRE_ClusterCount(int RE_ClusterCount) {
        this.RE_ClusterCount = RE_ClusterCount;
    }

    public int getRE_MaxClusterCount() {
        return RE_MaxClusterCount;
    }

    public void setRE_MaxClusterCount(int RE_MaxClusterCount) {
        this.RE_MaxClusterCount = RE_MaxClusterCount;
    }

    public Date getRE_MinDate() {
        return RE_MinDate;
    }

    public void setRE_MinDate(Date RE_MinDate) {
        this.RE_MinDate = RE_MinDate;
    }

    public Date getRE_MaxDate() {
        return RE_MaxDate;
    }

    public void setRE_MaxDate(Date RE_MaxDate) {
        this.RE_MaxDate = RE_MaxDate;
    }

    public int getRE_Count() {
        return RE_Count;
    }

    public void setRE_Count(int RE_Count) {
        this.RE_Count = RE_Count;
    }

    public Date getRE_CreateDateTime() {
        return RE_CreateDateTime;
    }

    public void setRE_CreateDateTime(Date RE_CreateDateTime) {
        this.RE_CreateDateTime = RE_CreateDateTime;
    }
}
