package com.paevolution.appiogateway;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@EnableAsync
@Configuration
public class AsyncEventsConfiguration {

    @Bean
    public InitializingBean initializingBean() {

	return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {

	SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
	executor.setThreadNamePrefix("simple-async");
	return executor;
    }

    @Bean
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) {

	return new DelegatingSecurityContextAsyncTaskExecutor(simpleAsyncTaskExecutor);
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {

	SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
	eventMulticaster.setTaskExecutor(simpleAsyncTaskExecutor());
	return eventMulticaster;
    }
    /*
     * @Bean public MethodInvokingFactoryBean methodInvokingFactoryBean() {
     * 
     * MethodInvokingFactoryBean methodInvokingFactoryBean = new
     * MethodInvokingFactoryBean();
     * methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
     * methodInvokingFactoryBean.setTargetMethod("setStrategyName");
     * methodInvokingFactoryBean.setArguments(SecurityContextHolder.
     * MODE_INHERITABLETHREADLOCAL); return methodInvokingFactoryBean; }
     */
}
