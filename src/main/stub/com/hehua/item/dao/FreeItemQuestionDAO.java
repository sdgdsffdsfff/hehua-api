/*
 * 由系统于2014-08-20 00:04:12生成。
 */

package com.hehua.item.dao;

import com.hehua.item.domain.FreeItemQuestion;
import org.apache.ibatis.annotations.*;

@javax.inject.Named
public interface FreeItemQuestionDAO {

    @Insert("INSERT INTO `free_item_question`(`uid`,`itemid`,`isdesiredbuy`,`isrecommendfriend`) VALUES (#{uid},#{itemid},#{isdesiredbuy},#{isrecommendfriend})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createFreeItemQuestion(FreeItemQuestion ent);

    @Update("update `free_item_question` set `uid`=#{uid},`itemid`=#{itemid},`isdesiredbuy`=#{isdesiredbuy},`isrecommendfriend`=#{isrecommendfriend} where id=#{id}")
    int updateFreeItemQuestionById(FreeItemQuestion ent);

    @Select("select `id`,`uid`,`itemid`,`isdesiredbuy`,`isrecommendfriend` from `free_item_question` where `itemid`=#{itemid} and `uid`=#{userid}")
    FreeItemQuestion getFreeItemQuestionByItemIdAndUserId(@Param("itemid") int itemid, @Param("userid") int userid);
    
}
