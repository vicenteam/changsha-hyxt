package com.stylefeng.guns.modular.main.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IProductReturnChangeService;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.beetl.ext.fn.Json;
import org.springframework.context.annotation.Scope;
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
import com.stylefeng.guns.modular.main.service.IInventoryManagementService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品库存控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 11:10:57
 */
@Controller
@Scope("prototype")
@RequestMapping("/inventoryManagement")
public class InventoryManagementController extends BaseController {

    private String PREFIX = "/main/inventoryManagement/";

    @Autowired
    private IInventoryManagementService inventoryManagementService;
    @Autowired
    private IIntegralrecordtypeService iIntegralrecordtypeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IProductReturnChangeService productReturnChangeService;
@Autowired
private IDeptService deptService;
    /**
     * 跳转到商品库存首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "inventoryManagement.html";
    }

    /**
     * 跳转到商品出库首页
     * @return
     */
    @RequestMapping("index_out")
    public String index_out() {
        return PREFIX + "inventoryManagement_out.html";
    }
    @RequestMapping("/order_page")
    public String order_page() {
        return PREFIX + "inventoryManagementOrder.html";
    }
    /**
     * 跳转到添加商品库存
     */
    @RequestMapping("/inventoryManagement_add")
    public String inventoryManagementAdd() {
        return PREFIX + "inventoryManagement_add.html";
    }
    /**
     * 跳转到添加商品库存出库
     */
    @RequestMapping("/inventoryManagement_out_add")
    public String inventoryManagement_out_add( Model model) {
        EntityWrapper<Dept> deptEntityWrapper = new EntityWrapper<>();
        deptEntityWrapper.eq("pid",ShiroKit.getUser().getDeptId());
        List<Dept> depts = deptService.selectList(deptEntityWrapper);
        model.addAttribute("depts",depts);
        return PREFIX + "inventoryManagement_out_add.html";
    }
    /**
     * 跳转到修改商品库存
     */
    @RequestMapping("/inventoryManagement_update/{inventoryManagementId}")
    public String inventoryManagementUpdate(@PathVariable Integer inventoryManagementId, Model model) {
        InventoryManagement inventoryManagement = inventoryManagementService.selectById(inventoryManagementId);
        model.addAttribute("item", inventoryManagement);
        LogObjectHolder.me().set(inventoryManagement);
        return PREFIX + "inventoryManagement_edit.html";
    }

    /**
     * 获取商品库存列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,Integer status) {
        Page<InventoryManagement> page = new PageFactory<InventoryManagement>().defaultPage();
        BaseEntityWrapper<InventoryManagement> baseEntityWrapper = new BaseEntityWrapper<>();
        if(!StringUtils.isEmpty(condition))baseEntityWrapper.like("name",condition);
        if(status!=null)baseEntityWrapper.eq("status",status);
        baseEntityWrapper.orderBy("createtime",false);
        Page<Map<String, Object>> page1 = inventoryManagementService.selectMapsPage(page, baseEntityWrapper);
        List<Map<String, Object>> records = page1.getRecords();
        records.forEach(a -> {
            User createuserid = userService.selectById(a.get("createuserid") + "");
            if (createuserid != null) {
                a.put("createuserid", createuserid.getName());
            }
            Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(a.get("integralrecordtypeid") + "");
            if (integralrecordtype != null) {
//                a.put("producttype", integralrecordtype.getProducttype() == 0 ? "礼品类" : integralrecordtype.getProducttype() == 1 ? "积分兑换类" : integralrecordtype.getProducttype() == 2 ? "销售类" : "积分+金额类");
                a.put("producttype",integralrecordtype.getProducttype()) ;
                a.put("productname",integralrecordtype.getProductname());
            }
            if(a.get("toDeptId")!=null){
                Dept toDeptId = deptService.selectById(a.get("toDeptId") + "");
                if(toDeptId!=null){
                    a.put("deptId",toDeptId.getFullname());
                }
            }
        });
        return super.packForBT(page1);
    }
    @RequestMapping(value = "/order")
    @ResponseBody
    public Object order(String condition) {
        Page<InventoryManagement> page = new PageFactory<InventoryManagement>().defaultPage();
        BaseEntityWrapper<InventoryManagement> baseEntityWrapper = new BaseEntityWrapper<>();
        if(!StringUtils.isEmpty(condition))baseEntityWrapper.like("memberName",condition);
        if(!StringUtils.isEmpty(condition))baseEntityWrapper.or().like("memberPhone",condition);
        baseEntityWrapper.eq("status",1);
        baseEntityWrapper.isNotNull("memberId");
        baseEntityWrapper.orderBy("createtime",false);
        Page<Map<String, Object>> page1 = inventoryManagementService.selectMapsPage(page, baseEntityWrapper);
        List<Map<String, Object>> records = page1.getRecords();
        records.forEach(a -> {
            Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(a.get("integralrecordtypeid") + "");
            if (integralrecordtype != null) {
                a.put("producttype",integralrecordtype.getProducttype()) ;
                a.put("productname",integralrecordtype.getProductname());
            }
            User createuserid = userService.selectById(a.get("createuserid") + "");
            if (createuserid != null) {
                a.put("createuserid", createuserid.getName());
            }
        });
        return super.packForBT(page1);
    }
    /**
     * 新增商品库存
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object add(InventoryManagement inventoryManagement, Integer productname) {
        //获取商品,名称
        Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(productname);
        integralrecordtype.setProductnum(integralrecordtype.getProductnum() + inventoryManagement.getConsumptionNum());
        iIntegralrecordtypeService.updateById(integralrecordtype);
        //新增库存记录表
        inventoryManagement.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        inventoryManagement.setCreateuserid(ShiroKit.getUser().getId() + "");
        inventoryManagement.setStatus("0");
//        inventoryManagement.setDeptid(ShiroKit.getUser().getDeptId() + "");
        inventoryManagement.setDeptid(integralrecordtype.getDeptid());
        inventoryManagement.setIntegralrecordtypeid(productname);
        inventoryManagement.setName(integralrecordtype.getProductname());
        inventoryManagementService.insert(inventoryManagement);
        return SUCCESS_TIP;
    }
    /**
     * 新增商品库存出库
     */
    @RequestMapping(value = "/out_add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object out_add(InventoryManagement inventoryManagement, Integer productname,String deptId) {
        //获取商品,名称
        Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(productname);
        integralrecordtype.setProductnum(integralrecordtype.getProductnum() - inventoryManagement.getConsumptionNum());
        iIntegralrecordtypeService.updateById(integralrecordtype);
        //新增库存记录表
        inventoryManagement.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        inventoryManagement.setCreateuserid(ShiroKit.getUser().getId() + "");
        inventoryManagement.setStatus("1");
        inventoryManagement.setDeptid(ShiroKit.getUser().getDeptId() + "");
        inventoryManagement.setIntegralrecordtypeid(productname);
        inventoryManagement.setToDeptId(deptId);
        inventoryManagement.setName(integralrecordtype.getProductname());
        inventoryManagementService.insert(inventoryManagement);
        //分部门进行添加商品
        EntityWrapper<Integralrecordtype> integralrecordtypeEntityWrapper = new EntityWrapper<>();
        integralrecordtypeEntityWrapper.eq("productPid",integralrecordtype.getId());
        integralrecordtypeEntityWrapper.eq("deptid",deptId);
        Integralrecordtype integralrecordtype1 = iIntegralrecordtypeService.selectOne(integralrecordtypeEntityWrapper);
        if(integralrecordtype1==null){//新增商品信息
            integralrecordtype.setId(null);
            integralrecordtype.setDeptid(deptId);
            integralrecordtype.setProductnum(0);
            integralrecordtype.setProductPid(productname);
            iIntegralrecordtypeService.insert(integralrecordtype);
        }else {
            integralrecordtype=integralrecordtype1;
        }
        //分部门进行添加库存
        {
            //获取商品,名称
            integralrecordtype.setProductnum(integralrecordtype.getProductnum() + inventoryManagement.getConsumptionNum());
            iIntegralrecordtypeService.updateById(integralrecordtype);
            //新增库存记录表
            inventoryManagement.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            inventoryManagement.setCreateuserid(ShiroKit.getUser().getId() + "");
            inventoryManagement.setStatus("0");
            inventoryManagement.setDeptid(deptId);
            inventoryManagement.setIntegralrecordtypeid(integralrecordtype.getId());
            inventoryManagement.setName(integralrecordtype.getProductname());
            inventoryManagementService.insert(inventoryManagement);
        }
        return SUCCESS_TIP;
    }
    /**
     * 申请退换货
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer inventoryManagementId,@RequestParam Integer type) throws Exception {
        EntityWrapper<ProductReturnChange> productReturnChangeEntityWrapper = new EntityWrapper<>();
        productReturnChangeEntityWrapper.eq("inventoryManagementId",inventoryManagementId);
        int i = productReturnChangeService.selectCount(productReturnChangeEntityWrapper);
        if(i>0){
           throw new Exception("该记录已执行退换货操作!");
        }
        InventoryManagement inventoryManagement = inventoryManagementService.selectById(inventoryManagementId);
        ProductReturnChange productReturnChange = new ProductReturnChange();
        productReturnChange.setCreatetime(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        productReturnChange.setCreateuserid(ShiroKit.getUser().getId()+"");
        productReturnChange.setDeptId(ShiroKit.getUser().getDeptId()+"");
        productReturnChange.setMemberId(Integer.parseInt(inventoryManagement.getMemberid()));
        productReturnChange.setMemberName(inventoryManagement.getMemberName());
        productReturnChange.setMemberPhone(inventoryManagement.getMemberPhone());
        productReturnChange.setProductId(inventoryManagement.getIntegralrecordtypeid());
        Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(inventoryManagement.getIntegralrecordtypeid());
        if(integralrecordtype!=null)productReturnChange.setProductName(integralrecordtype.getProductname());
        productReturnChange.setReturnchangeType(type);
        productReturnChange.setReturnchangeproductId(inventoryManagement.getIntegralrecordtypeid());
        productReturnChange.setReturnchangeproductName(integralrecordtype.getProductname());
        productReturnChange.setReturnchangeNum(inventoryManagement.getConsumptionNum());
        productReturnChange.setStatus(type);
        productReturnChange.setInventoryManagementId(inventoryManagementId);
        //设置积分表id
        productReturnChange.setIntegralrecodeId(inventoryManagement.getIntegralid());
        productReturnChangeService.insert(productReturnChange);
        return SUCCESS_TIP;
    }

    /**
     * 修改商品库存
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(InventoryManagement inventoryManagement) {
        inventoryManagementService.updateById(inventoryManagement);
        return SUCCESS_TIP;
    }

    /**
     * 商品库存详情
     */
    @RequestMapping(value = "/detail/{inventoryManagementId}")
    @ResponseBody
    public Object detail(@PathVariable("inventoryManagementId") Integer inventoryManagementId) {
        return inventoryManagementService.selectById(inventoryManagementId);
    }
}
