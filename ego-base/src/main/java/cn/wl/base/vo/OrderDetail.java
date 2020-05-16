package cn.wl.base.vo;

import java.util.List;

import cn.wl.base.pojo.Order;
import cn.wl.base.pojo.OrderItem;
import cn.wl.base.pojo.OrderShipping;

public class OrderDetail extends Order{

    private List<OrderItem> orderItems;
	
	private OrderShipping orderShipping;

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

	public OrderDetail() {
		super();
	}

	public OrderDetail(List<OrderItem> orderItems, OrderShipping orderShipping) {
		super();
		this.orderItems = orderItems;
		this.orderShipping = orderShipping;
	}
	
}
