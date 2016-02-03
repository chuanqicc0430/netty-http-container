package com.cu.http.container.helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cu.http.container.app.bean.HttpApplication;
import com.cu.http.container.app.context.ApplicationContext;

/**
 * 实例化application的factory
 * 
 * @author zongchuanqi
 *
 */
public class ApplicationFactory {

	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationFactory.class);

	/**
	 * 实例化每个Application，并加载每个application的load方法
	 * 
	 * @param classises
	 * @return applicationSet
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Set<HttpApplication<? extends ApplicationContext>> createApplications(Set<Class<? extends HttpApplication<? extends ApplicationContext>>> classises) throws Exception {
		Set<HttpApplication<? extends ApplicationContext>> applicationSet = new HashSet<HttpApplication<? extends ApplicationContext>>();
		for (Iterator<Class<? extends HttpApplication<? extends ApplicationContext>>> iterator = classises.iterator(); iterator.hasNext();) {
			Class<HttpApplication<? extends ApplicationContext>> applicationClass = (Class<HttpApplication<? extends ApplicationContext>>) iterator.next();
			try {
				HttpApplication<? extends ApplicationContext> newInstance = applicationClass.newInstance();
				newInstance.load();
				applicationSet.add(newInstance);
			} catch (Exception e) {
				LOGGER.error(String.format("load application %s error", applicationClass.getSimpleName()), e);
			}
		}
		return applicationSet;
	}

	/**
	 * 卸载Application，执行application的unload方法
	 * 
	 * @param classises
	 * @return applicationSet
	 * @throws Exception
	 */
	public static void releaseApplications(Set<HttpApplication<? extends ApplicationContext>> applicationSet) {
		for (Iterator<HttpApplication<? extends ApplicationContext>> iterator = applicationSet.iterator(); iterator.hasNext();) {
			HttpApplication<? extends ApplicationContext> application = (HttpApplication<? extends ApplicationContext>) iterator.next();
			try {
				application.unload();
			} catch (Exception e) {
				LOGGER.error(String.format("unload application %s error", application.getClass().getSimpleName()), e);
			}
		}
	}

}
