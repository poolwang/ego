package cn.wl.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wl.base.mapper.OrderItemMapper;
import cn.wl.base.mapper.OrderMapper;
import cn.wl.base.mapper.OrderShippingMapper;
import cn.wl.base.pojo.OrderItem;
import cn.wl.base.pojo.OrderShipping;
import cn.wl.base.utils.IDUtils;
import cn.wl.base.vo.OrderDetail;
import cn.wl.order.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper itemMapper;
	@Autowired
	private OrderShippingMapper shippingMapper;
	
	@Transactional
	@Override
	public String save(OrderDetail order) {
		//插入订单
		long orderId=IDUtils.genItemId();
		order.setOrderId(orderId+"");
		order.setStatus(1);
		order.setCreateTime(new Date());
		order.setUpdateTime(order.getCreateTime());
		orderMapper.insert(order);
		
		//插入订单详情
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setId(IDUtils.genItemId()+"");
			orderItem.setOrderId(orderId+"");
			itemMapper.insert(orderItem);
		}
		
		//插入收获详情等
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId+"");
		shippingMapper.insert(orderShipping);
		return orderId+"";
	}
}
