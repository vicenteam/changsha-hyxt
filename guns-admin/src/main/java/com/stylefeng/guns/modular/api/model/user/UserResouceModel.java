package com.stylefeng.guns.modular.api.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户角色资源model")
public class UserResouceModel {
    @ApiModelProperty("资源名称")
    private String resouceName;
    @ApiModelProperty("资源id")
    private String resouceId;
    @ApiModelProperty("资源是否可用")
    private boolean security=false;

    public String getResouceName() {
        return resouceName;
    }

    public void setResouceName(String resouceName) {
        this.resouceName = resouceName;
    }

    public String getResouceId() {
        return resouceId;
    }

    public void setResouceId(String resouceId) {
        this.resouceId = resouceId;
    }

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }
}
