package com.stylefeng.guns.modular.api.model.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "活动领取页面数据model")
public class SettlementActivityPageDataModel {
    @ApiModelProperty("会员id")
    private String memberId;
    @ApiModelProperty("会员名称")
    private String memberName;
    @ApiModelProperty("身份证")
    private String cadID;
    @ApiModelProperty("满足活动名称")
    private List<String> activityDetailListName;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public List<String> getActivityDetailListName() {
        return activityDetailListName;
    }

    public void setActivityDetailListName(List<String> activityDetailListName) {
        this.activityDetailListName = activityDetailListName;
    }
}
