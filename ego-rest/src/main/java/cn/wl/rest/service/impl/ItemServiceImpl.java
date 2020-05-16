package cn.wl.rest.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ItemMapper;
import cn.wl.base.pojo.Item;
import cn.wl.rest.service.ItemService;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper,Item> implements ItemService {

	@Override
	public Item getById(Long itemId) {
		Item item = this.selectById(itemId);
		return item;
	}
}
