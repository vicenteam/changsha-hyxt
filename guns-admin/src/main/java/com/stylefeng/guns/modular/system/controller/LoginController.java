package com.stylefeng.guns.modular.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.code.kaptcha.Constants;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.InvalidKaptchaException;
import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.node.MenuNode;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.AESUtil;
import com.stylefeng.guns.core.util.ApiMenuFilter;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IMenuService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.stylefeng.guns.core.support.HttpKit.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
//        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
//            return REDIRECT + "/";
//        } else {
//            return "/login.html";
//        }
        //
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            if(ShiroKit.getUser().getId()==null){
                return "/login.html";
            }else {
                return REDIRECT + "/";
            }

        } else {
            try{
                String path="";
                String os = System.getProperty("os.name");
                if (os.toLowerCase().startsWith("win")) {
                    path="C:/javalisten/";
                } else {
                    //linux
                    path = "/data/javalisten/";
                }
                File f = new File(path  + "listen.txt");
                if (!f.exists()) {
                    if(!new File(path).exists()){
                        new File(path).mkdir();
                    }
                    f.createNewFile();
                    return  "/listen.html";
                }else {//判断激活时间是否过期
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(f),"utf-8");//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    String timeVal="";
                    while((lineTxt = bufferedReader.readLine()) != null){
                        timeVal+=lineTxt;
                    }
                    read.close();
                    if(StringUtils.isEmpty(timeVal)){
                        return  "/listen.html";
                    }else {
                        String decrypt = AESUtil.getDecrypt(timeVal);
                        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date parse = simpleDateFormat.parse(decrypt);//到期时间
                        Date date = new Date();//当前时间
                        if(parse.getTime()>=date.getTime()){//监听时间可用
                            System.out.println("到期时间:"+decrypt);
                        }else {//不可用
                            return  "/listen.html";
                        }
                    }


                }
            }catch (Exception e){
                return  "/listen.html";
            }

            return "/login.html";
        }
    }
    @RequestMapping(value = "/listen", method = RequestMethod.POST)
    public String listen(){
        String listenVal = super.getPara("listenVal");
        listenVal.trim();
        System.out.println(listenVal+"---------");
        try {
            String path="";
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                path="C:/javalisten/";
            } else {
                //linux
                path = "/data/javalisten/";
            }
            File f = new File(path  + "listen.txt");
            if (!f.exists()) {
                if(!new File(path).exists()){
                    new File(path).mkdir();
                }
                f.createNewFile();

            }
            FileWriter fw = new FileWriter(f,false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(listenVal);
            bw.close(); fw.close();

            String decrypt = AESUtil.getDecrypt(listenVal);
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = simpleDateFormat.parse(decrypt);//到期时间
            Date date = new Date();//当前时间
            if(parse.getTime()>=date.getTime()){//监听时间可用
                return "/login.html";
            }else {//不可用
                return  "/listen.html";
            }
        }catch (Exception e){

        }
        return  "/listen.html";

    }
    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        deleteAllCookie();
        return REDIRECT + "/login";
    }
}
