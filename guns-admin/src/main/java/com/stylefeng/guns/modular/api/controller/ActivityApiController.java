package com.stylefeng.guns.modular.api.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.base.PageModel;
import com.stylefeng.guns.modular.api.model.activity.ActivityDetailModel;
import com.stylefeng.guns.modular.api.model.activity.ActivityModel;
import com.stylefeng.guns.modular.api.model.memerber.MemberModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.ActivityController;
import com.stylefeng.guns.modular.main.service.IActivityMemberService;
import com.stylefeng.guns.modular.main.service.IActivityService;
import com.stylefeng.guns.modular.system.model.Activity;
import com.stylefeng.guns.modular.system.model.ActivityMember;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activityapi")
public class ActivityApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(ActivityApiController.class);

    @Resource
    private IActivityService activityService;
    @Autowired
    private MemberApiController memberApiController;
    @Autowired
    private ActivityController activityController;
    @Autowired
    private IActivityMemberService activityMemberService;

    @RequestMapping(value = "/getActivityPageList", method = RequestMethod.POST)
    @ApiOperation("有效活动分页数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "登录人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "登录人门店id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "offset", value = "当前页码(从0开始)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "limit", value = "每页条数", paramType = "query"),
    })
    public ResponseData<PageModel<ActivityModel>> getActivityPageList(RequstData requstData, Integer offset, Integer limit) throws Exception {
        ResponseData<PageModel<ActivityModel>> activityModelResponseData = new ResponseData<>();
        EntityWrapper<Activity> activityEntityWrapper = new EntityWrapper<>();
        activityEntityWrapper.eq("deptid", requstData.getDeptId());
        activityEntityWrapper.eq("status", 2);
//        activityEntityWrapper.notIn("ruleexpression", 3);
        Page<Activity> page = new PageFactory<Activity>().defaultPage();
        if (offset != null && limit != null) {
            Page<Activity> activityPage = activityService.selectPage(page, activityEntityWrapper);
            List<Activity> records = activityPage.getRecords();
            List<ActivityModel> activityModels = new ReflectionObject<ActivityModel>().changeList(records, new ActivityModel());
            PageModel<ActivityModel> pageModel = new PageModel<ActivityModel>();
            pageModel.setTotal(activityPage.getTotal());
            pageModel.setDataList(activityModels);
            activityModelResponseData.setDataCollection(pageModel);
        } else {
            throw new Exception("offset,limit参数不能为空!");
        }
        return activityModelResponseData;
    }


    @RequestMapping(value = "/ementActivityData", method = RequestMethod.POST)
    @ApiOperation("领取活动")
    public Object ementActivityData(RequstData requstData, String memberId, String activityId) throws Exception {
        ResponseData activityModelResponseData = new ResponseData<>();
        //领取前验证处理
        Map<String, Object> o = ( Map<String, Object>)activityController.selectMemberInfo(Integer.parseInt(memberId), activityId);
        System.out.println(JSON.toJSONString(o));
        if(((String)o.get("error")).equals("202")){
            throw new Exception((String)o.get("errorMessage"));
        }else {
            activityController.lingqu(activityId, memberId);
            activityModelResponseData.setResultMessage("领取成功!");
        }

        return activityModelResponseData;
    }

    @RequestMapping(value = "/giftDetails", method = RequestMethod.POST)
    @ApiOperation("活动领取页面-礼品明细分页")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "登录人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "登录人门店id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "memberId", value = "会员id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "offset", value = "当前页码(从0开始)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "limit", value = "每页条数", paramType = "query"),
    })
    public ResponseData<Page<ActivityDetailModel>> giftDetails(RequstData requstData, String memberId, Integer offset, Integer limit) {
        ResponseData<Page<ActivityDetailModel>> activityModelResponseData = new ResponseData<>();
        EntityWrapper<Activity> activityEntityWrapper = new EntityWrapper<>();
        activityEntityWrapper.eq("deptid", requstData.getDeptId());
        activityEntityWrapper.eq("status", 2);
//        activityEntityWrapper.notIn("ruleexpression", 3);
        activityEntityWrapper.orderBy("createtime", false);
        Page<Activity> page = new PageFactory<Activity>().defaultPage();
        Page<ActivityDetailModel> detailModelPage = new PageFactory<ActivityDetailModel>().defaultPage();
        Page<Activity> activityPage = activityService.selectPage(page, activityEntityWrapper);
        List<Activity> records = activityPage.getRecords();
        List<ActivityDetailModel> resultList = new ArrayList<>();
        //查询会员活动领取次数
        records.forEach(a -> {
            String name = a.getName();
            Integer id = a.getId();
            Integer maxgetnum = a.getMaxgetnum();
            String content = a.getContent();
            EntityWrapper<ActivityMember> activityMemberEntityWrapper = new EntityWrapper<>();
            activityMemberEntityWrapper.eq("deptid", requstData.getDeptId());
            activityMemberEntityWrapper.eq("memberid", memberId);
            activityMemberEntityWrapper.eq("activityid", id);
            activityMemberEntityWrapper.orderBy("createtime", false);
            List<ActivityMember> activityMembers = activityMemberService.selectList(activityMemberEntityWrapper);
            ActivityDetailModel detailModel = new ActivityDetailModel();
            detailModel.setActivityId(id + "");
            detailModel.setActivityContent(content);
            detailModel.setActivityMaxgetnum(maxgetnum);
            detailModel.setActivityName(name);
            detailModel.setActivityReceiveNum(activityMembers.size());
            detailModel.setActivityStatus(a.getStatus());
            if(activityMembers.size()>0){
                detailModel.setActivityReceiveTime(activityMembers.get(0).getCreatetime());
            }
            resultList.add(detailModel);
        });
        detailModelPage.setRecords(resultList);
        activityModelResponseData.setDataCollection(detailModelPage);
        return activityModelResponseData;
    }

    @RequestMapping(value = "/getmemberInfo", method = RequestMethod.POST)
    @ApiOperation("活动领取页面-基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "selectId", value = "查询id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectType", value = "查询方式(1 读卡查询 2 姓名,身份证查询 3 memberId查询)", paramType = "query"),
    })
//    public  ResponseData<SettlementActivityPageDataModel> getmemberInfo(RequstData requstData, String selectId, String selectType) throws Exception {
    public  ResponseData<MemberModel> getmemberInfo(RequstData requstData, String selectId, String selectType) throws Exception {
        ResponseData<MemberModel> settlementActivityPageDataModelResponseData = new ResponseData<>();
        ResponseData<MemberModel> memberInfo = memberApiController.getMemberInfo(requstData, selectId, selectType);
        MemberModel dataCollection = memberInfo.getDataCollection();
        return memberInfo;
    }
}
