package com.icrown.gameapi.models;

import java.util.Date;

/**
 * @author Frank
 */
public class SessionModel {
    private String SAT_GUID;
    private String SEN_LoginToken;
    private String TKS_IP;
    private Date TKS_Expireddatetime;
    private Date TKS_Createdatetime;

    public String getSAT_GUID() {
        return SAT_GUID;
    }

    public void setSAT_GUID(String SAT_GUID) {
        this.SAT_GUID = SAT_GUID;
    }

    public String getSEN_LoginToken() {
        return SEN_LoginToken;
    }

    public void setSEN_LoginToken(String SEN_LoginToken) {
        this.SEN_LoginToken = SEN_LoginToken;
    }

    public String getTKS_IP() {
        return TKS_IP;
    }

    public void setTKS_IP(String TKS_IP) {
        this.TKS_IP = TKS_IP;
    }

    public Date getTKS_Expireddatetime() {
        return TKS_Expireddatetime;
    }

    public void setTKS_Expireddatetime(Date TKS_Expireddatetime) {
        this.TKS_Expireddatetime = TKS_Expireddatetime;
    }

    public Date getTKS_Createdatetime() {
        return TKS_Createdatetime;
    }

    public void setTKS_Createdatetime(Date TKS_Createdatetime) {
        this.TKS_Createdatetime = TKS_Createdatetime;
    }
}
