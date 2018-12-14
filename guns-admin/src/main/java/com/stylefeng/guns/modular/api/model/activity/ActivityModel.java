package com.stylefeng.guns.modular.api.model.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动信息model")
public class ActivityModel {
    @ApiModelProperty("活动id")
    private Integer id;
    /**
     * 活动名称
     */
    @ApiModelProperty("活动名称")
    private String name;
    /**
     * 活动内容
     */
    @ApiModelProperty("活动内容")
    private String content;
    /**
     * 活动创建者
     */
    @ApiModelProperty("活动创建者")
    private String creater;
    /**
     * 活动规则(0累计签到 1连续签到 2积分兑换 3推荐奖励 4被推荐奖励)
     */
    @ApiModelProperty("活动规则(0累计签到 1连续签到 2积分兑换 3推荐奖励 4被推荐奖励)")
    private Integer ruleexpression;
    /**
     * 活到开始时间
     */
    @ApiModelProperty("活到开始时间")
    private String begindate;
    /**
     * 活动结束时间
     */
    @ApiModelProperty("活动结束时间")
    private String enddate;
    /**
     * 活动状态(0表示有效，1表示无效)
     */
    @ApiModelProperty("活动状态(0表示有效，1表示无效)")
    private Integer status;
    /**
     * 最大领取次数
     */
    @ApiModelProperty("最大领取次数")
    private Integer maxgetnum;

    /**
     * 签到次数
     */
    @ApiModelProperty("签到次数")
    private Integer qiandaonum;

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

    public Integer getQiandaonum() {
        return qiandaonum;
    }

    public void setQiandaonum(Integer qiandaonum) {
        this.qiandaonum = qiandaonum;
    }
}
