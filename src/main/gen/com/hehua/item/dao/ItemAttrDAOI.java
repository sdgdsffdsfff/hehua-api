/*
 * 由系统于2014-08-10 18:21:04生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemAttr;
import java.util.List;

public interface ItemAttrDAOI{
    @Select("select * from `item_attr` where id=#{id}")
    ItemAttr getItemAttrById(long id);
    
    @Select("select 1 from `item_attr` where id=#{id}")
    Integer hasItemAttrWithId(long id);
    
    @Select("select * from `item_attr` where `itemid`=#{itemid}")
    ItemAttr getItemAttrByItemid(@Param("itemid")long itemid);
    
    @Select("select 1 from `item_attr` where `itemid`=#{itemid}")
    Integer hasItemAttrWithItemid(@Param("itemid")long itemid);
    
    @Select("select * from `item_attr`")
    List<ItemAttr> getAllItemAttr();
    
    @Insert("INSERT INTO `item_attr`(`cts`,`uts`,`itemid`,`attributes`) VALUES (#{cts},#{uts},#{itemid},#{attributes})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemAttr(ItemAttr ent);
    
    @Update("update `item_attr` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`attributes`=#{attributes} where id=#{id}")
    int updateItemAttrById(ItemAttr ent);
    
    @Delete("delete from `item_attr` where id=#{id}")
    int deleteItemAttrById(long id);
    
}
