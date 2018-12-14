package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.Clear;
import com.stylefeng.guns.modular.main.service.IClearService;

/**
 * 积分清零记录控制器
 *
 * @author fengshuonan
 * @Date 2018-08-16 10:16:36
 */
@Controller
@RequestMapping("/clear")
public class ClearController extends BaseController {

    private String PREFIX = "/main/clear/";

    @Autowired
    private IClearService clearService;

    /**
     * 跳转到积分清零记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "clear.html";
    }

    /**
     * 跳转到添加积分清零记录
     */
    @RequestMapping("/clear_add")
    public String clearAdd() {
        return PREFIX + "clear_add.html";
    }

    /**
     * 跳转到修改积分清零记录
     */
    @RequestMapping("/clear_update/{clearId}")
    public String clearUpdate(@PathVariable Integer clearId, Model model) {
        Clear clear = clearService.selectById(clearId);
        model.addAttribute("item",clear);
        LogObjectHolder.me().set(clear);
        return PREFIX + "clear_edit.html";
    }

    /**
     * 获取积分清零记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Clear> page = new PageFactory<Clear>().defaultPage();
        BaseEntityWrapper<Clear> baseEntityWrapper = new BaseEntityWrapper<>();
        Page<Clear> result = clearService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增积分清零记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Clear clear) {
        clearService.insert(clear);
        return SUCCESS_TIP;
    }

    /**
     * 删除积分清零记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clearId) {
        clearService.deleteById(clearId);
        return SUCCESS_TIP;
    }

    /**
     * 修改积分清零记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Clear clear) {
        clearService.updateById(clear);
        return SUCCESS_TIP;
    }

    /**
     * 积分清零记录详情
     */
    @RequestMapping(value = "/detail/{clearId}")
    @ResponseBody
    public Object detail(@PathVariable("clearId") Integer clearId) {
        return clearService.selectById(clearId);
    }
}
