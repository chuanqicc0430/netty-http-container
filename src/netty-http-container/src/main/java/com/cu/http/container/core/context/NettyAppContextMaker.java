package com.cu.http.container.core.context;

import javax.servlet.http.HttpServletRequest;

import com.cu.http.container.app.context.ApplicationContext;

/**
 * 构造自定义上下文context
 * 
 * @author zongchuanqi
 * 
 */
public interface NettyAppContextMaker<C extends ApplicationContext> {

	public C buildContext(HttpServletRequest httpServletRequest);
}
