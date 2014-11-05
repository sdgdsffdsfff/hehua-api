/*
 * 由系统于2014-08-08 01:02:14生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.hehua.item.domain.ItemAppraise;

public interface ItemAppraiseDAOI {

    @Select("select * from `item_appraise` where id=#{id} and `status`=#{status}")
    ItemAppraise getItemAppraiseById(@Param("id") long id, @Param("status") int status);

    @Select("select 1 from `item_appraise` where id=#{id}")
    Integer hasItemAppraiseWithId(long id);

    @Select("select * from `item_appraise`")
    List<ItemAppraise> getAllItemAppraise();

    @Insert("INSERT INTO `item_appraise`(`cts`,`uts`,`itemid`,`uid`,`name`,`appraise`,`offical`,`summary`,`score`,`freeflashid`,`freeorderid`, `title`, `status`) VALUES (#{cts},#{uts},#{itemid},#{uid},#{name},#{appraise},#{offical},#{summary},#{score},#{freeflashid},#{freeorderid},#{title},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createItemAppraise(ItemAppraise ent);

    @Update("update `item_appraise` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`uid`=#{uid},`name`=#{name},`appraise`=#{appraise},`offical`=#{offical},`summary`=#{summary},`score`=#{score},`freeflashid`=#{freeflashid},`freeorderid`=#{freeorderid},`title`=#{title},`status`=#{status} where id=#{id}")
    int updateItemAppraiseById(ItemAppraise ent);

    @Update("update `item_appraise` set `status`=#{status} where id=#{id}")
    int updateState(@Param("id")long id ,@Param("status") int status);

    @Delete("delete from `item_appraise` where id=#{id}")
    int deleteItemAppraiseById(long id);

}
