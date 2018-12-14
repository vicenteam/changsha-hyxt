package com.stylefeng.guns.modular.api.model.guashibuka;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="补卡挂失分页查询参数model")
public class FindPageParModel {
    @ApiModelProperty("查找类型（0普通搜索 1读卡搜索）")
    private Integer searchType;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别（1男 2女）")
    private Integer sex;
    @ApiModelProperty("家庭状况 （1有子女 2无子女）")
    private Integer familyStatusID;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("身份证")
    private String cadID;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("操作人id")
    private Integer staffId;
    @ApiModelProperty("省id")
    private Integer provinceId;
    @ApiModelProperty("市id")
    private Integer cityId;
    @ApiModelProperty("区id")
    private Integer districtId;
    @ApiModelProperty("卡状态（0可用 1不可用）")
    private Integer cardStatus;
    @ApiModelProperty("卡片信息")
    private String cardCode;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getFamilyStatusID() {
        return familyStatusID;
    }

    public void setFamilyStatusID(Integer familyStatusID) {
        this.familyStatusID = familyStatusID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }



    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }
}
