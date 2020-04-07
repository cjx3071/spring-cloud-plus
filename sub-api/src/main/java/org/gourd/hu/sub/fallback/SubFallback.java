package org.gourd.hu.sub.fallback;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.common.response.BaseResponse;
import org.gourd.hu.sub.api.SubApi;

/**
 * 熔断回调类
 * @author gourd
 */
@Slf4j
public class SubFallback implements SubApi {

    private Throwable throwable;

    SubFallback() {
    }

    SubFallback(Throwable throwable){
        this.throwable = throwable;
    }

    @Override
    public BaseResponse helloTest(Long id) {
        log.info("helloTest调用失败:",throwable);
        return BaseResponse.fail("helloTest调用失败:"+throwable.getCause());
    }

    @Override
    public BaseResponse helloTestParam(Long id) {
        log.info("helloTest调用失败:",throwable);
        return BaseResponse.fail("helloTest调用失败:"+throwable.getCause());
    }

    @Override
    public BaseResponse helloTestP(String name) {
        log.info("helloTest-post方法调用失败:",throwable);
        return BaseResponse.fail("helloTest-post方法调用失败:"+throwable.getCause());
    }

    @Override
    public BaseResponse seataTxTest() {
        log.info("seataTxTest调用失败:",throwable);
        return BaseResponse.fail("seataTxTest调用失败:"+throwable.getCause());
    }

}
