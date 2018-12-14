package com.stylefeng.guns.core.common.BaseEntityWrapper;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;

import javax.servlet.http.HttpServletRequest;

public class BaseEntityWrapper<T> extends EntityWrapper {
    HttpServletRequest request = HttpKit.getRequest();
    Integer userOrgId =null;
    {
        try {
            userOrgId=ShiroKit.getUser().getDeptId();
        }catch (Exception e){
               userOrgId=Integer.parseInt(request.getParameter("deptId"));
        }
    }


    public BaseEntityWrapper() {
        eq("deptId", userOrgId);
    }

    @Override
    public Wrapper eq(String column, Object params) {
        return super.eq(column, params);
    }
}
