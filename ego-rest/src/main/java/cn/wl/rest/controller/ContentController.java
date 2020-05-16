package cn.wl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wl.base.vo.EgoResult;
import cn.wl.rest.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService service;
	
	@RequestMapping("/category/{cid}")
	public EgoResult getContentList(@PathVariable Long cid) {
		return service.getContentByCatId(cid);
	}
}
