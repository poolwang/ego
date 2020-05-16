package cn.wl.manager.service;

import cn.wl.base.pojo.ItemCat;
import cn.wl.base.vo.EUTreeNode;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

public interface ItemCatService extends IService<ItemCat>{

	List<EUTreeNode> getByParentId(Long parentId);
}
