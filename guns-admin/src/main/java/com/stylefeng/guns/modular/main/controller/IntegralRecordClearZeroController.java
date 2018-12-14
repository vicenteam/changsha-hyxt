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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
            if(map.get("deptid").toString() != null){
                map.put("deptName",deptService.selectById(map.get("deptid").toString()).getFullname());
            }
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
        // clear start
        Clear clear = new Clear();
        clear.setDeptid(deptId);
        clear.setStaffid(staffId);
        clear.setCreatedt(DateUtil.getTime());
        //积分清零记录表保存
        clearService.insert(clear);
        Integralrecord clearM = new Integralrecord();
        for(Membermanagement m : mList){  //循环门店会员列表 并插入积分记录表
            clearM.setIntegral(m.getIntegral()); // 清零会员可用积分
            clearM.setTypeId("11");
            clearM.setMemberid(m.getId());
            clearM.setDeptid(deptId);
            clearM.setStaffid(staffId);
            clearM.setClearid(clear.getId());
            clearM.setCreateTime(DateUtil.getTime());
            //插入清除积分记录
            integralrecordService.insert(clearM);
            m.setIntegral(0.0);
            //清零会员可用积分
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
        Integralrecord i = new Integralrecord();
        for(Integralrecord integral : integrals){
            membermanagementService.updateIntegralRollBack(integral);
            i.setCreateTime(DateUtil.getTime());
            i.setClearid(integral.getClearid());
            i.setStaffid(integral.getStaffid());
            i.setMemberid(integral.getMemberid());
            i.setDeptid(integral.getDeptid());
            i.setTypeId("12");
            i.setIntegral(integral.getIntegral());
            integralrecordService.insert(i);
        }
        Clear clear = new Clear();
        clear.setId(integralRecordId);
        clear.setUpdatedt(DateUtil.getTime());
        clear.setStatus(1);
        clearService.updateById(clear);
        return SUCCESS_TIP;
    }
}
