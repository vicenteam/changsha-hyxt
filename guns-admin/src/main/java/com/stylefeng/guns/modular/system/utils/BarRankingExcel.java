package com.stylefeng.guns.modular.system.utils;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

@ExcelTarget("barRankingExcel")
public class BarRankingExcel {

    @Excel(name = "会员名称",orderNum = "1")
    private String mName;
    @Excel(name = "卡片等级",orderNum = "2")
    private String mLevel;
    @Excel(name = "积分值",orderNum = "3")
    private String mIntegral;

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

    public String getmIntegral() {
        return mIntegral;
    }

    public void setmIntegral(String mIntegral) {
        this.mIntegral = mIntegral;
    }
}
