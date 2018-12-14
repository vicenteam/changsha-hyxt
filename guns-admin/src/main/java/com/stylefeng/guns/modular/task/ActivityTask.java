package com.stylefeng.guns.modular.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import com.stylefeng.guns.modular.main.service.IActivityService;
import com.stylefeng.guns.modular.system.model.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ActivityTask {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IActivityService activityService;
    @Scheduled(cron = "2 1 0 1/1 * ?")
    public void scannerActivity() throws ParseException {
        log.info("定时更改活动状态");
        EntityWrapper<Activity> activityBaseEntityWrapper = new EntityWrapper<>();
        activityBaseEntityWrapper.ne("status",1);
        List<Activity> list = activityService.selectList(activityBaseEntityWrapper);
        for(Activity activity:list){
            String begindate = activity.getBegindate();
            String enddate = activity.getEnddate();
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
            long now = new Date().getTime();
            long start = simpleFormat.parse(begindate).getTime();
            long end = simpleFormat.parse(enddate).getTime();
            //判断当前时间是否大于开始时间
            if(activity.getStatus()==0&&(now>=start)){
                activity.setStatus(2);
                activityService.updateById(activity);
            }
            //判断当前时间是否大于结束时间
            if(activity.getStatus()==2&&(now>end)){
                activity.setStatus(1);
                activityService.updateById(activity);
            }
        }
    }
}
