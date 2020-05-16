package cn.wl.sso.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.UserMapper;
import cn.wl.base.pojo.User;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.EgoResult;
import cn.wl.sso.service.UserService;
import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

	    //校验Cookie的名称（Token）
		@Value("${EGO_USER_TOKEN}")
		private String EGO_USER_TOKEN;
		//Redis的Key的名称
		@Value("${EGO_USER_KEY}")
		private String EGO_USER_KEY;
	    //Redis的超时时间
		@Value("${EGO_USER_EXPIRE_TIME}")
		private Integer EGO_USER_EXPIRE_TIME;
		
		@Autowired
		private JedisCluster jedisCluster;
	
		@Override
		public EgoResult login(String username, String password,HttpServletResponse response) {
			EntityWrapper<User> wrapper=new EntityWrapper<User>();
			wrapper.eq("username", username);
			wrapper.eq("password", password);
			List<User> users = this.selectList(wrapper);
			if (users!=null && users.size()>0) {
				//查到有数据,则把数据添加到cookie和redis
				String token = UUID.randomUUID().toString();
				Cookie cookie = new Cookie(EGO_USER_TOKEN, token);
				cookie.setPath("/");
				response.addCookie(cookie);
				//同时把数据添加到redis和设置失效时间
				jedisCluster.set(EGO_USER_KEY+":"+token, JsonUtils.objectToJson(users.get(0)));
				jedisCluster.expire(EGO_USER_KEY+":"+token, EGO_USER_EXPIRE_TIME);
				return EgoResult.ok(token);
			}else {
				return EgoResult.build(400, "用户名或者密码错误，请重新登陆");
			}
		}
		
		//注册时候校验
		@Override
		public EgoResult check(String param, Integer type) {
			Map<String, Object> params=new HashMap<String, Object>();
			if (type!=null) {
				if (type==1) {
					params.put("username", param);
				}else if (type==2) {
					params.put("phone", param);
				}else if (type==3) {
					params.put("email", param);
				}else {
					return EgoResult.build(400, "待校验的数据的类型有误！");
				}
			}else {
				return EgoResult.build(400, "待校验的数据的类型不能为空，请查找格式！");
			}
			List<User> users = this.selectByMap(params);
			if (users!=null && users.size()>0) {
				return EgoResult.ok(false);
			}
			//如果数据库没有改用户的记录，就提示true
			return EgoResult.ok(true);
		}
		
		//用户注册
		@Override
		public EgoResult register(User user) {
			try {
				user.setCreated(new Date());
				user.setUpdated(user.getCreated());
				this.insert(user);
				return EgoResult.ok();
			} catch (Exception e) {
				e.printStackTrace();
				return EgoResult.build(400, null);
			}
		}
		
		@Override
		public EgoResult checkLogin(String token) {
			String jsonData = jedisCluster.get(EGO_USER_KEY+":"+token);
			if (jsonData!=null&&!"".equals(jsonData)) {
				User user = JsonUtils.jsonToPojo(jsonData, User.class);
				jedisCluster.expire(EGO_USER_KEY+":"+token, EGO_USER_EXPIRE_TIME);
				return EgoResult.ok(user);
			}else {
				return EgoResult.build(400, "用户未登录或者登陆失效，请重新登陆");
			}
		}
}
