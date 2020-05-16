package cn.wl.manager.service.impl;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ItemDescMapper;
import cn.wl.base.mapper.ItemMapper;
import cn.wl.base.mapper.ItemParamItemMapper;
import cn.wl.base.pojo.Item;
import cn.wl.base.pojo.ItemDesc;
import cn.wl.base.pojo.ItemParamItem;
import cn.wl.base.utils.IDUtils;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;
import cn.wl.base.vo.SearchItem;
import cn.wl.manager.service.ItemService;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService{

	@Autowired
	private ItemDescMapper descMapper;
	
	@Autowired
	private ItemParamItemMapper itemParamMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${MQ_ITEM_QUEUE_NAME}")
	private String MQ_ITEM_QUEUE_NAME;
	
	@Override
	public EUDataGridResult listAndPage(int curPage, int rows) {
		EUDataGridResult listAndPage=new EUDataGridResult();
		//查询数据
		Page<Item> page = this.selectPage(new Page<Item>(curPage, rows));
		listAndPage.setTotal(page.getTotal());
		listAndPage.setRows(page.getRecords());
		return listAndPage;
	}

	@Transactional
	@Override
	public EgoResult save(Item item, String desc, String paramData) {
		long itemId = IDUtils.genItemId();
		//插入商品
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		this.insert(item);
		
		//插入商品详情
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		descMapper.insert(itemDesc);
		
		//插入规格
		ItemParamItem paramItem=new ItemParamItem();
		paramItem.setItemId(itemId);
		paramItem.setParamData(paramData);
		paramItem.setCreated(item.getCreated());
		paramItem.setUpdated(item.getUpdated());
		itemParamMapper.insert(paramItem);
		
		//将商品写入到activemq消息队列
		SearchItem temp = new SearchItem();
		temp.setId(itemId);
		temp.setImage(item.getImage());
		temp.setPrice(item.getPrice());
		temp.setSellPoint(item.getSellPoint());
		temp.setTitle(item.getTitle());
		
		jmsTemplate.send(MQ_ITEM_QUEUE_NAME, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				   MapMessage mapMessage = session.createMapMessage();
				   mapMessage.setString("key", "add");
				   mapMessage.setString("value", JsonUtils.objectToJson(temp));
				return mapMessage;
			}
		});
		
		return EgoResult.ok();
	}

	
}
