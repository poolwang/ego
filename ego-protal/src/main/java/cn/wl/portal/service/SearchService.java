package cn.wl.portal.service;

import cn.wl.base.vo.SearchResult;

public interface SearchService {

	SearchResult query(String q, Integer page);

}
