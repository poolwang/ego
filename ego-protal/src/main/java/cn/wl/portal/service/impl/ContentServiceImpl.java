package cn.wl.portal.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.wl.base.pojo.Content;
import cn.wl.base.utils.HttpClientUtils;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.ADItem;
import cn.wl.base.vo.EgoResult;
import cn.wl.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAdItemList() throws Exception {
		String result = HttpClientUtils.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		
		//转成json
        EgoResult egoResult = EgoResult.formatToList(result, Content.class);
		
		//封装数据
		List<ADItem> itemList = new ArrayList<>();
		if (egoResult.getStatus()==200) {
			List<Content> contents = (List<Content>) egoResult.getData();
			for (Content content : contents) {
				ADItem item = new ADItem();
				item.setSrc(content.getPic());
				item.setSrcB(content.getPic2());
				item.setAlt(content.getTitleDesc());
				item.setHref(content.getUrl());
				itemList.add(item);
			}
			
		}
		//转换为json字符串返回到页面
		return JsonUtils.objectToJson(itemList);
	}
}
