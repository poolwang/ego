package cn.wl.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.pojo.Item;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/{itemID}")
	@ResponseBody
	public Item name(@PathVariable("itemID")Long id) {
		Item item = itemService.selectById(id);
		return item;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult listAndPage(int page, int rows) {
	 //查询结果返回到前端
		return itemService.listAndPage(page, rows);
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public EgoResult save(Item item, String desc, String itemParams) {
		EgoResult result=new EgoResult();
		try {
			System.out.println(item.getCid()+"/"+item.getPrice());
			System.out.println(desc);
			System.out.println(itemParams);
			return itemService.save(item, desc, itemParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setMsg("插入失败");
		result.setStatus(400);
		return result;
	}
	
}
