package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.ProvCityDist;
import com.stylefeng.guns.modular.main.service.IProvCityDistService;

import java.util.List;

/**
 * 省市区县控制器
 *
 * @author fengshuonan
 * @Date 2018-08-09 17:02:09
 */
@Controller
@RequestMapping("/provCityDist")
public class ProvCityDistController extends BaseController {

    private String PREFIX = "/main/provCityDist/";

    @Autowired
    private IProvCityDistService provCityDistService;

    /**
     * 跳转到省市区县首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "provCityDist.html";
    }

    /**
     * 跳转到添加省市区县
     */
    @RequestMapping("/provCityDist_add")
    public String provCityDistAdd() {
        return PREFIX + "provCityDist_add.html";
    }

    /**
     * 跳转到修改省市区县
     */
    @RequestMapping("/provCityDist_update/{provCityDistId}")
    public String provCityDistUpdate(@PathVariable Integer provCityDistId, Model model) {
        ProvCityDist provCityDist = provCityDistService.selectById(provCityDistId);
        model.addAttribute("item",provCityDist);
        LogObjectHolder.me().set(provCityDist);
        return PREFIX + "provCityDist_edit.html";
    }

    /**
     * 获取省市区县列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        return provCityDistService.selectList(null);
    }

    /**
     * 新增省市区县
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProvCityDist provCityDist) {
        provCityDistService.insert(provCityDist);
        return SUCCESS_TIP;
    }

    /**
     * 删除省市区县
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer provCityDistId) {
        provCityDistService.deleteById(provCityDistId);
        return SUCCESS_TIP;
    }

    /**
     * 修改省市区县
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProvCityDist provCityDist) {
        provCityDistService.updateById(provCityDist);
        return SUCCESS_TIP;
    }

    /**
     * 省市区县详情
     */
    @RequestMapping(value = "/detail/{provCityDistId}")
    @ResponseBody
    public Object detail(@PathVariable("provCityDistId") Integer provCityDistId) {
        return provCityDistService.selectById(provCityDistId);
    }

    /**
     * 获取省列表  1
     * @param condition
     * @return
     */
    @RequestMapping(value = "/province")
    @ResponseBody
    public Object province(String condition) {
        EntityWrapper<ProvCityDist> provCityDistEntityWrapper = new EntityWrapper<>();
        provCityDistEntityWrapper.eq("type",0);
        List<ProvCityDist> provCityDists = provCityDistService.selectList(provCityDistEntityWrapper);
        return provCityDists;
    }

    /**
     * 获取区域列表  2
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "/city")
    @ResponseBody
    public Object city(String provinceId) {
        EntityWrapper<ProvCityDist> provCityDistEntityWrapper = new EntityWrapper<>();
        provCityDistEntityWrapper.eq("type",1);
        provCityDistEntityWrapper.eq("province_id",provinceId);
        List<ProvCityDist> provCityDists = provCityDistService.selectList(provCityDistEntityWrapper);
        return provCityDists;
    }

    /**
     * 获取区列表  3
     * @param cityId
     * @return
     */
    @RequestMapping(value = "/district")
    @ResponseBody
    public Object district(String cityId) {
        EntityWrapper<ProvCityDist> provCityDistEntityWrapper = new EntityWrapper<>();
        provCityDistEntityWrapper.eq("type",2);
        provCityDistEntityWrapper.eq("city_id",cityId);
        List<ProvCityDist> provCityDists = provCityDistService.selectList(provCityDistEntityWrapper);
        return provCityDists;
    }
}
