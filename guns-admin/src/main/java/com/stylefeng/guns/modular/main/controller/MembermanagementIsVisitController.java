package com.stylefeng.guns.modular.main.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.main.service.*;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.Membershipcardtype;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会员管理控制器
 *
 * @author fengshuonan
 * @Date 2018-08-10 16:00:02
 */
@Controller
@Scope("prototype")
@RequestMapping("/membermanagementisvisit")
public class MembermanagementIsVisitController extends BaseController {

    private String PREFIX = "/main/membermanagementisvisit/";
    private List<Membermanagement> membermanagements;

    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMemberCardService memberCardService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private IMemberBamedicalService memberBamedicalService;
    @Autowired
    private IBaMedicalService baMedicalService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IntegralrecordController integralrecordController;
    @Autowired
    private ActivityController activityController;
    @Autowired
    private IMemberInactivityService memberInactivityService;

    /**
     * 跳转到会员管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        BaseEntityWrapper<User> deptBaseEntityWrapper = new BaseEntityWrapper<>();
        List list = userService.selectList(deptBaseEntityWrapper);
        model.addAttribute("staffs", list);
        EntityWrapper<Dept> deptBaseEntityWrapper1 = new EntityWrapper<>();
        if (ShiroKit.getUser().getAccount().equals("admin")) {
        } else {
            deptBaseEntityWrapper1.eq("id", ShiroKit.getUser().getDeptId());
        }
        List depts = deptService.selectList(deptBaseEntityWrapper1);
        model.addAttribute("depts", depts);
        //更改待回访状态

        membermanagementService.updateisvisit(getSpecifiedDayBefore(DateUtil.formatDate(new Date(), "yyyy-MM-dd"), 2));
        return PREFIX + "membermanagement.html";
    }


    /**
     * 获取会员管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer name) {
        Page<Membermanagement> page = new PageFactory<Membermanagement>().defaultPage();
        String specifiedDayBefore = getSpecifiedDayBefore(DateUtil.formatDate(new Date(), "yyyy-MM-dd"), name);
        EntityWrapper<Membermanagement> baseEntityWrapper = new EntityWrapper<>();
        baseEntityWrapper.le("CheckINTime1",specifiedDayBefore+" 23:59:59");
        baseEntityWrapper.le("deptId",ShiroKit.getUser().getDeptId());
        if(name!=null){
            baseEntityWrapper.eq("state", 0);
        }else {
            baseEntityWrapper.eq("state", -1);

        }
        membermanagements = membermanagementService.selectList(baseEntityWrapper);
        Page<Map<String, Object>> mapPage = membermanagementService.selectMapsPage(page, baseEntityWrapper);
        List<Map<String, Object>> records = mapPage.getRecords();
        for (Map<String, Object> map : records) {
            String s = (String) map.get("levelID");
            Integer id = (int) map.get("id");
            Membershipcardtype membershipcardtype = membershipcardtypeService.selectById(s);
            if (membershipcardtype != null) {
                map.put("levelID", membershipcardtype.getCardname());
            }
        }
        return super.packForBT(mapPage);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer membermanagementId) {
        Membermanagement membermanagement = membermanagementService.selectById(membermanagementId);
        membermanagement.setIsvisit(2);
        membermanagementService.updateById(membermanagement);
        return SUCCESS_TIP;
    }
    @BussinessLog(value = "会员资料导出", key = "export_excel")
    @RequestMapping("export_excel")
    public void export(HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<Map<String, Object>> memberExcels = new ArrayList<>();
        for (Membermanagement m : membermanagements) {
            Map<String, Object> mMap = new LinkedHashMap<>();
            mMap.put("name", m.getName());
            mMap.put("sex", m.getSex());
            mMap.put("phone", m.getPhone());
            mMap.put("address", m.getAddress());
            mMap.put("integral", m.getIntegral());
            mMap.put("countPrice", m.getCountPrice());
            mMap.put("isoldsociety", m.getIsoldsociety());
            mMap.put("level", membershipcardtypeService.selectById(m.getLevelID()).getCardname());
            mMap.put("createDt", m.getCreateTime());
            mMap.put("deptName", deptService.selectById(m.getDeptId()).getFullname());
            memberExcels.add(mMap);
        }
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(100);
        SXSSFSheet sxssfSheet = sxssfWorkbook.createSheet();
        Map<String, Object> mapTile = memberExcels.get(0);
        //创建excel 数据列名
        SXSSFRow rowTitle = sxssfSheet.createRow(0);
        Integer j = 0;
        for (Map.Entry<String, Object> entry : mapTile.entrySet()) {
            if (entry.getKey().equals("name")) {
                CellUtil.createCell(rowTitle, j, "客户名称");
            } else if (entry.getKey().equals("sex")) {
                CellUtil.createCell(rowTitle, j, "性别");
            } else if (entry.getKey().equals("phone")) {
                CellUtil.createCell(rowTitle, j, "联系电话");
            } else if (entry.getKey().equals("address")) {
                CellUtil.createCell(rowTitle, j, "联系地址");
            } else if (entry.getKey().equals("integral")) {
                CellUtil.createCell(rowTitle, j, "可用积分");
            } else if (entry.getKey().equals("countPrice")) {
                CellUtil.createCell(rowTitle, j, "总积分");
            } else if (entry.getKey().equals("isoldsociety")) {
                CellUtil.createCell(rowTitle, j, "是否老年协会会员");
            } else if (entry.getKey().equals("level")) {
                CellUtil.createCell(rowTitle, j, "卡片等级");
            } else if (entry.getKey().equals("createDt")) {
                CellUtil.createCell(rowTitle, j, "开卡时间");
            } else if (entry.getKey().equals("deptName")) {
                CellUtil.createCell(rowTitle, j, "门店名称");
            }
            j++;
        }
        for (int i = 0; i < memberExcels.size(); i++) {
            Map<String, Object> nMap = memberExcels.get(i);
            SXSSFRow row = sxssfSheet.createRow(i + 1);
            // 数据
            Integer k = 0;
            for (Map.Entry<String, Object> ma : nMap.entrySet()) {
                String value = "";
                if (ma.getValue() != null) {
                    value = ma.getValue().toString();
                }
                CellUtil.createCell(row, k, value);
                k++;
            }
        }
        response.setHeader("content-Type", "application/vnc.ms-excel;charset=utf-8");
        //文件名使用uuid，避免重复
        response.setHeader("Content-Disposition", "attachment;filename=" + "会员信息" + ".xlsx");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            sxssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            memberExcels.clear();
            outputStream.close();
        }
    }

    public static String getSpecifiedDayBefore(String specifiedDay,Integer cds) {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - cds);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }
}
