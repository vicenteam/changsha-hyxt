package com.stylefeng.guns.modular.main.service;

import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.UserAttendance;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户考勤信息表 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-07
 */
public interface IUserAttendanceService extends IService<UserAttendance> {

    public List<Map<String,Object>> findUserAttendanceData(List<Dept> deptList, String name, String begindate
                                , String enddate, Integer pageNum, Integer pageSize);
}
