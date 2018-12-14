package com.stylefeng.guns.modular.api.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.api.base.BaseController;
import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.api.model.user.UserModel;
import com.stylefeng.guns.modular.api.model.user.UserResouceModel;
import com.stylefeng.guns.modular.api.util.ReflectionObject;
import com.stylefeng.guns.modular.main.controller.QiandaoCheckinController;
import com.stylefeng.guns.modular.system.dao.UserMapper;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.Relation;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRelationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 终端系统用户api
 */
@RestController
@RequestMapping("/api/userapi")
public class UserApiController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(UserApiController.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IRelationService relationService;
    @Autowired
    private IDeptService deptService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "username", value = "登录名", paramType = "query"),
            @ApiImplicitParam(required = true, name = "password", value = "登录密码", paramType = "query")
    })
    public ResponseData<UserModel> login(@RequestParam("username") String username,
                                         @RequestParam("password") String password) throws Exception {
        ResponseData<UserModel> userResponseData = new ResponseData<>();
        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());
        //获取数据库中的账号密码，准备比对
        User user = userMapper.getByAccount(username);
        if (user == null) throw new Exception("用户名不存在!");
        String credentials = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(), credentials, credentialsSalt, "");
        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
                usernamePasswordToken, simpleAuthenticationInfo);
        if (passwordTrueFlag) {
            if(user.getStatus()!=1)throw new Exception("登录失败,请联系管理员!");
            //数据库entity对象转model层对象
            UserModel change = new ReflectionObject<UserModel>().change(user, new UserModel());
            change.setUserId(change.getId());
            change.setAvatar("http://47.104.252.44:8081/kaptcha/"+ change.getAvatar());//上线更改地址
            if(change.getDeptid()!=null){
                Dept dept = deptService.selectById(change.getDeptid());
                if(dept!=null) change.setDeptName(dept.getFullname());
            }
            userResponseData.setDataCollection(change);
        } else {
            throw new Exception("用户名或密码错误!");
        }
        return userResponseData;
    }

    public void initUserResouceList(UserModel change){
        List<UserResouceModel> list=change.getUserResouceModels();
        //新增会员
        UserResouceModel userResouceModel1=new UserResouceModel();
        userResouceModel1.setResouceName("新增会员");
        EntityWrapper<Relation> relationEntityWrapper1= new EntityWrapper<Relation>();
        relationEntityWrapper1.eq("roleid",change.getRoleid());
        relationEntityWrapper1.eq("menuid","173");
       int i1= relationService.selectCount(relationEntityWrapper1);
        userResouceModel1.setSecurity(i1==0?false:true);
        list.add(userResouceModel1);
        //会员签到
        UserResouceModel userResouceModel2=new UserResouceModel();
        userResouceModel2.setResouceName("会员签到");
        EntityWrapper<Relation> relationEntityWrapper2= new EntityWrapper<Relation>();
        relationEntityWrapper2.eq("roleid",change.getRoleid());
        relationEntityWrapper2.eq("menuid","171");
        int i2= relationService.selectCount(relationEntityWrapper2);
        userResouceModel2.setSecurity(i2==0?false:true);
        list.add(userResouceModel2);
         //积分查询
        UserResouceModel userResouceModel3=new UserResouceModel();
        userResouceModel3.setResouceName("积分查询");
        EntityWrapper<Relation> relationEntityWrapper3= new EntityWrapper<Relation>();
        relationEntityWrapper3.eq("roleid",change.getRoleid());
        relationEntityWrapper3.eq("menuid","175");
        int i3= relationService.selectCount(relationEntityWrapper3);
        userResouceModel3.setSecurity(i3==0?false:true);
        list.add(userResouceModel3);
        //新增积分
        UserResouceModel userResouceModel4=new UserResouceModel();
        userResouceModel4.setResouceName("新增积分");
        EntityWrapper<Relation> relationEntityWrapper4= new EntityWrapper<Relation>();
        relationEntityWrapper4.eq("roleid",change.getRoleid());
        relationEntityWrapper4.eq("menuid","176");
        int i4= relationService.selectCount(relationEntityWrapper4);
        userResouceModel4.setSecurity(i4==0?false:true);
        list.add(userResouceModel4);
        //签到查询
        UserResouceModel userResouceModel5=new UserResouceModel();
        userResouceModel5.setResouceName("签到查询");
        EntityWrapper<Relation> relationEntityWrapper5= new EntityWrapper<Relation>();
        relationEntityWrapper5.eq("roleid",change.getRoleid());
        relationEntityWrapper5.eq("menuid","178");
        int i5= relationService.selectCount(relationEntityWrapper5);
        userResouceModel5.setSecurity(i5==0?false:true);
        list.add(userResouceModel5);
        //挂失补卡
        UserResouceModel userResouceModel6=new UserResouceModel();
        userResouceModel6.setResouceName("挂失补卡");
        EntityWrapper<Relation> relationEntityWrapper6= new EntityWrapper<Relation>();
        relationEntityWrapper6.eq("roleid",change.getRoleid());
        relationEntityWrapper6.eq("menuid","179");
        int i6= relationService.selectCount(relationEntityWrapper6);
        userResouceModel6.setSecurity(i6==0?false:true);
        list.add(userResouceModel6);
        //积分赠送
        UserResouceModel userResouceModel7=new UserResouceModel();
        userResouceModel7.setResouceName("积分赠送");
        EntityWrapper<Relation> relationEntityWrapper7= new EntityWrapper<Relation>();
        relationEntityWrapper7.eq("roleid",change.getRoleid());
        relationEntityWrapper7.eq("menuid","181");
        int i7= relationService.selectCount(relationEntityWrapper7);
        userResouceModel7.setSecurity(i7==0?false:true);
        list.add(userResouceModel7);
        //会员补签
        UserResouceModel userResouceModel8=new UserResouceModel();
        userResouceModel8.setResouceName("会员补签");
        EntityWrapper<Relation> relationEntityWrapper8= new EntityWrapper<Relation>();
        relationEntityWrapper8.eq("roleid",change.getRoleid());
        relationEntityWrapper8.eq("menuid","192");
        int i8= relationService.selectCount(relationEntityWrapper8);
        userResouceModel8.setSecurity(i8==0?false:true);
        list.add(userResouceModel8);
        //数据报表
        UserResouceModel userResouceModel9=new UserResouceModel();
        userResouceModel9.setResouceName("数据报表");
        EntityWrapper<Relation> relationEntityWrapper9= new EntityWrapper<Relation>();
        relationEntityWrapper9.eq("roleid",change.getRoleid());
        relationEntityWrapper9.eq("menuid","187");
        int i9= relationService.selectCount(relationEntityWrapper9);
        userResouceModel9.setSecurity(i9==0?false:true);
        list.add(userResouceModel9);
        //复签
        UserResouceModel userResouceModel10=new UserResouceModel();
        userResouceModel10.setResouceName("复签");
        userResouceModel10.setSecurity(i2==0?false:true);
        list.add(userResouceModel10);
    }
}
