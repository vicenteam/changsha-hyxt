package com.stylefeng.guns.modular.api.model.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "签到详情model")
public class CheckInModel {

    @ApiModelProperty("会员姓名")
    private String name;
    @ApiModelProperty("会员卡编号")
    private String cardID;
    @ApiModelProperty("签到记录")
    private List<CheckInTimeModel> checkInRecord=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public List<CheckInTimeModel> getCheckInRecord() {
        return checkInRecord;
    }

    public void setCheckInRecord(List<CheckInTimeModel> checkInRecord) {
        this.checkInRecord = checkInRecord;
    }
}
