package com.stylefeng.guns.modular.api.model.jifen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value ="新增积分model")
public class JifenAddModel {
    @ApiModelProperty("新增积分用户id")
    private Integer jifenAddUserId;
    @ApiModelProperty("身份证")
    private String cardId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("会员等级")
    private String levelID;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("总积分")
    private Double countPrice;
    @ApiModelProperty("当前可用积分")
    private Double integral;
    @ApiModelProperty("积分类型List<Map<String,Object>>")
    private List<Map<String,Object>> jifenType=new ArrayList<>();
    @ApiModelProperty("获得积分的值")
    private Double nowPice;

    public Integer getJifenAddUserId() {
        return jifenAddUserId;
    }

    public void setJifenAddUserId(Integer jifenAddUserId) {
        this.jifenAddUserId = jifenAddUserId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(Double countPrice) {
        this.countPrice = countPrice;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public List<Map<String, Object>> getJifenType() {
        return jifenType;
    }

    public void setJifenType(List<Map<String, Object>> jifenType) {
        this.jifenType = jifenType;
    }

    public Double getNowPice() {
        return nowPice;
    }

    public void setNowPice(Double nowPice) {
        this.nowPice = nowPice;
    }
}
