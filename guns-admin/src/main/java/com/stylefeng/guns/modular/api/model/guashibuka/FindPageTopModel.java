package com.stylefeng.guns.modular.api.model.guashibuka;

import com.stylefeng.guns.modular.api.model.jifen.JifenPageDataMode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value ="分页返回对象model")
public class FindPageTopModel {
    @ApiModelProperty("子节点")
    private List<FindPageDataModel> rows=new ArrayList<>();
    @ApiModelProperty("数据总条数")
    private Integer total;

    public List<FindPageDataModel> getRows() {
        return rows;
    }

    public void setRows(List<FindPageDataModel> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
