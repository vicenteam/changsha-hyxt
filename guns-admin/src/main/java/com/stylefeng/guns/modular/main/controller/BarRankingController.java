package com.stylefeng.guns.modular.main.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.main.service.IMemberCardService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.system.controller.DeptController;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.MemberCard;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.utils.BarRankingExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 积分排名图表controller
 */
@Controller
@Scope("prototype")
@RequestMapping("/barranking")
public class BarRankingController extends BaseController {

    private String PREFIX = "/main/bar/";

    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IMemberCardService memberCardService;
    @Autowired
    private SellController sellController;
    @Autowired
    private  DeptController deptController;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("")
    public String index() {
        return PREFIX + "barranking.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(String deptId){
        List<Map<String, Object>> list=( List<Map<String, Object>>) deptController.findDeptLists(deptId.toString());
        String deptIds="";
        for(Map<String, Object> map:list){
            deptIds+=map.get("id")+",";
        }
        deptIds=deptIds.substring(0,deptIds.length()-1);
        Page<Membermanagement> page = new PageFactory<Membermanagement>().defaultPage();
        EntityWrapper<Membermanagement> mWrapper = new EntityWrapper<>();
        mWrapper.in("deptId",deptIds);
        mWrapper.orderBy("integral",false);
        List<Membermanagement> mList =new ArrayList<>();// membermanagementService.selectList(mWrapper);
        Page<Map<String,Object>> pagemap=membermanagementService.selectMapsPage(page,mWrapper);
        List< Map<String,Object>> mapList=pagemap.getRecords();
        mapList.forEach(a->{
            String levelID=(String)a.get("levelID");
            if(!StringUtils.isEmpty(levelID)){
                a.put("levelName",membershipcardtypeService.selectById(levelID).getCardname());
            }
        });
        return mapList;
    }

    @RequestMapping("export_excel")
    public void detailsExport(HttpServletResponse response, HttpServletRequest request)throws Exception{
        BaseEntityWrapper<Membermanagement> mWrapper = new BaseEntityWrapper<>();
        mWrapper.orderBy("integral",false);
        List<Map<String,Object>> maps = membermanagementService.selectMaps(mWrapper);
        List<BarRankingExcel> mapsExcel = new ArrayList<>();
        for (int i=0,size=maps.size(); i<size; i++){
            String levelID=(String)maps.get(i).get("levelID");
            if(!StringUtils.isEmpty(levelID)){
                BarRankingExcel excel = new BarRankingExcel();
                excel.setmLevel(membershipcardtypeService.selectById(levelID).getCardname());
                excel.setmName(maps.get(i).get("name").toString());
                excel.setmIntegral(maps.get(i).get("integral").toString());
                mapsExcel.add(excel);
            }
        }
        ExportParams params = new ExportParams();
        Workbook workbook = ExcelExportUtil.exportExcel(params, BarRankingExcel.class, mapsExcel);
        response.setHeader("content-Type","application/vnc.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("积分排名统计表", "UTF-8")+".xls");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }

    @RequestMapping(value = "/count")
    public String cardCount(Model model){
        List<Dept> deptList = new ArrayList<>();
        List<Dept> depts = sellController.getTreeMenuList(deptList, ShiroKit.getUser().getDeptId());
        model.addAttribute("depts", depts);
        return PREFIX + "barCardCount.html";
    }

    @RequestMapping(value = "/countData")
    @ResponseBody
    public Object countData(Integer deptId, String begindate, String enddate) throws Exception{
        if(deptId == null){
            deptId = ShiroKit.getUser().getDeptId();
        }
        List<Dept> deptList = new ArrayList<>();
        List<Dept> depts = sellController.getTreeMenuList(deptList, deptId);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Dept dept : depts) {
            EntityWrapper<Membermanagement> entityWrapper = new EntityWrapper();
            entityWrapper.eq("deptId",dept.getId());
            if(! StringUtils.isEmpty(begindate)) entityWrapper.gt("createTime",begindate);
            if(! StringUtils.isEmpty(enddate)) entityWrapper.lt("createTime",enddate);
            List<Membermanagement> membermanagements = membermanagementService.selectList(entityWrapper);
            Map<String,Object> map = new HashMap<>();
            map.put("deptName",dept.getFullname());
            map.put("number",membermanagements.size());
            resultList.add(map);
        }
        resultList.remove(0);
        return resultList;
    }

}
