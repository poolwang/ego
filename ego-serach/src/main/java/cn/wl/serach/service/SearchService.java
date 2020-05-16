package cn.wl.serach.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import cn.wl.base.vo.EgoResult;
import cn.wl.base.vo.SearchItem;
import cn.wl.base.vo.SearchResult;

public interface SearchService {

	List<SearchItem> getData();

	List<SolrInputDocument> getDocument(List<SearchItem> items);

	EgoResult addDocuments(List<SolrInputDocument> docs);

	SearchResult doSearch(String keyword, String categoryName, String price, int page, Integer sort) throws SolrServerException;

}
