package com.stylefeng.guns.modular.api.apiparam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "请求体")
public class RequstData {
    @ApiModelProperty("操作人id")
    private Integer userId;
    @ApiModelProperty("操作人门店id")
    private Integer deptId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
