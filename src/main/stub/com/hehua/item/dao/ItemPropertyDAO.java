/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemProperty;

import java.util.List;

@javax.inject.Named
public interface ItemPropertyDAO extends ItemPropertyDAOI{

    @Select("select * from `item_property` where `itemid`=#{itemid}")
    List<ItemProperty> getItemPropertiesByItemid(@Param("itemid") long itemid);

    @Select("select * from `item_property` where `itemid`=#{itemid} and `issku`=#{issku}")
    List<ItemProperty> getItemPropertiesByItemidAndIssku(@Param("itemid") long itemid, @Param("issku") int issku);

    @Delete("delete from `item_property` where itemid=#{itemid}")
    int deleteItemPropertyByItemid(@Param("itemid") long itemid);

    @Select("select * from `item_property` where `itemid`=#{itemid} and `pvid`=#{pvid} and `skuid`=#{skuid}")
    ItemProperty getByItemIdPVIDAndSkuid(@Param("itemid") long itemid, @Param("pvid") int pvid, @Param("skuid") long skuid);
}
