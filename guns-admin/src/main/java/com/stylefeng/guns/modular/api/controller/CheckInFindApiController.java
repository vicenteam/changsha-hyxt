package com.stylefeng.guns.modular.api.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.checkin.CheckInFindModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.CheckinController;
import com.stylefeng.guns.modular.main.service.ICheckinService;
import com.stylefeng.guns.modular.system.model.Checkin;
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
import java.util.List;

@RestController
@RequestMapping("/api/checkinfindapi")
@Api(description = "签到查询")
public class CheckInFindApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(CheckInFindApiController.class);
    @Autowired
    private ICheckinService checkinService;
    @Autowired
    private CheckinController checkinController;

    @RequestMapping(value = "/gettCheckinUser", method = RequestMethod.POST)
    @ApiOperation("获取签到列表")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "offset", value = "当前页码(从0开始)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "limit", value = "每页条数", paramType = "query"),
    })
    public ResponseData<Page<CheckInFindModel>> gettCheckinUser(RequstData requstData, String search, Integer offset, Integer limit) throws Exception {
        ResponseData<Page<CheckInFindModel>> firstCheckinUserModelResponseData = new ResponseData<>();
        Page<CheckInFindModel> detailModelPage = new PageFactory<CheckInFindModel>().defaultPage();
        Page<Checkin> checkInFindModelPage = new PageFactory<Checkin>().defaultPage();
        try {
            BaseEntityWrapper<Checkin> checkinBaseEntityWrapper = new BaseEntityWrapper<>();
            if(!StringUtils.isEmpty(search))checkinBaseEntityWrapper.eq("screenings",search);
            Page<Checkin> page = checkinService.selectPage(checkInFindModelPage, checkinBaseEntityWrapper);
            List<CheckInFindModel> list = new ArrayList<>();
            page.getRecords().forEach(a -> {
                CheckInFindModel change = new ReflectionObject<CheckInFindModel>().change(a, new CheckInFindModel());
                change.setActivityType(a.getStatus()==1?"签到中":"已结束");
                change.setScreeningId(a.getId());
                list.add(change);
            });
            detailModelPage.setRecords(list);
            detailModelPage.setTotal(page.getTotal());
            firstCheckinUserModelResponseData.setDataCollection(detailModelPage);
        } catch (Exception e) {
            throw new Exception("系统错误,请联系管理员");
        }
        return firstCheckinUserModelResponseData;
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation("结束签到")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "screeningId", value = "场次id", paramType = "query"),
    })
    public ResponseData updateStatus(Integer screeningId, RequstData requstData) {
        ResponseData responseData = new ResponseData();
        if(screeningId==null)throw new NullPointerException("场次异常,请刷新重试");
        checkinController.updatecheck("" + screeningId);
        return responseData;
    }

}
