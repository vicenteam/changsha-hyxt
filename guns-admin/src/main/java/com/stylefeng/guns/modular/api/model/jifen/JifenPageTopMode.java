package com.stylefeng.guns.modular.api.model.jifen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value ="Pagemodel")
public class JifenPageTopMode {
    @ApiModelProperty("子节点")
    private List<JifenPageDataMode> rows=new ArrayList<>();
    @ApiModelProperty("数据总条数")
    private Integer total;

    public List<JifenPageDataMode> getRows() {
        return rows;
    }

    public void setRows(List<JifenPageDataMode> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
