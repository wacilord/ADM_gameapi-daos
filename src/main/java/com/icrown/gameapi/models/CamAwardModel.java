package com.icrown.gameapi.models;

import java.math.BigDecimal;
import java.util.Date;

public class CamAwardModel {
    private String camAwardID;
    private long dtSeq;
    private String plyGuid;
    private String plyAccountID;
    private String mctDomain;
    private String agtAgent1;
    private String agtAgent2;
    private String agtAgent3;
    private String agtAccountID;
    private String dtCurrency;
    private int camCode;
    private int camStage;
    private Date camAwardStartTakeTime;
    private Date camDateLine;
    private BigDecimal camWin;
    private int camAwardTypeID;
    private String camAwardTypeName;
    private Date camAwardCreateDateTime;
    private BigDecimal camCondition;
    private int camImgID;
    private String camAwardDescription;

    public String getCamAwardID() {
        return camAwardID;
    }

    public void setCamAwardID(String camAwardID) {
        this.camAwardID = camAwardID;
    }

    public long getDtSeq() {
        return dtSeq;
    }

    public void setDtSeq(long dtSeq) {
        this.dtSeq = dtSeq;
    }

    public String getPlyGuid() {
        return plyGuid;
    }

    public void setPlyGuid(String plyGuid) {
        this.plyGuid = plyGuid;
    }

    public String getPlyAccountID() {
        return plyAccountID;
    }

    public void setPlyAccountID(String plyAccountID) {
        this.plyAccountID = plyAccountID;
    }

    public String getMctDomain() {
        return mctDomain;
    }

    public void setMctDomain(String mctDomain) {
        this.mctDomain = mctDomain;
    }

    public String getAgtAgent1() {
        return agtAgent1;
    }

    public void setAgtAgent1(String agtAgent1) {
        this.agtAgent1 = agtAgent1;
    }

    public String getAgtAgent2() {
        return agtAgent2;
    }

    public void setAgtAgent2(String agtAgent2) {
        this.agtAgent2 = agtAgent2;
    }

    public String getAgtAgent3() {
        return agtAgent3;
    }

    public void setAgtAgent3(String agtAgent3) {
        this.agtAgent3 = agtAgent3;
    }

    public String getAgtAccountID() {
        return agtAccountID;
    }

    public void setAgtAccountID(String agtAccountID) {
        this.agtAccountID = agtAccountID;
    }

    public String getDtCurrency() {
        return dtCurrency;
    }

    public void setDtCurrency(String dtCurrency) {
        this.dtCurrency = dtCurrency;
    }

    public int getCamCode() {
        return camCode;
    }

    public void setCamCode(int camCode) {
        this.camCode = camCode;
    }

    public int getCamStage() {
        return camStage;
    }

    public void setCamStage(int camStage) {
        this.camStage = camStage;
    }

    public Date getCamAwardStartTakeTime() {
        return camAwardStartTakeTime;
    }

    public void setCamAwardStartTakeTime(Date camAwardStartTakeTime) {
        this.camAwardStartTakeTime = camAwardStartTakeTime;
    }

    public Date getCamDateLine() {
        return camDateLine;
    }

    public void setCamDateLine(Date camDateLine) {
        this.camDateLine = camDateLine;
    }

    public BigDecimal getCamWin() {
        return camWin;
    }

    public void setCamWin(BigDecimal camWin) {
        this.camWin = camWin;
    }

    public int getCamAwardTypeID() {
        return camAwardTypeID;
    }


    public void setCamAwardTypeID(int camAwardTypeID) {
        this.camAwardTypeID = camAwardTypeID;
    }

    public Date getCamAwardCreateDateTime() {
        return camAwardCreateDateTime;
    }

    public void setCamAwardCreateDateTime(Date camAwardCreateDateTime) {
        this.camAwardCreateDateTime = camAwardCreateDateTime;
    }

    public String getCamAwardTypeName() {
        return camAwardTypeName;
    }

    public void setCamAwardTypeName(String camAwardTypeName) {
        this.camAwardTypeName = camAwardTypeName;
    }

    public BigDecimal getCamCondition() {
        return camCondition;
    }

    public void setCamCondition(BigDecimal camCondition) {
        this.camCondition = camCondition;
    }

    public int getCamImgID() {
        return camImgID;
    }

    public void setCamImgID(int camImgID) {
        this.camImgID = camImgID;
    }

    public String getCamAwardDescription() {
        return camAwardDescription;
    }

    public void setCamAwardDescription(String camAwardDescription) {
        this.camAwardDescription = camAwardDescription;
    }

    @Override
    public String toString() {
        return "CamAwardModel{" +
                "camAwardID='" + camAwardID + '\'' +
                ", dtSeq=" + dtSeq +
                ", plyGuid='" + plyGuid + '\'' +
                ", plyAccountID='" + plyAccountID + '\'' +
                ", mctDomain='" + mctDomain + '\'' +
                ", agtAgent1='" + agtAgent1 + '\'' +
                ", agtAgent2='" + agtAgent2 + '\'' +
                ", agtAgent3='" + agtAgent3 + '\'' +
                ", agtAccountID='" + agtAccountID + '\'' +
                ", dtCurrency='" + dtCurrency + '\'' +
                ", camCode=" + camCode +
                ", camStage=" + camStage +
                ", camAwardStartTakeTime=" + camAwardStartTakeTime +
                ", camDateLine=" + camDateLine +
                ", camWin=" + camWin +
                ", camAwardTypeID=" + camAwardTypeID +
                ", camAwardTypeName='" + camAwardTypeName + '\'' +
                ", camAwardCreateDateTime=" + camAwardCreateDateTime +
                ", camCondition=" + camCondition +
                ", camImgID=" + camImgID +
                ", camAwardDescription='" + camAwardDescription + '\'' +
                '}';
    }
}
