package com.cu.http.container.core;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;

import com.cu.http.container.app.context.ApplicationContext;
import com.cu.http.container.core.context.NettyAppContextMaker;
import com.cu.http.container.core.interceptor.NettyServletInterceptor;

/**
 * 
 * @author zongchuanqi
 *
 */
public class NettyServletContextHandler {

	private Map<UriHandlerMapper, NettyServletHolder> nettyServletHolders;
	private FilterChain filterChain;
	private NettyAppContextMaker<? extends ApplicationContext> nettyAppContextMaker;
	private List<NettyServletInterceptor> interceptors;

	public NettyServletContextHandler() {
		nettyServletHolders = new ConcurrentHashMap<UriHandlerMapper, NettyServletHolder>();
	}

	public void addHandler(UriHandlerMapper uriHandlerMapper, NettyServletHolder nettyServletHolder) {
		nettyServletHolders.put(uriHandlerMapper, nettyServletHolder);
	}

	public NettyServletHolder getHandler(String uri, HttpVersion httpVersion, HttpMethod httpMethod, HttpRequest httpRequest, FullHttpResponse httpResponse) throws Exception {
		UriHandlerMapper uriHandlerMapper = new UriHandlerMapper(uri, httpVersion, httpMethod);
		NettyServletHolder nettyServletHolder = nettyServletHolders.get(uriHandlerMapper);
		return nettyServletHolder;
	}

	/**
	 * @return
	 */
	public FilterChain getFilter() {
		return filterChain;
	}

	public void setFilter(FilterChain filterChain) {
		this.filterChain = filterChain;
	}

	/**
	 * @return
	 */
	public NettyAppContextMaker<? extends ApplicationContext> getNettyAppContextMaker() {
		return nettyAppContextMaker;
	}

	/**
	 * @param nettyAppContextMaker
	 *            the nettyAppContextMaker to set
	 */
	public void setNettyAppContextMaker(NettyAppContextMaker<? extends ApplicationContext> nettyAppContextMaker) {
		this.nettyAppContextMaker = nettyAppContextMaker;
	}

	/**
	 * @param interceptors
	 */
	public void setInterceptors(List<NettyServletInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	/**
	 * @return the interceptors
	 */
	public List<NettyServletInterceptor> getInterceptors() {
		return interceptors;
	}

}
