/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hehua.item.domain.ItemAppraise;

@javax.inject.Named
public interface ItemAppraiseDAO extends ItemAppraiseDAOI {

    @Select("select * from `item_appraise` where `itemid`=#{itemid} and `status`=#{status}")
    List<ItemAppraise> getItemAppraiseByItemid(@Param("itemid") long itemid, @Param("status") int status);

    @Select("select * from `item_appraise` where `itemid`=#{itemid} and `status`=#{status} limit #{offset}, #{limit}")
    List<ItemAppraise> getItemAppraiseByItemidAndOffsetLimit(@Param("itemid") long itemid,
            @Param("offset") int offset, @Param("limit") int limit, @Param("status") int status);


    @Select("select * from `item_appraise` where `uid`=#{userid} and `status`=#{status} limit #{offset}, #{limit}")
    List<ItemAppraise> getItemAppraiseByUseridAndOffsetLimit(@Param("userid") long userid,
                                                             @Param("offset") int offset, @Param("limit") int limit, @Param("status") int status);

    @Select("select count(1) from `item_appraise` where `itemid`=#{itemid} and `status`=#{status}")
    public int getItemAppraisesCount(@Param("itemid") long itemid,  @Param("status") int status);

    @Select("select count(1) from `item_appraise` where `uid`=#{userid} and `status`=#{status}")
    public int getItemAppraisesByUserCount(@Param("userid") long userid,  @Param("status") int status);

    @Select("select * from `item_appraise` where `uid`=#{userid} and `status`=#{status} limit 1")
    public ItemAppraise getItemAppraisesByUserId(@Param("userid") long userid,  @Param("status") int status);

    @Select("select * from `item_appraise` where `freeorderid`=#{freeorderid}  limit 1")
    ItemAppraise getItemAppraiseByOrderId(@Param("freeorderid") Long freeorderid);
}
