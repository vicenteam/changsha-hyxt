package com.stylefeng.guns.modular.api.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "分页model")
public class PageModel<T> {
    @ApiModelProperty("数据总条数")
    private Long total;
    @ApiModelProperty("当前页码数据集合")
    private List<T> dataList;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
