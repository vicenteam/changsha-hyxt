package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.InventoryManagement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库存管理 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-10
 */
public interface InventoryManagementMapper extends BaseMapper<InventoryManagement> {

    /**
     * 部门id
     * @param deptId
     * @return
     */
    public List<Map<String,Object>> findSellNumber(@Param("deptId") List<Dept> deptId);
}
