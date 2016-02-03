package com.cu.http.container;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentTest.class);
	private static int thread_num = 1;

	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(128);
		// 模拟thread_num个线程可以同时访问
		final Semaphore semp = new Semaphore(thread_num);
		while (true) {
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						HttpClientTest test = new HttpClientTest();
						long currentTime = System.currentTimeMillis();
						test.openConnection("http://cu.xiaoxiang.net:8012/nav");
						test.request("clientType=1&clientVersion=15&language=en&osVersion=9.2");
						test.response();
						test.closeConnection();
						LOGGER.info(String.format("Thread %s finish,cost time:%sms", Thread.currentThread().getName(), System.currentTimeMillis() - currentTime));
						semp.release();
					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("Fatal error", e);
					}
				}
			};
			exec.execute(run);
		}
	}
}
