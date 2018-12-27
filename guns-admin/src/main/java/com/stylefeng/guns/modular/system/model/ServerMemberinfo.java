package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 会员体验服务记录表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-27
 */
@TableName("main_server_memberInfo")
public class ServerMemberinfo extends Model<ServerMemberinfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String memberName;
    private Integer memberId;
    private String serverName;
    private Integer serverId;
    private String createTime;
    private Integer deptid;
    private Double jifen;


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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Double getJifen() {
        return jifen;
    }

    public void setJifen(Double jifen) {
        this.jifen = jifen;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServerMemberinfo{" +
        "id=" + id +
        ", memberName=" + memberName +
        ", memberId=" + memberId +
        ", serverName=" + serverName +
        ", serverId=" + serverId +
        ", createTime=" + createTime +
        ", deptid=" + deptid +
        ", jifen=" + jifen +
        "}";
    }
}
