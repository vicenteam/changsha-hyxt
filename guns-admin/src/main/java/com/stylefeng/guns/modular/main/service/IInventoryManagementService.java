package com.stylefeng.guns.modular.main.service;

import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.InventoryManagement;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库存管理 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-10
 */
public interface IInventoryManagementService extends IService<InventoryManagement> {

    /**
     * 部门id
     * @param dept
     * @return
     */
    List<Map<String,Object>> findSellNumber(List<Dept> dept);
}
