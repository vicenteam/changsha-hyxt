package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.InventoryManagement;
import com.stylefeng.guns.modular.system.dao.InventoryManagementMapper;
import com.stylefeng.guns.modular.main.service.IInventoryManagementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库存管理 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-10
 */
@Service
public class InventoryManagementServiceImpl extends ServiceImpl<InventoryManagementMapper, InventoryManagement> implements IInventoryManagementService {

    @Override
    public List<Map<String, Object>> findSellNumber(List<Dept> dept, String begindate, String enddate) {
        return this.baseMapper.findSellNumber(dept,begindate,enddate);
    }
}
