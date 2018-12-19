package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.IClearService;
import com.stylefeng.guns.modular.main.service.IIntegralrecordService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 积分清零控制器
 *
 * @author fengshuonan
 * @Date 2018-08-14 16:47:26
 */
@Controller
@RequestMapping("/integralrecordclearzero")
public class IntegralRecordClearZeroController extends BaseController {

    private String PREFIX = "/main/integralrecord/";

    @Autowired
    private IDeptService deptService;
    @Autowired
    private IIntegralrecordService integralrecordService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IClearService clearService;
    @Autowired
    private IUserService userService;

    @RequestMapping("")
    public String index(Model model){
        Dept dept = deptService.selectById(ShiroKit.getUser().getDeptId());
        model.addAttribute("dept",dept);
        return PREFIX + "integralRecordClearZero.html";
    }

    /**
     * 查询积分清零记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Clear> integralrecordPage = new PageFactory<Clear>().defaultPage();
        BaseEntityWrapper<Clear> wrapper = new BaseEntityWrapper<>();
        wrapper.eq("status",0);
        Page<Map<String, Object>> mapPage = clearService.selectMapsPage(integralrecordPage,wrapper);
        for(Map<String, Object> map : mapPage.getRecords()){
            if(! StringUtils.isEmpty(map.get("deptid").toString()))
                map.put("deptName",deptService.selectById(map.get("deptid").toString()).getFullname());
            if(! StringUtils.isEmpty(map.get("staffid").toString()))
                map.put("staffName",userService.selectById(map.get("staffid").toString()).getName());
        }
        return super.packForBT(mapPage);
    }

    /**
     * 积分清零
     */
    @BussinessLog(value = "积分清零", key = "jfql")
    @RequestMapping(value = "/clear")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object integralClear() {
        Integer deptId = ShiroKit.getUser().getDeptId();
        Integer staffId = ShiroKit.getUser().getId();
        BaseEntityWrapper<Membermanagement> wrapper = new BaseEntityWrapper<>();
        List<Membermanagement> mList = membermanagementService.selectList(wrapper);

        Clear clear = new Clear();
        clear.setDeptid(deptId);
        clear.setStaffid(staffId);
        clear.setCreatedt(DateUtil.getTime());
        clearService.insert(clear); //生成积分清零记录id

        Integralrecord integralrecord = new Integralrecord();
        for(Membermanagement m : mList){  //循环门店会员列表
            if(m.getIntegral() <= 0) continue; //积分为零不计入
            integralrecord.setIntegral(m.getIntegral()); //清零会员可用积分
            integralrecord.setMemberid(m.getId());
            integralrecord.setDeptid(deptId);
            integralrecord.setStaffid(staffId);
            integralrecord.setClearid(clear.getId()); //记录积分清零id
            integralrecord.setCreateTime(DateUtil.getTime());
            integralrecordService.insert(integralrecord);
            m.setIntegral(0.0);
            membermanagementService.updateById(m);
        }
        return SUCCESS_TIP;
    }

    /**
     * 积分恢复
     */
    @BussinessLog(value = "积分恢复", key = "jfhf")
    @RequestMapping(value = "/rollBack")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object integralRollBack(Integer integralRecordId){
        BaseEntityWrapper<Integralrecord> wrapper = new BaseEntityWrapper<>();
        wrapper.eq("clearid",integralRecordId);
        List<Integralrecord> integrals = integralrecordService.selectList(wrapper);
        Membermanagement membermanagement = new Membermanagement();
        for(Integralrecord integral : integrals){
            membermanagement.setId(integral.getMemberid());
            membermanagement.setIntegral(integral.getIntegral());
            membermanagementService.updateById(membermanagement); //回复积分
        }
        Clear clear = new Clear();
        clear.setId(integralRecordId);
        clear.setUpdatedt(DateUtil.getTime());
        clear.setStatus(1);
        clearService.updateById(clear);
        return SUCCESS_TIP;
    }
}
