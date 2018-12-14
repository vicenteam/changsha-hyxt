package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 省市区
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-09
 */
@TableName("sys_prov_city_dist")
public class ProvCityDist extends Model<ProvCityDist> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String province;
    /**
     * 0 省 1市区 2县
     */
    private String type;
    private String city;
    @TableField("province_id")
    private Integer provinceId;
    private String district;
    @TableField("city_id")
    private Integer cityId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProvCityDist{" +
        "id=" + id +
        ", province=" + province +
        ", type=" + type +
        ", city=" + city +
        ", provinceId=" + provinceId +
        ", district=" + district +
        ", cityId=" + cityId +
        "}";
    }
}
