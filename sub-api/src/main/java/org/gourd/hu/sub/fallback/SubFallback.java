package org.gourd.hu.sub.fallback;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.sub.api.SubApi;
import org.gourd.hu.sub.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 熔断回调类
 * @author gourd
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class SubFallback implements SubApi {

    private Throwable throwable;

    @Override
    public BaseResponse helloTest(Long id) {
        log.info("helloTest调用失败:",throwable);
        return BaseResponse.fail("helloTest调用失败:"+throwable.getMessage());
    }

    @Override
    public BaseResponse helloTestParam(Long id) {
        log.info("helloTest调用失败:",throwable);
        return BaseResponse.fail("helloTest调用失败:"+throwable.getMessage());
    }

    @Override
    public BaseResponse helloTestP(String name) {
        log.info("helloTest-post方法调用失败:",throwable);
        return BaseResponse.fail("helloTest-post方法调用失败:"+throwable.getMessage());
    }

    @Override
    public BaseResponse seataAtTest() {
        log.info("seataAtTest调用失败:",throwable);
        return BaseResponse.fail("seataAtTest调用失败:"+throwable.getMessage());
    }

    @Override
    public BaseResponse seataTccTest(@RequestBody Map params) {
        log.info("seataTccTest调用失败:",throwable);
        throw new RuntimeException("seataTccTest调用失败");
    }

}
