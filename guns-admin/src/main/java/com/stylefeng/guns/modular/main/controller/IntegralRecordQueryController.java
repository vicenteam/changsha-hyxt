package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.main.service.IIntegralrecordService;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.system.model.Integralrecord;
import com.stylefeng.guns.modular.system.model.Integralrecordtype;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 积分记录查询控制器
 *
 * @author fengshuonan
 * @Date 2018-08-14 16:47:26
 */
@Controller
@RequestMapping("/integralrecordquery")
public class IntegralRecordQueryController extends BaseController {

    private String PREFIX = "/main/integralrecord/";

    @Autowired
    private IIntegralrecordService integralrecordService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IIntegralrecordtypeService integralrecordtypeService;

    @RequestMapping("")
    public String index(Model model){
        BaseEntityWrapper<Integralrecord> iWrapper = new BaseEntityWrapper<>();
        iWrapper.groupBy("integralType");
        List<Integralrecord> types1 = integralrecordService.selectList(iWrapper);
        model.addAttribute("types1",types1);
        BaseEntityWrapper<User> wrapper = new BaseEntityWrapper<>();
        model.addAttribute("users",userService.selectList(wrapper));
        return PREFIX + "integralRecordQuery.html";
    }

    @RequestMapping("findIntegralType")
    @ResponseBody
    public Object findIntegralType(String type){
        BaseEntityWrapper<Integralrecord> iWrapper = new BaseEntityWrapper<>();
        iWrapper.eq("integralType",type);
        if(type.equals("1")) iWrapper.groupBy("typeId");
        if(type.equals("2")) iWrapper.groupBy("otherTypeId");
        List<Map<String,String>> types2 = integralrecordService.selectMaps(iWrapper);
        types2.forEach(e->{
            if(type.equals("1")){
                Integralrecordtype integralrecordtype = new Integralrecordtype();
                integralrecordtype.setId(Integer.parseInt(e.get("typeId")));
                e.put("name",integralrecordtypeService.selectById(integralrecordtype).getProductname());
            }else{
                if(e.get("otherTypeId").equals("0")){
                    e.put("name","签到积分");
                }else if(e.get("otherTypeId").equals("1")){
                    e.put("name","带新人积分");
                }else if(e.get("otherTypeId").equals("2")){
                    e.put("name","活动兑换积分");
                }
                else if(e.get("otherTypeId").equals("3")){
                    e.put("name","被推荐人打卡奖励积分");
                }
            }
        });
        return types2;
    }

    /**
     * 查询积分记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String operator, String memberName, String cadId
                        , String type, String integralType, String begindate, String enddate, String memberId) {

        BaseEntityWrapper<Membermanagement> mWrapper = new BaseEntityWrapper<>();
        if(memberId != null && ! memberId.equals("")){ //按直接读卡
            mWrapper.eq("id",memberId);
        }else { //按搜索条件
            if(! StringUtils.isEmpty(cadId)) mWrapper.like("cadID",cadId);
            if(! StringUtils.isEmpty(memberName)) mWrapper.like("name",memberName);
        }
        //会员 memberName、cadId 条件筛选
        List<Membermanagement> membermanagements = membermanagementService.selectList(mWrapper);
        Integer[] mIdArray = new Integer[membermanagements.size()];
        for(int i=0; i<mIdArray.length; i++){
            mIdArray[i] = membermanagements.get(i).getId();
        }

        //操作人 operator 条件筛选
        BaseEntityWrapper<User> uWrapper = new BaseEntityWrapper<>();
        if(! operator.equals("-1")) uWrapper.eq("id",operator);
        List<User> users = userService.selectList(uWrapper);
        Integer[] uIdArray = new Integer[users.size()];
        for(int i=0; i<uIdArray.length; i++){
            uIdArray[i] = users.get(i).getId();
        }

        //把 membermanagement 与 user 条件放入 积分记录表实现条件分页查询
        Page<Integralrecord> page = new PageFactory<Integralrecord>().defaultPage();
        BaseEntityWrapper<Integralrecord> iWrapper = new BaseEntityWrapper<>();
        if(! StringUtils.isEmpty(type)) iWrapper.eq("integralType",type);
        if(type.equals("1")){
            if(! StringUtils.isEmpty(integralType)) iWrapper.eq("typeId",integralType);
        }else if(type.equals("2")){
            if(! StringUtils.isEmpty(integralType)) iWrapper.eq("otherTypeId",integralType);
        }
        if(mIdArray.length <= 0) mIdArray = new Integer[]{-1}; //判断数组 <=0 赋予初始值 方便查询
        iWrapper.in("memberid",mIdArray);
        if(uIdArray.length <= 0) uIdArray = new Integer[]{-1}; //判断数组 <=0 赋予初始值 方便查询
        iWrapper.in("staffid",uIdArray);
        if(! StringUtils.isEmpty(begindate) || ! StringUtils.isEmpty(enddate)){
            iWrapper.between("createTime",begindate,enddate);
        }
        iWrapper.orderBy("createTime",false);
        Page<Map<String, Object>> serverPage = integralrecordService.selectMapsPage(page, iWrapper);
        if (serverPage.getRecords().size() >= 0){
            for(Map<String, Object> map : serverPage.getRecords()){
                Integralrecordtype integralrecordtype = new Integralrecordtype();
                if(map.get("typeId") != null){
                    integralrecordtype.setId(Integer.parseInt(map.get("typeId").toString()));
                    map.put("typeId",integralrecordtypeService.selectById(integralrecordtype).getProductname());
                }
                if(map.get("otherTypeId") != null){
                    if(map.get("otherTypeId").equals("0")){
                        map.put("typeId","签到积分");
                    }else if(map.get("otherTypeId").equals("1")){
                        map.put("typeId","带新人积分");
                    }else if(map.get("otherTypeId").equals("2")){
                        map.put("typeId","活动兑换积分");
                    }
                    else if(map.get("otherTypeId").equals("3")){
                        map.put("typeId","被推荐人打卡奖励积分");
                    }
                }
//                map.put("typeName",integralrecordtypeService.selectById(map.get("typeId").toString()).getProducttype()); //获取积分类型
                if(map.get("memberid") != null){
                    Membermanagement membermanagement = membermanagementService.selectById(map.get("memberid").toString());
                    map.put("memberName",membermanagement.getName());
                    map.put("memberPhone",membermanagement.getPhone());
                    map.put("membercadid",membermanagement.getCadID());
                }
                if(map.get("staffid") != null){
                    map.put("staffName",userService.selectById(map.get("staffid").toString()).getName());
                }
            }
        }
        return super.packForBT(serverPage);
    }

    @RequestMapping(value = "test")
    @ResponseBody
    public Object shouwMemberInfo(){
        return null;
    }

}
