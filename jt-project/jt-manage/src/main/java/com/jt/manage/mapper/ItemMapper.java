package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.User;

//定义Mapper接口
public interface ItemMapper extends SysMapper<Item>{
	
	@Select("select count(*) from tb_item")
	int findItemCount();
	
	
	List<Item> findItemListByPage(@Param("start")int start,@Param("rows")int rows);
	
	@Select("select name from tb_item_cat where id= #{itemCatId}")
	String findItemCatNameById(Long itemCatId);
	
	void updateStatus(@Param("ids")Long[] ids,@Param("status") int status);
}
