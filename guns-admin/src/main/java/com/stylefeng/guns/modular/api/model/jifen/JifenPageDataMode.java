package com.stylefeng.guns.modular.api.model.jifen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="积分分页查询返回参数model")
public class JifenPageDataMode {
    @ApiModelProperty("姓名")
    private String memberName;
    @ApiModelProperty("电话")
    private String memberPhone;
    @ApiModelProperty("身份证")
    private String membercadid;
    @ApiModelProperty("积分")
    private Double integral;
    @ApiModelProperty("积分类型")
    private String typeId;
    @ApiModelProperty("操作人")
    private String staffid;
    @ApiModelProperty("创建时间")
    private String createTime;



    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMembercadid() {
        return membercadid;
    }

    public void setMembercadid(String membercadid) {
        this.membercadid = membercadid;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
