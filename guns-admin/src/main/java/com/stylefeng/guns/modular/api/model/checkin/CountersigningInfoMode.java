package com.stylefeng.guns.modular.api.model.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "复签信息model")
public class CountersigningInfoMode {
    @ApiModelProperty("签到场次id")
    private Integer id;
    @ApiModelProperty("签到场次名称")
    private Integer screenings;
    /**
     * 签到人数
     */
    @ApiModelProperty("当前签到人数")
    private Integer memberCount;
    /**
     * 新卡签到数
     */
    @ApiModelProperty("新卡签到数")
    private Integer newCount;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startDate;
    /**
     * 签到场次状态；1：签到中   2：结束签到
     */
    @ApiModelProperty("签到场次状态；1：签到中   2：结束签到")
    private Integer status;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("创建时间")
    private String createDate;
    /**
     * 门店ID
     */
    @ApiModelProperty("门店ID")
    private String deptId;

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
}
