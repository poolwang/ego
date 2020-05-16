package cn.wl.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.wl.base.utils.HttpClientUtils;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.CartItem;
import cn.wl.base.vo.OrderDetail;
import cn.wl.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	    //Cookie购物车的名称
		@Value("${EGO_CART_COOKIE}")
		private String EGO_CART_COOKIE;
		
		@Value("${ORDER_URL}")
		private String ORDER_URL;
		
		@Override
		public List<CartItem> showOrder(HttpServletRequest request) {
			Cookie[] cookies = request.getCookies();
			List<CartItem> cartItems=null;
			boolean flag=false;
			for (Cookie cookie : cookies) {
				if (EGO_CART_COOKIE.equals(cookie.getName())) {
					cartItems = JsonUtils.jsonToList(cookie.getValue(), CartItem.class);
					//如果有数据，为true
					flag=true;
					break;
				}
			}
			 //如果没有就新建一个List<CartItem>对象
			if (!flag) {
				cartItems=new ArrayList<CartItem>();
			}
			 return cartItems;
		}
		
		@Override
		public String save(OrderDetail order) {
			Map<String, String> params=new HashMap<String, String>();
			params.put("order", JsonUtils.objectToJson(order));
			String orderId = HttpClientUtils.doGet(ORDER_URL+"/create", params);
			return orderId;
		}
}
