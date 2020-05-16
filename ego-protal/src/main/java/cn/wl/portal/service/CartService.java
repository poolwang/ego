package cn.wl.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.wl.base.vo.CartItem;

public interface CartService {

	List<CartItem> addToCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> showCart(HttpServletRequest request);

	List<CartItem> update(Long itemID, Integer num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> delete(Long itemId, HttpServletRequest request, HttpServletResponse response);

}
