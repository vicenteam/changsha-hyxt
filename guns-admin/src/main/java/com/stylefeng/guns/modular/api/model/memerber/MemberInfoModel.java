package com.stylefeng.guns.modular.api.model.memerber;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员信息动态")
public class MemberInfoModel {

    private Integer id;
    @ApiModelProperty("img")
    private String avatar;
    @ApiModelProperty("会员名称")
    private String name;
    @ApiModelProperty("身份证")
    private String cadID;
    @ApiModelProperty("性别")
    private Integer sex;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("会员卡号")
    private String cardId;
    @ApiModelProperty("会员等级")
    private String levelId;
    @ApiModelProperty("开卡日期")
    private String createDt;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("服务员工")
    private String servicePerson;
    @ApiModelProperty("介绍人")
    private String introducer;
    @ApiModelProperty("签到次数")
    private Integer signInCount;
    @ApiModelProperty("最近签到时间")
    private String signInNew;
    @ApiModelProperty("复签次数")
    private Integer signOutCount;
    @ApiModelProperty("最近复签时间")
    private String signOutNew;
    @ApiModelProperty("活动次数")
    private Integer activityNumber;
    @ApiModelProperty("会员状态 (0可用 1不可用)")
    private Integer memberStatus;
    @ApiModelProperty("总获得积分")
    private Double countPrice;
    @ApiModelProperty("当前可用积分呢")
    private Double integral;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getServicePerson() {
        return servicePerson;
    }

    public void setServicePerson(String servicePerson) {
        this.servicePerson = servicePerson;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public Integer getSignInCount() {
        return signInCount;
    }

    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }

    public String getSignInNew() {
        return signInNew;
    }

    public void setSignInNew(String signInNew) {
        this.signInNew = signInNew;
    }

    public Integer getSignOutCount() {
        return signOutCount;
    }

    public void setSignOutCount(Integer signOutCount) {
        this.signOutCount = signOutCount;
    }

    public String getSignOutNew() {
        return signOutNew;
    }

    public void setSignOutNew(String signOutNew) {
        this.signOutNew = signOutNew;
    }

    public Integer getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(Integer activityNumber) {
        this.activityNumber = activityNumber;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }
}
