package cn.wl.serach.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.vo.EgoResult;
import cn.wl.base.vo.SearchResult;
import cn.wl.serach.service.SearchService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/import/all")
	@ResponseBody
	public EgoResult createIndex(){
		EgoResult result = 
				searchService.addDocuments(searchService.getDocument(searchService.getData()));
		return result;
	}
	@RequestMapping(value="/doSearch",method=RequestMethod.GET)
	@ResponseBody
	public SearchResult doSearch(String keyword,String categoryName,String price,
			@RequestParam(defaultValue="1")Integer page,Integer sort){
		SearchResult result = null;
		try {
			result = searchService.doSearch(keyword, categoryName, price, page, sort);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;
	}
}
