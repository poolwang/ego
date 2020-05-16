package cn.wl.portal.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.wl.base.vo.CartItem;
import cn.wl.base.vo.OrderDetail;
import cn.wl.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrder(HttpServletRequest request){
		List<CartItem> showOrder = orderService.showOrder(request);
		request.setAttribute("cartList", showOrder);
		return "order-cart";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String save(OrderDetail order,ModelMap map){
		//调用插入订单成功，返回订单编号
		String orderid = orderService.save(order);
		if(null!=orderid && !"".equals(orderid)){
			//将相关参数设置在Request作用域里面
			map.addAttribute("orderId", orderid);
			map.addAttribute("payment", order.getPayment());
			
			//默认送到时间，在3天后
			Calendar c=Calendar.getInstance();
			SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd/E");
			c.add(Calendar.DATE, 3);
			System.out.println(df.format(c.getTime()));
			
			map.addAttribute("date", df.format(c.getTime()));
			
			return "success";
		}else{
			//订单创建失败
			map.addAttribute("message", orderid);
			return "error/exception";

		}
	}
}

