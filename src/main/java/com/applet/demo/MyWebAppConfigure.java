package com.applet.demo;

import com.applet.demo.web.login.session.TokenCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigure implements WebMvcConfigurer {
	// 此处必须通过Bean注入TokenCheckInterceptor，否则会导致拦截器中的sessionUtil为null
	@Bean
	public HandlerInterceptor TokenCheckInterceptor() {
		return new TokenCheckInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//注意顺序
		registry.addInterceptor(TokenCheckInterceptor()).excludePathPatterns("/login");
	}

	// 解决浏览器跨域问题
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
	}
}
