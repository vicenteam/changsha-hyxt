package com.stylefeng.guns.modular.api.model.checkin.firstcheckin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "创建签到model")
public class CreateCheckinMode {
    @ApiModelProperty("签到id")
    private Integer checkinId;
    @ApiModelProperty("签到场次名称")
    private Integer screenings;

    public Integer getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Integer checkinId) {
        this.checkinId = checkinId;
    }

    public Integer getScreenings() {
        return screenings;
    }

    public void setScreenings(Integer screenings) {
        this.screenings = screenings;
    }
}
