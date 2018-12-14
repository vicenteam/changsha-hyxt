package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 会员等级配置表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@TableName("main_membershipcardtype")
public class Membershipcardtype extends Model<Membershipcardtype> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员等级名称
     */
    private String cardname;
    /**
     * 签到积分
     */
    private Double signin;
    /**
     * 购物积分
     */
    private Double shopping;
    /**
     * 带新人奖励积分
     */
    private Double newpoints;
    /**
     * 新人签到积分
     */
    private Double signinnew;
    /**
     * 新人购物积分
     */
    private Double shoppingnew;
    /**
     * 升级金额
     */
    private Double upamount;
    /**
     * 部门id
     */
    private String deptid;
    /**
     * 备注
     */
    private String tips;
    /**
     * 状态(0.启用,1.冻结)
     */
    private Integer status;
    private String createdt;
    private String updatedt;
    private Integer checkleavenum;
    private Integer leaves;

    public Integer getLeaves() {
        return leaves;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
    }

    public Integer getCheckleavenum() {
        return checkleavenum;
    }

    public void setCheckleavenum(Integer checkleavenum) {
        this.checkleavenum = checkleavenum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public Double getSignin() {
        return signin;
    }

    public void setSignin(Double signin) {
        this.signin = signin;
    }

    public Double getShopping() {
        return shopping;
    }

    public void setShopping(Double shopping) {
        this.shopping = shopping;
    }

    public Double getNewpoints() {
        return newpoints;
    }

    public void setNewpoints(Double newpoints) {
        this.newpoints = newpoints;
    }

    public Double getSigninnew() {
        return signinnew;
    }

    public void setSigninnew(Double signinnew) {
        this.signinnew = signinnew;
    }

    public Double getShoppingnew() {
        return shoppingnew;
    }

    public void setShoppingnew(Double shoppingnew) {
        this.shoppingnew = shoppingnew;
    }

    public Double getUpamount() {
        return upamount;
    }

    public void setUpamount(Double upamount) {
        this.upamount = upamount;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedt() {
        return createdt;
    }

    public void setCreatedt(String createdt) {
        this.createdt = createdt;
    }

    public String getUpdatedt() {
        return updatedt;
    }

    public void setUpdatedt(String updatedt) {
        this.updatedt = updatedt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Membershipcardtype{" +
        "id=" + id +
        ", cardname=" + cardname +
        ", signin=" + signin +
        ", shopping=" + shopping +
        ", newpoints=" + newpoints +
        ", signinnew=" + signinnew +
        ", shoppingnew=" + shoppingnew +
        ", upamount=" + upamount +
        ", deptid=" + deptid +
        ", tips=" + tips +
        ", status=" + status +
        ", createdt=" + createdt +
        ", updatedt=" + updatedt +
        "}";
    }
}
