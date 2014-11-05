/*
 * 由系统于2014-08-08 01:02:14生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemComment;
import java.util.List;

public interface ItemCommentDAOI{
    @Select("select * from `item_comment` where id=#{id}")
    ItemComment getItemCommentById(long id);
    
    @Select("select 1 from `item_comment` where id=#{id}")
    Integer hasItemCommentWithId(long id);
    
    @Select("select * from `item_comment`")
    List<ItemComment> getAllItemComment();
    
    @Insert("INSERT INTO `item_comment`(`cts`,`uts`,`itemid`,`uid`,`name`,`comment`) VALUES (#{cts},#{uts},#{itemid},#{uid},#{name},#{comment})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemComment(ItemComment ent);
    
    @Update("update `item_comment` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`uid`=#{uid},`name`=#{name},`comment`=#{comment} where id=#{id}")
    int updateItemCommentById(ItemComment ent);
    
    @Delete("delete from `item_comment` where id=#{id}")
    int deleteItemCommentById(long id);
    
}
