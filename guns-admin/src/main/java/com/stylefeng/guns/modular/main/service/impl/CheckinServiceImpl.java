package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.Checkin;
import com.stylefeng.guns.modular.system.dao.CheckinMapper;
import com.stylefeng.guns.modular.main.service.ICheckinService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签到场次记录表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
@Service
public class CheckinServiceImpl extends ServiceImpl<CheckinMapper, Checkin> implements ICheckinService {

}
