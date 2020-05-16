package cn.wl.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.wl.base.pojo.Item;
import cn.wl.base.vo.SearchItem;
public interface ItemMapper extends BaseMapper<Item> {

	@Select(value="select i.id,i.sell_point as sellPoint,i.title,i.price,i.image,c.name as categoryName "
			+ "from tb_item i left join tb_item_cat c on i.cid = c.id where i.status=1")
	List<SearchItem> getData();
}
