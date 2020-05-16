package cn.wl.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.wl.base.vo.CartItem;
import cn.wl.base.vo.OrderDetail;

public interface OrderService {

	List<CartItem> showOrder(HttpServletRequest request);

	String save(OrderDetail order);

}
