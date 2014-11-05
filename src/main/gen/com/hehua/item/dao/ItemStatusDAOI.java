/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemStatus;
import java.util.List;

public interface ItemStatusDAOI{
    @Select("select * from `item_status` where id=#{id}")
    ItemStatus getItemStatusById(long id);
    
    @Select("select 1 from `item_status` where id=#{id}")
    Integer hasItemStatusWithId(long id);
    
    @Select("select * from `item_status` where `itemid`=#{itemid}")
    ItemStatus getItemStatusByItemid(@Param("itemid")long itemid);
    
    @Select("select 1 from `item_status` where `itemid`=#{itemid}")
    Integer hasItemStatusWithItemid(@Param("itemid")long itemid);
    
    @Select("select * from `item_status`")
    List<ItemStatus> getAllItemStatus();
    
    @Insert("INSERT INTO `item_status`(`cts`,`uts`,`itemid`,`saletype`,`starttime`,`endtime`,`sales`,`units`,`status`,`onlinetime`) VALUES (#{cts},#{uts},#{itemid},#{saletype},#{starttime},#{endtime},#{sales},#{units},#{status},#{onlinetime})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemStatus(ItemStatus ent);
    
    @Update("update `item_status` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`saletype`=#{saletype},`starttime`=#{starttime},`endtime`=#{endtime},`sales`=#{sales},`units`=#{units},`status`=#{status},`onlinetime`=#{onlinetime} where id=#{id}")
    int updateItemStatusById(ItemStatus ent);
    
    @Delete("delete from `item_status` where id=#{id}")
    int deleteItemStatusById(long id);
    
}
