package cn.wl.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ItemParamService;

@Controller
@RequestMapping(value="/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemcatid}")
	@ResponseBody
	public EgoResult name(@PathVariable("itemcatid")Long catId) {
		return itemParamService.getByItemCatId(catId);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult listAndPage(Integer page,Integer rows){
		return itemParamService.listAndPage(page, rows);
	}
	
	@RequestMapping("/save/{catId}")
	@ResponseBody
	public EgoResult saveItemParam(@PathVariable Long catId, String paramData) {
		return itemParamService.save(catId, paramData);
	}
	
	@RequestMapping(value="/delete")
	public EgoResult delete(Long[] ids) {
		return itemParamService.delete(ids);
	}
}
