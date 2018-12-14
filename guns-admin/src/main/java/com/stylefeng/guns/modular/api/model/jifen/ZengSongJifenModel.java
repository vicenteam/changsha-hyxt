package com.stylefeng.guns.modular.api.model.jifen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="赠送积分卡等级Model")
public class ZengSongJifenModel {
    @ApiModelProperty("等级id")
    private Integer leaveId;
    @ApiModelProperty("等级名称")
    private String leaveName;

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }
}
