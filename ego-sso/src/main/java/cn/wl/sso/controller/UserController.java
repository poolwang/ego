package cn.wl.sso.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wl.base.pojo.User;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.EgoResult;
import cn.wl.sso.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public EgoResult login(String username,String password,HttpServletResponse response){
		EgoResult result = userService.login(username, password, response);
		return result;
	}
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object check(@PathVariable("param")String param,@PathVariable("type")Integer type,
			String callback){
		EgoResult result = userService.check(param, type);
        if(null==callback || "".equals(callback)){
			return result;
		}else{
			String jsonData = JsonUtils.objectToJson(result);
			//使用回调函数，说明请求是通过ajax访问的
			String jsData = callback+"("+jsonData+")";
			return jsData;
		}
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public EgoResult register(User user) {
		return userService.register(user);
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object checkLogin(@PathVariable("token")String token,String callback){
		EgoResult result = userService.checkLogin(token);
		if(null==callback || "".equals(callback)){
			return result;
		}else{
			String jsonData = JsonUtils.objectToJson(result);
			//使用回调函数，说明请求是通过ajax访问的
			String jsData = callback+"("+jsonData+")";
			return jsData;
		}
	}
}
