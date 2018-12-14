package com.stylefeng.guns.modular.api.model.jifen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="积分分页查询参数model")
public class JifenPageFindMode {

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("身份证")
    private String cardId;
    @ApiModelProperty("积分类型")
    private Integer integraltype=-1;
    @ApiModelProperty("操作人")
    private String staffId="-1" ;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("查询类型(0搜索,1读卡)")
    private Integer queryType=0;
    @ApiModelProperty("卡片code")
    private String cardCode;
    @ApiModelProperty("每页条数")
    private Integer limit;
    @ApiModelProperty("当前页码(0开始)")
    private Integer offset;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getIntegraltype() {
        return integraltype;
    }

    public void setIntegraltype(Integer integraltype) {
        this.integraltype = integraltype;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
