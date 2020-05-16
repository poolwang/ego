package cn.wl.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ContentCategoryMapper;
import cn.wl.base.pojo.ContentCategory;
import cn.wl.base.vo.EUTreeNode;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl extends ServiceImpl<ContentCategoryMapper, ContentCategory> implements ContentCategoryService{
 
	@Override
	public List<EUTreeNode> selectByParentId(Long parentId) {
		List<EUTreeNode> nodes=new ArrayList<EUTreeNode>();
		EntityWrapper<ContentCategory> wrapper=new EntityWrapper<>();
		wrapper.eq("parent_id", parentId);
		wrapper.eq("status", "1");
		List<ContentCategory> list = this.selectList(wrapper);
		for (ContentCategory contentCategory : list) {
			EUTreeNode node=new EUTreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()==1 ? "closed" : "open");
			nodes.add(node);
		}
		return nodes;
	}
	
	//增加内容列表
	@Transactional
	@Override
	public EgoResult save(Long parentId, String name) {
		EgoResult result=new EgoResult();
		//第一步：封装一个插入的对象
		ContentCategory contentCategory=new ContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setIsParent((byte)0);
		contentCategory.setStatus((byte)1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(contentCategory.getUpdated());
		contentCategory.setSortOrdert(1);
		this.insert(contentCategory);
		//判断新增的节点，它的父id对应的节点是不是子节点，如果是，那么要将其改成父节点
		ContentCategory parentContentCategory = this.selectById(parentId);
		if (parentContentCategory.getIsParent()==0) {
			parentContentCategory.setIsParent((byte)1);
			parentContentCategory.setUpdated(new Date());
			this.updateById(parentContentCategory);
		}
        //第四步：返回结果，需要回传插入对象
		return EgoResult.ok(contentCategory);
	}
	
	//修改
	@Override
	public EgoResult updateNode(String name, Long id) {
		EgoResult result=new EgoResult();
		ContentCategory contentCategory = this.selectById(id);
		contentCategory.setId(id);
		contentCategory.setName(name);
		this.updateById(contentCategory);
		return result;
	}
	
	//删除
	@Transactional
	@Override
	public EgoResult deleteNode(Long id, Long parentId) {
		EgoResult result=new EgoResult();
		ContentCategory node = this.selectById(id);
		//如果删除的是父节点，则把下面的子节点同时全部删除
		if (node.getIsParent()==1) {
			EntityWrapper<ContentCategory> wrapper=new EntityWrapper<>();
			wrapper.eq("parent_id",id );
			List<ContentCategory> subNodes = this.selectList(wrapper);
			for (ContentCategory contentCategory : subNodes) {
				contentCategory.setStatus(2);
				contentCategory.setUpdated(new Date());
				this.updateById(contentCategory);
			}
		}
		node.setStatus(2);
		node.setUpdated(new Date());
		this.updateById(node);
		
		//判断父节点是否是最后一个子节点。如果是，就修改为叶节点
		if (parentId==null) {
			parentId=node.getParentId();
		}
		//判断父节点是否还有子节点
				EntityWrapper<ContentCategory> wrapper=new EntityWrapper<>();
				wrapper.eq("parent_id", parentId);
				wrapper.and().eq("status", 1);
				List<ContentCategory> subNodes = this.selectList(wrapper);
		if (subNodes==null||subNodes.size()==0) {
			//修改父节点为叶节点
			ContentCategory parentNode = this.selectById(parentId);
			parentNode.setIsParent((byte)0);
			parentNode.setUpdated(new Date());
			this.updateById(parentNode);
				}
		return result;
	}
}
