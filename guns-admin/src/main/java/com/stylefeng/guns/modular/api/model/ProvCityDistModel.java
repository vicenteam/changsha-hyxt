package com.stylefeng.guns.modular.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="省市区")
public class ProvCityDistModel {

    private Integer id;
    @ApiModelProperty("地名")
    private String name;
//    @ApiModelProperty("0 省 1市区 2县")
//    private String type;
//    @ApiModelProperty("市")
//    private String city;
//    @ApiModelProperty("省id")
//    private Integer provinceId;
//    @ApiModelProperty("省")
//    private String district;
//    @ApiModelProperty("市id")
//    private Integer cityId;


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
}
