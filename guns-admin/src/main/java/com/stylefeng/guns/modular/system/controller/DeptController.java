package com.stylefeng.guns.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.annotion.Permission;
import com.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.main.service.IActivityService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.main.service.IProvCityDistService;
import com.stylefeng.guns.modular.system.model.Activity;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.Membershipcardtype;
import com.stylefeng.guns.modular.system.model.ProvCityDist;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.warpper.DeptWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Autowired
    private IDeptService deptService;
    @Autowired
    private IProvCityDistService provCityDistService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IActivityService activityService;

    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object add(Dept dept, String province, String city, String district) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        dept.setProvinceid(province);
        dept.setCityid(city);
        dept.setDistrictid(district);
        dept.setCreatedt(DateUtil.getTime());
        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        boolean insert = this.deptService.insert(dept);
        //自动初始化会员配置
        BaseEntityWrapper<Membershipcardtype> membershipcardtypeBaseEntityWrapper = new BaseEntityWrapper<>();
        List<Membershipcardtype> list = membershipcardtypeService.selectList(membershipcardtypeBaseEntityWrapper);
        for (Membershipcardtype membershipcardtype : list) {
            Membershipcardtype membershipcardtype1 = new Membershipcardtype();
            membershipcardtype1.setCardname(membershipcardtype.getCardname());
            membershipcardtype1.setUpamount(membershipcardtype.getUpamount());
            membershipcardtype1.setTips(membershipcardtype.getTips());
            membershipcardtype1.setStatus(membershipcardtype.getStatus());
            membershipcardtype1.setCreatedt(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            if (membershipcardtype.getCheckleavenum() != null) {
                membershipcardtype1.setCheckleavenum(membershipcardtype.getCheckleavenum());
            }
            membershipcardtype1.setLeaves(membershipcardtype.getLeaves());
            membershipcardtype1.setDeptid(dept.getId() + "");
            membershipcardtypeService.insert(membershipcardtype1);
        }
        //初始化admin活动
        List<Activity> list1 = activityService.selectList(membershipcardtypeBaseEntityWrapper);
        list1.forEach(a->{
            Activity activity=new Activity();
            activity.setStatus(a.getStatus());
            activity.setJifen(a.getJifen());
            activity.setCreater(a.getCreater());
            activity.setDeptid(dept.getId()+"");
            activity.setCreatetime(a.getCreatetime());
            activity.setBegindate(a.getBegindate());
            activity.setContent(a.getContent());
            activity.setEnddate(a.getEnddate());
            activity.setMaxgetnum(a.getMaxgetnum());
            activity.setName(a.getName());
            activity.setQiandaonum(a.getQiandaonum());
            activity.setRuleexpression(a.getRuleexpression());
            activityService.insert(activity);
        });

        return insert;
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.deptService.list(condition);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        Dept dept = deptService.selectById(deptId);
        return dept;
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept, String province, String city, String district) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        dept.setProvinceid(province);
        dept.setCityid(city);
        dept.setDistrictid(district);
        dept.setCreatedt(DateUtil.getTime());
        deptSetPids(dept);
        deptService.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptService.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
}
