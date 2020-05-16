package cn.wl.rest.service;

import com.baomidou.mybatisplus.service.IService;

import cn.wl.base.pojo.Item;

public interface ItemService extends IService<Item>{

	Item getById(Long itemId);

}
