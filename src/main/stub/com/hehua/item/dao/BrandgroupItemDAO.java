/*
 * 由系统于2014-09-12 15:02:10生成。
 */

package com.hehua.item.dao;

import com.hehua.item.domain.Item;
import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.BrandgroupItem;

import java.util.Collection;
import java.util.List;

@javax.inject.Named
public interface BrandgroupItemDAO extends BrandgroupItemDAOI {
    @Select("select * from `brandgroup_item` where groupid=#{groupId} and status=1 limit #{offset},#{limit}")
    public List<BrandgroupItem> getBrandgroupItemByIdAndPage(@Param("groupId") int groupId, @Param("offset") int offset, @Param("limit") int limit);

    @Select("select * from `brandgroup_item` where groupid=#{groupId} and status=#{status}")
    public List<BrandgroupItem> getBrandgroupItemByGroupIdAndStatus(@Param("groupId") int group, @Param("status") int status);

    @Select("select * from `brandgroup_item` where groupid=#{groupId}")
    public List<BrandgroupItem> getBrandgroupItemByGroupId(@Param("groupId") int group);

    @Select("select count(id) from `brandgroup_item` where `itemid`=#{itemid} and `status`!=3")
    public Integer getBrandgroupSizeByItemIdAndStatus(@Param("itemid") long itemId);

    @Select("select * from `brandgroup_item` where itemid=#{itemid}")
    public List<BrandgroupItem> getBrandgroupByItemId(@Param("itemid") long itemId);


    @Select("select count(id) from `brandgroup_item` where groupid=#{groupId} and status=1")
    Integer getBrandGroupItemSizeBy(@Param("groupId") int groupId);

    @Update("update `brandgroup_item` set `status`=#{status} where id=#{id}")
    Integer updateBrandGroupItemStatus(@Param("status") int status, @Param("id") long id);

//    public List<BrandgroupItem> getGroupItemsByBrandGroupIds(@Param("groupIds") Collection<Integer> groupIds);

    @Select("select * from `brandgroup_item` where itemid=#{itemid} and groupid=#{groupid}")
    public BrandgroupItem getBrandgroupByItemIdAndGroupId(@Param("itemid") int itemId, @Param("groupid") long groupid);

}