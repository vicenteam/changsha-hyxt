package com.stylefeng.guns.modular.api.model.memerber;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="会员补签model")
public class MemberRepairModel {

    @ApiModelProperty("会员id")
    private String memberId;
    @ApiModelProperty("补签时间")
    private String time;
    @ApiModelProperty("身份证id")
    private String cadID;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }
}
