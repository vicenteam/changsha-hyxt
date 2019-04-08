package com.stylefeng.guns.modular.main.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.face.AipFace;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.face.FaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.stylefeng.guns.modular.system.model.UserAttendanceSource;
import com.stylefeng.guns.modular.main.service.IUserAttendanceSourceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 识别源数据控制器
 *
 * @author fengshuonan
 * @Date 2019-04-08 10:53:16
 */
@Controller
@RequestMapping("/userAttendanceSource")
public class UserAttendanceSourceController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private String PREFIX = "/main/userAttendanceSource/";

    @Autowired
    private IUserAttendanceSourceService userAttendanceSourceService;

    /**
     * 跳转到识别源数据首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "userAttendanceSource.html";
    }

    /**
     * 跳转到添加识别源数据
     */
    @RequestMapping("/userAttendanceSource_add")
    public String userAttendanceSourceAdd() {
        return PREFIX + "userAttendanceSource_add.html";
    }

    /**
     * 跳转到修改识别源数据
     */
    @RequestMapping("/userAttendanceSource_update/{userAttendanceSourceId}")
    public String userAttendanceSourceUpdate(@PathVariable Integer userAttendanceSourceId, Model model) {
        UserAttendanceSource userAttendanceSource = userAttendanceSourceService.selectById(userAttendanceSourceId);
        model.addAttribute("item",userAttendanceSource);
        LogObjectHolder.me().set(userAttendanceSource);
        return PREFIX + "userAttendanceSource_edit.html";
    }

    /**
     * 获取识别源数据列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<UserAttendanceSource> page = new PageFactory<UserAttendanceSource>().defaultPage();
        BaseEntityWrapper<UserAttendanceSource> baseEntityWrapper = new BaseEntityWrapper<>();
        if(!StringUtils.isEmpty(condition))baseEntityWrapper.like("name",condition);
        Page<UserAttendanceSource> result = userAttendanceSourceService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增识别源数据
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserAttendanceSource userAttendanceSource, HttpServletRequest request) {
        userAttendanceSource.setDeptId(ShiroKit.getUser().deptId);
        userAttendanceSource.setCreatedt(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        userAttendanceSourceService.insert(userAttendanceSource);

        //注册baiduai
//更新人脸库
        new Runnable() {
            @Override
            public void run() {
                String userBase64ImgData = (String) request.getSession().getAttribute("userBase64ImgData");
                log.info("base64->" + userBase64ImgData);
                if (!StringUtils.isEmpty(userBase64ImgData)) {
                    AipFace client = new AipFace(FaceUtil.APP_ID, FaceUtil.API_KEY, FaceUtil.SECRET_KEY);
                    new FaceUtil().userRegister(client, JSON.toJSONString(userAttendanceSource), userBase64ImgData, userAttendanceSource.getDeptId() + "", userAttendanceSource.getId() + "");
                }
            }
        }.run();
        return SUCCESS_TIP;
    }

    /**
     * 删除识别源数据
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userAttendanceSourceId) {
        userAttendanceSourceService.deleteById(userAttendanceSourceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改识别源数据
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserAttendanceSource userAttendanceSource,HttpServletRequest request) {
        userAttendanceSourceService.updateById(userAttendanceSource);
        UserAttendanceSource userAttendanceSource1 = userAttendanceSource.selectById();
        //修改baiduai
        new Runnable() {
            @Override
            public void run() {
                String userBase64ImgData = (String) request.getSession().getAttribute("userBase64ImgData");
                log.info("base64->" + userBase64ImgData);
                if (!StringUtils.isEmpty(userBase64ImgData)) {
                    AipFace client = new AipFace(FaceUtil.APP_ID, FaceUtil.API_KEY, FaceUtil.SECRET_KEY);
                    if (StringUtils.isEmpty(userAttendanceSource.getImg())) {//新增
                        new FaceUtil().userRegister(client, JSON.toJSONString(userAttendanceSource), userBase64ImgData, userAttendanceSource.getDeptId() + "", userAttendanceSource.getId() + "");
                    } else {//更新
                        new FaceUtil().faceUpdate(client, userAttendanceSource1.getId() + "", userBase64ImgData, JSON.toJSONString(userAttendanceSource1), userAttendanceSource1.getDeptId() + "");
                    }
                }
            }
        }.run();
        return SUCCESS_TIP;
    }

    /**
     * 识别源数据详情
     */
    @RequestMapping(value = "/detail/{userAttendanceSourceId}")
    @ResponseBody
    public Object detail(@PathVariable("userAttendanceSourceId") Integer userAttendanceSourceId) {
        return userAttendanceSourceService.selectById(userAttendanceSourceId);
    }
}
