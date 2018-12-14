package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 签到场次记录表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
@TableName("main_checkin")
public class Checkin extends Model<Checkin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer screenings;
    /**
     * 签到人数
     */
    private Integer memberCount;
    /**
     * 新卡签到数
     */
    private Integer newCount;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 签到场次状态；1：签到中   2：结束签到
     */
    private Integer status;
    /**
     * 结束时间
     */
    private String endDate;
    private String createDate;
    /**
     * 门店ID
     */
    private String deptId;
    private Integer isActive;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScreenings() {
        return screenings;
    }

    public void setScreenings(Integer screenings) {
        this.screenings = screenings;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Checkin{" +
        "id=" + id +
        ", screenings=" + screenings +
        ", memberCount=" + memberCount +
        ", newCount=" + newCount +
        ", startDate=" + startDate +
        ", status=" + status +
        ", endDate=" + endDate +
        ", createDate=" + createDate +
        ", deptId=" + deptId +
        ", isActive=" + isActive +
        "}";
    }
}
