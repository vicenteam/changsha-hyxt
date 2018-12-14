package com.stylefeng.guns.modular.api.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 序列化对象反序列化拿到新对象
 * 序列化与反序列化对象属性值相同即可赋为等值
 * @param <T>
 */
public class ReflectionObject<T> {
    /**
     * 转换单个对象
     * @param a
     * @param b
     * @return
     */
    public T change(Object a,T b){
        String s = JSON.toJSONString(a);
        final Object o = JSON.parseObject(s, b.getClass());
        return (T)o;
    }

    /**
     * 转换list对象
     * @param a
     * @param b
     * @return
     */
    public List<T> changeList(List<?> a, T b){
        String s = JSON.toJSONString(a);
        final Object o = JSON.parseArray(s, b.getClass());
        return (List<T>)o;
    }
}
