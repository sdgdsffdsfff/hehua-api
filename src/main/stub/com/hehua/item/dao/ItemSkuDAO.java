/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hehua.item.domain.ItemSku;

@javax.inject.Named
public interface ItemSkuDAO extends ItemSkuDAOI {

    @Select("select * from `item_sku` where `itemid`=#{itemid}")
    List<ItemSku> getItemSkusByItemid(@Param("itemid") long itemid);

    @Delete("delete from `item_sku` where itemid=#{itemid}")
    int deleteItemSkuByItemid(@Param("itemid") long itemid);

    //    @Select("select * from `item_sku` where `itemid` in (#{itemIds})")
    public List<ItemSku> getItemSkusByItemIds(@Param("itemIds") Collection<Integer> itemIds);

    @Update("update `item_sku` set `quantity`=`quantity`+#{quantity} where `id`=#{id}")
    int incrItemSkuQuantityById(@Param("id") long id, @Param("quantity") int quantity);

    @Update("update `item_sku` set `quantity =#{quantity} where `id`=#{id}")
    int setItemSkuQuantityById(@Param("id") long id, @Param("quantity") int quantity);

    @Update("update `item_sku` set `quantity`=`quantity`-#{quantity} where `id`=#{id} and `quantity`>=#{quantity}")
    int decrItemSkuQuantityById(@Param("id") long id, @Param("quantity") int quantity);

}
