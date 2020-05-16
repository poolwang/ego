package cn.wl.manager.service;

import com.baomidou.mybatisplus.service.IService;

import cn.wl.base.pojo.Content;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;

public interface ContentService extends IService<Content>{

	EUDataGridResult listByCatIdAndPage(Long categoryId, int page, int rows);

	EgoResult addContent(Content content) throws Exception;

	EgoResult delete(Integer[] ids);

	EgoResult update(Content content);

}
