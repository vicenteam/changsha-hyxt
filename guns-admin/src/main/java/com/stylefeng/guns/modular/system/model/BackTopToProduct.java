package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 退回商品信息记录表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-26
 */
@TableName("main_back_top_to_product")
public class BackTopToProduct extends Model<BackTopToProduct> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 退回时间
     */
    private String backTopToCreateTime;
    /**
     * 退回数量
     */
    private Integer backTopToNum;
    /**
     * 退回商品id
     */
    private Integer backTopToProductId;
    /**
     * 退回自己商品id
     */
    private Integer backTopToMyProductId;
    private String createUserId;
    private Integer deptId;
    /**
     * 退回商品名称
     */
    private String backTopToProductName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackTopToCreateTime() {
        return backTopToCreateTime;
    }

    public void setBackTopToCreateTime(String backTopToCreateTime) {
        this.backTopToCreateTime = backTopToCreateTime;
    }

    public Integer getBackTopToNum() {
        return backTopToNum;
    }

    public void setBackTopToNum(Integer backTopToNum) {
        this.backTopToNum = backTopToNum;
    }

    public Integer getBackTopToProductId() {
        return backTopToProductId;
    }

    public void setBackTopToProductId(Integer backTopToProductId) {
        this.backTopToProductId = backTopToProductId;
    }

    public Integer getBackTopToMyProductId() {
        return backTopToMyProductId;
    }

    public void setBackTopToMyProductId(Integer backTopToMyProductId) {
        this.backTopToMyProductId = backTopToMyProductId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getBackTopToProductName() {
        return backTopToProductName;
    }

    public void setBackTopToProductName(String backTopToProductName) {
        this.backTopToProductName = backTopToProductName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BackTopToProduct{" +
        "id=" + id +
        ", backTopToCreateTime=" + backTopToCreateTime +
        ", backTopToNum=" + backTopToNum +
        ", backTopToProductId=" + backTopToProductId +
        ", backTopToMyProductId=" + backTopToMyProductId +
        ", createUserId=" + createUserId +
        ", deptId=" + deptId +
        ", backTopToProductName=" + backTopToProductName +
        "}";
    }
}
