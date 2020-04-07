package org.gourd.hu.core.async;


import org.gourd.hu.base.common.response.BaseResponse;

import java.util.concurrent.Future;

/**
 * @author gourd
 */
public interface AsyncService {

    /**
     * 任务一（有返回结果）
     *
     * @return
     */
    Future<BaseResponse> doTaskOne();

    /**
     * 任务二（有返回结果）
     *
     * @return
     */
    Future<BaseResponse> doTaskTwo();

    /**
     * 任务三（无返回结果）
     *
     * @return
     */
    void doTaskThree() ;
}
