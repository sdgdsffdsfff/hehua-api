/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemImage;
import java.util.List;

public interface ItemImageDAOI{
    @Select("select * from `item_image` where id=#{id}")
    ItemImage getItemImageById(long id);
    
    @Select("select 1 from `item_image` where id=#{id}")
    Integer hasItemImageWithId(long id);
    
    @Select("select * from `item_image`")
    List<ItemImage> getAllItemImage();
    
    @Insert("INSERT INTO `item_image`(`cts`,`uts`,`itemid`,`imageid`,`imagelocation`,`ismaster`, `groupid`) VALUES (#{cts},#{uts},#{itemid},#{imageid},#{imagelocation},#{ismaster},#{groupid})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemImage(ItemImage ent);
    
    @Update("update `item_image` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`imageid`=#{imageid},`imagelocation`=#{imagelocation},`ismaster`=#{ismaster} where id=#{id}")
    int updateItemImageById(ItemImage ent);

    @Delete("delete from `item_image` where id=#{id}")
    int deleteItemImageById(long id);

    @Update("update `item_image` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`imageid`=#{imageid},`imagelocation`=#{imagelocation},`ismaster`=#{ismaster} where id=#{id}")
    int updateItemImageByGroupId(ItemImage ent);
    
}
