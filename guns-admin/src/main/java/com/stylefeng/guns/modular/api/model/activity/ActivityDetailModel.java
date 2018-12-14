package com.stylefeng.guns.modular.api.model.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动明细model")
public class ActivityDetailModel {
    @ApiModelProperty("活动id")
    private String activityId;
    @ApiModelProperty("活动名称")
    private String activityName;
    @ApiModelProperty("活动描述")
    private String activityContent;
    @ApiModelProperty("活动状态(0未开始，1表示无效 2进行中)")
    private Integer activityStatus;
    @ApiModelProperty("最大领取次数")
    private Integer activityMaxgetnum;
    @ApiModelProperty("领取时间")
    private String activityReceiveTime;
    @ApiModelProperty("当前领取次数")
    private Integer activityReceiveNum;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getActivityMaxgetnum() {
        return activityMaxgetnum;
    }

    public void setActivityMaxgetnum(Integer activityMaxgetnum) {
        this.activityMaxgetnum = activityMaxgetnum;
    }

    public String getActivityReceiveTime() {
        return activityReceiveTime;
    }

    public void setActivityReceiveTime(String activityReceiveTime) {
        this.activityReceiveTime = activityReceiveTime;
    }

    public Integer getActivityReceiveNum() {
        return activityReceiveNum;
    }

    public void setActivityReceiveNum(Integer activityReceiveNum) {
        this.activityReceiveNum = activityReceiveNum;
    }
}
