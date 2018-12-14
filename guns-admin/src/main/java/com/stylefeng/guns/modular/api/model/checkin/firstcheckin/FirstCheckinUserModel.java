package com.stylefeng.guns.modular.api.model.checkin.firstcheckin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "首签读卡获取用户数据model")
public class FirstCheckinUserModel {
    @ApiModelProperty("卡用户id")
    private Integer memberId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("身份证号")
    private String cadID;
    @ApiModelProperty("会员卡等级")
    private String levelID;
    @ApiModelProperty("可用积分")
    private Double integral;
    @ApiModelProperty("服务员工")
    private String staffName;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
