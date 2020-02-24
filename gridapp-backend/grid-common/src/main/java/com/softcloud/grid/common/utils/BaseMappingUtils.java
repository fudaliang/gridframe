/**
 * Project Name:gridapp-common
 * File Name:BaseMappingUtils.java
 * Package Name:com.softcloud.grid.common.utils
 * Date:2019年5月20日下午1:25:52
 * Copyright (c) 2019, softcloud All Rights Reserved.
 *
*/

package com.softcloud.grid.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:BaseMappingUtils <br/>
 * Function: 专门用于数据类型的映射 <br/>
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class BaseMappingUtils {
    
    private static Logger log = LoggerFactory.getLogger(BaseMappingUtils.class);
    /**
     * <转换单个对象> 此方法采用spring的BeanUtils实现，不支持类型自动转换,仅copy部分属性<br/>
     * 建议：如果能够预知不需要类型转换的情况下，请使用此方法，性能较好。
     * 
     * @param sourceObj
     *            要进行转换的源数据对象
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的对象
     * @param <T>
     *            type
     * @param <D>
     *            type
     */
    public static <T, D> T populateTbyDBySpring(D sourceObj, Class<T> clazz) {
        if (sourceObj == null) {
            return null;
        }
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("执行{}的register方法出现异常，具体原因是：{}",e);
        }
        org.springframework.beans.BeanUtils.copyProperties(sourceObj, t);
        return t;
    }

    /**
     * <转换list对象> 此方法采用spring的BeanUtils实现，不支持类型自动转换,仅copy部分属性<br/>
     * 建议：如果能够预知不需要类型转换的情况下，请使用此方法，性能较好。
     * 
     * @param sourceObjs
     *            要进行转换的源数据对象
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的List对象集合
     * @param <T>
     *            type
     * @param <D>
     *            type
     */
    public static <T, D> List<T> populateTListbyDListBySpring(List<D> sourceObjs, Class<T> clazz) {
        if (sourceObjs == null) {
            return new ArrayList<T>();
        }
        int len = sourceObjs.size();
        List<T> ts = new ArrayList<T>(len);
        T t = null;
        for (int i = 0; i < len; i++) {
            D d = sourceObjs.get(i);
            t = populateTbyDBySpring(d, clazz);
            ts.add(t);
        }
        return ts;
    }
}

