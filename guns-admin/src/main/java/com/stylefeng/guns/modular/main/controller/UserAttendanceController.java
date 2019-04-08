package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.context.annotation.Scope;
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
import com.stylefeng.guns.modular.system.model.UserAttendance;
import com.stylefeng.guns.modular.main.service.IUserAttendanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户考勤控制器
 *
 * @author fengshuonan
 * @Date 2019-01-07 17:13:48
 */
@Controller
@Scope("prototype")
@RequestMapping("/userAttendance")
public class UserAttendanceController extends BaseController {

    private String PREFIX = "/main/userAttendance/";

    @Autowired
    private IUserAttendanceService userAttendanceService;
    @Autowired
    private SellController sellController;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;

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
    public Object list(String deptId, String name, String begindate, String enddate, Integer type) {
        List<Dept> deptList = new ArrayList<>();
        List<Dept> depts = sellController.getTreeMenuList(deptList, Integer.parseInt(deptId));

        Page<Map<String,Object>> mapPage = new PageFactory<Map<String,Object>>().defaultPage();
        List<Map<String,Object>> mapList = userAttendanceService.findUserAttendanceData(depts,name,begindate,enddate,mapPage.getOffset(),mapPage.getLimit(),type);
        mapPage.setRecords(mapList);
        return super.packForBT(mapPage);
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
