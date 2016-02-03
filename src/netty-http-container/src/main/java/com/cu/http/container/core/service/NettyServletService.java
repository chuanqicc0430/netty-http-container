package com.cu.http.container.core.service;

import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;

import com.cu.http.container.app.bean.HttpApplication;
import com.cu.http.container.app.context.ApplicationContext;
import com.cu.http.container.core.configuration.NettyServletConfig;
import com.cu.http.container.core.context.NettyAppContextMaker;
import com.cu.http.container.core.interceptor.NettyServletInterceptor;

/**
 * Servlet netty的实现
 * 
 * @author zongchuanqi
 * 
 */
public interface NettyServletService {
	// 设置上下文生成工厂
	public void registerContextFactory(NettyAppContextMaker<? extends ApplicationContext> nettyAppContextMaker);

	// 设置过滤器
	public void setFilterChain(FilterChain filterChain);

	// 设置拦截器，日志，session
	public void setInterceptor(List<NettyServletInterceptor> interceptors);

	// 增加和删除Application
	public void addApplication(HttpApplication<? extends ApplicationContext> application);

	public void addApplications(Set<HttpApplication<? extends ApplicationContext>> applicationSet);

	public void removeApplication(HttpApplication<? extends ApplicationContext> application);

	public void removeApplications(Set<HttpApplication<? extends ApplicationContext>> applicationSet);

	// 启动和停止
	public void start(NettyServletConfig nettyServletConfiguration);

	public void stop();
}
