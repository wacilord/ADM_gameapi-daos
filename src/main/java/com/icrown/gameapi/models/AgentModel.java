package com.icrown.gameapi.models;

import java.util.Date;

/**
 * 代理商資料
 */
public class AgentModel {
    private String AGT_GUID;
    private String MCT_GUID;
    private String AGT_Parent;
    private Integer AGT_Level;
    private String AGT_Agent1;
    private String AGT_Agent2;
    private String AGT_Agent3;
    private String MCT_Domain;
    private String AGT_AccountID;
    private Integer AGT_Lock;
    private Date AGT_CreateDatetime;

    public String getAGT_GUID() {
        return AGT_GUID;
    }

    public void setAGT_GUID(String AGT_GUID) {
        this.AGT_GUID = AGT_GUID;
    }

    public String getMCT_GUID() {
        return MCT_GUID;
    }

    public void setMCT_GUID(String MCT_GUID) {
        this.MCT_GUID = MCT_GUID;
    }

    public String getAGT_Parent() {
        return AGT_Parent;
    }

    public void setAGT_Parent(String AGT_Parent) {
        this.AGT_Parent = AGT_Parent;
    }

    public Integer getAGT_Level() {
        return AGT_Level;
    }

    public void setAGT_Level(Integer AGT_Level) {
        this.AGT_Level = AGT_Level;
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

    public String getAGT_AccountID() {
        return AGT_AccountID;
    }

    public void setAGT_AccountID(String AGT_AccountID) {
        this.AGT_AccountID = AGT_AccountID;
    }

    public Integer getAGT_Lock() {
        return AGT_Lock;
    }

    public void setAGT_Lock(Integer AGT_Lock) {
        this.AGT_Lock = AGT_Lock;
    }

    public Date getAGT_CreateDatetime() {
        return AGT_CreateDatetime;
    }

    public void setAGT_CreateDatetime(Date AGT_CreateDatetime) {
        this.AGT_CreateDatetime = AGT_CreateDatetime;
    }

    public String getMCT_Domain() {
        return MCT_Domain;
    }

    public void setMCT_Domain(String MCT_Domain) {
        this.MCT_Domain = MCT_Domain;
    }
}
