package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 积分记录
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
@TableName("main_integralrecord")
public class Integralrecord extends Model<Integralrecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 积分记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 获得积分的值
     */
    private Double integral;
    /**
     * 附加参数，如果是本人或新人购物获得积分，则该列的值是该购物记录的ID，如果是带新人或新人签到获得积分，则是所带新人的ID
     */
    private String target;
    /**
     * 商品积分（0礼品类、1积分兑换类、2销售类、3积分+金额类）
     */
    private String typeId;
    /**
     * 行为积分(0.签到积分,1.推荐人积分，2.活动兑换积分)
     */
    private String otherTypeId;
    /**
     * 积分类型（1.商品积分，2.行为积分）
     */
    private String integralType;
    /**
     * 会员id
     */
    private Integer memberid;
    /**
     * 创建时间
     */
    private String createTime;

    private Integer deptid;
    private Integer clearid;
    private Integer staffid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getClearid() {
        return clearid;
    }

    public void setClearid(Integer clearid) {
        this.clearid = clearid;
    }

    public Integer getStaffid() {
        return staffid;
    }

    public void setStaffid(Integer staffid) {
        this.staffid = staffid;
    }

    public String getOtherTypeId() {
        return otherTypeId;
    }

    public void setOtherTypeId(String otherTypeId) {
        this.otherTypeId = otherTypeId;
    }

    public String getIntegralType() {
        return integralType;
    }

    public void setIntegralType(String integralType) {
        this.integralType = integralType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Integralrecord{" +
        "id=" + id +
        ", integral=" + integral +
        ", target=" + target +
        ", typeId=" + typeId +
        ", memberid=" + memberid +
        ", createTime=" + createTime +
        ", deptid=" + deptid +
        ", clearid=" + clearid +
        ", staffid=" + staffid +
        "}";
    }
}
