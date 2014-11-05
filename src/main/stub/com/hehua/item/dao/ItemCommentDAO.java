/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hehua.item.domain.ItemComment;

@javax.inject.Named
public interface ItemCommentDAO extends ItemCommentDAOI {

    @Select("select * from `item_comment` where `itemid`=#{itemid}")
    List<ItemComment> getItemCommentByItemid(@Param("itemid") long itemid);

    @Select("select * from `item_comment` where `itemid`=#{itemid} limit #{offset}, #{limit}")
    List<ItemComment> getItemCommentByItemidAndOffsetAndLimit(@Param("itemid") long itemid,
            @Param("offset") int offset, @Param("limit") int limit);

}
