package com.cu.http.container.app.bean;

import com.cu.http.container.app.context.ApplicationContext;
import com.cu.http.container.app.context.ApplicationTx;

/**
 * 借鉴FAE的AppBean，自定义http application,注意每个application都必须添加HttpPrefix注解
 * 
 * @author zongchuanqi
 */
public abstract class HttpApplication<C extends ApplicationContext> {
	/**
	 * {在这里补充功能说明}
	 * 
	 * @param genericParamOrder
	 */
	protected HttpApplication() {

	}

	public abstract void load() throws Exception;

	public abstract void unload() throws Exception;

	public abstract void process(ApplicationTx<C> tx) throws Exception;

}