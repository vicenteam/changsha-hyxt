package com.stylefeng.guns.modular.main.controller;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IInventoryManagementService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.system.controller.DeptController;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDictService;
import org.springframework.context.annotation.Scope;
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
import com.stylefeng.guns.modular.main.service.IIntegralrecordService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 新增积分控制器
 *
 * @author fengshuonan
 * @Date 2018-08-14 16:47:26
 */
@Controller
@Scope("prototype")
@RequestMapping("/integralrecord")
public class IntegralrecordController extends BaseController {

    private String PREFIX = "/main/integralrecord/";

    @Autowired
    private IIntegralrecordService integralrecordService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private MembermanagementController membermanagementController;
    @Autowired
    private IIntegralrecordtypeService integralrecordtypeService;
    @Autowired
    private IInventoryManagementService inventoryManagementService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private SellController sellController;
    @Autowired
    private DeptController deptController;


    /**
     * 跳转到新增积分首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "integralrecord.html";
    }

    @RequestMapping("productSalesRankingPage")
    public String productSalesRankingPage(Model model) {
        List<Dept> deptList = new ArrayList<>();
        List<Dept> depts = sellController.getTreeMenuList(deptList,ShiroKit.getUser().getDeptId());
        model.addAttribute("depts", depts);
        return PREFIX + "productSalesRankingPage.html";
    }

    /**
     * 跳转到添加新增积分
     */
    @RequestMapping("/integralrecord_add")
    public String integralrecordAdd(Model model) {
        EntityWrapper tWrapper = new EntityWrapper();
        tWrapper.notIn("names","积分清零","积分恢复","积分兑换");
        List<Integralrecordtype> types = integralrecordtypeService.selectList(tWrapper);
        model.addAttribute("type",types);
        //获取快捷积分
        EntityWrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper.eq("code","quickjf");
        Dict dict = dictService.selectOne(dictEntityWrapper);
        dictEntityWrapper=new EntityWrapper<>();
        dictEntityWrapper.eq("pid",dict.getId());
        List<Dict> dicts = dictService.selectList(dictEntityWrapper);
        model.addAttribute("kjjf",dicts);
        return PREFIX + "integralrecord_add.html";
    }

    /**
     * 跳转到修改新增积分
     */
    @RequestMapping("/integralrecord_update/{integralrecordId}")
    public String integralrecordUpdate(@PathVariable Integer integralrecordId, Model model) {
        Integralrecord integralrecord = integralrecordService.selectById(integralrecordId);
        model.addAttribute("item",integralrecord);
        LogObjectHolder.me().set(integralrecord);
        return PREFIX + "integralrecord_edit.html";
    }

    /**
     * 获取新增积分列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Integralrecord> page = new PageFactory<Integralrecord>().defaultPage();
        BaseEntityWrapper<Integralrecord> baseEntityWrapper = new BaseEntityWrapper<>();
        Page<Integralrecord> result = integralrecordService.selectPage(page, baseEntityWrapper);
        return super.packForBT(result);
    }

    /**
     * 商品销量排名
     * @param deptId
     * @param monthTime1
     * @param monthTime2
     * @param periodTime1
     * @param periodTime2
     * @param orderBy
     * @param desc
     * @return
     */
    @RequestMapping(value = "/productSalesRanking")
    @ResponseBody
    public Object productSalesRanking(Integer offset,
                                      Integer limit,
                                      Integer deptId,
                                      String monthTime1,
                                      String monthTime2,
                                      String periodTime1,
                                      String periodTime2,
                                      String orderBy,
                                      String desc) {
        String format1 = DateUtil.format(new Date(), "yyyy-MM");
        String format2 = DateUtil.format(new Date(), "yyyy-MM-dd");
        monthTime1=format1+"-01";
        monthTime2=format2;

        HttpServletRequest request = HttpKit.getRequest();
       try {
           orderBy = request.getParameter("sort");         //排序字段名称
           desc = request.getParameter("order");       //asc或desc(升序或降序)
       }catch (Exception e){

       }

       //获取deptids
        List<Map<String, Object>> list=( List<Map<String, Object>>) deptController.findDeptLists(deptId.toString());
       String deptIds="";
       for(Map<String, Object> map:list){
           deptIds+=map.get("id")+",";
       }
        deptIds=deptIds.substring(0,deptIds.length()-1);
        Page<Map<String,Object>> page = new PageFactory<Map<String,Object>>().defaultPage();
        System.out.println(JSON.toJSONString(page));
        int i = integralrecordService.productSalesRankingintCount(page.getOffset(), page.getLimit(), deptIds.toString(), monthTime1, monthTime2, periodTime1, periodTime2, orderBy, desc);
        page.setTotal(i);
        List<Map<String, Object>> mapList = integralrecordService.productSalesRanking(page.getOffset(), page.getLimit(), deptIds.toString(), monthTime1, monthTime2, periodTime1, periodTime2, orderBy, desc);
        page.setRecords(mapList);
        return super.packForBT(page);
    }
    /**
     * 新增积分
     */
    @BussinessLog(value = "新增会员积分", key = "xzhyjf")
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object add(Double integral, Integer productname, Integer memberId,Integer consumptionNum) throws Exception {
        BaseEntityWrapper<Membermanagement> mWrapper = new BaseEntityWrapper<>();
        mWrapper.eq("id",memberId);
        List<Membermanagement> membermanagements = membermanagementService.selectList(mWrapper);
        //积分添加操作
        List<Integralrecord> integralrecords = insertIntegral(integral,1,productname,membermanagements);

        //更新库存
        BaseEntityWrapper<Integralrecordtype> typeWrapper = new BaseEntityWrapper<>();
        typeWrapper.eq("id",productname);
        Integralrecordtype integralrecordtype = integralrecordtypeService.selectOne(typeWrapper);
        integralrecordtype.setProductnum(integralrecordtype.getProductnum()-consumptionNum);//库存减
        if(integralrecordtype.getProductnum()-consumptionNum<0){
            throw new GunsException(BizExceptionEnum.NUM_IS_ERROR);
        }
        integralrecordtype.setUpdatetime(DateUtil.getTime());
        integralrecordtype.setUpdateuserid(ShiroKit.getUser().getId().toString());
        integralrecordtypeService.updateById(integralrecordtype);

        //插入商品记录
        InventoryManagement inventoryManagement = new InventoryManagement();
        inventoryManagement.setCreatetime(DateUtil.getTime());
        inventoryManagement.setCreateuserid(ShiroKit.getUser().getId().toString());
        inventoryManagement.setDeptid(ShiroKit.getUser().getDeptId().toString());
        inventoryManagement.setIntegralrecordtypeid(integralrecordtype.getId());
        inventoryManagement.setStatus("1");
        inventoryManagement.setMemberPhone(membermanagements.get(0).getPhone());
        inventoryManagement.setMemberid(memberId.toString());
        inventoryManagement.setConsumptionNum(1);
        inventoryManagement.setName(integralrecordtype.getProductname());
        inventoryManagement.setMemberName(membermanagements.get(0).getName());
        inventoryManagement.setIntegralid(integralrecords.get(0).getId());
        inventoryManagement.setConsumptionNum(consumptionNum);
        inventoryManagementService.insert(inventoryManagement);
        return SUCCESS_TIP;
    }

    /**
     * 会员积分增加并新增记录
     * @param integral
     * @param type
     * @param typeId
     * @param mList
     * @return
     * @throws Exception
     */
    public List<Integralrecord> insertIntegral(double integral, Integer type, Integer typeId, List<Membermanagement> mList) throws Exception {
        List<Integralrecord> integralrecords = new ArrayList<>();
        Integralrecord integralrecord = new Integralrecord();
        double nowIntegral = 0;
        double nowCountPrice = 0;
        for(Membermanagement memberId : mList){  //循环当前门店会员列表为
            nowIntegral = memberId.getIntegral();
            nowCountPrice = memberId.getCountPrice();
            if(type == 1){
                if(integral < 0){ //扣除类积分
                    if((nowIntegral + integral) >= 0){
                        memberId.setIntegral(nowIntegral + integral);
                    }else {
                        throw new Exception("可用积分不足！");
                    }
                }else {
                    memberId.setIntegral(nowIntegral + integral);
                    memberId.setCountPrice(nowCountPrice + integral);
                }
            }else if(type == 2){
                if(typeId == 2) { //扣除积分
                    if((nowIntegral - integral) >= 0){
                        memberId.setIntegral(nowIntegral - integral);
                    }else {
                        throw new Exception("可用积分不足！");
                    }
                }else {
                    memberId.setIntegral(nowIntegral + integral);
                    memberId.setCountPrice(nowCountPrice + integral);
                }
            }
            //更新会员总积分和实际积分
            membermanagementService.updateById(memberId);
            membermanagementController.updateMemberLeave(memberId.getId()+"");

            if(type == 1){ // type=1 商品积分
                integralrecord.setIntegralType(type.toString());
                integralrecord.setTypeId(typeId.toString());
            }else if(type == 2){ // type=2 行为积分
                integralrecord.setIntegralType(type.toString());
                integralrecord.setOtherTypeId(typeId.toString());
            }
            //添加积分记录
            integralrecord.setIntegral(integral);
            if(type==2&&typeId==2)integralrecord.setIntegral(-integral);
            integralrecord.setCreateTime(DateUtil.getTime());
            integralrecord.setMemberid(memberId.getId());
            integralrecord.setDeptid(ShiroKit.getUser().getDeptId());
            integralrecord.setStaffid(ShiroKit.getUser().getId());
            integralrecordService.insert(integralrecord);
            integralrecords.add(integralrecord);
        }
        return integralrecords;
    }

    /**
     * 删除新增积分
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer integralrecordId) {
        integralrecordService.deleteById(integralrecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改新增积分
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Integralrecord integralrecord) {
        integralrecordService.updateById(integralrecord);
        return SUCCESS_TIP;
    }

    /**
     * 新增积分详情
     */
    @RequestMapping(value = "/detail/{integralrecordId}")
    @ResponseBody
    public Object detail(@PathVariable("integralrecordId") Integer integralrecordId) {
        return integralrecordService.selectById(integralrecordId);
    }
}
