package cn.wl.base.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName(value="tb_item_param")
public class ItemParam {

	@TableId(value="id",type=IdType.AUTO)
	private Long id;
	
	@TableField(value="item_cat_id")
	private long itemCatId;
	
	@TableField(value="param_data")
	private String paramData;
	
	private Date created;
	
	private Date updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getItemCatId() {
		return itemCatId;
	}

	public void setItemCatId(long itemCatId) {
		this.itemCatId = itemCatId;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public ItemParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemParam(Long id, long itemCatId, String paramData, Date created, Date updated) {
		super();
		this.id = id;
		this.itemCatId = itemCatId;
		this.paramData = paramData;
		this.created = created;
		this.updated = updated;
	}
	
}
