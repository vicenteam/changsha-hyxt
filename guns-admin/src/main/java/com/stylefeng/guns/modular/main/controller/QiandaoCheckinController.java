package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.ICheckinService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.system.model.Checkin;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.Membershipcardtype;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.QiandaoCheckin;
import com.stylefeng.guns.modular.main.service.IQiandaoCheckinService;

import java.util.Date;
import java.util.List;

/**
 * 复签记录控制器
 *
 * @author fengshuonan
 * @Date 2018-08-14 14:52:03
 */
@Controller
@RequestMapping("/qiandaoCheckin")
public class QiandaoCheckinController extends BaseController {

    private String PREFIX = "/main/qiandaoCheckin/";

    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private ICheckinService checkinService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;

    /**
     * 跳转到复签记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "qiandaoCheckin.html";
    }

    /**
     * 跳转到添加复签记录
     */
    @RequestMapping("/qiandaoCheckin_add")
    public String qiandaoCheckinAdd() {
        return PREFIX + "qiandaoCheckin_add.html";
    }

    /**
     * 跳转到修改复签记录
     */
    @RequestMapping("/qiandaoCheckin_update/{qiandaoCheckinId}")
    public String qiandaoCheckinUpdate(@PathVariable Integer qiandaoCheckinId, Model model) {
        QiandaoCheckin qiandaoCheckin = qiandaoCheckinService.selectById(qiandaoCheckinId);
        model.addAttribute("item", qiandaoCheckin);
        LogObjectHolder.me().set(qiandaoCheckin);
        return PREFIX + "qiandaoCheckin_edit.html";
    }

    /**
     * 获取复签记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<QiandaoCheckin> page = new PageFactory<QiandaoCheckin>().defaultPage();
        BaseEntityWrapper<QiandaoCheckin> baseEntityWrapper = new BaseEntityWrapper<>();
        Page<QiandaoCheckin> result = qiandaoCheckinService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增复签记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String memberId, String chechId) throws Exception {
        //判断签到场次是否被结束
        if (!StringUtils.isEmpty(chechId)) {
            Checkin checkin1 = checkinService.selectById(chechId);
            if (checkin1 != null) {
                if (checkin1.getStatus() == 2) {
                    throw new Exception("该场次已经结束无法进行该操作!");
                }
            }
        }
        //查询当天是否签到
        BaseEntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper = new BaseEntityWrapper<>();
        qiandaoCheckinBaseEntityWrapper.eq("memberid", memberId);
        qiandaoCheckinBaseEntityWrapper.eq("deptid", ShiroKit.getUser().getDeptId());
        qiandaoCheckinBaseEntityWrapper.like("createtime", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        int i = qiandaoCheckinService.selectCount(qiandaoCheckinBaseEntityWrapper);
        if (i != 0) {
            throw new Exception("该用户当天已首签无法进行该操作!");
        }
        QiandaoCheckin qiandaoCheckin = new QiandaoCheckin();
        qiandaoCheckin.setCheckinid(Integer.parseInt(chechId));
        qiandaoCheckin.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        qiandaoCheckin.setDeptid(ShiroKit.getUser().getDeptId());
        qiandaoCheckin.setMemberid(Integer.parseInt(memberId));
        qiandaoCheckinService.insert(qiandaoCheckin);
        return SUCCESS_TIP;
    }

    /**
     * 删除复签记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer qiandaoCheckinId) {
        qiandaoCheckinService.deleteById(qiandaoCheckinId);
        return SUCCESS_TIP;
    }

    /**
     * 修改复签记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object update(String memberId, String chechId) throws Exception {
        //判断签到场次是否被结束
        if (!StringUtils.isEmpty(chechId)) {
            Checkin checkin1 = checkinService.selectById(chechId);
            if (checkin1 != null) {
                if (checkin1.getStatus() == 2) {
//                    throw new Exception("该场次已经结束无法进行该操作!");
                }
            }
        }
        EntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper = new EntityWrapper<>();
        qiandaoCheckinBaseEntityWrapper.eq("memberid", memberId);
        qiandaoCheckinBaseEntityWrapper.eq("checkinid", chechId);
        QiandaoCheckin qiandaoCheckin = qiandaoCheckinService.selectOne(qiandaoCheckinBaseEntityWrapper);
        if (qiandaoCheckin != null && StringUtils.isEmpty(qiandaoCheckin.getUpdatetime())) {
            qiandaoCheckin.setUpdatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            qiandaoCheckinService.updateById(qiandaoCheckin);
            //如果当前用户复签次数累计>=10次更新为普通会员卡
            EntityWrapper<QiandaoCheckin> qiandaoc = new EntityWrapper<>();
            qiandaoc.eq("memberid", memberId);
            qiandaoc.isNotNull("updatetime");
            int count = qiandaoCheckinService.selectCount(qiandaoc);
            Membermanagement membermanagement1 = membermanagementService.selectById(memberId);
//            if (membermanagement1 != null && membermanagement1.getLevelID().equals("1")) {
            if (membermanagement1 != null) {
                String levelID = membermanagement1.getLevelID();
                Membershipcardtype membershipcardtype = membershipcardtypeService.selectById(levelID);
                if (membershipcardtype != null && membershipcardtype.getLeaves() == 0) {
                    if (membershipcardtype != null && count >= membershipcardtype.getCheckleavenum()) {
                        Membermanagement membermanagement = membermanagementService.selectById(memberId);
//                        if (membermanagement.getLevelID().equals("1")) {//零时卡更新普通会员卡
                        EntityWrapper<Membershipcardtype> membershipcardtypeEntityWrapper = new EntityWrapper<>();
                        membershipcardtypeEntityWrapper.eq("deptid", qiandaoCheckin.getDeptid());
                        List<Membershipcardtype> list = membershipcardtypeService.selectList(membershipcardtypeEntityWrapper);
                        if (list.size() >= 2) {
                            membermanagement.setLevelID(list.get(1).getId() + "");
                        }
//                        }
                        membermanagementService.updateById(membermanagement);
                    }
                }

            }

            //复签成功后统计当前场次完整签到人数
            Checkin checkin = checkinService.selectById(chechId);
            if (checkin != null) {
                if (checkin.getMemberCount() == null) {
                    checkin.setMemberCount(1);
                } else {
                    checkin.setMemberCount((checkin.getMemberCount() + 1));
                }
            }
            checkinService.updateById(checkin);
        }
        return SUCCESS_TIP;
    }

    /**
     * 复签记录详情
     */
    @RequestMapping(value = "/detail/{qiandaoCheckinId}")
    @ResponseBody
    public Object detail(@PathVariable("qiandaoCheckinId") Integer qiandaoCheckinId) {
        return qiandaoCheckinService.selectById(qiandaoCheckinId);
    }
}
