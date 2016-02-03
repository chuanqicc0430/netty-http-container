package com.cu.http.container.core.utils;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feinno.util.Guid;

/**
 * 打印请求和应答日志
 * 
 * @author zongchuanqi
 *
 */
public class ServletLogUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ServletLogUtil.class);
	// 生成一个transactionId以供查询，线程内唯一
	private final static ThreadLocal<String> transactionId = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			// TODO Auto-generated method stub
			return Guid.randomGuid().toStr();
		}
	};

	public static void recordRequestLog(FullHttpRequest httpRequest) {
		if (LOGGER.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(httpRequest.toString()).append("\r\n");
			sb.append("transactionId----").append(transactionId.get()).append("\r\n");
			ByteBuf contentBuf = httpRequest.content();
			byte[] bytes = new byte[contentBuf.readableBytes()];
			int readerIndex = contentBuf.readerIndex();
			contentBuf.getBytes(readerIndex, bytes);
			sb.append(new String(bytes));
			LOGGER.debug(String.format("NettyServer------------receive request : %s", sb.toString()));
		}

	}

	public static void recordResponseLog(FullHttpResponse httpResponse) {
		if (LOGGER.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(httpResponse.toString()).append("\r\n");
			sb.append("transactionId----").append(transactionId.get()).append("\r\n");
			ByteBuf contentBuf = httpResponse.content();
			byte[] bytes = new byte[contentBuf.readableBytes()];
			int readerIndex = contentBuf.readerIndex();
			contentBuf.getBytes(readerIndex, bytes);
			sb.append(new String(bytes));
			LOGGER.debug(String.format("NettyServer------------return response : %s", sb.toString()));
		}
	}
}
