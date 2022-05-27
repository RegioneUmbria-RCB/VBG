package com.paevolution.appiogateway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import io.netty.util.internal.InternalThreadLocalMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppiogatewayServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

	// TODO Auto-generated method stub
	ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

	InternalThreadLocalMap.remove();
    }
}
