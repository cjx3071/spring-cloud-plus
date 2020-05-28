package org.gourd.hu.core.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
/**
 * 集合复制工具类
 *
 * @author gourd.hu
 */
public class CollectionUtil {

    /**
     * 修改集合中的数据类型
     * @param list
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List copyList(List<T> list, Class tClass) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), tClass);
    }

    /**
     * map转换为object
     * @param map
     * @return
     */
    public static Object copyMap(Map map) {
        return JSON.parseObject(JSON.toJSONString(map));

    }
}
