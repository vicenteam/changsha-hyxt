package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品库存管理
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-10
 */
@TableName("main_inventory_management")
public class InventoryManagement extends Model<InventoryManagement> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String createtime;
    private String createuserid;
    private String deptid;
    private Integer integralrecordtypeid;
    /**
     * 记录状态(0添加库存 1消耗库存)
     */
    private String status;
    /**
     * 消耗库存人id(meimberid)
     */
    private String memberid;
    /**
     * 消耗数量
     */
    private Integer consumptionNum;
    private String name;
    private String memberName;
    private Integer integralid;
    private Integer clearid;
    private String memberPhone;

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public Integer getIntegralrecordtypeid() {
        return integralrecordtypeid;
    }

    public void setIntegralrecordtypeid(Integer integralrecordtypeid) {
        this.integralrecordtypeid = integralrecordtypeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Integer getConsumptionNum() {
        return consumptionNum;
    }

    public void setConsumptionNum(Integer consumptionNum) {
        this.consumptionNum = consumptionNum;
    }

    public Integer getIntegralid() {
        return integralid;
    }

    public void setIntegralid(Integer integralid) {
        this.integralid = integralid;
    }

    public Integer getClearid() {
        return clearid;
    }

    public void setClearid(Integer clearid) {
        this.clearid = clearid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "InventoryManagement{" +
        "id=" + id +
        ", createtime=" + createtime +
        ", createuserid=" + createuserid +
        ", deptid=" + deptid +
        ", integralrecordtypeid=" + integralrecordtypeid +
        ", status=" + status +
        ", memberid=" + memberid +
        ", consumptionNum=" + consumptionNum +
        "}";
    }
}
