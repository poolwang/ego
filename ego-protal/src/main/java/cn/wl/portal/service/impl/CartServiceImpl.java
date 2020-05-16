package cn.wl.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.wl.base.pojo.Item;
import cn.wl.base.utils.HttpClientUtils;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.CartItem;
import cn.wl.portal.service.CartService;
import net.sf.jsqlparser.expression.LongValue;

@Service
public class CartServiceImpl implements CartService{

	//Rest 服务的http路径
		@Value("${REST_BASE_URL}")
		private String REST_BASE_URL;
		
		@Value("${EGO_CART_COOKIE}")
		private String EGO_CART_COOKIE;
		
	@Override
	public List<CartItem> addToCart(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> cart = this.getCart(request);
		boolean flag=false;
		for (CartItem cartItem : cart) {
			//cookie存在该购物车
			if (itemId.longValue()==Long.valueOf(cartItem.getId()).longValue()) {
				cartItem.setNum(cartItem.getNum()+num);
				flag=true;
				break;
			}
		}
		//cookie不存在该购物车
		if (!flag) {
			//查询rest里的商品属性
			String jsonData = HttpClientUtils.doGet(REST_BASE_URL+"/item/"+itemId);
			Item item = JsonUtils.jsonToPojo(jsonData, Item.class);
			
			CartItem cartItem=new CartItem();
			cartItem.setId(itemId+"");
			cartItem.setNum(num);
			cartItem.setImage(item.getImage());
			cartItem.setImages(item.getImages());
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
			cart.add(cartItem);
		}
		
		//更新cookie数据
		Cookie cookie=new Cookie(EGO_CART_COOKIE, JsonUtils.objectToJson(cart));
		cookie.setPath("/");
		response.addCookie(cookie);
		return cart;
	}
	
	//展示购物车
	@Override
	public List<CartItem> showCart(HttpServletRequest request) {
		List<CartItem> cart = this.getCart(request);
		return cart;
	}
	
	//修改购物车数量
	@Override
	public List<CartItem> update(Long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cart = this.getCart(request);
		if (num<1) {
			return cart;
		}
		for (CartItem cartItem : cart) {
			if (itemId.longValue()==Long.valueOf(cartItem.getId()).longValue()) {
				cartItem.setNum(num);
				break;
			}
		}
		Cookie cookie=new Cookie(EGO_CART_COOKIE, JsonUtils.objectToJson(cart));
		cookie.setPath("/");
		response.addCookie(cookie);
		return cart;
	}
	
	//删除购物车
	@Override
	public List<CartItem> delete(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cart = this.getCart(request);
		for (CartItem cartItem : cart) {
			if (itemId.longValue()==Long.valueOf(cartItem.getId()).longValue()) {
				cart.remove(cartItem);
				break;
			}
		}
		Cookie cookie=new Cookie(EGO_CART_COOKIE, JsonUtils.objectToJson(cart));
		cookie.setPath("/");
		response.addCookie(cookie);
		return cart;
	}
	
	//获取购物车列表
		private List<CartItem> getCart(HttpServletRequest request) {
			Cookie[] cookies = request.getCookies();
			List<CartItem> cartItems=null;
			boolean flag=false;
			for (Cookie cookie : cookies) {
				if (EGO_CART_COOKIE.equals(cookie.getName())) {
					cartItems = JsonUtils.jsonToList(cookie.getValue(), CartItem.class);
					flag=true;
					break;
				}
			}
			if (!flag) {
				cartItems=new ArrayList<CartItem>();
			}
			return cartItems;
		}
}
