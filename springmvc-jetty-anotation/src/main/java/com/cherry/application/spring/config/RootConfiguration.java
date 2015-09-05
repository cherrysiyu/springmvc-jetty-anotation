package com.cherry.application.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;


@Configuration
@ComponentScan(value={"com.cherry.application.spring.**.service", "com.cherry.application.spring.**.dao"},excludeFilters={ @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class RootConfiguration {

}
