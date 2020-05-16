package cn.wl.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.wl.base.mapper.ItemCatMapper;
import cn.wl.base.pojo.ItemCat;
import cn.wl.base.vo.EUTreeNode;
import cn.wl.manager.service.ItemCatService;

@Service
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService {

	@Override
	public List<EUTreeNode> getByParentId(Long parentId) {
		
		List<EUTreeNode> nodes=new ArrayList<>();
		//根据parentId查询出下属的所有类目
		EntityWrapper<ItemCat> wrapper=new EntityWrapper<>();
		wrapper.eq("parent_id", parentId);
		List<ItemCat> itemCats = this.selectList(wrapper);
		//添加到easyUI
		EUTreeNode node=null;
		for (ItemCat itemCat : itemCats) {
			node=new EUTreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			if (itemCat.getIsParent()==1) {
				node.setState("closed");
			}else {
				node.setState("open");
			}
			nodes.add(node);
		}
		return nodes;
	}
}
