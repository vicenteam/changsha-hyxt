package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.Integralrecord;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.stylefeng.guns.modular.system.dao.MembermanagementMapper;
import com.stylefeng.guns.modular.main.service.IMembermanagementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员基础信息表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-10
 */
@Service
public class MembermanagementServiceImpl extends ServiceImpl<MembermanagementMapper, Membermanagement> implements IMembermanagementService {

    @Override
    public boolean updateIntegralRollBack(Integralrecord integralrecord) {
        return this.baseMapper.updateIntegralRollBack(integralrecord);
    }
}
