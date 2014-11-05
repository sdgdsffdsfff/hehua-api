/*
 * 由系统于2014-08-21 23:22:05生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemProperty;
import java.util.List;

public interface ItemPropertyDAOI{
    @Select("select * from `item_property` where id=#{id}")
    ItemProperty getItemPropertyById(long id);
    
    @Select("select 1 from `item_property` where id=#{id}")
    Integer hasItemPropertyWithId(long id);
    
    @Select("select * from `item_property`")
    List<ItemProperty> getAllItemProperty();
    
    @Insert("INSERT INTO `item_property`(`cts`,`uts`,`itemid`,`pid`,`pvid`,`issku`,`skuid`,`sort`,`pext`,`pvext`) VALUES (#{cts},#{uts},#{itemid},#{pid},#{pvid},#{issku},#{skuid},#{sort},#{pext},#{pvext})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemProperty(ItemProperty ent);
    
    @Update("update `item_property` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`pid`=#{pid},`pvid`=#{pvid},`issku`=#{issku},`skuid`=#{skuid},`sort`=#{sort}, `pext`=#{pext}, `pvext`=#{pvext} where id=#{id}")
    int updateItemPropertyById(ItemProperty ent);
    
    @Delete("delete from `item_property` where id=#{id}")
    int deleteItemPropertyById(long id);
    
}
