package com.stylefeng.guns.modular.api.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.memerber.*;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.MembermanagementController;
import com.stylefeng.guns.modular.main.service.*;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memberClientInfo")
@Api(description = "会员信息")
public class MemberInfoController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(MemberInfoController.class);

    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IActivityMemberService activityMemberService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private MemberApiController memberApiController;
    @Autowired
    private MembermanagementController membermanagementController;


    @RequestMapping(value = "resultInfo",method = RequestMethod.POST)
    @ApiOperation("会员详情获取")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "selectId", value = "查询id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectType", value = "查询方式(1 读卡查询 2 姓名,身份证查询 3 memberId查询)", paramType = "query"),
    })
    public ResponseData<MemberInfoModel> searchMemberInfo(RequstData requstData, String selectId, String selectType)throws Exception{
        ResponseData<MemberInfoModel> responseData = new ResponseData<>();
        // 调用查询方式接口
        ResponseData<MemberModel> dataCollection = memberApiController.getMemberInfo(requstData, selectId, selectType);
        MemberModel modelInfo = dataCollection.getDataCollection();
        if(modelInfo != null){
            Membermanagement mInfo = membermanagementService.selectById(modelInfo.getMemberId()); //获取会员信息
            StringBuilder mIName = new StringBuilder();
            Membermanagement mIntroducer = membermanagementService.selectById(mInfo.getIntroducerId()); // 获取推荐人信息
            if(mIntroducer != null) mIName.append(mIntroducer.getName());
            EntityWrapper<ActivityMember> aWrapper = new EntityWrapper<>();
            aWrapper.eq("memberid",mInfo.getId());
            aWrapper.eq("deptid",mInfo.getDeptId());
//            aWrapper.groupBy("memberid");
            List<ActivityMember> aList = activityMemberService.selectList(aWrapper); //获取活动信息表数据
            Integer aCount = 0;
            if(aList != null){  //获取会员活动次数
                aCount = aList.size();
            }
            Membershipcardtype tName = membershipcardtypeService.selectById(Integer.parseInt(mInfo.getLevelID())); //获取会员等级
            Dept dName = deptService.selectById(mInfo.getDeptId()); // 获取门店名称
            User uName = userService.selectById(mInfo.getStaffID()); //获取服务员工
            System.out.println(uName==null);
            // 获取签到、复签次数
            Map<String, Object> maps = this.signInCount(mInfo.getId(),mInfo.getDeptId(),"","");
            //result info
            MemberInfoModel infoModel = new ReflectionObject<MemberInfoModel>().change(mInfo, new MemberInfoModel());
            infoModel.setAvatar("http://47.104.252.44:8081/kaptcha/"+mInfo.getAvatar());
            infoModel.setCadID(mInfo.getCadID());
            infoModel.setCreateDt(mInfo.getCreateTime());
            infoModel.setDeptName(dName.getFullname());
            infoModel.setServicePerson(uName.getName());
            infoModel.setIntroducer(mIName.toString());
            infoModel.setSignInNew(maps.get("signInNew").toString()); //最新签到时间
            infoModel.setSignInCount((Integer) maps.get("signInCount")); //签到次数
            infoModel.setSignOutNew(maps.get("signOutNew").toString()); //最新复签时间
            infoModel.setSignOutCount((Integer) maps.get("signOutCount")); //复签次数
            infoModel.setActivityNumber(aCount);
            infoModel.setMemberStatus(mInfo.getState());
            infoModel.setLevelId(tName.getCardname());
            infoModel.setCountPrice(mInfo.getCountPrice());
            infoModel.setIntegral(mInfo.getIntegral());
            //result info
            responseData.setDataCollection(infoModel);
        }else {
            throw new NullPointerException("卡片信息无效");
        }
        return responseData;
    }

    /**
     *  获取签到、复签次数
     * @param memberId
     * @param deptId
     * @return
     */
    public Map<String, Object>  signInCount(Integer memberId, String deptId, String beginTime, String endTime){
        Map<String, Object> maps = new HashMap<>();
        // 数值初始化
        Integer signInCount = 0; Integer signOutCount = 0;
        for(int i=0; i<2; i++){  //获取签到、复签次数
            EntityWrapper<QiandaoCheckin> qWrapper = new EntityWrapper<>();
            qWrapper.eq("memberid",memberId);
            qWrapper.eq("deptid",deptId);
            if(! StringUtils.isEmpty(beginTime) || !StringUtils.isEmpty(endTime)){
                qWrapper.between("createTime",beginTime,endTime);
            }
            if(i == 0) {
                qWrapper.ne("createtime","");
                qWrapper.isNotNull("createtime");
            }else if(i == 1){
                qWrapper.ne("updatetime","");
                qWrapper.isNotNull("updatetime");
            }
            Integer qNumber = qiandaoCheckinService.selectCount(qWrapper);
            if(i == 0){
                if(qNumber > 0) signInCount = qNumber;
                maps.put("signInCount",signInCount);
            }else if(i == 1){
                if(qNumber > 0) signOutCount = qNumber;
                maps.put("signOutCount",signOutCount);
            }
        }
        String signInNew = qiandaoCheckinService.selectNewCreateTime(memberId,beginTime,endTime);
        if(signInNew == null) signInNew = "";
        maps.put("signInNew",signInNew);
        String signOutNew = qiandaoCheckinService.selectNewUpdateTime(memberId,beginTime,endTime);
        if(signOutNew == null) signOutNew = "";
        maps.put("signOutNew",signOutNew);
        return maps;
    }

    @RequestMapping(value = "recommendQuery",method = RequestMethod.POST)
    @ApiOperation("推荐人与被推荐人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "selectId", value = "查询id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectType", value = "查询方式(1 读卡查询 2 姓名,身份证查询 3 memberId查询)", paramType = "query"),
    })
    public ResponseData<RecommendModel> recommendInfo(RequstData requstData, String selectId, String selectType) throws Exception{
        ResponseData<RecommendModel> resultInfo = new ResponseData<>();
        RecommendModel recommendModel = new RecommendModel();
        List<MemberInfoModel> memberInfoList = new ArrayList<>();
        ResponseData<MemberModel> dataCollection = memberApiController.getMemberInfo(requstData, selectId, selectType);
        MemberModel modelInfo = dataCollection.getDataCollection();
        if(modelInfo != null){
            recommendModel.setName(modelInfo.getName());
            recommendModel.setCadID(modelInfo.getCadID()); //获取推荐人信息
            EntityWrapper<Membermanagement> mWrapper = new EntityWrapper<>();
            mWrapper.eq("introducerId",modelInfo.getMemberId());
            mWrapper.eq("deptid",modelInfo.getDeptId());
            List<Membermanagement> mRInfos = membermanagementService.selectList(mWrapper); //获取被推荐人信息
            for(int i=0,size=mRInfos.size(); i<size; i++){  //循环赋值被推荐人信息
                MemberInfoModel mI2 = new MemberInfoModel();
                mI2.setName(mRInfos.get(i).getName());
                mI2.setCadID(mRInfos.get(i).getCadID());
                mI2.setCreateDt(mRInfos.get(i).getCreateTime());
                // 获取签到、复签次数
                Map<String, Object> maps = this.signInCount(mRInfos.get(i).getId(),mRInfos.get(i).getDeptId(),"","");
                if(maps.size() > 0){
                    mI2.setSignInCount((Integer) maps.get("signInCount"));
                    mI2.setSignInNew(maps.get("signInNew").toString());
                    mI2.setSignOutCount((Integer) maps.get("signOutCount"));
                    mI2.setSignOutNew(maps.get("signOutNew").toString());
                }
                mI2.setMemberStatus(Integer.parseInt(mRInfos.get(i).getTownshipid()));
                mI2.setSex(mRInfos.get(i).getSex());
                Membershipcardtype membershipcardtype = membershipcardtypeService.selectById(mRInfos.get(i).getLevelID());
                if (membershipcardtype != null && membershipcardtype.getUpamount() == 0) {
                    //获取总签到场次次数
                    EntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper = new EntityWrapper<>();
                    qiandaoCheckinBaseEntityWrapper.eq("memberid", mRInfos.get(i).getId());
                    qiandaoCheckinBaseEntityWrapper.eq("deptid", requstData.getDeptId());
                    qiandaoCheckinBaseEntityWrapper.isNotNull("updatetime");
                    int count = qiandaoCheckinService.selectCount(qiandaoCheckinBaseEntityWrapper);
                    if((membershipcardtype.getCheckleavenum() - count)==0){
                        mI2.setLevelId(membershipcardtype.getCardname());
                    }else {
                        mI2.setLevelId("还差" + (membershipcardtype.getCheckleavenum() - count) + "次签到成为普通会员");
                    }
                } else if (membershipcardtype != null && membershipcardtype.getUpamount() != 0) {
                    mI2.setLevelId(membershipcardtype.getCardname());
                }
                memberInfoList.add(mI2);
            }
            recommendModel.setmInfos(memberInfoList);
            resultInfo.setDataCollection(recommendModel);
        }else{
            throw new Exception("");
        }
        return resultInfo;
    }

    @RequestMapping(value = "addMember",method = RequestMethod.POST)
    @ApiOperation("新增会员")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "code", value = "新开卡code", paramType = "query"),
            @ApiImplicitParam(required = true, name = "cadID", value = "身份证id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "name", value = "姓名", paramType = "query"),
            @ApiImplicitParam(required = true, name = "telphone", value = "电话", paramType = "query"),
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData saveMemberInfo(RequstData requstData, MembermanagementModel membermanagementModel) throws Exception{
        ResponseData responseData = new ResponseData();
        Membermanagement membermanagement = new Membermanagement(); //将值装入 membermanagement 会员基础信息类
        membermanagement.setCadID(membermanagementModel.getCadID());
        membermanagement.setIntroducerId(membermanagementModel.getIntroducerId());
        membermanagement.setAvatar(membermanagementModel.getAvatar());
        membermanagement.setStaffID(requstData.getUserId().toString());
        membermanagement.setName(membermanagementModel.getName());
        membermanagement.setSex(membermanagementModel.getSex());
        membermanagement.setEmail(membermanagementModel.getEmail());
        membermanagement.setPhone(membermanagementModel.getTelphone());
        membermanagement.setIsoldsociety(membermanagementModel.getIsoldsociety());
        membermanagement.setBirthday(membermanagementModel.getBirthday());
        membermanagement.setProvince(membermanagementModel.getProvince());
        membermanagement.setCity(membermanagementModel.getCity());
        membermanagement.setDistrict(membermanagementModel.getDistrict());
        membermanagement.setAddress(membermanagementModel.getAddress());
        membermanagement.setFamilyStatusID(membermanagementModel.getFamilyStatusID());
        membermanagementController.add(membermanagement,membermanagementModel.getCardCode(),membermanagementModel.getBaMedicals()
                ,membermanagementModel.getCode(),membermanagementModel.getOtherMemberId());
        return responseData;
    }

    @RequestMapping(value = "/saveCardInfo", method = RequestMethod.POST)
    @ApiOperation("写卡")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "cardCode", value = "卡片信息", paramType = "query"),
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData<MemberCardModel> saveCardInfo(RequstData requstData, String cardCode) throws Exception{
        ResponseData<MemberCardModel> responseData = new ResponseData<>();
        try{
            MemberCardModel memberCardModel = new MemberCardModel();
            String code = (String) membermanagementController.getXieKaVal(cardCode);
            memberCardModel.setCardCode(code);
            responseData.setDataCollection(memberCardModel);
        }catch (Exception e){
            throw e;
        }
        return responseData;
    }

    @RequestMapping(value = "/selectUserInfo", method = RequestMethod.POST)
    @ApiOperation("读卡获取会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "cardCode", value = "卡片信息", paramType = "query"),
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData<MemberCardModel> selectUserInfo(RequstData requstData, String cardCode) throws Exception{
        ResponseData<MemberCardModel> responseData = new ResponseData<>();
        try{
            MemberCard memberCard = (MemberCard) membermanagementController.getUserInfo(cardCode);
            Integer memberId = memberCard.getMemberid();
            Membermanagement membermanagement = membermanagementService.selectById(memberId);
            MemberCardModel cardModel = new MemberCardModel();
            cardModel.setMemberId(membermanagement.getId().toString());
            cardModel.setName(membermanagement.getName());
            responseData.setDataCollection(cardModel);
        }catch (Exception e){
            throw e;
        }
        return responseData;
    }
}
