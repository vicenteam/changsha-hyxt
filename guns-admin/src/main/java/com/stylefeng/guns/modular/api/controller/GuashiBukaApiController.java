package com.stylefeng.guns.modular.api.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.model.guashibuka.FindPageDataModel;
import com.stylefeng.guns.modular.api.model.guashibuka.FindPageParModel;
import com.stylefeng.guns.modular.api.model.guashibuka.FindPageTopModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.MembermanagementController;
import com.stylefeng.guns.modular.main.service.IMemberCardService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/guashibukaapi")
@Api(description = "补卡挂失")
public class GuashiBukaApiController {
    private final Logger log = LoggerFactory.getLogger(GuashiBukaApiController.class);
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IMemberCardService memberCardService;
    @Autowired
    private MembermanagementController membermanagementController;

    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    @ApiOperation("补卡挂失-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "searchType", value = "查找类型（0普通搜索 1读卡搜索）", paramType = "query"),
    })
    public ResponseData<FindPageTopModel> findPage(RequstData requstData, FindPageParModel fp) throws Exception {
        ResponseData<FindPageTopModel> responseData=new  ResponseData<FindPageTopModel>();
        Page<Membermanagement> page = new PageFactory<Membermanagement>().defaultPage();
        BaseEntityWrapper<Membermanagement> membermanagementBaseEntityWrapper = new BaseEntityWrapper<>();
        if(fp.getSearchType()==0){//条件查询
            initFindParm(membermanagementBaseEntityWrapper,fp);
        }else {//读卡查询
            BaseEntityWrapper<MemberCard> memberCardBaseEntityWrapper = new BaseEntityWrapper<>();
            memberCardBaseEntityWrapper.eq("code",fp.getCardCode());
            MemberCard memberCard=memberCardService.selectOne(memberCardBaseEntityWrapper);
            if(memberCard!=null&&memberCard.getMemberid()!=null){
                membermanagementBaseEntityWrapper.eq("id",memberCard.getMemberid());
            }
        }
        page=membermanagementService.selectPage(page,membermanagementBaseEntityWrapper);
        FindPageTopModel findPageTopModel=new FindPageTopModel();
        findPageTopModel.setTotal((int)page.getTotal());
        List<Membermanagement> list=page.getRecords();
        list.forEach(a->{
            FindPageDataModel findPageDataModel= new ReflectionObject<FindPageDataModel>().change(a, new FindPageDataModel());
            findPageDataModel.setFindPageDataModelId(a.getId());
            //会员等级
            Membershipcardtype membershipcardtype=membershipcardtypeService.selectById(a.getLevelID());
            findPageDataModel.setLevelID(membershipcardtype!=null?membershipcardtype.getCardname():"");
            //是否老年协会
            findPageDataModel.setIsoldsociety(a.getIsoldsociety()==1?"是":"否");
            //性别
            findPageDataModel.setSex(a.getSex()==1?"男":"女");
            findPageDataModel.setStatus(a.getState());
            findPageTopModel.getRows().add(findPageDataModel);
        });
        responseData.setDataCollection(findPageTopModel);
        return responseData;
    }

    @RequestMapping(value = "/guashi", method = RequestMethod.POST)
    @ApiOperation("挂失&解除挂失")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "type", value = "0挂失 1解除挂失", paramType = "query"),
            @ApiImplicitParam(required = true, name = "findPageDataModelId", value = "操作用户对应id", paramType = "query"),
    })
    public ResponseData guashi(RequstData requstData,Integer type,Integer findPageDataModelId) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
           if(type==0){
               membermanagementController.guashiData(""+findPageDataModelId);
           }else if(type==1) {
               membermanagementController.guashiData1(""+findPageDataModelId);
           }
        }catch (Exception e){
            throw new Exception("操作失败 请联系管理员!");
        }
        return responseData;
    }

    /**
     * 构造查询条件
     * @param membermanagementBaseEntityWrapper
     * @param fp
     */
    public void initFindParm(BaseEntityWrapper<Membermanagement> membermanagementBaseEntityWrapper,FindPageParModel fp) {
        if(fp!=null){
            if(fp.getName()!=null)membermanagementBaseEntityWrapper.like("name",fp.getName());
            if(fp.getSex()!=null)membermanagementBaseEntityWrapper.eq("sex",fp.getSex());
            if(fp.getFamilyStatusID()!=null)membermanagementBaseEntityWrapper.eq("familyStatusID",fp.getFamilyStatusID());
            if(fp.getAddress()!=null)membermanagementBaseEntityWrapper.like("address",fp.getAddress());
            if(fp.getCadID()!=null)membermanagementBaseEntityWrapper.eq("cadID",fp.getCadID());
            if(fp.getPhone()!=null)membermanagementBaseEntityWrapper.eq("phone",fp.getPhone());
            if(fp.getProvinceId()!=null)membermanagementBaseEntityWrapper.eq("provinceId",fp.getProvinceId());
            if(fp.getCityId()!=null)membermanagementBaseEntityWrapper.eq("cityId",fp.getCityId());
            if(fp.getDistrictId()!=null)membermanagementBaseEntityWrapper.eq("districtId",fp.getDistrictId());
            if(fp.getCardStatus()!=null)membermanagementBaseEntityWrapper.eq("state",fp.getCardStatus());
        }
    }
}
