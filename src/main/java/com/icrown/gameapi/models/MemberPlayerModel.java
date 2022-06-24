package com.icrown.gameapi.models;

import java.math.BigDecimal;
import java.util.Date;

public class MemberPlayerModel {
    private String PLY_GUID;
    private BigDecimal PLY_Point;
    private Date PLY_PointUpdatetime;
    private String MCT_GUID;
    private String AGT_Agent1;
    private String AGT_Agent2;
    private String AGT_Agent3;
    private String PLY_AccountID;
    private String AGT_AccountID;
    private Boolean PLY_Tester;
    private Boolean PLY_Lock;
    private String PLY_Locker;
    private Date PLY_LockDatetime;
    private String PLY_NickName;
    private boolean PLY_Kick;
    private String PLY_LastTicket;
    private Date PLY_CreateDatetime;

    public String getPLY_GUID() {
        return PLY_GUID;
    }

    public void setPLY_GUID(String PLY_GUID) {
        this.PLY_GUID = PLY_GUID;
    }

    public BigDecimal getPLY_Point() {
        return PLY_Point;
    }

    public void setPLY_Point(BigDecimal PLY_Point) {
        this.PLY_Point = PLY_Point;
    }

    public Date getPLY_PointUpdatetime() {
        return PLY_PointUpdatetime;
    }

    public void setPLY_PointUpdatetime(Date PLY_PointUpdatetime) {
        this.PLY_PointUpdatetime = PLY_PointUpdatetime;
    }

    public String getMCT_GUID() {
        return MCT_GUID;
    }

    public void setMCT_GUID(String MCT_GUID) {
        this.MCT_GUID = MCT_GUID;
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

    public String getPLY_AccountID() {
        return PLY_AccountID;
    }

    public void setPLY_AccountID(String PLY_AccountID) {
        this.PLY_AccountID = PLY_AccountID;
    }

    public Boolean getPLY_Tester() {
        return PLY_Tester;
    }

    public void setPLY_Tester(Boolean PLY_Tester) {

        this.PLY_Tester = PLY_Tester;
    }

    public boolean isPLY_Lock() {
        return PLY_Lock;
    }

    public void setPLY_Lock(boolean PLY_Lock) {
        this.PLY_Lock = PLY_Lock;
    }

    public String getPLY_Locker() {
        return PLY_Locker;
    }

    public void setPLY_Locker(String PLY_Locker) {
        this.PLY_Locker = PLY_Locker;
    }

    public Date getPLY_LockDatetime() {
        return PLY_LockDatetime;
    }

    public void setPLY_LockDatetime(Date PLY_LockDatetime) {
        this.PLY_LockDatetime = PLY_LockDatetime;
    }

   public String getPLY_NickName() {
        return PLY_NickName;
    }

    public void setPLY_NickName(String PLY_NickName) {
        this.PLY_NickName = PLY_NickName;
    }

    public boolean getPLY_Kick(){
        return this.PLY_Kick;
    }

    public void setPLY_Kick(boolean PLY_Kick){
        this.PLY_Kick = PLY_Kick;
    }

    public Boolean getPLY_Lock() {
        return PLY_Lock;
    }

    public void setPLY_Lock(Boolean PLY_Lock) {
        this.PLY_Lock = PLY_Lock;
    }

    public String getPLY_LastTicket() {
        return PLY_LastTicket;
    }

    public void setPLY_LastTicket(String PLY_LastTicket) {
        this.PLY_LastTicket = PLY_LastTicket;
    }

    public Date getPLY_CreateDatetime() {
        return PLY_CreateDatetime;
    }

    public void setPLY_CreateDatetime(Date PLY_CreateDatetime) {
        this.PLY_CreateDatetime = PLY_CreateDatetime;
    }

    public String getAGT_AccountID() {
        return AGT_AccountID;
    }

    public void setAGT_AccountID(String AGT_AccountID) {
        this.AGT_AccountID = AGT_AccountID;
    }

    public boolean isPLY_Tester() {
        return PLY_Tester;
    }

    public boolean isPLY_Kick() {
        return PLY_Kick;
    }
}
