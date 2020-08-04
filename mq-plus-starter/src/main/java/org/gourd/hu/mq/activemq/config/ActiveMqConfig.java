package org.gourd.hu.mq.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.gourd.hu.mq.activemq.listeners.ActiveConsumerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * active消息队列配置
 * @author gour.hu
 */
@Configuration
@EnableJms
@ConditionalOnProperty(prefix = "spring.activemq",value = "broker-url")
@Import({ActiveConsumerListener.class})
public class ActiveMqConfig {

    /**
     * 定义存放消息的队列
     * @return
     */
    @Bean
    public Queue queue(@Value("${spring.activemq.queue-name:gourd-queue}")String queueName) {
        return new ActiveMQQueue(queueName);
    }

    /**
     * 发布订阅模式
     * @return
     */
    @Bean
    public Topic topic(@Value("${spring.activemq.topic-name:gourd-topic}")String topicName) {
        return new ActiveMQTopic(topicName);
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy  redeliveryPolicy= new RedeliveryPolicy();
        // 启用指数倍数递增的方式增加延迟时间。
        redeliveryPolicy.setUseExponentialBackOff(true);
        // 重连时间间隔递增倍数，只有值大于1和启用useExponentialBackOff参数时才生效。默认5
        redeliveryPolicy.setBackOffMultiplier(2);
        // 最大重传次数，达到最大重连次数后抛出异常。为-1时不限制次数，为0时表示不进行重传。默认6
        redeliveryPolicy.setMaximumRedeliveries(6);
        // 重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(5000L);
        // 启用防止冲突功能，因为消息接收时是可以使用多线程并发处理的，应该是为了重发的安全性，避开所有并发线程都在同一个时间点进行消息接收处理。
        redeliveryPolicy.setUseCollisionAvoidance(true);
        // 设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);

        return redeliveryPolicy;
    }


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (
            @Value("${spring.activemq.broker-url}")String url,
            @Value("${spring.activemq.user}")String user,
            @Value("${spring.activemq.password}")String password,
            RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(user,password,url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate getJmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory,
        @Value("${spring.jms.pub-sub-domain}")Boolean subAdmin){
        //使用CachingConnectionFactory可以提高部分性能。
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(100);
        cachingConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory);
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        //设置deliveryMode（持久化）
        jmsTemplate.setExplicitQosEnabled(true);
        // 设置消息是否持久化
        jmsTemplate.setDeliveryPersistent(true);
        // 设置消息是否以事务
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setPubSubDomain(subAdmin);
        return jmsTemplate;
    }

    /**
     * topic模式的ListenerContainer
     *
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean("jmsListenerContainerTopic")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(
            ActiveMQConnectionFactory activeMQConnectionFactory,
            @Value("${spring.application.name}")String applicationName) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setPubSubDomain(true);
        factory.setSessionTransacted(true);
        factory.setAutoStartup(true);
        //开启持久化订阅
        factory.setSubscriptionDurable(true);
        factory.setClientId(applicationName);
        FixedBackOff backOff=new FixedBackOff();
        backOff.setInterval(200);
        backOff.setMaxAttempts(6);
        factory.setBackOff(backOff);
        return factory;
    }

    /**
     * queue模式的ListenerContainer
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean("jmsListenerContainerQueue")
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(
            ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }


}