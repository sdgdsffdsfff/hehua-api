/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.dao;

import com.hehua.framework.image.domain.Image;
import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemImage;

import java.util.Collection;
import java.util.List;

@javax.inject.Named
public interface ItemImageDAO extends ItemImageDAOI{

    @Select("select * from `item_image` where `itemid`=#{itemid} and `ismaster` != 0")
    ItemImage getMasterItemImageByItemid(@Param("itemid")long itemid);

    @Select("select * from `item_image` where `itemid`=#{itemid}")
    List<ItemImage> getItemImagesByItemid(@Param("itemid")long itemid);

    @Delete("delete from `item_image` where itemid=#{itemid}")
    int deleteItemImageByItemid(@Param("itemid") long itemid);

    public List<ItemImage> getByIds(@Param("ids") Collection<Long> ids);

    @Delete("delete from `item_image` where groupid=#{groupid}")
    int deleteItemImageByGroupId(@Param("groupid") int groupid);

    @Select("select * from `item_image` where groupid=#{groupid} limit 1")
    ItemImage getItemImageByGroupId(@Param("groupid") int groupid);
}
