package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IInventoryManagementService;
import com.stylefeng.guns.modular.main.service.impl.InventoryManagementServiceImpl;
import com.stylefeng.guns.modular.system.dao.InventoryManagementMapper;
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
        Map<String,Object> rowsMap = new HashMap();
        List<Map<String,Object>> maps = inventoryManagementService.findSellNumber(deptList);
        Map<String,Integer> map1 = new HashMap<>();
        Integer sumNumber = 0;
        for (Map<String, Object> map : maps) {
            map1.put(map.get("name").toString(),Integer.parseInt(map.get("consumptionNum").toString()));
            sumNumber += Integer.parseInt(map.get("consumptionNum").toString());
        }
        rowsMap.put("all",sumNumber);
        rowsMap.put("charts",map1);

        Map<String,Object> map2 = new HashMap<>();
        for(Map.Entry<String,Integer> m : map1.entrySet()){
            map2.put((m.getKey()+" "),m.getValue());
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        resultList.add(rowsMap);
        resultList.add(map2);
        return resultList;
    }
}
