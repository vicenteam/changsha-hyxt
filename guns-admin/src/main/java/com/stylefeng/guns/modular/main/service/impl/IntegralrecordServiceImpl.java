package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.Integralrecord;
import com.stylefeng.guns.modular.system.dao.IntegralrecordMapper;
import com.stylefeng.guns.modular.main.service.IIntegralrecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分记录 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
@Service
public class IntegralrecordServiceImpl extends ServiceImpl<IntegralrecordMapper, Integralrecord> implements IIntegralrecordService {

    @Override
    public List<Map<String, Object>> productSalesRanking(Integer pagetNum, Integer pageSize, String deptId, String monthTime1, String monthTime2, String periodTime1, String periodTime2, String orderBy, String desc) {
        return this.baseMapper.productSalesRanking(pagetNum,pageSize,deptId,monthTime1,monthTime2,periodTime1,periodTime2,orderBy,desc);
    }

    @Override
    public int productSalesRankingintCount(Integer pagetNum, Integer pageSize, String deptId, String monthTime1, String monthTime2, String periodTime1, String periodTime2, String orderBy, String desc) {
        return this.baseMapper.productSalesRankingCount(pagetNum,pageSize,deptId,monthTime1,monthTime2,periodTime1,periodTime2,orderBy,desc);
    }
}
