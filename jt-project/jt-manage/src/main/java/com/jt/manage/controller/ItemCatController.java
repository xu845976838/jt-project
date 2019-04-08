package com.jt.manage.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jt.manage.anno.CacheAnno;
import com.jt.manage.anno.CacheAnno.CACHE_TYPE;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.EasyUITree;

@RestController  //@Controller+@ResponseBody
@RequestMapping("/item/cat")
public class ItemCatController {
	/**
	 * 原则:如果controller不需要跳转
	 * 页面,只是返回json串,页面中
	 * 可以使用RestController.
	 */
	@Autowired
	private ItemCatService itemCatService;
	
	
	
	@RequestMapping("/list")
	public List<EasyUITree> findItemCatList
	(@RequestParam(value="id",defaultValue="0") Long parentId){
		
		//查询一级商品分类信息
		return itemCatService.findCacheItemCat(parentId);
	}
	
	
	
	
	
	
}
