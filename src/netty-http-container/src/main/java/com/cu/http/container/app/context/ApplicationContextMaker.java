package com.cu.http.container.app.context;

import javax.servlet.http.HttpServletRequest;

import com.cu.http.container.core.context.NettyAppContextMaker;

/**
 * Application暂定是无状态的
 * 
 * @author zongchuanqi
 *
 */
public class ApplicationContextMaker implements NettyAppContextMaker<NullContext> {

	@Override
	public NullContext buildContext(HttpServletRequest httpServletRequest) {
		return NullContext.INSTANCE;
	}

}
