package cn.wl.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.wl.base.pojo.Item;
import cn.wl.base.utils.HttpClientUtils;
import cn.wl.base.utils.JsonUtils;
import cn.wl.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Override
	public Item getById(Long itemId) {
		Item item=null;
		String jsonData = HttpClientUtils.doGet(REST_BASE_URL+"/item/"+itemId);
		if (jsonData!=null && !"".equals(jsonData)) {
			item = JsonUtils.jsonToPojo(jsonData, Item.class);
		}
		return item;
	}
}
