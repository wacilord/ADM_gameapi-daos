package com.icrown.gameapi.models;

import java.util.Date;

/**
 * @author Frank
 */
public class LogGameTicketModel {
    private String LGT_Ticket;
    private String GameServer_Name;
    private int LGL_Work;
    private String AGT_Agent1;
    private String AGT_Agent2;
    private String LGT_Token;
    private String AGT_Agent3;
    private String PLY_GUID;
    private int LGT_IsMember;
    private String LGT_IP;
    private String LGL_GameCode;
    private Date LGT_Createdatetime;
    private Date LGT_Closedatetime;
    private String LGT_Host;

    public String getLGT_Ticket() {
        return LGT_Ticket;
    }

    public void setLGT_Ticket(String LGT_Ticket) {
        this.LGT_Ticket = LGT_Ticket;
    }

    public String getGameServer_Name() {
        return GameServer_Name;
    }

    public void setGameServer_Name(String gameServer_Name) {
        GameServer_Name = gameServer_Name;
    }

    public int getLGL_Work() {
        return LGL_Work;
    }

    public void setLGL_Work(int LGL_Work) {
        this.LGL_Work = LGL_Work;
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

    public String getLGT_Token() {
        return LGT_Token;
    }

    public void setLGT_Token(String LGT_Token) {
        this.LGT_Token = LGT_Token;
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

    public int getLGT_IsMember() {
        return LGT_IsMember;
    }

    public void setLGT_IsMember(int LGT_IsMember) {
        this.LGT_IsMember = LGT_IsMember;
    }

    public String getLGT_IP() {
        return LGT_IP;
    }

    public void setLGT_IP(String LGT_IP) {
        this.LGT_IP = LGT_IP;
    }

    public String getLGL_GameCode() {
        return LGL_GameCode;
    }

    public void setLGL_GameCode(String LGL_GameCode) {
        this.LGL_GameCode = LGL_GameCode;
    }

    public Date getLGT_Createdatetime() {
        return LGT_Createdatetime;
    }

    public void setLGT_Createdatetime(Date LGT_Createdatetime) {
        this.LGT_Createdatetime = LGT_Createdatetime;
    }

    public Date getLGT_Closedatetime() {
        return LGT_Closedatetime;
    }

    public void setLGT_Closedatetime(Date LGT_Closedatetime) {
        this.LGT_Closedatetime = LGT_Closedatetime;
    }

    public String getLGT_Host() {
        return LGT_Host;
    }

    public void setLGT_Host(String LGT_Host) {
        this.LGT_Host = LGT_Host;
    }
}
