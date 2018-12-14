package com.stylefeng.guns.modular.main.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.main.service.IMemberCardService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.system.model.MemberCard;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.utils.BarRankingExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/barranking")
public class BarRankingController extends BaseController {

    private String PREFIX = "/main/bar/";

    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private IMemberCardService memberCardService;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("")
    public String index() {
        return PREFIX + "barranking.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(){
        Page<Membermanagement> page = new PageFactory<Membermanagement>().defaultPage();
        BaseEntityWrapper<Membermanagement> mWrapper = new BaseEntityWrapper<>();
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
    public String cardCount(){
        return PREFIX + "barCardCount.html";
    }

    @RequestMapping(value = "/countData")
    @ResponseBody
    public Object countData(String startDate) throws Exception{
        Integer[] num = new Integer[7];
        for (int i=0; i<num.length; i++){
            Date date = simpleDateFormat.parse(startDate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //获得比较前一天时间
            BaseEntityWrapper<MemberCard> cWrapper = new BaseEntityWrapper<>();
            cWrapper.like("createtime",simpleDateFormat.format(date));
            Integer cards = memberCardService.selectCount(cWrapper);
            num[i] = cards;
        }
        return num;
    }

}
