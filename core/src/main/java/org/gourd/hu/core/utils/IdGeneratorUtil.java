package org.gourd.hu.core.utils;


import java.util.HashSet;
import java.util.Set;

/**
 * @author gourd
 * @description
 * @date 2018/10/26 10:20
 **/
public class IdGeneratorUtil {

    private static final IdWorker idWorker = new IdWorker(0,1);

    /**
     * 获取一个id
     * @return
     */
    public static Long getSeqId(){
        return idWorker.nextId();
    }

    /**
     * 获取num个id
     * @return
     */
    public static Set<Long> getSeqIds(int num){
        Set<Long> ids = new HashSet(4);
        if(num>0){
            for(int i=0; i<num;i++){
                ids.add(idWorker.nextId());
            }
        }else {
            throw new RuntimeException("获取id的num需大于0");
        }
        return ids;
    }
}
