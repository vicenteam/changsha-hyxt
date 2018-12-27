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
import com.stylefeng.guns.modular.system.model.ServerMemberinfo;
import com.stylefeng.guns.modular.main.service.IServerMemberinfoService;

/**
 * 体验服务记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-27 16:12:20
 */
@Controller
@RequestMapping("/serverMemberinfo")
public class ServerMemberinfoController extends BaseController {

    private String PREFIX = "/main/serverMemberinfo/";

    @Autowired
    private IServerMemberinfoService serverMemberinfoService;

    /**
     * 跳转到体验服务记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "serverMemberinfo.html";
    }

    /**
     * 跳转到添加体验服务记录
     */
    @RequestMapping("/serverMemberinfo_add")
    public String serverMemberinfoAdd() {
        return PREFIX + "serverMemberinfo_add.html";
    }

    /**
     * 跳转到修改体验服务记录
     */
    @RequestMapping("/serverMemberinfo_update/{serverMemberinfoId}")
    public String serverMemberinfoUpdate(@PathVariable Integer serverMemberinfoId, Model model) {
        ServerMemberinfo serverMemberinfo = serverMemberinfoService.selectById(serverMemberinfoId);
        model.addAttribute("item",serverMemberinfo);
        LogObjectHolder.me().set(serverMemberinfo);
        return PREFIX + "serverMemberinfo_edit.html";
    }

    /**
     * 获取体验服务记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<ServerMemberinfo> page = new PageFactory<ServerMemberinfo>().defaultPage();
        BaseEntityWrapper<ServerMemberinfo> baseEntityWrapper = new BaseEntityWrapper<>();
        Page<ServerMemberinfo> result = serverMemberinfoService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增体验服务记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ServerMemberinfo serverMemberinfo) {
        serverMemberinfoService.insert(serverMemberinfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除体验服务记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer serverMemberinfoId) {
        serverMemberinfoService.deleteById(serverMemberinfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改体验服务记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ServerMemberinfo serverMemberinfo) {
        serverMemberinfoService.updateById(serverMemberinfo);
        return SUCCESS_TIP;
    }

    /**
     * 体验服务记录详情
     */
    @RequestMapping(value = "/detail/{serverMemberinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("serverMemberinfoId") Integer serverMemberinfoId) {
        return serverMemberinfoService.selectById(serverMemberinfoId);
    }
}
