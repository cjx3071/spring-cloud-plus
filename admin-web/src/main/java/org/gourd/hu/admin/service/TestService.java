package org.gourd.hu.admin.service;

import org.gourd.hu.base.response.BaseResponse;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

/**
 *@Description 测试service
 *@Author gourd.hu
 *@Date 2019/12/11 18:13
 *@Version 1.0
 */
public interface TestService {

    /**
     * 测试缓存
     * @return
     */
    BaseResponse testCache(String param1, String param2);

    /**
     * 测试重试
     *
     * @Retryable的参数说明： •value：抛出指定异常才会重试
     * •include：和value一样，默认为空，当exclude也为空时，默认所以异常
     * •exclude：指定不处理的异常
     * •maxAttempts：最大重试次数，默认3次
     * •backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；
     * multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试
     */
    @Retryable(value = RetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1))
    BaseResponse testRetry();

}
