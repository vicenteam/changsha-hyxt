package com.stylefeng.guns.modular.api.controller;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.modular.api.apiparam.RequstData;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.model.jifen.JifenPageDataMode;
import com.stylefeng.guns.modular.api.model.jifen.JifenPageFindMode;
import com.stylefeng.guns.modular.api.model.jifen.JifenPageTopMode;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.IntegralRecordQueryController;
import com.stylefeng.guns.modular.main.controller.MembermanagementController;
import com.stylefeng.guns.modular.main.service.IIntegralrecordtypeService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.system.model.Integralrecordtype;
import com.stylefeng.guns.modular.system.model.MemberCard;
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
import java.util.Map;

@RestController
@RequestMapping("/api/jifenfindapi")
@Api(description = "积分查询")
public class JifenFindApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(JifenFindApiController.class);

    @Autowired
    private IntegralRecordQueryController integralRecordQueryController;
    @Autowired
    private MembermanagementController membermanagementController;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IIntegralrecordtypeService iIntegralrecordtypeService;

    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    @ApiOperation("积分分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "queryType", value = "查询类型(0搜索,1读卡)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "offset", value = "当前页码(0开始)", paramType = "query"),
            @ApiImplicitParam(required = true, name = "limit", value = "每页条数", paramType = "query"),
    })
    public ResponseData<JifenPageTopMode> findPage(RequstData requstData, JifenPageFindMode jf) throws Exception {
        ResponseData<JifenPageTopMode> responseData = new ResponseData();
        String memberId = null;
        if (jf.getQueryType() == 1) {
           try {
               MemberCard userInfo = (MemberCard)membermanagementController.getUserInfo(jf.getCardCode());
               memberId=userInfo.getMemberid()+"";
           }catch (Exception e){
               throw new Exception("该卡暂无法使用!");
           }
        }
//        Map map1 = JSON.parseObject(JSON.toJSONString(integralRecordQueryController.list("", jf.getStaffId(), jf.getName(), jf.getCardId(), jf.getIntegraltype() + "", jf.getStartTime(), jf.getEndTime(), memberId)), Map.class);
//        System.out.println(JSON.toJSONString(map1)+"------------");
//        Map<String,Object> map = JSON.parseObject(JSON.toJSONString(integralRecordQueryController.list("", jf.getStaffId(), jf.getName(), jf.getCardId(), jf.getIntegraltype() + "", jf.getStartTime(), jf.getEndTime(), memberId)), Map.class);
        Map<String,Object> map = null;
//        System.out.println(JSON.toJSONString(map)+"-------");
        List<Object> rows=(List<Object>)map.get("rows");
        Integer total=(int)map.get("total");
        if(total!=0){
            List<JifenPageDataMode> jifenPageDataModes = new ReflectionObject<JifenPageDataMode>().changeList(rows, new JifenPageDataMode());
            jifenPageDataModes.forEach(a->{
                //改变积分类型
                Integralrecordtype integralrecordtype = iIntegralrecordtypeService.selectById(a.getTypeId());
                if(integralrecordtype!=null)a.setTypeId(integralrecordtype.getNames());
            });
            JifenPageTopMode jifenPageTopMode=new JifenPageTopMode();
            jifenPageTopMode.setRows(jifenPageDataModes);
            jifenPageTopMode.setTotal(total);
            responseData.setDataCollection(jifenPageTopMode);
        }
        return responseData;
    }

}
