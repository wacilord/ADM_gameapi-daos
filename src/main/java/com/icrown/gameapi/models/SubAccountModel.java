package com.icrown.gameapi.models;

import java.util.Date;

/**
 * @author Frank
 */
public class SubAccountModel {
    private String SAT_GUID;
    private String AGT_GUID;
    private int SAT_Type;
    private String MCT_Domain;
    private String SAT_AccountID;
    private String SAT_Password;
    private String SAT_NickName;
    private String SAT_Memo;
    private boolean SAT_Enable;
    private String SAT_LastIP;
    private int SAT_ErrorCount;
    private boolean SAT_IsFirstLogin;
    private Date SAT_LastErrorLoginDatetime;
    private Date SAT_RegainDatetime;
    private Date SAT_CreateDatetime;
    private Date SAT_LastLoginDatetime;

    public String getSAT_GUID() {
        return SAT_GUID;
    }

    public void setSAT_GUID(String SAT_GUID) {
        this.SAT_GUID = SAT_GUID;
    }

    public String getAGT_GUID() {
        return AGT_GUID;
    }

    public void setAGT_GUID(String AGT_GUID) {
        this.AGT_GUID = AGT_GUID;
    }

    public int getSAT_Type() {
        return SAT_Type;
    }

    public void setSAT_Type(int SAT_Type) {
        this.SAT_Type = SAT_Type;
    }

    public String getMCT_Domain() {
        return MCT_Domain;
    }

    public void setMCT_Domain(String MCT_Domain) {
        this.MCT_Domain = MCT_Domain;
    }

    public String getSAT_AccountID() {
        return SAT_AccountID;
    }

    public void setSAT_AccountID(String SAT_AccountID) {
        this.SAT_AccountID = SAT_AccountID;
    }

    public String getSAT_Password() {
        return SAT_Password;
    }

    public void setSAT_Password(String SAT_Password) {
        this.SAT_Password = SAT_Password;
    }

    public String getSAT_NickName() {
        return SAT_NickName;
    }

    public void setSAT_NickName(String SAT_NickName) {
        this.SAT_NickName = SAT_NickName;
    }

    public String getSAT_Memo() {
        return SAT_Memo;
    }

    public void setSAT_Memo(String SAT_Memo) {
        this.SAT_Memo = SAT_Memo;
    }

    public boolean isSAT_Enable() {
        return SAT_Enable;
    }

    public void setSAT_Enable(boolean SAT_Enable) {
        this.SAT_Enable = SAT_Enable;
    }

    public String getSAT_LastIP() {
        return SAT_LastIP;
    }

    public void setSAT_LastIP(String SAT_LastIP) {
        this.SAT_LastIP = SAT_LastIP;
    }

    public int getSAT_ErrorCount() {
        return SAT_ErrorCount;
    }

    public void setSAT_ErrorCount(int SAT_ErrorCount) {
        this.SAT_ErrorCount = SAT_ErrorCount;
    }

    public boolean isSAT_IsFirstLogin() {
        return SAT_IsFirstLogin;
    }

    public void setSAT_IsFirstLogin(boolean SAT_IsFirstLogin) {
        this.SAT_IsFirstLogin = SAT_IsFirstLogin;
    }

    public Date getSAT_LastErrorLoginDatetime() {
        return SAT_LastErrorLoginDatetime;
    }

    public void setSAT_LastErrorLoginDatetime(Date SAT_LastErrorLoginDatetime) {
        this.SAT_LastErrorLoginDatetime = SAT_LastErrorLoginDatetime;
    }

    public Date getSAT_RegainDatetime() {
        return SAT_RegainDatetime;
    }

    public void setSAT_RegainDatetime(Date SAT_RegainDatetime) {
        this.SAT_RegainDatetime = SAT_RegainDatetime;
    }

    public Date getSAT_CreateDatetime() {
        return SAT_CreateDatetime;
    }

    public void setSAT_CreateDatetime(Date SAT_CreateDatetime) {
        this.SAT_CreateDatetime = SAT_CreateDatetime;
    }

    public Date getSAT_LastLoginDatetime() {
        return SAT_LastLoginDatetime;
    }

    public void setSAT_LastLoginDatetime(Date SAT_LastLoginDatetime) {
        this.SAT_LastLoginDatetime = SAT_LastLoginDatetime;
    }
}
