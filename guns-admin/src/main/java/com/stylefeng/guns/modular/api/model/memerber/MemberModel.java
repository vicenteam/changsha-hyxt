package com.stylefeng.guns.modular.api.model.memerber;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员信息model")
public class MemberModel {

    @ApiModelProperty("当前会员id")
    private Integer memberId;
    /**
     * 编号id
     */
    @ApiModelProperty("身份证")
    private String cadID;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String telphone;
    /**
     * 性别(1男 2女)
     */
    @ApiModelProperty("性别(1男 2女)")
    private Integer sex;
    /**
     * 邮箱地址
     */
    @ApiModelProperty("邮箱地址")
    private String email;
    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String phone;
    /**
     * 状态(0可用 1不可用)
     */
    @ApiModelProperty("状态(0可用 1不可用)")
    private Integer state;
    @ApiModelProperty("当前可用积分呢")
    private Double integral;
    /**
     * 会员等级id
     */
    @ApiModelProperty("会员等级id")
    private String levelId;
    /**
     * 会员卡信息
     */
    @ApiModelProperty("会员卡信息")
    private String cardID;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 是否老年协会会员 (1是2否)
     */
    @ApiModelProperty("是否老年协会会员 (1是2否)")
    private Integer isoldsociety;
    /**
     * 生日
     */
    @ApiModelProperty("生日")
    private String birthday;
    @ApiModelProperty("门店名称")
    private String deptName;
    /**
     * 介绍人id
     */
    @ApiModelProperty("介绍人id")
    private String introducerId;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String district;




    /**
     * 最新签到时间1
     */
    @ApiModelProperty("最新签到时间1")
    private String CheckINTime1;
    /**
     * 最新签到时间2
     */
    @ApiModelProperty("最新签到时间2")
    private String CheckINTime2;
    /**
     * 总消费金额
     */
    @ApiModelProperty("总获得积分")
    private Double countPrice;
    /**
     * 用户图片名称
     */
    @ApiModelProperty("用户图片名称")
    private String avatar;
    /**
     * 所属门店id
     */
    @ApiModelProperty("所属门店id")
    private String deptId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIntroducerId() {
        return introducerId;
    }

    public void setIntroducerId(String introducerId) {
        this.introducerId = introducerId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCheckINTime1() {
        return CheckINTime1;
    }

    public void setCheckINTime1(String checkINTime1) {
        CheckINTime1 = checkINTime1;
    }

    public String getCheckINTime2() {
        return CheckINTime2;
    }

    public void setCheckINTime2(String checkINTime2) {
        CheckINTime2 = checkINTime2;
    }

    public Double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(Double countPrice) {
        this.countPrice = countPrice;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
