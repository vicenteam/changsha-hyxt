package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.ServerMemberinfo;
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
import com.stylefeng.guns.modular.system.model.MainServerConsumption;
import com.stylefeng.guns.modular.main.service.IMainServerConsumptionService;

import java.util.Date;
import java.util.List;

/**
 * 增值服务控制器
 *
 * @author fengshuonan
 * @Date 2018-12-27 11:33:05
 */
@Controller
@RequestMapping("/mainServerConsumption")
public class MainServerConsumptionController extends BaseController {

    private String PREFIX = "/main/mainServerConsumption/";

    @Autowired
    private IMainServerConsumptionService mainServerConsumptionService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMainServerConsumptionService iMainServerConsumptionService;

    /**
     * 跳转到增值服务首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "mainServerConsumption.html";
    }

    @RequestMapping("info")
    public String info(Model model) {
        EntityWrapper<MainServerConsumption> mainServerConsumptionEntityWrapper = new EntityWrapper<>();
        mainServerConsumptionEntityWrapper.eq("status",0);
        List<MainServerConsumption> mainServerConsumptions = mainServerConsumptionService.selectList(null);
        model.addAttribute("list", mainServerConsumptions);
        return PREFIX + "mainServerConsumptionInfo.html";
    }

    /**
     * 跳转到添加增值服务
     */
    @RequestMapping("/mainServerConsumption_add")
    public String mainServerConsumptionAdd() {
        return PREFIX + "mainServerConsumption_add.html";
    }

    /**
     * 跳转到修改增值服务
     */
    @RequestMapping("/mainServerConsumption_update/{mainServerConsumptionId}")
    public String mainServerConsumptionUpdate(@PathVariable Integer mainServerConsumptionId, Model model) {
        MainServerConsumption mainServerConsumption = mainServerConsumptionService.selectById(mainServerConsumptionId);
        model.addAttribute("item", mainServerConsumption);
        LogObjectHolder.me().set(mainServerConsumption);
        return PREFIX + "mainServerConsumption_edit.html";
    }

    /**
     * 跳转到修改增值服务
     */
    @RequestMapping("/mainServerConsumptionInfoLingqu/{id}")
    public String mainServerConsumptionInfoLingqu(@PathVariable Integer id, Model model) {
        MainServerConsumption mainServerConsumption = mainServerConsumptionService.selectById(id);
        model.addAttribute("item", mainServerConsumption);
        return PREFIX + "mainServerConsumptionInfo_lingqu.html";
    }

    /**
     * 获取增值服务列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<MainServerConsumption> page = new PageFactory<MainServerConsumption>().defaultPage();
        EntityWrapper<MainServerConsumption> baseEntityWrapper = new EntityWrapper<>();
        baseEntityWrapper.eq("status", 0);
        if (!StringUtils.isEmpty(condition)) baseEntityWrapper.like("serverName", condition);
        Page<MainServerConsumption> result = mainServerConsumptionService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 新增增值服务
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MainServerConsumption mainServerConsumption) {
        String createTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        mainServerConsumption.setCreateTime(createTime);
        mainServerConsumption.setUpdateTime(createTime);
        mainServerConsumptionService.insert(mainServerConsumption);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/lingqu")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object lingqu(String memberId, String serverId) {
        MainServerConsumption mainServerConsumption = mainServerConsumptionService.selectById(serverId);
        Membermanagement membermanagement = membermanagementService.selectById(memberId);
        if (membermanagement.getIntegral() - Double.parseDouble(mainServerConsumption.getConsumptionJiFen()) < 0) {
            throw new GunsException(BizExceptionEnum.NUM_IS_ERROR);
        }
        //扣除积分
        membermanagement.setIntegral(membermanagement.getIntegral() - Double.parseDouble(mainServerConsumption.getConsumptionJiFen()));
        membermanagementService.updateById(membermanagement);
        //新增积分记录
        ServerMemberinfo serverMemberinfo = new ServerMemberinfo();
        serverMemberinfo.setDeptid(ShiroKit.getUser().getDeptId());
        serverMemberinfo.setJifen(Double.parseDouble(mainServerConsumption.getConsumptionJiFen()));
        serverMemberinfo.setServerName(mainServerConsumption.getServerName());
        serverMemberinfo.setServerId(mainServerConsumption.getId());
        serverMemberinfo.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        serverMemberinfo.setMemberId(Integer.parseInt(memberId));
        serverMemberinfo.setMemberName(membermanagement.getName());
        serverMemberinfo.insert();
        return SUCCESS_TIP;
    }

    /**
     * 删除增值服务
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer mainServerConsumptionId) {
        MainServerConsumption mainServerConsumption = mainServerConsumptionService.selectById(mainServerConsumptionId);
        mainServerConsumption.setStatus(1);
        mainServerConsumptionService.updateById(mainServerConsumption);
        return SUCCESS_TIP;
    }

    /**
     * 修改增值服务
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MainServerConsumption mainServerConsumption) {
        mainServerConsumption.setUpdateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mainServerConsumptionService.updateById(mainServerConsumption);
        return SUCCESS_TIP;
    }

    /**
     * 增值服务详情
     */
    @RequestMapping(value = "/detail/{mainServerConsumptionId}")
    @ResponseBody
    public Object detail(@PathVariable("mainServerConsumptionId") Integer mainServerConsumptionId) {
        return mainServerConsumptionService.selectById(mainServerConsumptionId);
    }
}
