package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品退换货表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-11
 */
@TableName("main_product_return_change")
public class ProductReturnChange extends Model<ProductReturnChange> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 退换货人姓名
     */
    private String memberName;
    /**
     * 退换货人id
     */
    private Integer memberId;
    private String createtime;
    private String deptId;
    private String createuserid;
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 退换货类型(0退货 1换货)
     */
    private Integer returnchangeType;
    /**
     * 退换货商品id
     */
    private Integer returnchangeproductId;
    /**
     * 退换货商品名称
     */
    private String returnchangeproductName;
    /**
     * 退换货数量
     */
    private Integer returnchangeNum;
    /**
     * 状态(0待处理 1已处理)
     */
    private Integer status;
    /**
     * 积分记录表id
     */
    private Integer integralrecodeId;
    /**
     * 商品库存id
     */
    private Integer inventoryManagementId;
    /**
     * 是否入库
     */
    private Integer isInsert;

    private String memberPhone;

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public Integer getIsInsert() {
        return isInsert;
    }

    public void setIsInsert(Integer isInsert) {
        this.isInsert = isInsert;
    }

    public Integer getInventoryManagementId() {
        return inventoryManagementId;
    }

    public void setInventoryManagementId(Integer inventoryManagementId) {
        this.inventoryManagementId = inventoryManagementId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getReturnchangeType() {
        return returnchangeType;
    }

    public void setReturnchangeType(Integer returnchangeType) {
        this.returnchangeType = returnchangeType;
    }

    public Integer getReturnchangeproductId() {
        return returnchangeproductId;
    }

    public void setReturnchangeproductId(Integer returnchangeproductId) {
        this.returnchangeproductId = returnchangeproductId;
    }

    public String getReturnchangeproductName() {
        return returnchangeproductName;
    }

    public void setReturnchangeproductName(String returnchangeproductName) {
        this.returnchangeproductName = returnchangeproductName;
    }

    public Integer getReturnchangeNum() {
        return returnchangeNum;
    }

    public void setReturnchangeNum(Integer returnchangeNum) {
        this.returnchangeNum = returnchangeNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIntegralrecodeId() {
        return integralrecodeId;
    }

    public void setIntegralrecodeId(Integer integralrecodeId) {
        this.integralrecodeId = integralrecodeId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProductReturnChange{" +
        "id=" + id +
        ", memberName=" + memberName +
        ", memberId=" + memberId +
        ", createtime=" + createtime +
        ", deptId=" + deptId +
        ", createuserid=" + createuserid +
        ", productId=" + productId +
        ", productName=" + productName +
        ", returnchangeType=" + returnchangeType +
        ", returnchangeproductId=" + returnchangeproductId +
        ", returnchangeproductName=" + returnchangeproductName +
        ", returnchangeNum=" + returnchangeNum +
        ", status=" + status +
        ", integralrecodeId=" + integralrecodeId +
        "}";
    }
}
