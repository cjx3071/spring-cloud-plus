package org.gourd.hu.sub.fallback;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断回调工厂
 * @author gourd
 */
@Component
public class SubFallbackFactory implements FallbackFactory<SubFallback> {
    @Override
    public SubFallback create(Throwable throwable) {
        return new SubFallback(throwable);
    }
}