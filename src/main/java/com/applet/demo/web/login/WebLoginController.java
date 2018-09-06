package com.applet.demo.web.login;

import com.alex.commons.biz.http.Result;
import com.alex.commons.biz.http.StatusCode;
import com.applet.demo.web.login.session.Session;
import com.applet.demo.web.login.session.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Api(basePath = "login", value = "login", description = "登录相关操作",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WebLoginController {
	@Autowired
	private SessionUtil sessionUtil;

	@ApiOperation(value="小程序登录获取token", notes="小程序登录获取token。")
	@RequestMapping(value = "/{code}",  method = RequestMethod.POST)
	public String login(@ApiParam(value = "微信登录code") @RequestBody String code) throws Exception{
		if (code == null)
			throw new Exception("微信登录接口未返回code");
		Session session = new Session();
		session.setToken(UUID.randomUUID().toString());

		sessionUtil.save(session);
		return session.getToken();
	}

	@ApiOperation(value = "小程序验证token", notes = "小程序验证token。")
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public Boolean verifyLogin(@ApiParam(value = "微信登录验证") @RequestBody String token){
		if (!sessionUtil.valid(token))
			return false;
		return true;
	}
}
