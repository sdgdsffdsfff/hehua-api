/*
 * 由系统于2014-08-10 17:12:48生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemCategory;
import java.util.List;

public interface ItemCategoryDAOI{
    @Select("select * from `item_category` where id=#{id}")
    ItemCategory getItemCategoryById(long id);
    
    @Select("select 1 from `item_category` where id=#{id}")
    Integer hasItemCategoryWithId(long id);
    
    @Select("select * from `item_category`")
    List<ItemCategory> getAllItemCategory();
    
    @Insert("INSERT INTO `item_category`(`cts`,`uts`,`itemid`,`category`) VALUES (#{cts},#{uts},#{itemid},#{category})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemCategory(ItemCategory ent);
    
    @Update("update `item_category` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`category`=#{category} where id=#{id}")
    int updateItemCategoryById(ItemCategory ent);
    
    @Delete("delete from `item_category` where id=#{id}")
    int deleteItemCategoryById(long id);
    
}
