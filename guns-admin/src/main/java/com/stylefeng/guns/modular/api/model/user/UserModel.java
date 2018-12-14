package com.stylefeng.guns.modular.api.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "登录用户model")
public class UserModel {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("登录名称")
    private String account;
    @ApiModelProperty("门店id")
    private Integer deptid;
    @ApiModelProperty("角色id")
    private String roleid;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("性别（1：男 2：女）")
    private Integer sex;
    @ApiModelProperty("状态(1：启用  2：冻结  3：删除）")
    private Integer status;
    @ApiModelProperty("门店名称")
    private String deptName;
    @ApiModelProperty("角色资源")
    private List<UserResouceModel> userResouceModels=new ArrayList<>();

    private Integer userId;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public List<UserResouceModel> getUserResouceModels() {
        return userResouceModels;
    }

    public void setUserResouceModels(List<UserResouceModel> userResouceModels) {
        this.userResouceModels = userResouceModels;
    }

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
