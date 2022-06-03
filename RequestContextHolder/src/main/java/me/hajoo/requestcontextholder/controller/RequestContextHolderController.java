package me.hajoo.requestcontextholder.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.hajoo.requestcontextholder.service.RequestContextHolderService;

@RestController
@RequiredArgsConstructor
public class RequestContextHolderController {

	private final RequestContextHolderService requestContextHolderService;

	@GetMapping
	public Map<String, String> getCookie() {
		return requestContextHolderService.getCookies();
	}

	@PostMapping
	public String getBody() {
		return requestContextHolderService.getBody();
	}
}
