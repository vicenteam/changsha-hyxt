package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 增值服务
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-27
 */
@TableName("main_server_consumption")
public class MainServerConsumption extends Model<MainServerConsumption> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 服务名称
     */
    private String serverName;
    /**
     * 服务介绍
     */
    private String serverContent;
    private String createTime;
    private String updateTime;
    /**
     * 消耗积分
     */
    private String consumptionJiFen;
    private Integer status;

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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerContent() {
        return serverContent;
    }

    public void setServerContent(String serverContent) {
        this.serverContent = serverContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getConsumptionJiFen() {
        return consumptionJiFen;
    }

    public void setConsumptionJiFen(String consumptionJiFen) {
        this.consumptionJiFen = consumptionJiFen;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MainServerConsumption{" +
        "id=" + id +
        ", serverName=" + serverName +
        ", serverContent=" + serverContent +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", consumptionJiFen=" + consumptionJiFen +
        "}";
    }
}
