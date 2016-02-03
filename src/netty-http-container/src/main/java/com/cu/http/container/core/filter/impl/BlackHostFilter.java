package com.cu.http.container.core.filter.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cu.http.container.core.configuration.NettyFilterConfig;
import com.cu.http.container.core.filter.BaseNettyFilter;
import com.cu.http.container.core.filter.FilterThreadLocal;
import com.cu.util.CUException;

/**
 * host黑名单或者白名单之类的
 * 
 * @author zongchuanqi
 *
 */
public class BlackHostFilter extends FilterThreadLocal implements BaseNettyFilter {

	private final static Logger LOGGER = LoggerFactory.getLogger(BlackHostFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String remoteHost = servletRequest.getRemoteAddr();
		try {
			if (NettyFilterConfig.getInstance().isBlackHost(remoteHost)) {
				setIsPass(false);
				return;
			}
		} catch (CUException e) {
			LOGGER.error("Get is black host error", e);
		}
		setIsPass(true);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPass() {
		return getPass();
	}

}
