package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Integralrecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分记录 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-14
 */
public interface IntegralrecordMapper extends BaseMapper<Integralrecord> {

    List<Map<String,Object>> productSalesRanking(@Param("pageNum")Integer pagetNum,
                                                 @Param("pageSize")Integer pageSize,
                                                 @Param("deptId")String deptId,
                                                 @Param("monthTime1")String monthTime1,
                                                 @Param("monthTime2")String monthTime2,
                                                 @Param("periodTime1")String periodTime1,
                                                 @Param("periodTime2")String periodTime2,
                                                 @Param("orderBy")String orderBy,
                                                 @Param("desc")String desc);
    int productSalesRankingCount(@Param("pageNum")Integer pagetNum,
                                           @Param("pageSize")Integer pageSize,
                                           @Param("deptId")String deptId,
                                           @Param("monthTime1")String monthTime1,
                                           @Param("monthTime2")String monthTime2,
                                           @Param("periodTime1")String periodTime1,
                                           @Param("periodTime2")String periodTime2,
                                           @Param("orderBy")String orderBy,
                                           @Param("desc")String desc);
}
