package cn.wl.manager.service.impl;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ContentMapper;
import cn.wl.base.pojo.Content;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ContentService;
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService{

	@Override
	public EUDataGridResult listByCatIdAndPage(Long categoryId, int page, int rows) {
		EUDataGridResult result=new EUDataGridResult();
		EntityWrapper<Content> wrapper=new EntityWrapper<>();
		wrapper.eq("category_id", categoryId);
		Page<Content> selectPage = this.selectPage(new Page<>(page, rows), wrapper);
		result.setTotal(selectPage.getTotal());
		result.setRows(selectPage.getRecords());
		return result;
	}
	//增
	@Override
	public EgoResult addContent(Content content) throws Exception {
		EgoResult result=new EgoResult();
		content.setCreated(new Date());
		content.setUpdated(new Date());
		this.insert(content);
		return result;
	}
	
	//删
	@Override
	public EgoResult delete(Integer[] ids) {
		EgoResult result=new EgoResult();
		this.deleteBatchIds(Arrays.asList(ids));
		return result;
	}
	
	//改
	@Override
	public EgoResult update(Content content) {
		EgoResult result=new EgoResult();
		this.updateById(content);
		return result;
	}
}
