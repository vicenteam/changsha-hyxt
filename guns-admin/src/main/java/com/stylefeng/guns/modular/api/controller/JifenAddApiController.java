package com.stylefeng.guns.modular.api.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.jifen.JifenAddModel;
import com.stylefeng.guns.modular.api.model.jifen.JifenPageDataMode;
import com.stylefeng.guns.modular.api.model.jifen.ZengSongJifenModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.IntegralrecordController;
import com.stylefeng.guns.modular.main.controller.MembermanagementController;
import com.stylefeng.guns.modular.main.service.IIntegralrecordService;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.system.model.Integralrecordtype;
import com.stylefeng.guns.modular.system.model.MemberCard;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.Membershipcardtype;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/api/jifenaddapi")
@Api(description = "新增积分")
public class JifenAddApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(JifenAddApiController.class);

    @Autowired
    private MembermanagementController membermanagementController;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IntegralrecordController integralrecordController;
    @Autowired
    private IIntegralrecordtypeService integralrecordtypeService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;

    @RequestMapping(value = "/findUserInfo", method = RequestMethod.POST)
    @ApiOperation("读卡查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "cardCode", value = "卡片信息", paramType = "query"),
    })
    public ResponseData<JifenAddModel> findUserInfo(RequstData requstData, String cardCode) throws Exception {
        ResponseData<JifenAddModel> responseData = new ResponseData();
        try {
            MemberCard userInfo = (MemberCard)membermanagementController.getUserInfo(cardCode);
            Integer memberid = userInfo.getMemberid();
            Membermanagement membermanagement = membermanagementService.selectById(memberid);
            JifenAddModel change = new ReflectionObject<JifenAddModel>().change(membermanagement, new JifenAddModel());
            change.setJifenAddUserId(memberid);
            //获取积分类型
            String deptId = membermanagement.getDeptId();
            EntityWrapper<Integralrecordtype> integralrecordtypeEntityWrapper = new EntityWrapper<>();
            List<Map<String, Object>> mapList = integralrecordtypeService.selectMaps(integralrecordtypeEntityWrapper);
            if(mapList==null)throw new NoSuchMethodException("无积分类型");
            change.setJifenType(mapList);
            //设置卡等级
            Membershipcardtype membershipcardtype = membershipcardtypeService.selectById(change.getLevelID());
            if(membershipcardtype!=null)change.setLevelID(membershipcardtype.getCardname());
            responseData.setDataCollection(change);
        }catch (Exception e){
            throw new Exception("系统异常!"+e.getMessage());
        }
        return responseData;
    }
    @RequestMapping(value = "/addJifen", method = RequestMethod.POST)
    @ApiOperation("新增积分")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "jifenAddUserId", value = "卡片用户id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "nowPice", value = "新增积分", paramType = "query"),
            @ApiImplicitParam(required = true, name = "jifenType", value = "积分类型", paramType = "query"),
            @ApiImplicitParam(required = true, name = "consumptionNum", value = "购买数量", paramType = "query"),
    })
    public ResponseData addJifen(RequstData requstData,Integer jifenAddUserId,Double nowPice,Integer jifenType,Integer consumptionNum) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            integralrecordController.add(nowPice,jifenType,jifenAddUserId,consumptionNum);
        }catch (Exception e){
            throw new Exception("新增失败 请联系管理员!");
        }
        return responseData;
    }

    @RequestMapping(value = "/getLeaveInfo", method = RequestMethod.POST)
    @ApiOperation("获取卡等级")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
//            @ApiImplicitParam(required = true, name = "leaveId", value = "卡等级", paramType = "query"),
//            @ApiImplicitParam(required = true, name = "nowPice", value = "积分值", paramType = "query"),
    })
    public ResponseData<List<ZengSongJifenModel>> getLeaveInfo(RequstData requstData) throws Exception {
        ResponseData<List<ZengSongJifenModel>> responseData = new ResponseData();
        try {

            BaseEntityWrapper<Membershipcardtype> zengSongJifenModelBaseEntityWrapper=new BaseEntityWrapper();
            List<Membershipcardtype> list=membershipcardtypeService.selectList(zengSongJifenModelBaseEntityWrapper);
            List<ZengSongJifenModel> list1=new ArrayList<>();
            list.forEach(a->{
                ZengSongJifenModel zengSongJifenModel=new ZengSongJifenModel();
                zengSongJifenModel.setLeaveId(a.getId());
                zengSongJifenModel.setLeaveName(a.getCardname());
                list1.add(zengSongJifenModel);
            });
            responseData.setDataCollection(list1);
        }catch (Exception e){
            throw new Exception("获取会员卡等级失败 请联系管理员!");
        }
        return responseData;
    }
    @RequestMapping(value = "/zengsongjifen", method = RequestMethod.POST)
    @ApiOperation("赠送积分")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "leaveIds", value = "卡等级id组合（例 43，55，66）", paramType = "query"),
            @ApiImplicitParam(required = true, name = "nowPice", value = "积分值", paramType = "query"),
    })
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData zengsongjifen(RequstData requstData,String leaveIds,Double nowPice) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            BaseEntityWrapper<Membermanagement> wrapper = new BaseEntityWrapper<>();
            if(leaveIds!=null&&leaveIds.length()>0){
                wrapper.in("levelID",leaveIds);
            }
            List<Membermanagement> ms = membermanagementService.selectList(wrapper);
            //积分添加操作
            integralrecordController.insertIntegral(nowPice,2,10,ms);

        }catch (Exception e){
            throw new Exception("赠送积分失败 请联系管理员!");
        }
        return responseData;
    }
}
