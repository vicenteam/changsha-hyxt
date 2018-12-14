package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.Membershipcardtype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/piesimple")
public class PiesimpleController extends BaseController {
    private String PREFIX = "/main/bar/";
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    /**
     * 跳转到会员分布数据图表
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "piesimple.html";
    }
    @RequestMapping(value = "/getData")
    @ResponseBody
    public Object getData() throws ParseException {
       List<Map<String,Object>> result=new ArrayList<>();
        BaseEntityWrapper<Membermanagement> wapper= new BaseEntityWrapper<Membermanagement>();
        BaseEntityWrapper<Membershipcardtype> membershipcardtypeBaseEntityWrapper= new BaseEntityWrapper<>();
       List<Membershipcardtype> list=membershipcardtypeService.selectList(membershipcardtypeBaseEntityWrapper);
       int index=0;
       for(Membershipcardtype membershipcardtype:list){
          Integer meid= membershipcardtype.getId();
          String mename=membershipcardtype.getCardname();
           wapper= new BaseEntityWrapper<Membermanagement>();
           wapper.eq("levelID",meid);
          int count= membermanagementService.selectCount(wapper);
           Map<String,Object> map=new HashMap<>();
           map.put("name",mename);
           map.put("value",count);
           map.put("index",index);
           result.add(map);
           index++;
       }
        return result;
    }
}
