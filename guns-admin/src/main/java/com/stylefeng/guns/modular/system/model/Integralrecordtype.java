package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 积分类型
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-03
 */
@TableName("main_integralrecordtype")
public class Integralrecordtype extends Model<Integralrecordtype> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 积分类型
     */
    private String names;
    /**
     * 产品名称
     */
    private String productname;
    /**
     * 产品类别(0礼品类、1积分兑换类、2销售类、3积分+金额类)
     */
    private Integer producttype;
    /**
     * 产品规格
     */
    private String productspecification;
    /**
     * 产品数量
     */
    private Integer productnum;
    /**
     * 产品结余
     */
    private String productbalance;
    /**
     * 产品积分
     */
    private String productjifen;
    /**
     * 食用剂量(,号隔开)
     */
    private String producteatingdose;
    private String deptid;
    private String createtime;
    private String updatetime;
    private String createuserid;
    private String updateuserid;
    private Integer status;
    private Integer productPid;


    /**
     * 父级药品id
     */
    public Integer getProductPid() {
        return productPid;
    }

    public void setProductPid(Integer productPid) {
        this.productPid = productPid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getProducttype() {
        return producttype;
    }

    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
    }

    public String getProductspecification() {
        return productspecification;
    }

    public void setProductspecification(String productspecification) {
        this.productspecification = productspecification;
    }

    public Integer getProductnum() {
        return productnum;
    }

    public void setProductnum(Integer productnum) {
        this.productnum = productnum;
    }

    public String getProductbalance() {
        return productbalance;
    }

    public void setProductbalance(String productbalance) {
        this.productbalance = productbalance;
    }

    public String getProductjifen() {
        return productjifen;
    }

    public void setProductjifen(String productjifen) {
        this.productjifen = productjifen;
    }

    public String getProducteatingdose() {
        return producteatingdose;
    }

    public void setProducteatingdose(String producteatingdose) {
        this.producteatingdose = producteatingdose;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getUpdateuserid() {
        return updateuserid;
    }

    public void setUpdateuserid(String updateuserid) {
        this.updateuserid = updateuserid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Integralrecordtype{" +
        "id=" + id +
        ", names=" + names +
        ", productname=" + productname +
        ", producttype=" + producttype +
        ", productspecification=" + productspecification +
        ", productnum=" + productnum +
        ", productbalance=" + productbalance +
        ", productjifen=" + productjifen +
        ", producteatingdose=" + producteatingdose +
        ", deptid=" + deptid +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", createuserid=" + createuserid +
        ", updateuserid=" + updateuserid +
        "}";
    }
}
