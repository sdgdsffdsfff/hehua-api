/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hehua.item.domain.ItemStatus;

@javax.inject.Named
public interface ItemStatusDAO extends ItemStatusDAOI {

    @Select("select * from `item_status` where `saletype`=#{saletype} and `starttime`&lt;=#{time} and `endtime`&gt;=#{time} and `status`=#{status}")
    List<ItemStatus> getAllFlashItemStatusesByStatus(@Param("saletype") long saletype,
            @Param("time") Date time, @Param("status") long status);

    @Select("select * from `item_status` where `saletype`=#{saletype} and `status`=#{status}")
    List<ItemStatus> getAllNormalItemStatusesByStatus(@Param("saletype") long saletype,
            @Param("status") long status);

    @Select("select * from `item_status` where `saletype`=#{saletype} and `starttime`&lt;=#{time} and `endtime`&gt;=#{time} and `status`=#{status} limit #{offset}, #{limit}")
    List<ItemStatus> getFlashItemStatusesByStatus(@Param("saletype") long saletype,
            @Param("time") Date time, @Param("status") long status, @Param("offset") int offset,
            @Param("limit") int limit);

    @Select("select * from `item_status` where `saletype`=#{saletype} and `status`=#{status} limit #{offset}, #{limit}")
    List<ItemStatus> getNormalItemStatusesByStatus(@Param("saletype") long saletype,
            @Param("status") long status, @Param("offset") int offset, @Param("limit") int limit);

    @Update("update `item_status` set `uts`=#{uts},`status`=#{status} where itemid=#{itemid}")
    int updateItemStatusByItemid(@Param("uts") Date uts, @Param("status") long status,
            @Param("itemid") long itemid);

    //    @Select("select * from `item_status` where `itemid` in (#{itemIds})")
    List<ItemStatus> getItemStatuesByItemIds(@Param("itemIds") Collection<Integer> itemIds);

}
