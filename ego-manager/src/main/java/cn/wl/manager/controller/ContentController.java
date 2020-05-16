package cn.wl.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.wl.base.pojo.Content;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService service;

	@RequestMapping(value = "/query/list")
	public EUDataGridResult selectByCatIdAndPage(Long categoryId, Integer page, Integer rows) {
		return service.listByCatIdAndPage(categoryId, page, rows);
	}
	
	@RequestMapping("/save")
	public EgoResult addContent(Content content) throws Exception {
		return service.addContent(content);
	}
	
	@RequestMapping("/delete")
	public EgoResult delete(Integer[] ids){
		return service.delete(ids);
	}
	
	@RequestMapping("/edit")
	public EgoResult update(Content content){
		return service.update(content);
	}
}
