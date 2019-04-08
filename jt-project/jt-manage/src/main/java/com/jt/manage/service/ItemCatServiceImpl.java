package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.service.RedisService;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.manage.anno.CacheAnno;
import com.jt.manage.anno.CacheAnno.CACHE_TYPE;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private RedisService redisService;
	//private Jedis jedis;
	//private ShardedJedis jedis;
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	//tb_item_cat 
	public List<ItemCat> findItemCatByParentId(long parentId){
		
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		return itemCatMapper.select(itemCat);
	}
	
	@Override
	//@CacheAnno(cacheType=CACHE_TYPE.UPDATE,index=0,key="ITEM_CAT_",targetClass=ArrayList.class)
	public List<EasyUITree> findItemCatList(long parentId) {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		List<ItemCat> itemCatList =  
				findItemCatByParentId(parentId);
		for (ItemCat itemCat : itemCatList) {
			EasyUITree uiTree = new EasyUITree();
			uiTree.setId(itemCat.getId());
			uiTree.setText(itemCat.getName());
			//一二级使用closed  三级使用open 
			String state = itemCat.getIsParent()?"closed":"open";
			uiTree.setState(state);
			treeList.add(uiTree);
		}
		System.out.println("查询数据库");
		return treeList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findCacheItemCat(Long parentId) {
		String key = "ITEM_CAT_" + parentId; 
		String json = redisService.get(key);
		List<EasyUITree> treeList = new ArrayList<>();
		if(StringUtils.isEmpty(json)) {
			treeList = findItemCatList(parentId);
			String result = 
					ObjectMapperUtil.toJSON(treeList);
			redisService.set(key, result);
			//System.out.println("查询数据库!!!!!");
		}else {
			treeList = ObjectMapperUtil.toObject(json,ArrayList.class);
			//System.out.println("查询缓存!!!!!!!");
		}
		
		return treeList;
	}
}
