package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.UserAttendance;
import com.stylefeng.guns.modular.system.dao.UserAttendanceMapper;
import com.stylefeng.guns.modular.main.service.IUserAttendanceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户考勤信息表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-07
 */
@Service
public class UserAttendanceServiceImpl extends ServiceImpl<UserAttendanceMapper, UserAttendance> implements IUserAttendanceService {

    @Override
    public List<Map<String, Object>> findUserAttendanceData(List<Dept> deptList, String name, String begindate, String enddate, Integer pageNum, Integer pageSize) {
        return this.baseMapper.findUserAttendanceData(deptList,name,begindate,enddate,pageNum,pageSize);
    }
}
