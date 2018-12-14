package com.stylefeng.guns.modular.api.model.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "签到查询返回model")
public class CheckInFindModel {

    @ApiModelProperty("签到场次id")
    private Integer screeningId;
    @ApiModelProperty("场次名称")
    private Integer screenings;
    @ApiModelProperty("签到人数")
    private Integer memberCount;
    @ApiModelProperty("创建时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("活动状态")
    private String activityType;

    public Integer getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Integer screeningId) {
        this.screeningId = screeningId;
    }

    public Integer getScreenings() {
        return screenings;
    }

    public void setScreenings(Integer screenings) {
        this.screenings = screenings;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
