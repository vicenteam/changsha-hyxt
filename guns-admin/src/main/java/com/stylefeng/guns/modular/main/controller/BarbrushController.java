package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.modular.api.controller.MemberInfoController;
import com.stylefeng.guns.modular.main.service.ICheckinService;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.stylefeng.guns.modular.main.service.IMembershipcardtypeService;
import com.stylefeng.guns.modular.main.service.IQiandaoCheckinService;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.model.QiandaoCheckin;
import com.stylefeng.guns.modular.system.service.IDeptService;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 签到数据图表controller
 */
@Controller
@RequestMapping("/barbrush")
public class BarbrushController extends BaseController {
    private String PREFIX = "/main/bar/";
    @Autowired
    private IQiandaoCheckinService qiandaoCheckinService;
    @Autowired
    private IMembermanagementService membermanagementService;
    @Autowired
    private MemberInfoController memberInfoController;
    @Autowired
    private IMembershipcardtypeService membershipcardtypeService;
    @Autowired
    private ICheckinService checkinService;
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到签到数据图表
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "barbrush.html";
    }

    /**
     * 获取周签到统计数据
     * @param startdate
     * @param enddate
     * @return
     */
    @RequestMapping(value = "/getWeekdata")
    @ResponseBody
    public Object getWeekdata(String startdate,String enddate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Map<String,Object> result=new HashMap<>();
        int[] data1=new int[7];
        int[] data2=new int[7];
        int[] data3=new int[7];
        for(int i=0;i<7;i++){
            Date date = simpleDateFormat.parse(startdate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1+i);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //获得比较前一天时间
            //获得比较时间
            Date date2 = simpleDateFormat.parse(startdate);
            Calendar calendar2 = new GregorianCalendar();
            calendar2.setTime(date2);
            calendar2.add(calendar2.DATE, i);
            date2 = calendar2.getTime();
            BaseEntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper = new BaseEntityWrapper<>();
            qiandaoCheckinBaseEntityWrapper.like("createtime",simpleDateFormat.format(date2));
            qiandaoCheckinBaseEntityWrapper.isNotNull("updatetime");

            BaseEntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper2 = new BaseEntityWrapper<>();
            qiandaoCheckinBaseEntityWrapper2.like("createtime",simpleDateFormat.format(date));
            qiandaoCheckinBaseEntityWrapper2.isNotNull("updatetime");
            int i1 = qiandaoCheckinService.selectCount(qiandaoCheckinBaseEntityWrapper);//当天签到人数
            int i2 = qiandaoCheckinService.selectCount(qiandaoCheckinBaseEntityWrapper2);//当天前一天签到人数
            data1[i]=i1;
            data2[i]=i2;
            data3[i]=(i1-i2);
        }
        result.put("data1",data1);
        result.put("data2",data2);
        result.put("data3",data3);
        return result;
    }
    /**
     * 获取月签到统计数据
     * @param startdate
     * @param enddate
     * @return
     */
    @RequestMapping(value = "/getMonthdata")
    @ResponseBody
    public Object getMonthdata(String startdate,Integer enddate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Map<String,Object> result=new HashMap<>();
        int[] data4=new int[enddate];
        int[] data5=new int[enddate];
        int[] data6=new int[enddate];
        for(int i=0;i<enddate;i++){
            Date date = simpleDateFormat.parse(startdate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1+i);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //获得比较前一天时间
            //获得比较时间
            Date date2 = simpleDateFormat.parse(startdate);
            Calendar calendar2 = new GregorianCalendar();
            calendar2.setTime(date2);
            calendar2.add(calendar2.DATE, i);
            date2 = calendar2.getTime();
            BaseEntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper = new BaseEntityWrapper<>();
            qiandaoCheckinBaseEntityWrapper.like("createtime",simpleDateFormat.format(date2));
            qiandaoCheckinBaseEntityWrapper.isNotNull("updatetime");

            BaseEntityWrapper<QiandaoCheckin> qiandaoCheckinBaseEntityWrapper2 = new BaseEntityWrapper<>();
            qiandaoCheckinBaseEntityWrapper2.like("createtime",simpleDateFormat.format(date));
            qiandaoCheckinBaseEntityWrapper2.isNotNull("updatetime");
            int i1 = qiandaoCheckinService.selectCount(qiandaoCheckinBaseEntityWrapper);//当天签到人数
            int i2 = qiandaoCheckinService.selectCount(qiandaoCheckinBaseEntityWrapper2);//当天前一天签到人数
            data4[i]=i1;
            data5[i]=i2;
            data6[i]=(i1-i2);
        }
        result.put("data4",data4);
        result.put("data5",data5);
        result.put("data6",data6);
        return result;
    }

    /**
     * 显示导出页面
     * @return
     */
    @RequestMapping("exportView")
    public Object signInView(){
        return PREFIX + "signIn_export.html";
    }

    @RequestMapping("export_excel")
    public void signInDetailsExport(HttpServletResponse response, HttpServletRequest request, String beginTime, String endTime) throws Exception{
        BaseEntityWrapper<QiandaoCheckin> qWrapper = new BaseEntityWrapper<>();
        if(! StringUtils.isEmpty(beginTime) || !StringUtils.isEmpty(endTime)){
            qWrapper.between("createTime",beginTime,endTime);
        }
        qWrapper.eq("status",0);
        List<QiandaoCheckin> qLists = qiandaoCheckinService.selectList(qWrapper);
        List<Map<String,Object>> signInExcels = new ArrayList<>();
        Membermanagement membermanagement;
        for(QiandaoCheckin list: qLists){
            membermanagement = membermanagementService.selectById(list.getMemberid());
           if(membermanagement!=null){
               Map<String,Object> map = new LinkedHashMap<>();
               map.put("name",membermanagement.getName());
               map.put("cadID",membermanagement.getCadID());
               map.put("level",membershipcardtypeService.selectById(membermanagement.getLevelID()).getCardname());
               map.put("createtime",list.getCreatetime());
               map.put("updatetime",list.getUpdatetime());
               map.put("check",checkinService.selectById(list.getCheckinid()).getScreenings());
               map.put("dept",deptService.selectById(list.getDeptid()).getFullname());
               signInExcels.add(map);
           }
        }
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(100);
        SXSSFSheet sxssfSheet = sxssfWorkbook.createSheet();
        Map<String,Object> mapTile = signInExcels.get(0);
        //创建excel 数据列名
        SXSSFRow rowTitle = sxssfSheet.createRow(0);
        Integer j = 0;
        for (Map.Entry<String,Object> entry: mapTile.entrySet()) {
            if(entry.getKey().equals("name")){
                CellUtil.createCell(rowTitle,j,"客户名称");
            }else if (entry.getKey().equals("cadID")){
                CellUtil.createCell(rowTitle,j,"身份证");
            }else if (entry.getKey().equals("level")){
                CellUtil.createCell(rowTitle,j,"卡片等级");
            }else if (entry.getKey().equals("createtime")){
                CellUtil.createCell(rowTitle,j,"签到时间");
            }else if (entry.getKey().equals("updatetime")){
                CellUtil.createCell(rowTitle,j,"复签时间");
            }else if (entry.getKey().equals("check")){
                CellUtil.createCell(rowTitle,j,"签到场次");
            }else if (entry.getKey().equals("dept")){
                CellUtil.createCell(rowTitle,j,"门店名称");
            }
            j++;
        }
        for (int i = 0; i < signInExcels.size(); i++) {
            Map<String,Object> nMap = signInExcels.get(i);
            SXSSFRow row = sxssfSheet.createRow(i+1);
            // 数据
            Integer k = 0;
            for (Map.Entry<String,Object> ma: nMap.entrySet()) {
                String value = "";
                if(ma.getValue() != null){
                    value = ma.getValue().toString();
                }
                CellUtil.createCell(row,k,value);
                k++;
            }
        }
        response.setHeader("content-Type","application/vnc.ms-excel;charset=utf-8");
        //文件名使用uuid，避免重复
        response.setHeader("Content-Disposition", "attachment;filename=" + "会员签到信息" + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            sxssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            signInExcels.clear();
            outputStream.close();
        }
    }
}
