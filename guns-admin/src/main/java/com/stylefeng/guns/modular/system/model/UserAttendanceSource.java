package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 人脸识别打卡用户数据
 * </p>
 *
 * @author stylefeng
 * @since 2019-04-08
 */
@TableName("main_user_attendance_source")
public class UserAttendanceSource extends Model<UserAttendanceSource> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 考勤人员名称
     */
    private String name;
    /**
     * 头像名称
     */
    private String img;
    /**
     * 性别
     */
    private String sex;
    /**
     * 电话
     */
    private String phone;
    private Integer deptId;
    /**
     * 创建时间
     */
    private String createdt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getCreatedt() {
        return createdt;
    }

    public void setCreatedt(String createdt) {
        this.createdt = createdt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserAttendanceSource{" +
        "id=" + id +
        ", name=" + name +
        ", img=" + img +
        ", sex=" + sex +
        ", phone=" + phone +
        ", deptId=" + deptId +
        ", createdt=" + createdt +
        "}";
    }
}
