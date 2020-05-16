package cn.wl.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.vo.EUTreeNode;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ContentCategoryService;

@Controller
@RequestMapping(value = "/content/category/")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<EUTreeNode> selectByParentId(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		return service.selectByParentId(parentId);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public EgoResult create(Long parentId, String name) {
		return service.save(parentId, name);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public EgoResult updateNode(String name,Long id) {
		return service.updateNode(name,id);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public EgoResult deleteNode(Long id,Long parentId){
		return service.deleteNode(id, parentId);
	}
}
