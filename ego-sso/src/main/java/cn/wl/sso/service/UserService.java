package cn.wl.sso.service;

import javax.servlet.http.HttpServletResponse;

import cn.wl.base.pojo.User;
import cn.wl.base.vo.EgoResult;

public interface UserService {

	EgoResult login(String username, String password, HttpServletResponse response);

	EgoResult check(String param, Integer type);

	EgoResult register(User user);

	EgoResult checkLogin(String token);

}
