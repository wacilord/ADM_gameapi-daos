package com.icrown.gameapi.models;

import java.math.BigDecimal;

public class CamAwardTypeModel {
    private int camAwardTypeMainID;
    private String camAwardTypeMainName;
    private int camAwardTypeID;
    private String camAwardTypeName;
    private int camImgID;
    private String camAwardDescription;
    private int camCode;
    private BigDecimal camAwardTypeMin;
    private BigDecimal camAwardTypeMax;
    private BigDecimal camWin;

    public int getCamAwardTypeMainID() {
        return camAwardTypeMainID;
    }

    public void setCamAwardTypeMainID(int camAwardTypeMainID) {
        this.camAwardTypeMainID = camAwardTypeMainID;
    }

    public String getCamAwardTypeMainName() {
        return camAwardTypeMainName;
    }

    public void setCamAwardTypeMainName(String camAwardTypeMainName) {
        this.camAwardTypeMainName = camAwardTypeMainName;
    }

    public int getCamAwardTypeID() {
        return camAwardTypeID;
    }

    public void setCamAwardTypeID(int camAwardTypeID) {
        this.camAwardTypeID = camAwardTypeID;
    }

    public String getCamAwardTypeName() {
        return camAwardTypeName;
    }

    public void setCamAwardTypeName(String camAwardTypeName) {
        this.camAwardTypeName = camAwardTypeName;
    }

    public int getCamCode() {
        return camCode;
    }

    public void setCamCode(int camCode) {
        this.camCode = camCode;
    }

    public BigDecimal getCamAwardTypeMin() {
        return camAwardTypeMin;
    }

    public void setCamAwardTypeMin(BigDecimal camAwardTypeMin) {
        this.camAwardTypeMin = camAwardTypeMin;
    }

    public BigDecimal getCamAwardTypeMax() {
        return camAwardTypeMax;
    }

    public void setCamAwardTypeMax(BigDecimal camAwardTypeMax) {
        this.camAwardTypeMax = camAwardTypeMax;
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

    public BigDecimal getCamWin() {
        return camWin;
    }

    public void setCamWin(BigDecimal camWin) {
        this.camWin = camWin;
    }

    @Override
    public String toString() {
        return "CamAwardTypeModel{" +
                "camAwardTypeMainID=" + camAwardTypeMainID +
                ", camAwardTypeMainName='" + camAwardTypeMainName + '\'' +
                ", camAwardTypeID=" + camAwardTypeID +
                ", camAwardTypeName='" + camAwardTypeName + '\'' +
                ", camImgID=" + camImgID +
                ", camAwardDescription='" + camAwardDescription + '\'' +
                ", camCode=" + camCode +
                ", camAwardTypeMin=" + camAwardTypeMin +
                ", camAwardTypeMax=" + camAwardTypeMax +
                ", camWin=" + camWin +
                '}';
    }
}
