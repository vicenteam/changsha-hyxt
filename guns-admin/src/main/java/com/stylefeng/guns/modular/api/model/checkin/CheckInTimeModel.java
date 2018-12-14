package com.stylefeng.guns.modular.api.model.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "签到时间model")
public class CheckInTimeModel {
    @ApiModelProperty("签到时间")
    private String createtime;
    @ApiModelProperty("复签时间")
    private String updatetime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
