package com.stylefeng.guns.modular.api.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.checkin.CheckInModel;
import com.stylefeng.guns.modular.api.model.checkin.CheckInTimeModel;
import com.stylefeng.guns.modular.api.model.checkin.CountersigningInfoMode;
import com.stylefeng.guns.modular.api.model.memerber.MemberModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.QiandaoCheckinController;
import com.stylefeng.guns.modular.main.service.ICheckinService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IQiandaoCheckinService;
import com.stylefeng.guns.modular.system.model.Checkin;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.QiandaoCheckin;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/checkinapi")
public class CheckInApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(CheckInApiController.class);
    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private ICheckinService checkinService;
    @Autowired
    private MemberApiController memberApiController;
    @Autowired
    private QiandaoCheckinController qiandaoCheckinController;

    @RequestMapping(value = "/getCheckInRecord", method = RequestMethod.POST)
    @ApiOperation("获取签到详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "selectId", value = "查询id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectType", value = "查询方式(1 读卡查询 2 姓名,身份证查询 3 memberId查询)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectYear", value = "查询年", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectMonth", value = "查询月", paramType = "query"),
    })
    public ResponseData<CheckInModel> getCheckInRecord(RequstData requstData, String selectId, String selectType, String selectYear, String selectMonth) throws Exception {
        ResponseData<CheckInModel> checkInModelResponseData = new ResponseData<>();
        EntityWrapper<QiandaoCheckin> memberCardEntityWrapper = new EntityWrapper<>();
        ResponseData<MemberModel> memberInfo = memberApiController.getMemberInfo(requstData, selectId, selectType);
        MemberModel dataCollection = memberInfo.getDataCollection();
        memberCardEntityWrapper.eq("deptid", requstData.getDeptId());
        memberCardEntityWrapper.eq("memberid", dataCollection.getMemberId());
        String starTime = selectYear + "-";
        if (selectMonth.length() == 1) {
            starTime += "0" + selectMonth + "-01 00:00:01";
        } else {
            starTime += selectMonth + "-01 00:00:01";
        }
        String endTime = selectYear + "-";
        if (selectMonth.length() == 1) {
            endTime += "0" + selectMonth + "-31 23:59:59";
        } else {
            endTime += selectMonth + "-31 23:59:59";
        }
        memberCardEntityWrapper.between("createtime", starTime, endTime);
        List<QiandaoCheckin> list = qiandaoCheckinService.selectList(memberCardEntityWrapper);
        List<CheckInTimeModel> checkInTimeModels = new ReflectionObject<CheckInTimeModel>().changeList(list, new CheckInTimeModel());
        Membermanagement membermanagement = null;
        CheckInModel checkInModel = new CheckInModel();
        membermanagement = membermanagementService.selectById(dataCollection.getMemberId());
        checkInModel.setName(membermanagement.getName());
        checkInModel.setCardID(membermanagement.getCardID());
        checkInModel.setCheckInRecord(checkInTimeModels);
        checkInModelResponseData.setDataCollection(checkInModel);
        return checkInModelResponseData;
    }

    @RequestMapping(value = "/getCountersigningInfo", method = RequestMethod.POST)
    @ApiOperation("获取复签列表")
    public ResponseData<List<CountersigningInfoMode>> getCountersigningInfo(RequstData requstData) {
        ResponseData<List<CountersigningInfoMode>> checkInModelResponseData = new ResponseData<>();
        EntityWrapper<Checkin> checkinEntityWrapper = new EntityWrapper<>();
        checkinEntityWrapper.eq("deptid", requstData.getDeptId());
//        checkinEntityWrapper.eq("status", 1);
        //获取当天时间范围
        String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        checkinEntityWrapper.between("startDate", start, end);
        checkinEntityWrapper.orderBy("createDate", false);
        List<Checkin> checkins = checkinService.selectList(checkinEntityWrapper);
        List<Checkin> checkins2 = new ArrayList<>();
        if(checkins.size()>0){
            checkins2.add(checkins.get(0));
        }
//        List<CountersigningInfoMode> countersigningInfoModes = new ReflectionObject<CountersigningInfoMode>().changeList(checkins, new CountersigningInfoMode());
        List<CountersigningInfoMode> countersigningInfoModes = new ReflectionObject<CountersigningInfoMode>().changeList(checkins2, new CountersigningInfoMode());
        checkInModelResponseData.setDataCollection(countersigningInfoModes);
        return checkInModelResponseData;
    }

    @RequestMapping(value = "/countersigning", method = RequestMethod.POST)
    @ApiOperation("进行复签")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "selectId", value = "复签方式对应值", paramType = "query"),
            @ApiImplicitParam(required = true, name = "selectType", value = "复签方式(1 读卡复签 2身份证复签 3 memberId复签)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "screeningId", value = "签到场次id", paramType = "query"),
    })
    public ResponseData countersigning(RequstData requstData, String screeningId, String selectId, String selectType) throws Exception {
        ResponseData checkInModelResponseData = new ResponseData<>();
        ResponseData<MemberModel> memberInfo = memberApiController.getMemberInfo(requstData, selectId, selectType);
        MemberModel dataCollection = memberInfo.getDataCollection();
        EntityWrapper<QiandaoCheckin> qiandaoCheckinEntityWrapper = new EntityWrapper<>();
        qiandaoCheckinEntityWrapper.eq("deptid", requstData.getDeptId());
        qiandaoCheckinEntityWrapper.eq("memberid", dataCollection.getMemberId());
//        qiandaoCheckinEntityWrapper.eq("checkinid",screeningId);
        //复签当天信息
        qiandaoCheckinEntityWrapper.like("createtime", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        QiandaoCheckin qiandaoCheckin = qiandaoCheckinService.selectOne(qiandaoCheckinEntityWrapper);
        if (qiandaoCheckin == null) {
            //进行首签
            throw new Exception("该场次用户未首签,不能进行复签操作!");
        } else {
            if (StringUtils.isEmpty(qiandaoCheckin.getUpdatetime())) {
                //进行复签
//                qiandaoCheckin.setUpdatetime(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
//                qiandaoCheckinService.updateById(qiandaoCheckin);
//                qiandaoCheckinController.update(dataCollection.getMemberId() + "", screeningId + "");
                qiandaoCheckinController.update(dataCollection.getMemberId() + "", qiandaoCheckin.getCheckinid() + "");
                System.out.println("____________");
            } else {
                //不能进行操作
                throw new Exception("该场次用户已经复签不能重复操作!");
            }
        }

        checkInModelResponseData.setResultMessage("复签成功!");
        return checkInModelResponseData;
    }

    @RequestMapping(value = "/closecountersigning", method = RequestMethod.POST)
    @ApiOperation("结束复签")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "screeningId", value = "签到场次id", paramType = "query"),
    })
    public ResponseData closecountersigning(RequstData requstData, String screeningId) throws Exception {
        ResponseData checkInModelResponseData = new ResponseData<>();
        Checkin checkin = checkinService.selectById(screeningId);
        checkin.setEndDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        checkin.setStatus(2);
        checkinService.updateById(checkin);
//        checkInModelResponseData.setDataCollection("操作成功!");
        return checkInModelResponseData;
    }
}
