package com.stylefeng.guns.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.face.AipFace;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.annotion.Permission;
import com.stylefeng.guns.core.common.constant.Const;
import com.stylefeng.guns.core.common.constant.dictmap.UserDict;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.state.ManagerStatus;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.db.Db;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.face.FaceMode;
import com.stylefeng.guns.modular.face.FaceUser;
import com.stylefeng.guns.modular.face.FaceUtil;
import com.stylefeng.guns.modular.main.service.IUserAttendanceService;
import com.stylefeng.guns.modular.system.dao.UserMapper;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.model.UserAttendance;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.system.transfer.UserDto;
import com.stylefeng.guns.modular.system.warpper.UserWarpper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static String PREFIX = "/system/user/";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAttendanceService userAttendanceService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/user_add")
    public String addView(HttpServletRequest request) {
        //清除人脸识别临时图片数据
        request.getSession().removeAttribute("userBase64ImgData");
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到角色分配页面
     */
    //@RequiresPermissions("/mgr/role_assign")  //利用shiro自带的权限检查
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String roleAssign(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = (User) Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model, HttpServletRequest request) {
        //清除人脸识别临时图片数据
        request.getSession().removeAttribute("userBase64ImgData");
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userService.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
//        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Integer userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userService.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new GunsException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new GunsException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 查询管理员列表
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        if (ShiroKit.isAdmin()) {
            List<Map<String, Object>> users = userService.selectUsers(null, name, beginTime, endTime, deptid);
            return new UserWarpper(users).warp();
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            List<Map<String, Object>> users = userService.selectUsers(dataScope, name, beginTime, endTime, deptid);
            return new UserWarpper(users).warp();
        }
    }

    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid UserDto user, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断账号是否重复
        User theUser = userService.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new GunsException(BizExceptionEnum.USER_ALREADY_REG);
        }

        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());

        this.userService.insert(UserFactory.createUser(user));

        //更新人脸库
        new Runnable() {
            @Override
            public void run() {
                String userBase64ImgData = (String) request.getSession().getAttribute("userBase64ImgData");
                log.info("base64->" + userBase64ImgData);
                if (!StringUtils.isEmpty(userBase64ImgData)) {
                    AipFace client = new AipFace(FaceUtil.APP_ID, FaceUtil.API_KEY, FaceUtil.SECRET_KEY);
                    new FaceUtil().userRegister(client, JSON.toJSONString(user), userBase64ImgData, user.getDeptid() + "", user.getId() + "");
                }
            }
        }.run();

        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Tip edit(@Valid UserDto user, BindingResult result, HttpServletRequest request) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        User oldUser = userService.selectById(user.getId());

        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            this.userService.updateById(UserFactory.editUser(user, oldUser));
            //更新人脸库
            new Runnable() {
                @Override
                public void run() {
                    String userBase64ImgData = (String) request.getSession().getAttribute("userBase64ImgData");
                    log.info("base64->" + userBase64ImgData);
                    if (!StringUtils.isEmpty(userBase64ImgData)) {
                        AipFace client = new AipFace(FaceUtil.APP_ID, FaceUtil.API_KEY, FaceUtil.SECRET_KEY);
                        System.out.println(FaceUtil.APP_ID+"---");
                        if (StringUtils.isEmpty(user.getAvatar())) {//新增
                            new FaceUtil().userRegister(client, JSON.toJSONString(user), userBase64ImgData, user.getDeptid() + "", user.getId() + "");
                        } else {//更新
                            new FaceUtil().faceUpdate(client, user.getId() + "", userBase64ImgData, JSON.toJSONString(user), user.getDeptid() + "");

                        }
                    }
                }
            }.run();
            //更改密码
            if (user.getPassword() != null) {
                if (!user.getPassword().equals(oldUser.getPassword())) {
                    User userPass = this.userService.selectById(user.getId());
                    userPass.setSalt(oldUser.getSalt());
                    userPass.setPassword(ShiroKit.md5(user.getPassword(), oldUser.getSalt()));
                    this.userService.updateById(userPass);
                }
            }
            return SUCCESS_TIP;
        } else {
            assertAuth(user.getId());
            ShiroUser shiroUser = ShiroKit.getUser();
            if (shiroUser.getId().equals(user.getId())) {
                this.userService.updateById(UserFactory.editUser(user, oldUser));
                //更新人脸库
                new Runnable() {
                    @Override
                    public void run() {
                        String userBase64ImgData = (String) request.getSession().getAttribute("userBase64ImgData");
                        log.info("base64->" + userBase64ImgData);
                        if (!StringUtils.isEmpty(userBase64ImgData)) {
                            AipFace client = new AipFace(FaceUtil.APP_ID, FaceUtil.API_KEY, FaceUtil.SECRET_KEY);
                            if (StringUtils.isEmpty(user.getAvatar())) {//新增
                                new FaceUtil().userRegister(client, JSON.toJSONString(user), userBase64ImgData, user.getDeptid() + "", user.getId() + "");
                            } else {//更新
                                new FaceUtil().faceUpdate(client, user.getId() + "", userBase64ImgData, JSON.toJSONString(user), user.getId() + "");

                            }
                        }
                    }
                }.run();
                //更改密码
                if (user.getPassword() != null) {
                    if (!user.getPassword().equals(oldUser.getPassword())) {
                        User userPass = this.userService.selectById(user.getId());
                        userPass.setSalt(oldUser.getSalt());
                        userPass.setPassword(ShiroKit.md5(user.getPassword(), oldUser.getSalt()));
                        this.userService.updateById(userPass);
                    }
                }
                return SUCCESS_TIP;
            } else {
                throw new GunsException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return this.userService.selectById(userId);
    }

    /**
     * 重置管理员的密码
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip reset(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userService.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    @RequestMapping("/roleAssignByDeptId")
    @ResponseBody
    public Tip roleAssignByDeptId(String deptid) {
        if (!StringUtils.isEmpty(deptid)) {
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("deptid", deptid);
            List<User> users = userService.selectList(userEntityWrapper);
            for (User user : users) {
                setRole(user.getId(), "18");//防差角色
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture, HttpServletRequest request) {
        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            byte[] bytes = picture.getBytes();
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(bytes);
            request.getSession().setAttribute("userBase64ImgData", encode);//临时存储人脸识别创建图片数据
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/upload1")
    @ResponseBody
    public String upload1(HttpServletRequest request) {
        String imgStr;//接收经过base64编 之后的字符串
        imgStr = request.getParameter("file");
        String pictureName = "qq";//UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            String fileSavePath = gunsProperties.getFileUploadPath().substring(0, gunsProperties.getFileUploadPath().length() - 1);
            BASE64Decoder decoder = new BASE64Decoder();                // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }                // 生成jpeg图片
            String imgFilePath = "";
            pictureName = imgFilePath = UUID.randomUUID().toString() + "." + "jpg";
            OutputStream out = null;
            try {
                out = new FileOutputStream(fileSavePath + "/" + imgFilePath);
                log.info("base64-》" + imgStr);
                log.info("保存文件地址-》" + fileSavePath);
                log.info("保存文件地址-》" + fileSavePath + "/" + imgFilePath);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * 打卡识别图
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/checkFace")
    @ResponseBody
    public Object checkFace(HttpServletRequest request) {
        User user2 = new User();
        String imgStr;//接收经过base64编 之后的字符串
        imgStr = request.getParameter("file");
        String pictureName = "qq";//UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            log.info("base64-》" + imgStr);
            //进行人脸对比
            AipFace client = new AipFace(FaceUtil.APP_ID, FaceUtil.API_KEY, FaceUtil.SECRET_KEY);
            FaceUtil faceUtil = new FaceUtil();
            JSONObject user = faceUtil.findUser(client, imgStr, ShiroKit.getUser().getDeptId() + "");
            if (user.getString("error_msg").equals("liveness check fail")) {
                log.info("活性检查失败");
                user2.setVersion(202);
                user2.setAvatar("暂无匹配数据");
                return user2;
            } else if (user.getString("error_msg").equals("pic not has face")) {
                log.info("照片没有脸");
                user2.setVersion(203);
                user2.setAvatar("待识别");
                return user2;
            }
            //获得对比结果
            FaceMode faceMode = JSON.parseObject(user.toString(2), FaceMode.class);
            if (faceMode != null && faceMode.getResult() != null && faceMode.getResult().getUser_list().size() >= 1) {
                //数据转换
                FaceUser faceUser = faceMode.getResult().getUser_list().get(0);
                String deptId = faceUser.getGroup_id();
                String user_id = faceUser.getUser_id();
                Double score = faceUser.getScore();
                if (score >= 60) {//图片相似率
                    //数据处理
                    User user1 = userService.selectById(user_id);
                    String format = DateUtil.format(new Date(), "yyyy-MM-dd");
                    //判断上午或者下午
                    int hous = Integer.parseInt(DateUtil.format(new Date(), "HH"));
                    if (hous >= 0 && hous <= 12) {
                        log.info("上班打卡");
                        EntityWrapper<UserAttendance> userAttendanceEntityWrapper = new EntityWrapper<>();
                        userAttendanceEntityWrapper.eq("checkYearMonth", format);
                        userAttendanceEntityWrapper.eq("userId", user_id);
                        userAttendanceEntityWrapper.eq("deptId", user1.getDeptid());
                        int i = userAttendanceService.selectCount(userAttendanceEntityWrapper);
                        if (i == 0) {
                            UserAttendance userAttendance = new UserAttendance();
                            userAttendance.setCheckTime1(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            userAttendance.setDeptId(user1.getDeptid());
                            userAttendance.setUserId(Integer.parseInt(user_id));
                            userAttendance.setCheckYearMonth(format);
                            userAttendanceService.insert(userAttendance);
                        }

                    } else {
                        log.info("下班打卡");
                        EntityWrapper<UserAttendance> userAttendanceEntityWrapper = new EntityWrapper<>();
                        userAttendanceEntityWrapper.eq("checkYearMonth", format);
                        userAttendanceEntityWrapper.eq("userId", user_id);
                        userAttendanceEntityWrapper.eq("deptId", user1.getDeptid());
                        int i = userAttendanceService.selectCount(userAttendanceEntityWrapper);
                        if (i == 0) {
                            UserAttendance userAttendance = new UserAttendance();
                            userAttendance.setCheckTime1(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            userAttendance.setDeptId(user1.getDeptid());
                            userAttendance.setUserId(Integer.parseInt(user_id));
                            userAttendance.setCheckYearMonth(format);
                            userAttendance.setCheckTime2(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            userAttendanceService.insert(userAttendance);
                        } else {
                            UserAttendance userAttendance = userAttendanceService.selectOne(userAttendanceEntityWrapper);
                            userAttendance.setCheckTime2(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            userAttendanceService.updateById(userAttendance);
                        }
                    }

//                    userAttendanceEntityWrapper.eq("")
                    //返回结果
                    user1.setVersion(200);
                    return user1;
                } else {
                    user2.setVersion(202);
                    user2.setAvatar("暂无匹配数据");
                    return user2;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return null;
    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Integer userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        List<Integer> deptDataScope = ShiroKit.getDeptDataScope();
        User user = this.userService.selectById(userId);
        Integer deptid = user.getDeptid();
        if (deptDataScope.contains(deptid)) {
            return;
        } else {
            throw new GunsException(BizExceptionEnum.NO_PERMITION);
        }

    }

    /**
     * 员工考勤页面
     *
     * @return
     */
    @RequestMapping("/userCheckIn")
    public String userCheckIn() {
        return PREFIX + "userCheckIn.html";
    }

    /**
     * 签到记录页面
     *
     * @param membermanagementId
     * @param model
     * @return
     */
    @RequestMapping("/membermanagementcheckHistory/{membermanagementId}")
    public String membermanagementcheckHistory(@PathVariable Integer membermanagementId, Model model) {
        model.addAttribute("memberId", membermanagementId);
        return PREFIX + "membermanagementcheckHistory.html";
    }

}
