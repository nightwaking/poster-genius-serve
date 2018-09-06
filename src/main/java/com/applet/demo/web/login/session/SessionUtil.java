package com.applet.demo.web.login.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SessionUtil {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, Object> valueOps;

	public void save(final Session session) {
		valueOps.set(session.getToken(), session, 1, TimeUnit.HOURS);
	}

	public boolean valid(final String token) {
		return redisTemplate.getExpire(token) > 0;
	}

	public void update(final String token) {
		if (valid(token))
			redisTemplate.expire(token, 72, TimeUnit.HOURS);
	}

	public void remove(final String token) {
		if (redisTemplate.hasKey(token))
			redisTemplate.delete(token);
	}

	public Session get(final String token) {
		return (Session) valueOps.get(token);
	}
}


