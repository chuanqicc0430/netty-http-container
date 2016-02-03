package com.cu.http.container.app.context;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cu.util.CUException;
import com.feinno.appengine.http.Base64;

/**
 * ApplicationTx
 * 
 * @author zongchuanqi
 */
public class ApplicationTx<C extends ApplicationContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTx.class);
	private C context = null;
	private byte[] contextData;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ApplicationTx(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		contextData = base64decode(request.getHeader("ContextData"));
	}

	public C context() {
		return context;
	}

	public void setContext(C context) {
		this.context = context;
	}

	/**
	 * {在这里补充功能说明}
	 * 
	 * @param header
	 * @return
	 */
	private byte[] base64decode(String header) {
		return Base64.decode(header); // 暂不用decodeFast
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	/*
	 * @see com.feinno.appengine.AppTxWithContext#extractContextData()
	 */
	/**
	 * {在这里补充功能说明}
	 * 
	 * @return
	 */
	protected byte[] extractContextData() {
		return contextData;
	}

	public void processSucceed(String responseStr,String contentType) {
		response.setStatus(200);
		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Length", String.valueOf(responseStr.length()));
		response.setHeader("Content-Language", "en");
		try {
			response.getWriter().write(responseStr);
		} catch (IOException e) {
			LOGGER.error("Send success response error!", e);
		}
	}

	public void processFailed(CUException error) {
		response.setStatus(error.getReturnCode());
		response.setHeader("Content-Type", "text/plain");
		response.setHeader("Content-Length", String.valueOf(error.getMessage().length()));
		response.setHeader("Content-Language", "en");
		try {
			response.getWriter().write(error.getMessage());
		} catch (IOException e) {
			LOGGER.error("Send failed response error!", e);
		}
	}
}
