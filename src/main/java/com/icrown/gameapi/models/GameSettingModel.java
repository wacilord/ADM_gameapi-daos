package com.icrown.gameapi.models;

/**
 * @author Frank
 */
public class GameSettingModel {
    private long SYS_GameSetting_ID;
    private String AGT_GUID;
    private String AGT_Agent1;
    private String AGT_Agent2;
    private String AGT_Agent3;
    private int AGT_Level;
    private String AGT_AccountID;
    private String MCT_Domain;
    private String SYS_GameSetting_Key;
    private String SYS_GameSetting_Value;
    private String SYS_GameSetting_Note;

    public long getSYS_GameSetting_ID() {
        return SYS_GameSetting_ID;
    }

    public void setSYS_GameSetting_ID(long SYS_GameSetting_ID) {
        this.SYS_GameSetting_ID = SYS_GameSetting_ID;
    }

    public String getAGT_GUID() {
        return AGT_GUID;
    }

    public void setAGT_GUID(String AGT_GUID) {
        this.AGT_GUID = AGT_GUID;
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

    public int getAGT_Level() {
        return AGT_Level;
    }

    public void setAGT_Level(int AGT_Level) {
        this.AGT_Level = AGT_Level;
    }

    public String getAGT_AccountID() {
        return AGT_AccountID;
    }

    public void setAGT_AccountID(String AGT_AccountID) {
        this.AGT_AccountID = AGT_AccountID;
    }

    public String getMCT_Domain() {
        return MCT_Domain;
    }

    public void setMCT_Domain(String MCT_Domain) {
        this.MCT_Domain = MCT_Domain;
    }

    public String getSYS_GameSetting_Key() {
        return SYS_GameSetting_Key;
    }

    public void setSYS_GameSetting_Key(String SYS_GameSetting_Key) {
        this.SYS_GameSetting_Key = SYS_GameSetting_Key;
    }

    public String getSYS_GameSetting_Value() {
        return SYS_GameSetting_Value;
    }

    public void setSYS_GameSetting_Value(String SYS_GameSetting_Value) {
        this.SYS_GameSetting_Value = SYS_GameSetting_Value;
    }

    public String getSYS_GameSetting_Note() {
        return SYS_GameSetting_Note;
    }

    public void setSYS_GameSetting_Note(String SYS_GameSetting_Note) {
        this.SYS_GameSetting_Note = SYS_GameSetting_Note;
    }
}
