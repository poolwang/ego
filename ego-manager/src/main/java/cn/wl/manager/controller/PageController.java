package cn.wl.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping("/{page}")
	public String showPage(@PathVariable("page")String page){
		return page;
	}
	@RequestMapping("/")
	public String page() {
		return "index";
	}
}
