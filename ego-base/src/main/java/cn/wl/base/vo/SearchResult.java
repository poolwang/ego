package cn.wl.base.vo;

import java.util.List;

public class SearchResult {

	 //总记录数
		private Long recordCount;
		//分页数据
		private List<SearchItem> itemList;
		//总页数
		private Integer totalPages;
		//当前页 1开始
		private Integer curPage;
		public Long getRecordCount() {
			return recordCount;
		}
		public void setRecordCount(Long recordCount) {
			this.recordCount = recordCount;
		}
		public List<SearchItem> getItemList() {
			return itemList;
		}
		public void setItemList(List<SearchItem> itemList) {
			this.itemList = itemList;
		}
		public Integer getTotalPages() {
			return totalPages;
		}
		public void setTotalPages(Integer totalPages) {
			this.totalPages = totalPages;
		}
		public Integer getCurPage() {
			return curPage;
		}
		public void setCurPage(Integer curPage) {
			this.curPage = curPage;
		}
}
