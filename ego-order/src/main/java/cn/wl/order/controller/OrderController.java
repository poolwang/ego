package cn.wl.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.OrderDetail;
import cn.wl.order.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/create")
	@ResponseBody
	public String save(String order){
		try {
			OrderDetail orderDetail = JsonUtils.jsonToPojo(order, OrderDetail.class);
			String orderId  = orderService.save(orderDetail);
			return orderId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
