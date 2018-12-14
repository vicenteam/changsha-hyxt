package com.stylefeng.guns.modular.api.controller;

import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.checkin.firstcheckin.CreateCheckinMode;
import com.stylefeng.guns.modular.api.model.checkin.firstcheckin.FirstCheckinUserModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.CheckinController;
import com.stylefeng.guns.modular.main.controller.QiandaoCheckinController;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.system.model.Checkin;
import com.stylefeng.guns.modular.system.model.Membershipcardtype;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/firstcheckinapi")
@Api(description = "会员签到")
public class FirstCheckinApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(FirstCheckinApiController.class);
    @Autowired
    private CheckinController checkinController;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private QiandaoCheckinController qiandaoCheckinController;

    @RequestMapping(value = "/getcheck", method = RequestMethod.POST)
    @ApiOperation("创建签到场次信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData<CreateCheckinMode> getCheckIn(RequstData requstData) throws Exception {
        ResponseData<CreateCheckinMode> createCheckinModeResponseData = new ResponseData<>();
        try {
            Checkin getcheck = (Checkin) checkinController.getcheck();
            if (getcheck != null) {
                CreateCheckinMode createCheckinMode = new CreateCheckinMode();
                createCheckinMode.setCheckinId(getcheck.getId());
                createCheckinMode.setScreenings(getcheck.getScreenings());
                createCheckinModeResponseData.setDataCollection(createCheckinMode);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
        return createCheckinModeResponseData;
    }

    @RequestMapping(value = "/updatecheck", method = RequestMethod.POST)
    @ApiOperation("结束签到场次")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "checkid", value = "签到场次id", paramType = "query"),
    })
    public ResponseData updatecheck(RequstData requstData, String checkid) {
        ResponseData responseData = new ResponseData();
        checkinController.updatecheck(checkid);
        return responseData;
    }

    @RequestMapping(value = "/gettCheckinUser", method = RequestMethod.POST)
    @ApiOperation("读卡获用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "cardCode", value = "卡片数据", paramType = "query"),
            @ApiImplicitParam(required = true, name = "checkid", value = "签到场次id", paramType = "query"),
    })
    public ResponseData<FirstCheckinUserModel> gettCheckinUser(RequstData requstData, String cardCode, String checkid) throws Exception {
        ResponseData<FirstCheckinUserModel> firstCheckinUserModelResponseData = new ResponseData<>();
        try {
            Map<String, Object> userInfo = (Map<String, Object>) checkinController.getUserInfo(cardCode, checkid);
            if (userInfo != null) {
                switch ((int) userInfo.get("qiandao")) {
                    case 0:
                        FirstCheckinUserModel firstCheckinUserModel = new ReflectionObject<FirstCheckinUserModel>().change(userInfo, new FirstCheckinUserModel());
                        firstCheckinUserModel.setMemberId((int) userInfo.get("id"));
                        //会员等级
                        Membershipcardtype membershipcardtype = membershipcardtypeService.selectById("" + userInfo.get("levelID"));
                        firstCheckinUserModel.setLevelID(membershipcardtype.getCardname());
                        //服务员工
                        User staffID = userService.selectById("" + userInfo.get("staffID"));
                        firstCheckinUserModel.setStaffName(staffID.getName());

                        //性别
                        if (userInfo.get("sex").equals("1")) {
                            firstCheckinUserModel.setSex("男");
                        } else if (userInfo.get("sex").equals("2")) {
                            firstCheckinUserModel.setSex("女");
                        }
                        firstCheckinUserModelResponseData.setDataCollection(firstCheckinUserModel);
                        break;
                    case 1://复签
                        throw new Exception("当前用户当日已首签");
                    case 2:
                        throw new Exception("当前用户当日已首签");
                }
            } else {
                throw new Exception("服务器开小差了..");
            }
        } catch (Exception e) {
            throw e;
        }
        return firstCheckinUserModelResponseData;
    }

    @RequestMapping(value = "/checkIn", method = RequestMethod.POST)
    @ApiOperation("进行首签操作")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "memberId", value = "卡用户id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "checkid", value = "签到场次id", paramType = "query"),
    })
    public ResponseData checkIn(RequstData requstData, Integer memberId, String checkid) throws Exception {
        ResponseData responseData = new ResponseData();
        qiandaoCheckinController.add("" + memberId, checkid);
        return responseData;
    }
}
