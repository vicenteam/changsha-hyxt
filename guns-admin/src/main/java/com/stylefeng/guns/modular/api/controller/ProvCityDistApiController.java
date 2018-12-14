package com.stylefeng.guns.modular.api.controller;

import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.ProvCityDistModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.ProvCityDistController;
import com.stylefeng.guns.modular.system.model.ProvCityDist;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/provCityDistApi")
@Api(description = "省市区")
public class ProvCityDistApiController extends BaseController {

    @Autowired
    private ProvCityDistController provCityDistController;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ApiOperation("省市区获取")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "type", value = "获取类型（0省 1市 2区）", paramType = "query"),
            @ApiImplicitParam(required = true, name = "parentTreeId", value = "上层地区id(当type=0时可为空)"),
    })
    public ResponseData<List<ProvCityDistModel>> clientTreeList(RequstData requstData,  Integer type, String parentTreeId){
        ResponseData<List<ProvCityDistModel>> responseData = new ResponseData();
        List<ProvCityDistModel> provCityDistModels = new ArrayList<>();
        try{
            if(type == 0){
                List<ProvCityDist> o = (List<ProvCityDist>) provCityDistController.province(null);
                List<ProvCityDist> provCityDists = new ReflectionObject<ProvCityDist>().changeList(o,new ProvCityDist());
                provCityDists.forEach(provCityDist -> {
                    ProvCityDistModel provCityDistModel = new ProvCityDistModel();
                    provCityDistModel.setId(provCityDist.getId());
                    provCityDistModel.setName(provCityDist.getProvince());
                    provCityDistModels.add(provCityDistModel);
                });
            }else if(type == 1){
                List<ProvCityDist> o = (List<ProvCityDist>) provCityDistController.city(parentTreeId);
                List<ProvCityDist> provCityDists = new ReflectionObject<ProvCityDist>().changeList(o,new ProvCityDist());
                provCityDists.forEach(provCityDist -> {
                    ProvCityDistModel provCityDistModel = new ProvCityDistModel();
                    provCityDistModel.setId(provCityDist.getId());
                    provCityDistModel.setName(provCityDist.getCity());
                    provCityDistModels.add(provCityDistModel);
                });
            }else if(type == 2){
                List<ProvCityDist> o = (List<ProvCityDist>) provCityDistController.district(parentTreeId);
                List<ProvCityDist> provCityDists = new ReflectionObject<ProvCityDist>().changeList(o,new ProvCityDist());
                provCityDists.forEach(provCityDist -> {
                    ProvCityDistModel provCityDistModel = new ProvCityDistModel();
                    provCityDistModel.setId(provCityDist.getId());
                    provCityDistModel.setName(provCityDist.getDistrict());
                    provCityDistModels.add(provCityDistModel);
                });
            }
        }catch (Exception e){
            throw new GunsException(BizExceptionEnum.SERVER_ERROR);
        }
        responseData.setDataCollection(provCityDistModels);
        return responseData;
    }

}
