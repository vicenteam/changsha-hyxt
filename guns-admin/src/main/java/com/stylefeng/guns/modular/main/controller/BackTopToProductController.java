package com.stylefeng.guns.modular.main.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.system.model.Integralrecordtype;
import com.stylefeng.guns.modular.system.model.InventoryManagement;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.BackTopToProduct;
import com.stylefeng.guns.modular.main.service.IBackTopToProductService;

import java.util.Date;
import java.util.List;

/**
 * 退回商品记录控制器
 *
 * @author fengshuonan
 * @Date 2018-12-26 14:22:08
 */
@Controller
@RequestMapping("/backTopToProduct")
public class BackTopToProductController extends BaseController {

    private String PREFIX = "/main/backTopToProduct/";

    @Autowired
    private IBackTopToProductService backTopToProductService;
    @Autowired
    private IIntegralrecordtypeService iIntegralrecordtypeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private InventoryManagementController inventoryManagementController;

    /**
     * 跳转到退回商品记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "backTopToProduct.html";
    }

    /**
     * 跳转到添加退回商品记录
     */
    @RequestMapping("/backTopToProduct_add")
    public String backTopToProductAdd() {
        return PREFIX + "backTopToProduct_add.html";
    }

    /**
     * 跳转到修改退回商品记录
     */
    @RequestMapping("/backTopToProduct_update/{backTopToProductId}")
    public String backTopToProductUpdate(@PathVariable Integer backTopToProductId, Model model) {
        BackTopToProduct backTopToProduct = backTopToProductService.selectById(backTopToProductId);
        model.addAttribute("item",backTopToProduct);
        LogObjectHolder.me().set(backTopToProduct);
        return PREFIX + "backTopToProduct_edit.html";
    }

    /**
     * 获取退回商品记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<BackTopToProduct> page = new PageFactory<BackTopToProduct>().defaultPage();
        BaseEntityWrapper<BackTopToProduct> baseEntityWrapper = new BaseEntityWrapper<>();
        Page<BackTopToProduct> result = backTopToProductService.selectPage(page, baseEntityWrapper);
        List<BackTopToProduct> records = result.getRecords();
        records.forEach(a->{
            String createUserId = a.getCreateUserId();
            User user = userService.selectById(createUserId);
            if(user!=null)a.setCreateUserId(user.getName());
        });
        return super.packForBT(result);
    }

    /**
     * 新增退回商品记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object add(BackTopToProduct backTopToProduct,Integer consumptionNum,String productname) {
        Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(productname);
        if(integralrecordtype!=null){
            backTopToProduct.setBackTopToCreateTime(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            backTopToProduct.setBackTopToMyProductId(Integer.parseInt(productname));
            backTopToProduct.setDeptId(ShiroKit.getUser().getDeptId());
            backTopToProduct.setBackTopToNum(consumptionNum);
            backTopToProduct.setCreateUserId(ShiroKit.getUser().id.toString());
            backTopToProduct.setBackTopToProductName(integralrecordtype.getProductname());
            Integralrecordtype integralrecordtype1 = new Integralrecordtype();
            integralrecordtype1.setProductPid(integralrecordtype.getProductPid());
             getProductTopPid(integralrecordtype1);
            if(integralrecordtype1!=null){
                backTopToProduct.setBackTopToProductId(integralrecordtype1.getProductPid());
            }
        }
        backTopToProductService.insert(backTopToProduct);

        //扣除当前库存
        Integralrecordtype integralrecordtype1 = iIntegralrecordtypeService.selectById(backTopToProduct.getBackTopToMyProductId());
        if(integralrecordtype1.getProductnum()>=consumptionNum){
            integralrecordtype1.setProductnum((integralrecordtype1.getProductnum()-consumptionNum));
            iIntegralrecordtypeService.updateById(integralrecordtype1);
        }else {
            throw new GunsException(BizExceptionEnum.NUM_IS_ERROR);
        }
        //添加总部库存
        InventoryManagement inventoryManagement = new InventoryManagement();
        inventoryManagement.setConsumptionNum(consumptionNum);
        inventoryManagementController.add(inventoryManagement,backTopToProduct.getBackTopToProductId());
        return SUCCESS_TIP;
    }

    public void getProductTopPid(Integralrecordtype type){
        Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(type.getProductPid());
         if(integralrecordtype.getProductPid()!=null) {
             type.setProductPid(integralrecordtype.getProductPid());
             type.setId(integralrecordtype.getId());
            getProductTopPid(type);
        }

    }
    /**
     * 删除退回商品记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer backTopToProductId) {
        backTopToProductService.deleteById(backTopToProductId);
        return SUCCESS_TIP;
    }

    /**
     * 修改退回商品记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BackTopToProduct backTopToProduct) {
        backTopToProductService.updateById(backTopToProduct);
        return SUCCESS_TIP;
    }

    /**
     * 退回商品记录详情
     */
    @RequestMapping(value = "/detail/{backTopToProductId}")
    @ResponseBody
    public Object detail(@PathVariable("backTopToProductId") Integer backTopToProductId) {
        return backTopToProductService.selectById(backTopToProductId);
    }
}
