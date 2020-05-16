package cn.wl.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wl.portal.service.ContentService;

@Controller
public class PageController {
	
	@Autowired
	private ContentService service;

	@RequestMapping("/{page}")
	public String showPage(@PathVariable("page")String page){
		return page;
	}
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		try {
			String adItemList = service.getAdItemList();
			model.addAttribute("ads", adItemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
}
