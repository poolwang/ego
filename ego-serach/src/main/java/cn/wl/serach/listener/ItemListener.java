package cn.wl.serach.listener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.SearchItem;

@Component
public class ItemListener implements MessageListener{
	@Autowired
	private HttpSolrServer server;
	
	@Override
	public void onMessage(Message message) {
		if (message !=null) {
			MapMessage mapMessage = (MapMessage) message;
			try {
				String key = mapMessage.getString("key");
				if ("add".equals(key)) {
					String value = mapMessage.getString("value");
					SearchItem item = JsonUtils.jsonToPojo(value, SearchItem.class);
					SolrInputDocument doc = new SolrInputDocument();
					doc.addField("id", item.getId());
					doc.addField("item_title", item.getTitle());
					doc.addField("item_sell_point", item.getSellPoint());
					doc.addField("item_price", item.getPrice());
					doc.addField("item_image", item.getImage());
					doc.addField("item_category_name", item.getCategoryName());
					
					server.add(doc);
					server.commit();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
