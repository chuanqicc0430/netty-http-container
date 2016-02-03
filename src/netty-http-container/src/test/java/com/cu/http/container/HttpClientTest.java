package com.cu.http.container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientTest.class);
//	private final static ThreadLocal<String> transactionId = new ThreadLocal<String>() {
//		@Override
//		protected String initialValue() {
//			// TODO Auto-generated method stub
//			return Guid.randomGuid().toStr();
//		}
//	};
	private HttpURLConnection connection;

	public void openConnection(String httpURL) {
		try {
			URL url = new URL(httpURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("User-Agent", "Allure/1.3.1 (iPhone; iOS 9.2; Scale/3.00)");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Accept-Language", "en-US;q=1");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setConnectTimeout(500000);
		} catch (MalformedURLException e1) {
			LOGGER.error("Fatal Error", e1);
		} catch (IOException e2) {
			LOGGER.error("Fatal Error", e2);
		}
	}

	public void request(String data) throws IOException {
		OutputStream out = null;
		try {
//			LOGGER.info(String.format("Request  TranscationId:%s", transactionId.get()));
			out = connection.getOutputStream();
			byte[] requsetContent = data.getBytes();
			out.write(requsetContent);
			out.flush();
		} finally {
			out.close();
		}
	}

	public void response() throws IOException {
		int code = connection.getResponseCode();
		if (code == HttpURLConnection.HTTP_OK) {
//			LOGGER.info(String.format("Response TranscationId:%s,content:%s", transactionId.get(), new String(getResponseBody())));
//			LOGGER.info(String.format("Response TranscationId:%s", transactionId.get()));
		} else {
			LOGGER.error(String.format("Error responseCode:%s", connection.getResponseCode()));
		}
	}

	public byte[] getResponseBody() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int contentLength = connection.getInputStream().available();
		try {
			if (contentLength > 0) {
				int sum = 0;
				byte[] buffer = new byte[contentLength];
				while (sum < contentLength) {
					int count = connection.getInputStream().read(buffer);
					if (count > 0) {
						outputStream.write(buffer, 0, count);
						sum += count;
					} else if (count < 0) {
						throw new IOException(String.format("getResponseBody read return %s. contentLength:%s sum:%s ", count, contentLength, sum));
					}
				}
			}
			outputStream.flush();
			return outputStream.toByteArray();
		} finally {
			outputStream.close();
		}
	}

	public void closeConnection() {
		connection.disconnect();
	}

	public static void main(String[] args) {
		HttpClientTest test = new HttpClientTest();
		test.openConnection("http://cu.xiaoxiang.net:8012/nav");
		try {
			test.request("clientType=1&clientVersion=15&language=en&osVersion=9.2");
			test.response();
			test.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
