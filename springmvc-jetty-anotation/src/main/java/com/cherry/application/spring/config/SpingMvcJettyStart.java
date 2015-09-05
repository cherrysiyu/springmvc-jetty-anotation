package com.cherry.application.spring.config;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.cherry.application.webcommon.SetCharacterEncodingFilter;

public class SpingMvcJettyStart {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private int port ;
	private Class<?> springRootConfiguration = null;
	private Class<?> springMvcConfiguration = null;
	private Server server;
	private String contextPath;
	
	
	public SpingMvcJettyStart() {
		this(RootConfiguration.class, SpringMvcConfiguration.class);
	}
	public SpingMvcJettyStart(String contextPath) {
		this(contextPath,RootConfiguration.class, SpringMvcConfiguration.class);
	}
	public SpingMvcJettyStart(String contextPath,int port) {
		this(port,contextPath,RootConfiguration.class, SpringMvcConfiguration.class);
	}
	public SpingMvcJettyStart(Class<?> springRootConfiguration,Class<?> springMvcConfiguration) {
		this("/",springRootConfiguration, springMvcConfiguration);
	}
	public SpingMvcJettyStart(String contextPath,Class<?> springRootConfiguration,Class<?> springMvcConfiguration) {
		this(80, contextPath,springRootConfiguration, springMvcConfiguration);
	}


	public SpingMvcJettyStart(int port,String contextPath, Class<?> springRootConfiguration,Class<?> springMvcConfiguration) {
		super();
		this.port = port;
		this.springRootConfiguration = springRootConfiguration;
		this.springMvcConfiguration = springMvcConfiguration;
		this.contextPath = contextPath;
		init();
	}


	private void init() {
		server = new Server(port);
		// 设置在JVM退出时关闭Jetty的钩子。
        server.setStopAtShutdown(true);
        
		ServletContextHandler context = new ServletContextHandler();
		
		context.setContextPath(getContextPath());
		server.setHandler(context);
		//设置编码
		context.addFilter(SetCharacterEncodingFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		//1、根上下文
        AnnotationConfigWebApplicationContext  rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(this.springRootConfiguration);
        context.addEventListener(new ContextLoaderListener(rootContext));

        
        //2、springmvc上下文
        AnnotationConfigWebApplicationContext springMvcContext = new AnnotationConfigWebApplicationContext();
        springMvcContext.register(springMvcConfiguration);
        

        //3、DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springMvcContext);
        context.addServlet(new ServletHolder("dispatcherServlet",dispatcherServlet), "/*");
	}
	
	public void start() throws Exception{
		if (server!= null) {
			if (server.isStarting() || server.isStarted() || server.isRunning() ) {
				return;
			}
		}
		TimeUnit.SECONDS.sleep(1);
		server.start();
		logger.info("启动springmvc成功，端口:"+port+" contentPath:"+getContextPath());
	}
	public void stop() throws Exception{
		if (server != null) {
			if (server.isRunning()) {
				server.stop();
			}
		}
	}
	public void join() throws InterruptedException{
		if (server!=null) {
			server.join();
		}
	}


	public String getContextPath() {
		return (contextPath==null||"".equals(contextPath))?"/":contextPath;
	}


	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	

}
