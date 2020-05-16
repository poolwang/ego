package cn.wl.manager.service;

import com.baomidou.mybatisplus.service.IService;

import cn.wl.base.pojo.Item;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;

public interface ItemService extends IService<Item>{

	EUDataGridResult listAndPage(int curPage,int rows);
	EgoResult save(Item item, String desc, String paramData);
}
