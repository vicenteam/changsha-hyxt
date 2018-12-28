package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IInventoryManagementService;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.Integralrecordtype;
import com.stylefeng.guns.modular.system.model.InventoryManagement;
import com.stylefeng.guns.modular.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销量统计controller
 */
@Controller
@RequestMapping("/sell")
public class SellController {

    private String PREFIX = "/main/bar/";

    @Autowired
    private IIntegralrecordtypeService integralrecordtypeService;
    @Autowired
    private IInventoryManagementService inventoryManagementService;
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转销量统计
     *
     * @return
     */
    @RequestMapping("")
    public String index(Model model) {
        List<Dept> deptList = new ArrayList<>();
        List<Dept> depts = getTreeMenuList(deptList,ShiroKit.getUser().getDeptId());
        model.addAttribute("depts", depts);
        return PREFIX + "watermark.html";
    }

    @RequestMapping("/findChildren")
    @ResponseBody
    public Object findChildren(Integer deptId) {
        EntityWrapper<Dept> deptEntityWrapper = new EntityWrapper<>();
        deptEntityWrapper.eq("pid", deptId);
        List<Dept> depts = deptService.selectList(deptEntityWrapper);
        return depts;
    }

    public List<Dept> getTreeMenuList(List<Dept> domainList, Integer pid) {
        //加入它本身
        Dept dept = new Dept();
        dept.setId(pid);
        domainList.add(deptService.selectById(dept));
        //加入子集
        EntityWrapper<Dept> dWrapper = new EntityWrapper<>();
        dWrapper.eq("pid", pid);
        List<Dept> depts = deptService.selectList(dWrapper);
        for (Dept type : depts) {
            getTreeMenuList(domainList, type.getId());
    }
        return domainList;
    }

    @RequestMapping("/initSellData")
    @ResponseBody
    public Object initSellData(Integer deptId, String begindate, String enddate) {
        if(deptId == null ){
            deptId = ShiroKit.getUser().getDeptId();
        }
        List<Dept> depts = new ArrayList<>();
        List<Dept> deptList = getTreeMenuList(depts, deptId);
        List<InventoryManagement> inventoryManagements = new ArrayList<>();
        for (Dept dept : deptList) {
            EntityWrapper<InventoryManagement> wrapper = new EntityWrapper();
            wrapper.eq("deptid",dept.getId());
            wrapper.eq("status",1); //
            wrapper.ne("memberid","");
            if(! StringUtils.isEmpty(begindate)) wrapper.gt("createtime",begindate);
            if(! StringUtils.isEmpty(enddate)) wrapper.lt("createtime",enddate);
            wrapper.isNotNull("memberid");
            InventoryManagement inventoryManagement = inventoryManagementService.selectOne(wrapper);
            if(inventoryManagement != null) inventoryManagements.add(inventoryManagement);
        }
        Map<String,Object> rowsMap = new HashMap();
        Map<String,Object> map1 = new HashMap<>();
//        Map<String,Object> map2 = new HashMap<>();
        Integer oldNum; Integer newNum; Integer zongNum = 0;
        for (InventoryManagement inv : inventoryManagements) {
            if(! map1.isEmpty() && ! StringUtils.isEmpty(map1.get(inv.getName()))){
                oldNum = (Integer) map1.get(inv.getName());
                newNum = inv.getConsumptionNum();
                map1.put(inv.getName(),oldNum + newNum);
                zongNum += newNum;
            }else {
                map1.put(inv.getName(),inv.getConsumptionNum());
                zongNum += inv.getConsumptionNum();
            }
        }
        rowsMap.put("all",zongNum);
        rowsMap.put("charts",map1);
        rowsMap.put("components","");
        rowsMap.put("ie",9743);

        Map<String,Object> map2 = new HashMap<>();
        for(Map.Entry<String,Object> m : map1.entrySet()){
            map2.put((m.getKey()+" "),m.getValue());
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        resultList.add(rowsMap);
        resultList.add(map2);
        return resultList;
//        return null;
    }
}
