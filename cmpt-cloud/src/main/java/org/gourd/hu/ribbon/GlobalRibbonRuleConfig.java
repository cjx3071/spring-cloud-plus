package org.gourd.hu.ribbon;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gourd.hu
 */
@Configuration
public class GlobalRibbonRuleConfig {
    @Bean
    public IRule ribbonRule() {
        return new BestAvailableRule();
    }
}