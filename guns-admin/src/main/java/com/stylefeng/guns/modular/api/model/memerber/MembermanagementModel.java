package com.stylefeng.guns.modular.api.model.memerber;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "新增会员基本信息")
public class MembermanagementModel {

//    private Integer id;
    @ApiModelProperty("身份证id")
    private String cadID;
    @ApiModelProperty("推荐人id")
    private String introducerId;
    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别（1.男，2.女）")
    private Integer sex;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String telphone;

    @ApiModelProperty("关联会员id")
    private String otherMemberId;
    @ApiModelProperty("是否老年协会会员 (1是2否)")
    private Integer isoldsociety;
    @ApiModelProperty("生日")
    private String birthday;
    @ApiModelProperty("省")
    private String province;
    @ApiModelProperty("市")
    private String city;
    @ApiModelProperty("区")
    private String district;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("家庭状态id（1.有子女,2.无子女）")
    private String familyStatusID;
    @ApiModelProperty("健康信息")
    private String baMedicals;
    @ApiModelProperty("新开卡code")
    private String code;

    private String cardCode;


    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public String getIntroducerId() {
        return introducerId;
    }

    public void setIntroducerId(String introducerId) {
        this.introducerId = introducerId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getOtherMemberId() {
        return otherMemberId;
    }

    public void setOtherMemberId(String otherMemberId) {
        this.otherMemberId = otherMemberId;
    }

    public Integer getIsoldsociety() {
        return isoldsociety;
    }

    public void setIsoldsociety(Integer isoldsociety) {
        this.isoldsociety = isoldsociety;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFamilyStatusID() {
        return familyStatusID;
    }

    public void setFamilyStatusID(String familyStatusID) {
        this.familyStatusID = familyStatusID;
    }

    public String getBaMedicals() {
        return baMedicals;
    }

    public void setBaMedicals(String baMedicals) {
        this.baMedicals = baMedicals;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
}
