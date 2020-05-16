package cn.wl.portal.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.wl.base.pojo.User;
import cn.wl.base.utils.HttpClientUtils;
import cn.wl.base.vo.EgoResult;

public class LoginInterceptor implements HandlerInterceptor{

	       //Cookie的Token的名称
			@Value("${EGO_USER_TOKEN}")
			private String EGO_USER_TOKEN;
			//远程调用SSO的基础路径
			@Value("${SSO_BASE_URL}")
			private String SSO_BASE_URL;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Cookie[] cookies = request.getCookies();
		String token="";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(EGO_USER_TOKEN)) {
				token = cookie.getValue();
				break;
			}
		}
		if (token!=null && !"".equals(token)) {
			String jsonData = HttpClientUtils.doGet(SSO_BASE_URL+"/token/"+token);
			if (jsonData !=null && !"".equals(jsonData)) {
				EgoResult egoResult = EgoResult.formatToPojo(jsonData, User.class);
				if (egoResult.getStatus()==200) {
					Object user = egoResult.getData();
					//校验登录成功
					request.setAttribute("loginUser",user);
					return true;
				}
			}
		}
		//如果登录不成功，跳转回登录页面
		response.sendRedirect(SSO_BASE_URL+"/showLogin");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
