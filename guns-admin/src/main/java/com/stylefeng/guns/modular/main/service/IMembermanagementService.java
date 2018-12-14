package com.stylefeng.guns.modular.main.service;

import com.stylefeng.guns.modular.system.model.Integralrecord;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员基础信息表 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-10
 */
public interface IMembermanagementService extends IService<Membermanagement> {

    /**
     * 恢复积分
     */
    boolean updateIntegralRollBack(Integralrecord integralrecord);
}
