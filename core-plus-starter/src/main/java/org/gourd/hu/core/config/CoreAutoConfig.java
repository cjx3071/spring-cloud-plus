package org.gourd.hu.core.config;

import org.gourd.hu.core.async.impl.AsyncServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@EnableRetry
@Import({ConverterConfig.class,AsyncPoolConfig.class, AsyncServiceImpl.class})
public class CoreAutoConfig {

}
