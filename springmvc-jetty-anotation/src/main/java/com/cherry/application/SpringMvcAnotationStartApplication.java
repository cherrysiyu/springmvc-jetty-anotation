package com.cherry.application;

import java.io.File;

import com.cherry.application.logback.LogbackConfigurer;
import com.cherry.application.spring.config.SpingMvcJettyStart;

public class SpringMvcAnotationStartApplication {
	public static String fileConfigPath = "config/";
	public static void main(String[] args) {
		try{
			if(args != null && args.length>0){
				fileConfigPath = args[0];
			}
			String filePah = fileConfigPath+"logback.xml";
			if(new File(filePah).exists()){
				LogbackConfigurer.initLogging("file:"+filePah);
				new SpingMvcJettyStart("/", 8080).start();
			}else{
				System.err.println("请指定logback.xml文件路径");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

}
