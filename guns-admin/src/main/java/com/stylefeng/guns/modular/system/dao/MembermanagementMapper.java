package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Integralrecord;
import com.stylefeng.guns.modular.system.model.Membermanagement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员基础信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-10
 */
public interface MembermanagementMapper extends BaseMapper<Membermanagement> {

    /**
     * 恢复积分
     * @param integralrecord
     * @return
     */
    boolean updateIntegralRollBack(@Param("integralrecord") Integralrecord integralrecord);
    int updateisvisit(@Param("time") String time);
}
