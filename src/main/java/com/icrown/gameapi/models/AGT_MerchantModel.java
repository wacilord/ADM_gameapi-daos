package com.icrown.gameapi.models;

/**
 * 代理商資料
 */
public class AGT_MerchantModel {
    private String MCT_GUID;
    private String MCT_FriendlyName;
    private boolean MCT_Enable;
    private String MCT_Domain;
    private String MCT_Currency;
    private String LAG_Code;
    private String MCT_AllowIP;
    private String MCT_BackendAllowIP;
    private String MCT_FrontendAllowIP;
    private String MCT_MerchantCode;
    private boolean MCT_IsMaintain;

    public String getMCT_GUID() {
        return MCT_GUID;
    }

    public void setMCT_GUID(String MCT_GUID) {
        this.MCT_GUID = MCT_GUID;
    }

    public String getMCT_FriendlyName() {
        return MCT_FriendlyName;
    }

    public void setMCT_FriendlyName(String MCT_FriendlyName) {
        this.MCT_FriendlyName = MCT_FriendlyName;
    }

    public boolean isMCT_Enable() {
        return MCT_Enable;
    }

    public void setMCT_Enable(boolean MCT_Enable) {
        this.MCT_Enable = MCT_Enable;
    }

    public String getMCT_Domain() {
        return MCT_Domain;
    }

    public void setMCT_Domain(String MCT_Domain) {
        this.MCT_Domain = MCT_Domain;
    }

    public String getMCT_Currency() {
        return MCT_Currency;
    }

    public void setMCT_Currency(String MCT_Currency) {
        this.MCT_Currency = MCT_Currency;
    }

    public String getLAG_Code() {
        return LAG_Code;
    }

    public void setLAG_Code(String LAG_Code) {
        this.LAG_Code = LAG_Code;
    }

    public String getMCT_AllowIP() {
        return MCT_AllowIP;
    }

    public void setMCT_AllowIP(String MCT_AllowIP) {
        this.MCT_AllowIP = MCT_AllowIP;
    }

    public String getMCT_BackendAllowIP() {
        return MCT_BackendAllowIP;
    }

    public void setMCT_BackendAllowIP(String MCT_BackendAllowIP) {
        this.MCT_BackendAllowIP = MCT_BackendAllowIP;
    }

    public String getMCT_FrontendAllowIP() {
        return MCT_FrontendAllowIP;
    }

    public void setMCT_FrontendAllowIP(String MCT_FrontendAllowIP) {
        this.MCT_FrontendAllowIP = MCT_FrontendAllowIP;
    }

    public String getMCT_MerchantCode() {
        return MCT_MerchantCode;
    }

    public void setMCT_MerchantCode(String MCT_MerchantCode) {
        this.MCT_MerchantCode = MCT_MerchantCode;
    }

    public boolean isMCT_IsMaintain() {
        return MCT_IsMaintain;
    }

    public void setMCT_IsMaintain(boolean MCT_IsMaintain) {
        this.MCT_IsMaintain = MCT_IsMaintain;
    }
}
