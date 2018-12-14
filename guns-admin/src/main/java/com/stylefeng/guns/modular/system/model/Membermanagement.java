package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 会员基础信息表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-10
 */
@TableName("main_membermanagement")
public class Membermanagement extends Model<Membermanagement> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 编号id
     */
    private String cadID;
    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;
    /**
     * 电话
     */
    private String telphone;
    /**
     * 性别(1男 2女)
     */
    private Integer sex;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 状态(0可用 1不可用)
     */
    private Integer state;
    private Double integral;
    /**
     * 会员等级id
     */
    private String levelID;
    /**
     * 会员卡信息
     */
    private String cardID;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 是否老年协会会员 (1是2否)
     */
    private Integer isoldsociety;
    /**
     * 生日
     */
    private String birthday;
    private String deptName;
    /**
     * 介绍人id
     */
    private String introducerId;
    private String province;
    private String city;
    /**
     * 地址
     */
    private String district;
    /**
     * 病史
     */
    private String medicalHistory;
    /**
     * 家庭状态id
     */
    private String familyStatusID;
    /**
     * 服务员工id
     */
    private String staffID;
    private String countyID;
    private String townshipid;
    /**
     * 健康状态
     */
    private String healthStatus;
    /**
     * 最新签到时间1
     */
    private String CheckINTime1;
    /**
     * 最新签到时间2
     */
    private String CheckINTime2;
    private String recommendMember;
    private String address;
    /**
     * 总消费金额
     */
    private Double countPrice;
    private String relation;
    /**
     * 用户图片名称
     */
    private String avatar;
    /**
     * 所属门店id
     */
    private String deptId;
    private String token;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
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

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getFamilyStatusID() {
        return familyStatusID;
    }

    public void setFamilyStatusID(String familyStatusID) {
        this.familyStatusID = familyStatusID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCountyID() {
        return countyID;
    }

    public void setCountyID(String countyID) {
        this.countyID = countyID;
    }

    public String getTownshipid() {
        return townshipid;
    }

    public void setTownshipid(String townshipid) {
        this.townshipid = townshipid;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getCheckINTime1() {
        return CheckINTime1;
    }

    public void setCheckINTime1(String CheckINTime1) {
        this.CheckINTime1 = CheckINTime1;
    }

    public String getCheckINTime2() {
        return CheckINTime2;
    }

    public void setCheckINTime2(String CheckINTime2) {
        this.CheckINTime2 = CheckINTime2;
    }

    public String getRecommendMember() {
        return recommendMember;
    }

    public void setRecommendMember(String recommendMember) {
        this.recommendMember = recommendMember;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Membermanagement{" +
        "id=" + id +
        ", cadID=" + cadID +
        ", name=" + name +
        ", telphone=" + telphone +
        ", sex=" + sex +
        ", email=" + email +
        ", phone=" + phone +
        ", state=" + state +
        ", integral=" + integral +
        ", levelID=" + levelID +
        ", cardID=" + cardID +
        ", createTime=" + createTime +
        ", isoldsociety=" + isoldsociety +
        ", birthday=" + birthday +
        ", deptName=" + deptName +
        ", introducerId=" + introducerId +
        ", province=" + province +
        ", city=" + city +
        ", district=" + district +
        ", medicalHistory=" + medicalHistory +
        ", familyStatusID=" + familyStatusID +
        ", staffID=" + staffID +
        ", countyID=" + countyID +
        ", townshipid=" + townshipid +
        ", healthStatus=" + healthStatus +
        ", CheckINTime1=" + CheckINTime1 +
        ", CheckINTime2=" + CheckINTime2 +
        ", recommendMember=" + recommendMember +
        ", address=" + address +
        ", countPrice=" + countPrice +
        ", deptId=" + deptId +
        ", token=" + token +
        "}";
    }
}
