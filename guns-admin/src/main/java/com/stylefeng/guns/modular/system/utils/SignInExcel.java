package com.stylefeng.guns.modular.system.utils;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

@ExcelTarget("signInExcel")
public class SignInExcel {

    @Excel(name = "会员名称",orderNum = "1")
    private String mName;
    @Excel(name = "卡片等级",orderNum = "2")
    private String mLevel;
    @Excel(name = "身份证",orderNum = "3")
    private String cadID;
    @Excel(name = "签到次数",orderNum = "4")
    private Integer signInCount;
    @Excel(name = "最新签到时间",orderNum = "5")
    private String signInNew;
    @Excel(name = "复签次数",orderNum = "6")
    private Integer signOutCount;
    @Excel(name = "最新复签时间",orderNum = "7")
    private String signOutNew;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLevel() {
        return mLevel;
    }

    public void setmLevel(String mLevel) {
        this.mLevel = mLevel;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public Integer getSignInCount() {
        return signInCount;
    }

    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }

    public String getSignInNew() {
        return signInNew;
    }

    public void setSignInNew(String signInNew) {
        this.signInNew = signInNew;
    }

    public Integer getSignOutCount() {
        return signOutCount;
    }

    public void setSignOutCount(Integer signOutCount) {
        this.signOutCount = signOutCount;
    }

    public String getSignOutNew() {
        return signOutNew;
    }

    public void setSignOutNew(String signOutNew) {
        this.signOutNew = signOutNew;
    }
}
