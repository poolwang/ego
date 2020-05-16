package cn.wl.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wl.base.pojo.Item;
import cn.wl.portal.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/{itemId}")
	public String getItemById(@PathVariable("itemId")Long itemId,ModelMap map){
		try {
			Item item = itemService.getById(itemId);
			if (item!=null) {
				map.addAttribute("item", item);
				return "item";
			}else {
				map.addAttribute("message", "外星人把服务器抢走了，地球卫士正在修复!");
				return "error/exception";
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.addAttribute("message", "外星人把服务器抢走了，地球卫士正在修复!");
			return "error/exception";
		}
	}
	
}
