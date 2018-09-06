package com.applet.demo.web.login.session;

import com.alex.commons.biz.exception.UnLoginException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenCheckInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SessionUtil sessionUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		System.out.println("请求url=" + uri);
		if ("OPTIONS".equals(request.getMethod()))  //跨域时，会先发起OPTIONS请求，此处忽略
			return true;

		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			throw new UnLoginException("未登录或session已过期。");
		}

		if (!sessionUtil.valid(token))
			throw new UnLoginException("未登录或session已过期。");

		//更新session有效时间
		sessionUtil.update(token);

		return true;
	}
}
