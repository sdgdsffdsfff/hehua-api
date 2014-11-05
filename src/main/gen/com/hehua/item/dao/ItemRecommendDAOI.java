/*
 * 由系统于2014-08-11 00:23:22生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemRecommend;
import java.util.List;

public interface ItemRecommendDAOI{
    @Select("select * from `item_recommend` where id=#{id}")
    ItemRecommend getItemRecommendById(long id);
    
    @Select("select 1 from `item_recommend` where id=#{id}")
    Integer hasItemRecommendWithId(long id);
    
    @Select("select * from `item_recommend`")
    List<ItemRecommend> getAllItemRecommend();
    
    @Insert("INSERT INTO `item_recommend`(`cts`,`uts`,`itemid`,`uid`,`name`,`type`,`avatar`,`reason`) VALUES (#{cts},#{uts},#{itemid},#{uid},#{name},#{type},#{avatar},#{reason})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemRecommend(ItemRecommend ent);
    
    @Update("update `item_recommend` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`uid`=#{uid},`name`=#{name},`type`=#{type},`avatar`=#{avatar},`reason`=#{reason} where id=#{id}")
    int updateItemRecommendById(ItemRecommend ent);
    
    @Delete("delete from `item_recommend` where id=#{id}")
    int deleteItemRecommendById(long id);
    
}
