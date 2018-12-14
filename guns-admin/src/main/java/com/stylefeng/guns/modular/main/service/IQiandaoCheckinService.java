package com.stylefeng.guns.modular.main.service;

import com.stylefeng.guns.modular.system.model.QiandaoCheckin;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 复签记录表 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
public interface IQiandaoCheckinService extends IService<QiandaoCheckin> {

    /**
     * 查询最新签到时间
     * @param memberId
     * @return
     */
    String selectNewCreateTime(Integer memberId, String beginTime, String endTime);

    /**
     * 查询最新复签时间
     * @param memberId
     * @return
     */
    String selectNewUpdateTime(Integer memberId, String beginTime, String endTime);
}
