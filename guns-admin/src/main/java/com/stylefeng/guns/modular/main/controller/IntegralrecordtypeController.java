package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.Integralrecordtype;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;

import java.util.Date;

/**
 * 积分类型控制器
 *
 * @author fengshuonan
 * @Date 2018-10-30 07:52:51
 */
@Controller
@RequestMapping("/integralrecordtype")
public class IntegralrecordtypeController extends BaseController {

    private String PREFIX = "/main/integralrecordtype/";

    @Autowired
    private IIntegralrecordtypeService integralrecordtypeService;

    /**
     * 跳转到积分类型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "integralrecordtype.html";
    }

    /**
     * 跳转到添加积分类型
     */
    @RequestMapping("/integralrecordtype_add")
    public String integralrecordtypeAdd() {
        return PREFIX + "integralrecordtype_add.html";
    }

    /**
     * 跳转到修改积分类型
     */
    @RequestMapping("/integralrecordtype_update/{integralrecordtypeId}")
    public String integralrecordtypeUpdate(@PathVariable Integer integralrecordtypeId, Model model) {
        Integralrecordtype integralrecordtype = integralrecordtypeService.selectById(integralrecordtypeId);
        model.addAttribute("item",integralrecordtype);
        LogObjectHolder.me().set(integralrecordtype);
        return PREFIX + "integralrecordtype_edit.html";
    }

    /**
     * 获取积分类型列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,Integer producttype,Integer id) {
        Page<Integralrecordtype> page = new PageFactory<Integralrecordtype>().defaultPage();
        BaseEntityWrapper<Integralrecordtype> baseEntityWrapper = new BaseEntityWrapper<>();
        if(id!=null){
            baseEntityWrapper.eq("id",id);
        }else {
            if(!StringUtils.isEmpty(condition))baseEntityWrapper.like("productname",condition);
        }

        if(producttype!=null) baseEntityWrapper.eq("producttype",producttype);
        baseEntityWrapper.eq("status",0);
        Page<Integralrecordtype> result = integralrecordtypeService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增积分类型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Integralrecordtype integralrecordtype) {
        integralrecordtype.setCreatetime(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        integralrecordtype.setCreateuserid(ShiroKit.getUser().id+"");
        integralrecordtype.setDeptid(ShiroKit.getUser().getDeptId()+"");
        integralrecordtype.setStatus(0);
        integralrecordtypeService.insert(integralrecordtype);
        return SUCCESS_TIP;
    }

    /**
     * 删除积分类型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer integralrecordtypeId) {
        Integralrecordtype integralrecordtype = integralrecordtypeService.selectById(integralrecordtypeId);
        integralrecordtype.setStatus(1);
        integralrecordtypeService.updateById(integralrecordtype);
        return SUCCESS_TIP;
    }

    /**
     * 修改积分类型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Integralrecordtype integralrecordtype) {
        integralrecordtype.setUpdatetime(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        integralrecordtype.setUpdateuserid(ShiroKit.getUser().getId()+"");
        integralrecordtypeService.updateById(integralrecordtype);
        return SUCCESS_TIP;
    }

    /**
     * 积分类型详情
     */
    @RequestMapping(value = "/detail/{integralrecordtypeId}")
    @ResponseBody
    public Object detail(@PathVariable("integralrecordtypeId") Integer integralrecordtypeId) {
        return integralrecordtypeService.selectById(integralrecordtypeId);
    }
}
