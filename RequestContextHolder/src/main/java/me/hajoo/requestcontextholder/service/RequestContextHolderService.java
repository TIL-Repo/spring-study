package me.hajoo.requestcontextholder.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import me.hajoo.requestcontextholder.util.RequestUtils;

@Service
public class RequestContextHolderService {

	public Map<String, String> getCookies() {
		final HttpServletRequest request = RequestUtils.getRequest();
		final HashMap<String, String> ret = new HashMap<>();
		for (Cookie cookie : request.getCookies()) {
			ret.put(cookie.getName(), cookie.getValue());
		}
		return ret;
	}

	public String getBody() {
		final HttpServletRequest request = RequestUtils.getRequest();
		try {
			return StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
