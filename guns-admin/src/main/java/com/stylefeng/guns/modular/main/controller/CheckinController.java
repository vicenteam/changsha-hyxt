package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.*;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
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

import java.util.*;

/**
 * 签到场次控制器
 *
 * @author fengshuonan
 * @Date 2018-08-14 10:12:47
 */
@Controller
@RequestMapping("/checkin")
public class CheckinController extends BaseController {

    private String PREFIX = "/main/checkin/";

    @Autowired
    private ICheckinService checkinService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMemberCardService memberCardService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到签到场次首页
     */
    @RequestMapping("")
    public String index(Model model) {
        List list = membershipcardtypeService.selectList(new BaseEntityWrapper<>());
        model.addAttribute("leave", list);
        List list1 = userService.selectList(new BaseEntityWrapper<>());
        model.addAttribute("staffs", list1);
        Dept dept = deptService.selectById(ShiroKit.getUser().getDeptId());
        model.addAttribute("dept", dept);
        return PREFIX + "checkin.html";
    }

    //签到场次
    @RequestMapping("checkinIndex")
    public String checkinIndex(Model model) {
        return PREFIX + "checkinIndex.html";
    }

    /**
     * 跳转到添加签到场次
     */
    @RequestMapping("/checkin_add")
    public String checkinAdd() {
        return PREFIX + "checkin_add.html";
    }

    /**
     * 跳转到修改签到场次
     */
    @RequestMapping("/checkin_update/{checkinId}")
    public String checkinUpdate(@PathVariable Integer checkinId, Model model) {
        Checkin checkin = checkinService.selectById(checkinId);
        model.addAttribute("item", checkin);
        LogObjectHolder.me().set(checkin);
        return PREFIX + "checkin_edit.html";
    }

    /**
     * 获取签到场次列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Checkin> page = new PageFactory<Checkin>().defaultPage();
        BaseEntityWrapper<Checkin> baseEntityWrapper = new BaseEntityWrapper<>();
        if (!StringUtils.isEmpty(condition)) baseEntityWrapper.like("screenings", condition);
        baseEntityWrapper.orderBy("startDate", false);
        Page<Checkin> result = checkinService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增签到场次
     */
    @BussinessLog(value = "新增签到场次", key = "xzqdcc")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Checkin checkin) {
        checkinService.insert(checkin);
        return SUCCESS_TIP;
    }

    /**
     * 删除签到场次
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer checkinId) {
        checkinService.deleteById(checkinId);
        return SUCCESS_TIP;
    }

    /**
     * 修改签到场次
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Checkin checkin) {
        checkinService.updateById(checkin);
        return SUCCESS_TIP;
    }

    /**
     * 签到场次详情
     */
    @RequestMapping(value = "/detail/{checkinId}")
    @ResponseBody
    public Object detail(@PathVariable("checkinId") Integer checkinId) {
        return checkinService.selectById(checkinId);
    }

    /**
     * 获取签到场次信息
     *
     * @return
     */
    @RequestMapping(value = "/getcheck")
    @ResponseBody
    public Object getcheck() {
        BaseEntityWrapper<Checkin> checkinBaseEntityWrapper = new BaseEntityWrapper<>();
        checkinBaseEntityWrapper.ge("startDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd") + " 00:00:00");
        List<Checkin> list = checkinService.selectList(checkinBaseEntityWrapper);
        if (list.size() == 0) {
            Checkin checkin = new Checkin();
            checkin.setCreateDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            checkin.setStartDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            checkin.setDeptId(ShiroKit.getUser().getDeptId() + "");
            checkin.setStatus(1);
            String s = DateUtil.formatDate(new Date(), "yyyyMMdd") + "01";
            checkin.setScreenings(Integer.parseInt(s));
            checkinService.insert(checkin);
            return checkin;
        } else {
            Checkin checkinold = list.get(list.size() - 1);
            Checkin checkin = new Checkin();
            checkin.setCreateDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            checkin.setStartDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            checkin.setDeptId(ShiroKit.getUser().getDeptId() + "");
            checkin.setStatus(1);
            int v = (checkinold.getScreenings() + 1);
            checkin.setScreenings(v);
            checkinService.insert(checkin);
            return checkin;
        }
    }

    @BussinessLog(value = "结束签到场次", key = "jsqdcc")
    @RequestMapping(value = "/updatecheck")
    @ResponseBody
    public Object updatecheck(String checkid) {
        Checkin checkin = checkinService.selectById(checkid);
        checkin.setStatus(2);
        checkin.setEndDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        checkinService.updateById(checkin);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public Object getUserInfo(String value, String checkid) {
        BaseEntityWrapper<MemberCard> memberCardBaseEntityWrapper = new BaseEntityWrapper<>();
        memberCardBaseEntityWrapper.eq("code", value);
        MemberCard memberCard = memberCardService.selectOne(memberCardBaseEntityWrapper);
        if (memberCard != null && memberCard.getMemberid() != null) {
            BaseEntityWrapper<Membermanagement> membermanagementBaseEntityWrapper = new BaseEntityWrapper<>();
            membermanagementBaseEntityWrapper.eq("id", memberCard.getMemberid());
            Map<String, Object> map = membermanagementService.selectMap(membermanagementBaseEntityWrapper);
            //获取当前签到场次签到信息
            BaseEntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper = new BaseEntityWrapper<>();
            qiandaoCheckinBaseEntityWrapper.eq("memberid", memberCard.getMemberid());
            qiandaoCheckinBaseEntityWrapper.eq("checkinid", checkid);
            QiandaoCheckin qiandaoCheckin = qiandaoCheckinService.selectOne(qiandaoCheckinBaseEntityWrapper);
            if (qiandaoCheckin == null) {
                //进行首签
                map.put("qiandao", 0);
            } else {
                if (StringUtils.isEmpty(qiandaoCheckin.getUpdatetime())) {
                    //进行复签
                    map.put("qiandao", 1);
                } else {
                    //不能进行操作
                    map.put("qiandao", 2);
                }
            }
            if(map.get("relation")==null){
                map.put("relation","");
            };
            return map;
        }
        return null;
    }

    //
    @RequestMapping(value = "/getRelationUserInfo")
    @ResponseBody
    public Object getRelationUserInfo(String delId, String relation) {
        BaseEntityWrapper<Membermanagement> membermanagementBaseEntityWrapper = new BaseEntityWrapper<>();
        membermanagementBaseEntityWrapper.eq("relation", relation);
        membermanagementBaseEntityWrapper.notIn("id", delId);
        Membermanagement membermanagement = membermanagementService.selectOne(membermanagementBaseEntityWrapper);
        if (membermanagement != null) {
            return membermanagement;
        }
        return null;
    }

    @RequestMapping(value = "/findUserCheckInfoByMonth")
    @ResponseBody
    public Object findUserCheckInfoByMonth(String memberId, String valMonth, String valYear) {
        Map<String, Object> result = new HashMap<>();
        BaseEntityWrapper<QiandaoCheckin> memberCardBaseEntityWrapper = new BaseEntityWrapper<>();
        memberCardBaseEntityWrapper.eq("memberid", memberId);
        String starTime = valYear + "-";
        if (valMonth.length() == 1) {
            starTime += "0" + valMonth + "-01 00:00:01";
        } else {
            starTime += valMonth + "-01 00:00:01";
        }
        String endTime = valYear + "-";
        if (valMonth.length() == 1) {
            endTime += "0" + valMonth + "-31 23:59:59";
        } else {
            endTime += valMonth + "-31 23:59:59";
        }
        memberCardBaseEntityWrapper.between("createtime", starTime, endTime);
        List<QiandaoCheckin> list = qiandaoCheckinService.selectList(memberCardBaseEntityWrapper);
        StringBuilder sb = new StringBuilder();
        List<Map<String, Object>> list1 = new ArrayList<>();
        for (QiandaoCheckin qi : list) {
            String createtime = qi.getCreatetime();
            //日期转化
            Date parse = DateUtil.parse(createtime, "yyyy-MM-dd HH:mm:ss");
            String s = DateUtil.formatDate(parse, "d/M/yyyy");
            String[] split = createtime.split(" ");
            String time1 = split[1];
            String backgroundcolor = "#29b451";
            if (StringUtils.isEmpty(qi.getUpdatetime())) {
                backgroundcolor = "#dbbf22";
            }
            sb.append("<div class='added-event' data-date='" + s + "' data-time='" + time1 + "' data-title='首签时间' style='background-color:" + backgroundcolor + ";'></div>");
            Map<String, Object> map = new HashMap<>();
            map.put("time", s);
            map.put("color", backgroundcolor);
            list1.add(map);
            if (!StringUtils.isEmpty(qi.getUpdatetime())) {
                //日期转化
                String updatetime = qi.getUpdatetime();
                Date parse2 = DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss");
                String s2 = DateUtil.formatDate(parse2, "d/M/yyyy");
                String[] split2 = updatetime.split(" ");
                String time12 = split2[1];
                sb.append("<div class='added-event' data-date='" + s2 + "' data-time='" + time12 + "' data-title='复签时间' style='background-color:" + backgroundcolor + ";'></div>");
            }
        }
        result.put("dom", sb.toString());
        result.put("timeObj", list1);
        return result;
    }
}
