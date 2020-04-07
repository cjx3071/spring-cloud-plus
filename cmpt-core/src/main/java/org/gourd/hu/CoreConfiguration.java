package org.gourd.hu;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author gourd
 * @date 2018-11-20
 */
@Configuration
@EnableRetry
@EnableAsync
public class CoreConfiguration {

}
