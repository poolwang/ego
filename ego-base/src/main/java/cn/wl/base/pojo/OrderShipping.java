package cn.wl.base.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("tb_order_shipping")
public class OrderShipping {

	 /**
     * 订单ID
     */
    @TableId(value="order_id",type=IdType.INPUT)
    private String orderId;
    /**
     * 收货人全名
     */
    @TableField("receiver_name")
    private String receiverName;
    /**
     * 固定电话
     */
    @TableField("receiver_phone")
    private String receiverPhone;
    /**
     * 移动电话
     */
    @TableField("receiver_mobile")
    private String receiverMobile;
    /**
     * 省份
     */
    @TableField("receiver_state")
    private String receiverState;
    /**
     * 城市
     */
    @TableField("receiver_city")
    private String receiverCity;
    /**
     * 区/县
     */
    @TableField("receiver_district")
    private String receiverDistrict;
    /**
     * 收货地址，如：xx路xx号
     */
    @TableField("receiver_address")
    private String receiverAddress;
    /**
     * 邮政编码,如：310001
     */
    @TableField("receiver_zip")
    private String receiverZip;
    private Date created;
    private Date updated;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverZip() {
		return receiverZip;
	}
	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
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
}
