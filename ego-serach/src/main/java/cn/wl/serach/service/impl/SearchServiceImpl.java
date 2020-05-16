package cn.wl.serach.service.impl;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.wl.base.mapper.ItemMapper;
import cn.wl.base.vo.EgoResult;
import cn.wl.base.vo.SearchItem;
import cn.wl.base.vo.SearchResult;
import cn.wl.serach.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private HttpSolrServer solrServer;

	@Autowired
	private ItemMapper itemMapper;

	@Value("${solr.pageSize}")
	private int pageSize;

	//1.数据库采集数据
	@Override
	public List<SearchItem> getData() {
		List<SearchItem> items = itemMapper.getData();
		return items;
	}

	//2.将上面采集的数据变成文档
	@Override
	public List<SolrInputDocument> getDocument(List<SearchItem> items) {
		List<SolrInputDocument> docs=new ArrayList<SolrInputDocument>();
		SolrInputDocument doc = null;
		for (SearchItem item : items) {
			doc=new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_category_name", item.getCategoryName());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_sell_point", item.getPrice());
			doc.addField("item_image", item.getImage());
			docs.add(doc);
		}
		return docs;
	}
	
	//3.将文档添加到solr索引库
	@Override
	public EgoResult addDocuments(List<SolrInputDocument> docs) {
		try {
			solrServer.add(docs); 
			solrServer.commit();
			return EgoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(400, "连接solr服务器异常");
		} 
	}
	
	//4.查询索引库
	@Override
	public SearchResult doSearch(String keyword, String categoryName, String price, int page, Integer sort) throws SolrServerException {
		SearchResult result=new SearchResult();
		//查询条件语句
		SolrQuery query = this.getSolrQuery(keyword, categoryName, price, page, sort);
		//查询itemList
		QueryResponse queryResponse = solrServer.query(query);
		if (queryResponse.getStatus()==0) {
			SolrDocumentList documentList = queryResponse.getResults();
			//设置高亮
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<SearchItem> searchItems=new ArrayList<SearchItem>();
			for (SolrDocument solrDocument : documentList) {
				SearchItem item = new SearchItem();
				item.setId(Long.parseLong((String) solrDocument.get("id")));
				item.setCategoryName((String) solrDocument.get("item_category_name"));
				item.setImage((String) solrDocument.get("item_image"));
				item.setPrice((Long) solrDocument.get("item_price"));
				item.setSellPoint((String) solrDocument.get("item_sell_point"));
			   //通过id,获得每条高亮数据
				Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
				List<String> list = map.get("item_title");
				if (list!=null&&list.size()>0) {
					item.setTitle(list.get(0));
				}else {
					item.setTitle((String) solrDocument.get("item_title"));	
				}
				searchItems.add(item);
			}
		//将查询的itemList,page等属性添加到返回集中	
		result.setItemList(searchItems);;
		result.setCurPage(page);
		result.setRecordCount(documentList.getNumFound());
		Integer totalPages = (int) Math.ceil(result.getRecordCount()/pageSize);
		result.setTotalPages(totalPages);
		}
		return result;
	}
	
	  //设置查询条件
	private SolrQuery getSolrQuery(String keyword, String categoryName, String price, int page, Integer sort) {
		SolrQuery query=new SolrQuery();
		//关键字设置
		if (keyword!=null && !"".equals(keyword)) {
		    query.set("q", keyword);	
		}else {
			query.set("q", "*");
		}
		
		//categoryName设置
		if (categoryName!=null && !"".equals(categoryName)) {
			query.add("df", "item_category_name:"+categoryName);
		}
		
		//价格设置 价格格式为：[20-50]
		if (price!=null && !"".equals(price)) {
			String[] priceArr = price.split("-");
			query.add("fq", "item_price:[" + priceArr[0] + " TO " + priceArr[1] + "]");
		}
		//排序设置  注意：如果为1，倒序，非1 就是正序
		if (sort==null||sort==1) {
			query.setSort("item_price", ORDER.desc);
		} else {
			query.setSort("item_price", ORDER.asc);
		}
		//开始位置
		query.setStart((page-1)*pageSize);
		query.setRows(pageSize);
		
		query.set("df", "item_title");
		
		//设置支持高亮
		query.setHighlight(true);
		query.setHighlightSimplePre("<font style='color:red'>");
		query.setHighlightSimplePost("</font>");
		query.addHighlightField("item_title");
		
		return query;
	}
}
