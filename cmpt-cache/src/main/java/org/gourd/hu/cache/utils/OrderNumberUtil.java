package org.gourd.hu.cache.utils;


import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.base.utils.DateUtil;

import java.util.Date;

/**
 * @author gourd
 */
public class OrderNumberUtil {

    /**
     * 获取当天的自增订单
     * @return
     */
    public static String getOrderNumber(){
        String nowDateStr = DateUtil.date2Str(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        long number = 1;
        if(RedisUtil.existAny(nowDateStr)){
            number = RedisUtil.incr(nowDateStr,1);
        }else {
            RedisUtil.setExpire(nowDateStr,String.valueOf(number),24*60*60L);
        }
        return nowDateStr + StringUtils.leftPad(String.valueOf(number), 4, '0');
    }


}
