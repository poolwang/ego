package cn.wl.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.wl.base.pojo.ItemParam;

public interface ItemParamMapper extends BaseMapper<ItemParam>{

	@Select(value="select p.id,p.item_cat_id as itemCatId,t.name as itemCatName,p.param_data as paramData,p.created,p.updated "
			+ "from tb_item_param p left join tb_item_cat t on p.item_cat_id = t.id"
			+ " limit ${start},${pageSize}")
	List<Map<String, Object>> listAndPage(@Param("start")int start,@Param("pageSize")int pageSize);
}
