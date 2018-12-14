package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 活动列表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-16
 */
@TableName("main_activity")
public class Activity extends Model<Activity> {

    private static final long serialVersionUID = 1L;

    /**
     * 活动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动内容
     */
    private String content;
    /**
     * 活动创建者
     */
    private String creater;
    /**
     * 所属部门
     */
    private String deptid;
    /**
     * 活动规则(0累计签到 1连续签到 2积分兑换)
     */
    private Integer ruleexpression;
    /**
     * 活到开始时间
     */
    private String begindate;
    /**
     * 活动结束时间
     */
    private String enddate;
    private String createtime;
    /**
     * 活动状态(0表示有效，1表示无效)
     */
    private Integer status;
    /**
     * 最大领取次数
     */
    private Integer maxgetnum;

    /**
     * 签到次数
     */
    private Integer qiandaonum;
    /**
     * 消耗积分
     */
    private Double jifen;

    public Double getJifen() {
        return jifen;
    }

    public void setJifen(Double jifen) {
        this.jifen = jifen;
    }

    public Integer getQiandaonum() {
        return qiandaonum;
    }

    public void setQiandaonum(Integer qiandaonum) {
        this.qiandaonum = qiandaonum;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public Integer getRuleexpression() {
        return ruleexpression;
    }

    public void setRuleexpression(Integer ruleexpression) {
        this.ruleexpression = ruleexpression;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMaxgetnum() {
        return maxgetnum;
    }

    public void setMaxgetnum(Integer maxgetnum) {
        this.maxgetnum = maxgetnum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Activity{" +
        "id=" + id +
        ", name=" + name +
        ", content=" + content +
        ", creater=" + creater +
        ", deptid=" + deptid +
        ", ruleexpression=" + ruleexpression +
        ", begindate=" + begindate +
        ", enddate=" + enddate +
        ", createtime=" + createtime +
        ", status=" + status +
        ", maxgetnum=" + maxgetnum +
        "}";
    }
}
