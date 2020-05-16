package cn.wl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.pojo.Item;
import cn.wl.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/{itemId}",method=RequestMethod.GET)
	@ResponseBody
	public Item getById(@PathVariable("itemId")Long itemId){
		Item item = itemService.getById(itemId);
		return item;
	}
}
