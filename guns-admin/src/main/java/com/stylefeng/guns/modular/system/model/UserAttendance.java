package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户考勤信息表
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-07
 */
@TableName("main_user_attendance")
public class UserAttendance extends Model<UserAttendance> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * userId
     */
    private Integer userId;
    /**
     * 签到年月
     */
    private String checkYearMonth;
    /**
     * 当天考勤时间1
     */
    private String checkTime1;
    /**
     * 当天考勤时间2
     */
    private String checkTime2;
    /**
     * deptId
     */
    private Integer deptId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCheckYearMonth() {
        return checkYearMonth;
    }

    public void setCheckYearMonth(String checkYearMonth) {
        this.checkYearMonth = checkYearMonth;
    }

    public String getCheckTime1() {
        return checkTime1;
    }

    public void setCheckTime1(String checkTime1) {
        this.checkTime1 = checkTime1;
    }

    public String getCheckTime2() {
        return checkTime2;
    }

    public void setCheckTime2(String checkTime2) {
        this.checkTime2 = checkTime2;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserAttendance{" +
        "id=" + id +
        ", userId=" + userId +
        ", checkYearMonth=" + checkYearMonth +
        ", checkTime1=" + checkTime1 +
        ", checkTime2=" + checkTime2 +
        ", deptId=" + deptId +
        "}";
    }
}
