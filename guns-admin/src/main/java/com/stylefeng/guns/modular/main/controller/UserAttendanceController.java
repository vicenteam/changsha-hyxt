package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.model.Dept;
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
import com.stylefeng.guns.modular.system.model.UserAttendance;
import com.stylefeng.guns.modular.main.service.IUserAttendanceService;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户考勤控制器
 *
 * @author fengshuonan
 * @Date 2019-01-07 17:13:48
 */
@Controller
@RequestMapping("/userAttendance")
public class UserAttendanceController extends BaseController {

    private String PREFIX = "/main/userAttendance/";

    @Autowired
    private IUserAttendanceService userAttendanceService;
    @Autowired
    private SellController sellController;

    /**
     * 跳转到用户考勤首页
     */
    @RequestMapping("")
    public String index(Model model) {
        List<Dept> deptList = new ArrayList<>();
        List<Dept> depts = sellController.getTreeMenuList(deptList, ShiroKit.getUser().getDeptId());
        model.addAttribute("depts", depts);
        return PREFIX + "userAttendance.html";
    }

    /**
     * 跳转到添加用户考勤
     */
    @RequestMapping("/userAttendance_add")
    public String userAttendanceAdd() {
        return PREFIX + "userAttendance_add.html";
    }

    /**
     * 跳转到修改用户考勤
     */
    @RequestMapping("/userAttendance_update/{userAttendanceId}")
    public String userAttendanceUpdate(@PathVariable Integer userAttendanceId, Model model) {
        UserAttendance userAttendance = userAttendanceService.selectById(userAttendanceId);
        model.addAttribute("item",userAttendance);
        LogObjectHolder.me().set(userAttendance);
        return PREFIX + "userAttendance_edit.html";
    }

    /**
     * 获取用户考勤列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<UserAttendance> page = new PageFactory<UserAttendance>().defaultPage();
        BaseEntityWrapper<UserAttendance> baseEntityWrapper = new BaseEntityWrapper<>();
        Page<UserAttendance> result = userAttendanceService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增用户考勤
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserAttendance userAttendance) {
        userAttendanceService.insert(userAttendance);
        return SUCCESS_TIP;
    }

    /**
     * 删除用户考勤
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userAttendanceId) {
        userAttendanceService.deleteById(userAttendanceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改用户考勤
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserAttendance userAttendance) {
        userAttendanceService.updateById(userAttendance);
        return SUCCESS_TIP;
    }

    /**
     * 用户考勤详情
     */
    @RequestMapping(value = "/detail/{userAttendanceId}")
    @ResponseBody
    public Object detail(@PathVariable("userAttendanceId") Integer userAttendanceId) {
        return userAttendanceService.selectById(userAttendanceId);
    }
}
