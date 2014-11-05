/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hehua.item.domain.ItemRecommend;

@javax.inject.Named
public interface ItemRecommendDAO extends ItemRecommendDAOI {

    @Select("select * from `item_recommend` where `itemid`=#{itemid}")
    List<ItemRecommend> getItemRecommendByItemid(@Param("itemid") long itemid);

    @Delete("delete from `item_recommend` where itemid=#{itemid}")
    int deleteItemRecommendByItemid(@Param("itemid") long itemid);

    //    @Select("select * from `item_recommend` where `itemid` in (#{itemIds})")
    List<ItemRecommend> getItemRecommendsByItemIds(@Param("itemIds") Collection<Integer> itemIds);

    @Select("select * from `item_recommend` where itemid=#{itemid} and uid=#{uid} limit 1")
    ItemRecommend getItemRecommendByitemIdAndDarenId(@Param("itemid") int itemid, @Param("uid") int uid);

    @Select("select * from `item_recommend` where itemid=#{itemid} and uid=#{uid} and reason=#{reason} limit 1")
    ItemRecommend getItemRecommendByitemIdAndDarenIdReason(@Param("itemid") int itemid, @Param("uid") int uid, @Param("reason") String reason);

}
