package com.icrown.gameapi.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author adi
 */
public class ACC_TransferRecordModel {

    public long getTFR_SEQ() {
        return TFR_SEQ;
    }

    public void setTFR_SEQ(long TFR_SEQ) {
        this.TFR_SEQ = TFR_SEQ;
    }

    private long TFR_SEQ;
    private String TFR_TransferID;
    private String DT_TradeGUID;
    private String AGT_Agent1;
    private String AGT_Agent2;
    private String AGT_Agent3;


    private int AGP_Status;
    private int AGP_Type;
    private BigDecimal AGP_Point;
    private Date TFR_CallDatetime;
    private Date TFR_CreateDatetime;
    private String TFR_ClientType;
    private String TFR_ErrorCode;
    private String TFR_FalseReason;
    private BigDecimal TFR_AfterBalance;

    private String PLY_GUID;
    private String AGT_AccountID;
    private String PLY_AccountID;
    private int Acc_Code;
    private BigDecimal TFR_BeforeBalance;


    public String getPLY_GUID() {
        return PLY_GUID;
    }

    public void setPLY_GUID(String PLY_GUID) {
        this.PLY_GUID = PLY_GUID;
    }

    public String getAGT_AccountID() {
        return AGT_AccountID;
    }

    public void setAGT_AccountID(String AGT_AccountID) {
        this.AGT_AccountID = AGT_AccountID;
    }

    public String getPLY_AccountID() {
        return PLY_AccountID;
    }

    public void setPLY_AccountID(String PLY_AccountID) {
        this.PLY_AccountID = PLY_AccountID;
    }

    public int getAcc_Code() {
        return Acc_Code;
    }

    public void setAcc_Code(int acc_Code) {
        Acc_Code = acc_Code;
    }

    public BigDecimal getTFR_BeforeBalance() {
        return TFR_BeforeBalance;
    }

    public void setTFR_BeforeBalance(BigDecimal TFR_BeforeBalance) {
        this.TFR_BeforeBalance = TFR_BeforeBalance;
    }


    public String getTFR_TransferID() {
        return TFR_TransferID;
    }

    public String getTFR_ErrorCode() {
        return TFR_ErrorCode;
    }

    public void setTFR_ErrorCode(String tFR_ErrorCode) {
        this.TFR_ErrorCode = tFR_ErrorCode;
    }

    public String getTFR_FalseReason() {
        return TFR_FalseReason;
    }

    public void setTFR_FalseReason(String tFR_FalseReason) {
        this.TFR_FalseReason = tFR_FalseReason;
    }

    public void setTFR_TransferID(String TFR_TransferID) {
        this.TFR_TransferID = TFR_TransferID;
    }


    public int getAGP_Status() {
        return AGP_Status;
    }

    public void setAGP_Status(int AGP_Status) {
        this.AGP_Status = AGP_Status;
    }

    public int getAGP_Type() {
        return AGP_Type;
    }

    public void setAGP_Type(int AGP_Type) {
        this.AGP_Type = AGP_Type;
    }

    public BigDecimal getAGP_Point() {
        return AGP_Point;
    }

    public void setAGP_Point(BigDecimal AGP_Point) {
        this.AGP_Point = AGP_Point;
    }


    public Date getTFR_CreateDatetime() {
        return TFR_CreateDatetime;
    }

    public void setTFR_CreateDatetime(Date TFR_CreateDatetime) {
        this.TFR_CreateDatetime = TFR_CreateDatetime;
    }

    public String getTFR_ClientType() {
        return TFR_ClientType;
    }

    public void setTFR_ClientType(String TFR_ClientType) {
        this.TFR_ClientType = TFR_ClientType;
    }

    public Date getTFR_CallDatetime() {
        return TFR_CallDatetime;
    }

    public void setTFR_CallDatetime(Date TFR_CallDatetime) {
        this.TFR_CallDatetime = TFR_CallDatetime;
    }

    public String getDT_TradeGUID() {
        return DT_TradeGUID;
    }

    public void setDT_TradeGUID(String DT_TradeGUID) {
        this.DT_TradeGUID = DT_TradeGUID;
    }

    public BigDecimal getTFR_AfterBalance() {
        return TFR_AfterBalance;
    }

    public void setTFR_AfterBalance(BigDecimal TFR_AfterBalance) {
        this.TFR_AfterBalance = TFR_AfterBalance;
    }
}


