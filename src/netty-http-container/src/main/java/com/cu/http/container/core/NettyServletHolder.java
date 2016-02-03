package com.cu.http.container.core;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cu.http.container.app.bean.HttpApplication;
import com.cu.http.container.app.context.ApplicationContext;
import com.cu.http.container.app.context.ApplicationTx;

/**
 * 相当于application的代理,解耦netty server和application
 * 
 * @author zongchuanqi
 *
 */
public class NettyServletHolder {
	private final static Logger LOGGER = LoggerFactory.getLogger(NettyServletHolder.class);

	private HttpApplication<? extends ApplicationContext> application;
	private UriHandlerMapper uriHandlerMapper;

	public HttpApplication<? extends ApplicationContext> getApplication() {
		return application;
	}

	public void setApplication(HttpApplication<? extends ApplicationContext> application) {
		this.application = application;
	}

	public UriHandlerMapper getUriHandlerMapper() {
		return uriHandlerMapper;
	}

	public void setUriHandlerMapper(UriHandlerMapper uriHandlerMapper) {
		this.uriHandlerMapper = uriHandlerMapper;
	}

	@SuppressWarnings("unchecked")
	public void passAction(@SuppressWarnings("rawtypes") ApplicationTx tx) throws ServletException {
		try {
			application.process(tx);
		} catch (Exception e) {
			LOGGER.error("do application process error", e);
			throw new ServletException("invoke application process with inner error", e);
		}
	}

}
