package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.Clear;
import com.stylefeng.guns.modular.system.dao.ClearMapper;
import com.stylefeng.guns.modular.main.service.IClearService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分清零记录表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-16
 */
@Service
public class ClearServiceImpl extends ServiceImpl<ClearMapper, Clear> implements IClearService {

}
