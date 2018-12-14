package com.stylefeng.guns.modular.api.model.memerber;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员卡片数据")
public class MemberCardModel {

    @ApiModelProperty("会员id")
    private String memberId;
    @ApiModelProperty("会员卡片code")
    private String cardCode;
    @ApiModelProperty("会员名称")
    private String name;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
