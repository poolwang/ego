package cn.wl.manager.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.wl.base.pojo.ContentCategory;
import cn.wl.base.vo.EUTreeNode;
import cn.wl.base.vo.EgoResult;

public interface ContentCategoryService extends IService<ContentCategory>{

	List<EUTreeNode> selectByParentId(Long parentId);

	EgoResult save(Long parentId, String name);

	EgoResult updateNode(String name, Long id);

	EgoResult deleteNode(Long id, Long parentId);

}
