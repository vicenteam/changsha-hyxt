package com.stylefeng.guns.modular.api.controller;

import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.memerber.MemberRepairModel;
import com.stylefeng.guns.modular.main.controller.MemberRepairController;
import com.stylefeng.guns.modular.main.controller.MembermanagementController;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.system.model.MemberCard;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientRepairApi")
@Api(description = "会员补签")
public class MemberRepairApiController extends BaseController {

    @Autowired
    private MemberRepairController memberRepairController;
    @Autowired
    private MembermanagementController membermanagementController;
    @Autowired
    private IMembermanagementService membermanagementService;

    @RequestMapping(value = "/getRepairUser",method = RequestMethod.POST)
    @ApiOperation("补签获取会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "cardCode", value = "卡片信息", paramType = "query"),
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData<MemberRepairModel> getRepairUser(RequstData requstData, String cardCode) throws Exception{
        ResponseData<MemberRepairModel> responseData = new ResponseData();
        try{
            MemberCard memberCard = (MemberCard) membermanagementController.getUserInfo(cardCode);
            Integer memberId = memberCard.getMemberid();
            Membermanagement membermanagement = membermanagementService.selectById(memberId);
            MemberRepairModel repairModel = new MemberRepairModel();
            repairModel.setCadID(membermanagement.getCadID());
            repairModel.setMemberId(membermanagement.getId().toString());
            responseData.setDataCollection(repairModel);
        }catch (Exception e){
            throw e;
        }
        return responseData;
    }

    @RequestMapping(value = "/clientRepair", method = RequestMethod.POST)
    @ApiOperation("会员补签")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "time", value = "补签时间", paramType = "query"),
            @ApiImplicitParam(required = true, name = "memberId", value = "会员id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData memberRepair(RequstData requstData,MemberRepairModel memberRepairModel) throws Exception{
        ResponseData responseData = new ResponseData();
        memberRepairController.repair(memberRepairModel.getMemberId(),memberRepairModel.getTime());
        return responseData;
    }

}
