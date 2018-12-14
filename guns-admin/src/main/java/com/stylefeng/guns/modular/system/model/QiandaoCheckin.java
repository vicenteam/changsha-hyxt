package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 复签记录表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
@TableName("main_qiandao_checkin")
public class QiandaoCheckin extends Model<QiandaoCheckin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员id
     */
    private Integer memberid;
    /**
     * 签到时间
     */
    private String createtime;
    /**
     * 复签时间
     */
    private String updatetime;
    /**
     * 签到场次id
     */
    private Integer checkinid;
    /**
     * 门店id
     */
    private Integer deptid;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
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

    public Integer getCheckinid() {
        return checkinid;
    }

    public void setCheckinid(Integer checkinid) {
        this.checkinid = checkinid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "QiandaoCheckin{" +
        "id=" + id +
        ", memberid=" + memberid +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", checkinid=" + checkinid +
        ", deptid=" + deptid +
        ", status=" + status +
        "}";
    }
}
