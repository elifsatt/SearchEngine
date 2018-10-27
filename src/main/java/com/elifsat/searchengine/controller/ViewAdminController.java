package com.elifsat.searchengine.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class ViewAdminController {
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String indexPage() {
		return "/";
	}
}
