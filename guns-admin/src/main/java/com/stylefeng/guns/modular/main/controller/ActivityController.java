package com.stylefeng.guns.modular.main.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.*;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.task.ActivityTask;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 活动管理控制器
 *
 * @author fengshuonan
 * @Date 2018-08-16 10:54:46
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    private String PREFIX = "/main/activity/";

    @Autowired
    private IActivityService activityService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IActivityMemberService activityMemberService;
    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private IntegralrecordController integralrecordController;
    @Autowired
    private ActivityTask activityTask;
    @Autowired
    private MembermanagementController membermanagementController;
    @Autowired
    private IMemberInactivityService memberInactivityService;

    /**
     * 跳转到活动管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "activity.html";
    }

    /**
     * 跳转到添加活动管理
     */
    @RequestMapping("/activity_add")
    public String activityAdd() {
        return PREFIX + "activity_add.html";
    }

    /**
     * 跳转到修改活动管理
     */
    @RequestMapping("/activity_update/{activityId}")
    public String activityUpdate(@PathVariable Integer activityId, Model model) {
        Activity activity = activityService.selectById(activityId);
        model.addAttribute("item", activity);
        LogObjectHolder.me().set(activity);
        return PREFIX + "activity_edit.html";
    }

    /**
     * 获取活动管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) throws ParseException {
        activityTask.scannerActivity();
        Page<Activity> page = new PageFactory<Activity>().defaultPage();
        BaseEntityWrapper<Activity> baseEntityWrapper = new BaseEntityWrapper<>();
        baseEntityWrapper.orderBy("createtime",false);
        if(!StringUtils.isEmpty(condition))baseEntityWrapper.like("name",condition);
        Page<Activity> result = activityService.selectPage(page, baseEntityWrapper);
        List<Activity> records = result.getRecords();
        records.forEach(a -> {
            String creater = a.getCreater();
            User user = userService.selectById(creater);
            a.setCreater(user.getName());
            //获取当前活动签到人数
            BaseEntityWrapper<ActivityMember> activityMemberBaseEntityWrapper = new BaseEntityWrapper<>();
            activityMemberBaseEntityWrapper.eq("activityid",a.getId());
            int i = activityMemberService.selectCount(activityMemberBaseEntityWrapper);
            a.setJifen((double) i);
        });
        return super.packForBT(result);
    }

    /**
     * 新增活动管理
     */
    @BussinessLog(value = "新增活动管理", key = "xzhdgl")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Activity activity) {
        activity.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        activity.setCreater(ShiroKit.getUser().getId() + "");
        activity.setDeptid(ShiroKit.getUser().getDeptId() + "");
        String begindate = activity.getBegindate();
        String createtime = activity.getCreatetime();
        Date date = DateUtil.parse(begindate, "yyyy-MM-dd HH:mm:ss");
        Date date2 = DateUtil.parse(createtime, "yyyy-MM-dd HH:mm:ss");
        if (date2.getTime() >= date.getTime()) {
            activity.setStatus(2);
        } else {
            activity.setStatus(0);
        }
        activityService.insert(activity);
        return SUCCESS_TIP;
    }

    /**
     * 删除活动管理
     */
    @BussinessLog(value = "删除活动管理", key = "schdgl")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer activityId) {
        activityService.deleteById(activityId);
        return SUCCESS_TIP;
    }

    /**
     * 修改活动管理
     */
    @BussinessLog(value = "修改活动管理", key = "xghdgl")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Activity activity) {
        activityService.updateById(activity);
        return SUCCESS_TIP;
    }

    /**
     * 活动管理详情
     */
    @RequestMapping(value = "/activity_lingqu/{activityId}")
    public Object detail(@PathVariable("activityId") Integer activityId, Model model) {
        Activity activity = activityService.selectById(activityId);
        model.addAttribute("item", activity);
        return PREFIX + "activity_lingqu.html";
    }

    /**
     * 通过id获取用户信息
     */
    @RequestMapping("getMemberInfo")
    @ResponseBody
    public Object selectMemberInfo(Integer memberId, String activityId) throws ParseException {
        Membermanagement m = membermanagementService.selectById(memberId);
        Membershipcardtype ms = membershipcardtypeService.selectById(m.getLevelID());
        Map<String, Object> memberinfo = new HashMap<>();
        memberinfo.put("cadID", m.getCadID());
        memberinfo.put("name", m.getName());
        memberinfo.put("id", m.getId());
        memberinfo.put("phone", m.getPhone());
        memberinfo.put("address", m.getAddress());
        memberinfo.put("countPrice", m.getCountPrice());
        memberinfo.put("levelID", ms.getCardname());
        memberinfo.put("integral", m.getIntegral());
        //获取活动类型
        Activity activity = activityService.selectById(activityId);
        Integer ruleexpression = activity.getRuleexpression();
        //是否存在参与活动或参与活动是否达到最大上限
        EntityWrapper<ActivityMember> qiWrapper = new EntityWrapper<>();
        qiWrapper.eq("memberid", memberId);
        qiWrapper.eq("activityid", activityId);
        int i1 = activityMemberService.selectCount(qiWrapper);
        if (i1 >= activity.getMaxgetnum()) {
            memberinfo.put("error", "202");
            memberinfo.put("errorMessage", "你已经参与过该活动或已达参与最大上限!");
        } else {
            if (ruleexpression != 2) {//累计 连续 签到
                String begindate = activity.getBegindate();//活动开始日期
                String enddate = activity.getEnddate();//活动结束日期
                //查询会员签到记录从开始日期-结束日期的总签到次数
                EntityWrapper<QiandaoCheckin> qWrapper = new EntityWrapper<>();
                qWrapper.eq("memberid", memberId);
                qWrapper.isNotNull("updatetime");
                qWrapper.between("createtime", begindate, enddate);
                int i = qiandaoCheckinService.selectCount(qWrapper);
                //对比总签到次数与活动累计签到次数 >=满足
                if (i >= activity.getQiandaonum()) {
                    memberinfo.put("error", "200");
//                    insertAcitvityMember(activityId, memberId + "");
                } else {
                    //获取明天时间
                    Date date = new Date();//取时间
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
                    date = calendar.getTime(); //这个时间就是日期往后推一天的结果

                    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
                    long from = date.getTime();
                    long to = simpleFormat.parse(enddate).getTime();
                    int days = (int) ((to - from) / (1000 * 60 * 60 * 24));//时间天数差
                    if (days >= (activity.getQiandaonum() - i)) {
                        memberinfo.put("error", "202");
                        memberinfo.put("errorMessage", "你还需签到 " + (activity.getQiandaonum() - i) + "次 才可领取!");
                    } else {
                        memberinfo.put("error", "202");
                        memberinfo.put("errorMessage", "签到未能满足该条件 请参加下一次活动!");
                    }
                }
                //小于的话 判读当前日期后面签到最大天数是否> (累计签到次数-当前签到次数)?"提示还需要多少次签到":"请参加下一次活动"

            }else {//判断会员积分是否足够参与该活动
                Double integral = m.getIntegral();
                if(integral<activity.getJifen()){
                    memberinfo.put("error", "202");
                    memberinfo.put("errorMessage", "积分不足!");
                }
            }
        }
//        BaseEntityWrapper<ActivityMember> activityMemberBaseEntityWrapper = new BaseEntityWrapper<>兑换();
//        activityMemberBaseEntityWrapper.eq("memberid", memberinfo.get("id"));
//        activityMemberBaseEntityWrapper.eq("activityid", activityId);
//        int i = activityMemberService.selectCount(activityMemberBaseEntityWrapper);
        memberinfo.put("countnum", i1);
        return memberinfo;
    }

    /**
     * 领取活动奖励
     *
     * @param activityId
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/lingqu")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Object lingqu(String activityId, String memberId) throws Exception {
        Activity activity = activityService.selectById(activityId);
        Integer ruleexpression = activity.getRuleexpression();
        if (ruleexpression == 2) {//积分操作 积分兑换
            Double jifen = activity.getJifen();//将被扣除的积分
            //积分操作
            BaseEntityWrapper<Membermanagement> mWrapper = new BaseEntityWrapper<>();
            mWrapper.eq("id",memberId);
            List<Membermanagement> membermanagements = membermanagementService.selectList(mWrapper);
            //调用积分变动方法
            integralrecordController.insertIntegral(jifen,2, 2, membermanagements);
        }else if (ruleexpression == 3||ruleexpression == 4){
            //清除一条待领取活动信息
            EntityWrapper<MemberInactivity> baseEntityWrapper=new EntityWrapper<MemberInactivity>();
            baseEntityWrapper.eq("memberId",memberId);
            baseEntityWrapper.eq("activityId",activityId);
            List<MemberInactivity> list=memberInactivityService.selectList(baseEntityWrapper);
            if(list.size()>=1){
                MemberInactivity memberInactivity=list.get(0);
                memberInactivityService.deleteById(memberInactivity.getId());
                if(ruleexpression == 3){ //推荐人获得积分
                    Double jifen = activity.getJifen();
                    //积分操作
                    BaseEntityWrapper<Membermanagement> mWrapper = new BaseEntityWrapper<>();
                    mWrapper.eq("id",memberId);
                    List<Membermanagement> membermanagements = membermanagementService.selectList(mWrapper);
                    //调用积分变动方法
                    integralrecordController.insertIntegral(jifen,2, 1, membermanagements);
                }
            }else {
                throw new GunsException(BizExceptionEnum.NO_PERMITION1);
            }
        }
            insertAcitvityMember(activityId, memberId,Integer.parseInt(activity.getDeptid()));
        return SUCCESS_TIP;
    }

    public void insertAcitvityMember(String activityId, String memberId,Integer deptId) {
        ActivityMember activityMember = new ActivityMember();
        activityMember.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        activityMember.setActivityid(Integer.parseInt(activityId));
        activityMember.setMemberid(Integer.parseInt(memberId));
        activityMember.setDeptid(deptId);
        activityMemberService.insert(activityMember);
    }

    /**
     * 活动领取查询页面跳转
     * @return
     */
    @RequestMapping("/activitygetpage")
    public String activitygetpage() {
        return PREFIX + "activitygetpage.html";
    }
    /**
     * 获取活动管理列表
     */
    @RequestMapping(value = "/activitygetpagelist")
    @ResponseBody
    public Object activitygetpagelist(String condition,Integer type) throws ParseException {
        Membermanagement membermanagement=null;
        //获取用户信息
        if(type==0){
            BaseEntityWrapper<Membermanagement> baseEntityWrapper=new BaseEntityWrapper<Membermanagement>();
            baseEntityWrapper.eq("cadID",condition);
            membermanagement=membermanagementService.selectOne(baseEntityWrapper);
        }else if(type==1){
            Object obj=membermanagementController.getUserInfo(condition);
            if(!(obj).equals("202")){
                MemberCard memberCard=(MemberCard)obj;
                membermanagement=membermanagementService.selectById(memberCard.getMemberid());
            }
        }else if(type==2){
            membermanagement=membermanagementService.selectById(condition);
        }
        //查询用户已领取活动时间排序
        Page<ActivityMember> page = new PageFactory<ActivityMember>().defaultPage();
        if(membermanagement!=null){
            BaseEntityWrapper<ActivityMember> baseEntityWrapper = new BaseEntityWrapper<>();
            baseEntityWrapper.eq("memberid",membermanagement.getId());
            baseEntityWrapper.orderBy("createtime",false);
            Page<ActivityMember> result =activityMemberService.selectPage(page,baseEntityWrapper);
            Page<Map<String,Object>> page2 = new PageFactory<Map<String,Object>>().defaultPage();
            page2.setRecords(new ArrayList<>());
           List<ActivityMember> list= result.getRecords();
           list.forEach(a->{
              Activity activity= activityService.selectById(a.getActivityid());
              Map<String,Object> map=new HashMap<>();
              map.put("id",activity.getId());
              map.put("name",activity.getName());
              map.put("createtime",a.getCreatetime());
               page2.getRecords().add(map);
           });
            page2.setTotal(result.getTotal());
            return super.packForBT(page2);
        }
        return super.packForBT(page);
    }
    /**
     * 获取活动管理列表
     */
    @RequestMapping(value = "/activitygetpagelist2")
    @ResponseBody
    public Object activitygetpagelist2(String condition,Integer type) throws ParseException {
        Membermanagement membermanagement=null;
        //获取用户信息
        if(type==0){
            BaseEntityWrapper<Membermanagement> baseEntityWrapper=new BaseEntityWrapper<Membermanagement>();
            baseEntityWrapper.eq("cadID",condition);
            membermanagement=membermanagementService.selectOne(baseEntityWrapper);
        }else if(type==1){
        Object obj=membermanagementController.getUserInfo(condition);
        if(!(obj).equals("202")){

            MemberCard memberCard=(MemberCard)obj;
            membermanagement=membermanagementService.selectById(memberCard.getMemberid());
          }
        }
        Page<ActivityMember> result =null;
        System.out.println(membermanagement!=null);
        if(membermanagement!=null){

            BaseEntityWrapper<Membermanagement> baseEntityWrapper=new BaseEntityWrapper<Membermanagement>();
            baseEntityWrapper.eq("introducerId",membermanagement.getId());
            Page<Membermanagement> page = new PageFactory<Membermanagement>().defaultPage();
           result =membermanagementService.selectPage(page,baseEntityWrapper);
        }
        return super.packForBT(result);
    }
}
