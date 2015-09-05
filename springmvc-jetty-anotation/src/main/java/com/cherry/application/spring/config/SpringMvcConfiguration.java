package com.cherry.application.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(
        value = "com.cherry.application.spring.**.controller",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        })
public class SpringMvcConfiguration {
	
}
