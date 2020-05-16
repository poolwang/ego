package cn.wl.manager.service;

import com.baomidou.mybatisplus.service.IService;

import cn.wl.base.pojo.ItemParam;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;

public interface ItemParamService extends IService<ItemParam>{

	EgoResult getByItemCatId(long catId);
    EUDataGridResult listAndPage(int curPage, int pageSize) ;
	EgoResult save(Long catId, String paramData);
	EgoResult delete(Long[] ids);
}
