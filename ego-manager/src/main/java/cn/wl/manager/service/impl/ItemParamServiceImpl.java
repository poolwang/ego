package cn.wl.manager.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ItemCatMapper;
import cn.wl.base.mapper.ItemParamMapper;
import cn.wl.base.pojo.ItemParam;
import cn.wl.base.vo.EUDataGridResult;
import cn.wl.base.vo.EgoResult;
import cn.wl.manager.service.ItemParamService;

@Service
public class ItemParamServiceImpl extends ServiceImpl<ItemParamMapper, ItemParam>implements ItemParamService{

	@Override
	public EgoResult getByItemCatId(long catId) {
		EgoResult result=new EgoResult();
		EntityWrapper<ItemParam> wrapper=new EntityWrapper<>();
		wrapper.eq("item_cat_id", catId);
		List<ItemParam> selectList = this.selectList(wrapper);
		if (selectList !=null&&selectList.size()>0) {
			result.setData(selectList.get(0));
			result.setMsg("ok");
			result.setStatus(200);
			return result;
		}else {
			result.setStatus(400);
			result.setMsg("没有查到该类商品的模板");
			return result;
		}
		
	}

	@Autowired
	private ItemParamMapper itemParamMapper;
	@Autowired
	private ItemCatMapper ItemCatMapper;
	
	@Override
	public EUDataGridResult listAndPage(int curPage, int pageSize) {
		EUDataGridResult result=new EUDataGridResult();
		int start=(curPage-1)*pageSize;
		List<Map<String, Object>> data = itemParamMapper.listAndPage(start, pageSize);
		result.setRows(data);
		result.setTotal(ItemCatMapper.selectCount(null));
		return result;
	}

	@Override
	public EgoResult save(Long catId, String paramData) {
		EgoResult result=new EgoResult();
		ItemParam itemParam=new ItemParam();
		//设置参数
		itemParam.setItemCatId(catId);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(itemParam.getCreated());
		//插入数据
		this.insert(itemParam);
		result.setStatus(200);
		result.setMsg("插入成功");
		return result;
	}
	
	@Override
	public EgoResult delete(Long[] ids) {
		EgoResult result=new EgoResult();
		this.deleteBatchIds(Arrays.asList(ids));
		result.setStatus(200);
		result.setMsg("删除成功");
		return result;
		
	}
}
