package cn.wl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.Menu;
import cn.wl.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="/item/all",produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getMenu(String callback){
		Menu menu = itemCatService.initMenu();
		String jsonMenu = JsonUtils.objectToJson(menu);
		String jsMenu = callback+"("+jsonMenu+")";
		return jsMenu;
	}
}
