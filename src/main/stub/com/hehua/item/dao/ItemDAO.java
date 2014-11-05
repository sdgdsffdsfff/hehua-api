/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hehua.item.domain.Item;
import com.hehua.item.model.ItemLite;

@javax.inject.Named
public interface ItemDAO extends ItemDAOI {

    //    @Select("select * from `item` where `id` in (#{ids})")
    public List<Item> getItemsByIds(@Param("ids") Collection<Integer> ids);

    @Update("update `item` set `status` = #{status} where `id` = #{id}")
    public int setStatus(@Param("id") int id, @Param("status") int status);

    @Update("update `item` set `flashid` = #{flashid} where `id` = #{id}")
    public int setFlash(@Param("id") int id, @Param("flashid") int flashid);

    @Update("update `item` set `sales` = `sales` + #{sales} where `id` = #{id}")
    public int incrSales(@Param("id") int id, @Param("sales") int sales);

    @Update("update `item` set `sales` = #{sales} where `id` = #{id}")
    public int setSales(@Param("id") int id, @Param("sales") int sales);

    @Select("select `id`,`name`,`originprice`,`realprice`,`image`,`sales`,`flashid` from `item` where `id` = #{id}")
    public ItemLite getItemLiteById(@Param("id") int id);

    @Select("select `id` from `item` where `name` = #{name}")
    public Collection<Long> getItemLiteByName(@Param("name") String name);

    public List<ItemLite> getItemLitesByIds(@Param("ids") Collection<Integer> ids);

    List<Item> getItemBySearch(@Param("name") String name, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice,
                               @Param("start") int start, @Param("size") int size, @Param("status") int status);

    Integer getItemBySearchCount(@Param("name") String name, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, @Param("status") int status);

    @Select("select * from `item` where `status` = 1")
    List<Item> getAllOnlineItem();
}
