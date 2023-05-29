package com.github.demo.configure;

import com.github.demo.flows.DynamicIntegrationFlowRegistration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据流Bean配置
 */
@Configuration
@EnableIntegration
public class IntegrationConfig implements InitializingBean {

    @Autowired
    private DynamicIntegrationFlowRegistration dynamicIntegrationFlowRegistration;

    /**
     * 消息来源
     */
    @Bean
    public AtomicInteger integerSource() {
        return new AtomicInteger();
    }

    /**
     * 数据流定义
     */
//    @Bean
//    public IntegrationFlow myFlow(AtomicInteger integerSource) {
//        // 定义数据流起点、终点
//        return getFlow(integerSource);
//    }

    private IntegrationFlow createFlow() {
        AtomicInteger atomicInteger = new AtomicInteger();
        return IntegrationFlow.fromSupplier(atomicInteger::getAndIncrement,
                        c -> c.poller(Pollers.fixedRate(100)))
                .channel("inputChannel")
                .filter((Integer p) -> p > 0)
                .transform(Object::toString)
                .handle((GenericHandler<String>) (payload, headers) -> {
                    System.out.println(payload);
                    return null;
                })
                .get();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 动态注册Flow
        dynamicIntegrationFlowRegistration.registerIntegrationFlow(createFlow(), "test").start();
    }

}
