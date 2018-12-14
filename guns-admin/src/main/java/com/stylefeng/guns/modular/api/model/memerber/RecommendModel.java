package com.stylefeng.guns.modular.api.model.memerber;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "推荐人查询 model")
public class RecommendModel {

    @ApiModelProperty("推荐人名称")
    private String name;
    @ApiModelProperty("身份证")
    private String cadID;
    @ApiModelProperty("被推荐新信息")
    private List<MemberInfoModel> mInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCadID() {
        return cadID;
    }

    public void setCadID(String cadID) {
        this.cadID = cadID;
    }

    public List<MemberInfoModel> getmInfos() {
        return mInfos;
    }

    public void setmInfos(List<MemberInfoModel> mInfos) {
        this.mInfos = mInfos;
    }
}
