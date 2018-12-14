package com.stylefeng.guns.modular.api.model.guashibuka;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="补卡挂失分页查询返回值model")
public class FindPageDataModel {
    @ApiModelProperty("id")
    private Integer findPageDataModelId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("当前可用积分")
    private Double integral;
    @ApiModelProperty("会员等级")
    private String levelID;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("是否老年协会")
    private String isoldsociety;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("总积分")
    private Double countPrice;
    @ApiModelProperty("状态 （=0可进行挂失操作，=1可进行解除挂失与补卡操作）")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFindPageDataModelId() {
        return findPageDataModelId;
    }

    public void setFindPageDataModelId(Integer findPageDataModelId) {
        this.findPageDataModelId = findPageDataModelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsoldsociety() {
        return isoldsociety;
    }

    public void setIsoldsociety(String isoldsociety) {
        this.isoldsociety = isoldsociety;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(Double countPrice) {
        this.countPrice = countPrice;
    }
}
