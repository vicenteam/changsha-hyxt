package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.UserAttendance;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户考勤信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-07
 */
public interface UserAttendanceMapper extends BaseMapper<UserAttendance> {

    public List<Map<String,Object>> findUserAttendanceData(
                        @Param("deptList") List<Dept> deptList, @Param("name") String name
                        , @Param("begindate") String begindate, @Param("enddate") String enddate
                        , @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
