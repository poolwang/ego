package cn.wl.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.wl.base.mapper.ContentMapper;
import cn.wl.base.pojo.Content;
import cn.wl.base.utils.JsonUtils;
import cn.wl.base.vo.EgoResult;
import cn.wl.rest.service.ContentService;
import redis.clients.jedis.JedisCluster;

@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService{

	//声明一个Redis数据的key
	private String EGO_CONTENT = "EGO_CONTENT"; 
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public EgoResult getContentByCatId(Long catId) {
		List<Content> contents=null;
		try {
			//先查redis库
			String jedisData = jedisCluster.hget(EGO_CONTENT, catId+"");
			//判断redis里有没有数据，如果有
			if (jedisData!=null && !"".equals(jedisData)) {
				contents = JsonUtils.jsonToList(jedisData, Content.class);
				System.out.println(jedisData);
			}else {
				//如果没有，就先从数据库查询数据，然后将其设置到redis
				EntityWrapper<Content> wrapper=new EntityWrapper<>();
				wrapper.eq("category_id", catId);
				contents = this.selectList(wrapper);
				jedisCluster.hsetnx(EGO_CONTENT, catId+"", JsonUtils.objectToJson(contents));
			}
		} catch (Exception e) {
			e.printStackTrace();
			//如果redis出问题，那么也要保证可以正常查询数据库
			EntityWrapper<Content> wrapper=new EntityWrapper<>();
			wrapper.eq("category_id", catId);
			contents = this.selectList(wrapper);
		}
		
		return EgoResult.ok(contents);
	}
}
