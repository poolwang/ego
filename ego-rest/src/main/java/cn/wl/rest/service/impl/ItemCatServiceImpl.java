package cn.wl.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ItemCatMapper;
import cn.wl.base.pojo.ItemCat;
import cn.wl.base.vo.Menu;
import cn.wl.base.vo.MenuNode;
import cn.wl.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService{

	@Override
	public Menu initMenu() {
		Menu menu=new Menu();
		 List<?> data=this.getNodesByParantId(0L);
		menu.setData(data);
		return menu;
	}
	
	private List<Object> getNodesByParantId(Long parentId) {
		EntityWrapper<ItemCat> wrapper=new EntityWrapper<>();
		wrapper.eq("parent_id", parentId);
		List<ItemCat> itemCats = this.selectList(wrapper);
		
		List<Object> nodes = new ArrayList<>();
		for (ItemCat itemCat : itemCats) {
			if (itemCat.getIsParent()==1) {
				MenuNode node = new MenuNode();
				node.setN("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				node.setU("/products/" + itemCat.getId() + ".html");
				node.setI(getNodesByParantId(itemCat.getId()));
				nodes.add(node);
			}else {
				nodes.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
			}
			
		}
		return nodes;
	}
}
