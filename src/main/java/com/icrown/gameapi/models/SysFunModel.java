package com.icrown.gameapi.models;

public class SysFunModel {
    private String Fun_Code;
    private String FunType_Code;
    private String Fun_Name = "";
    private int Fun_Open = 1;
    private int Fun_Sort = 0;

    public String getFun_Code() {
        return Fun_Code;
    }

    public void setFun_Code(String fun_Code) {
        Fun_Code = fun_Code;
    }

    public String getFunType_Code() {
        return FunType_Code;
    }

    public void setFunType_Code(String funType_Code) {
        FunType_Code = funType_Code;
    }

    public String getFun_Name() {
        return Fun_Name;
    }

    public void setFun_Name(String fun_Name) {
        Fun_Name = fun_Name;
    }

    public int getFun_Open() {
        return Fun_Open;
    }

    public void setFun_Open(int fun_Open) {
        Fun_Open = fun_Open;
    }

    public int getFun_Sort() {
        return Fun_Sort;
    }

    public void setFun_Sort(int fun_Sort) {
        Fun_Sort = fun_Sort;
    }
}
