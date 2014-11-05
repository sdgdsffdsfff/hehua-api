/*
 * 由系统于2014-08-18 23:58:23生成。
 */

package com.hehua.item.dao;

import com.hehua.item.domain.FreeFlash;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@javax.inject.Named
public interface FreeFlashDAO {
//    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`starttime`,`endtime`,`status`,`priority` from `free_flash` where `id`=#{id} limit 1")
//    FreeFlash getFreeFlashByid(@Param("id") int id);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` where `starttime` &lt;= #{onlinedate} and `endtime` &gt;= #{onlinedate} and `status` = 1")
    public List<FreeFlash> getSessionOnlineVoteFlashes(@Param("onlinedate") Date onlinedate);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` where `id`=#{id} limit 1")
    public FreeFlash getFreeFlashById(@Param("id") int id);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` where `id`=#{id} limit 1")
    public FreeFlash getFreeFlashNotContainStatusById(@Param("id") int id);

    @Select("select count(1) from `free_flash`")
    public Integer getFreeFlashCount();

    @Select("select count(1) from `free_flash` where `status` = #{status}")
    public Integer getFreeFlashCountByState(@Param("status") int status);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` limit #{start},#{size}")
    public List<FreeFlash> getFreeFlashByPage(@Param("start") int start, @Param("size") int size);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` where  `status`=#{status} limit #{start},#{size}")
    public List<FreeFlash> getFreeFlashByStateAndPage(@Param("status") int status, @Param("start") int start, @Param("size") int size);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` where `itemid`=#{itemid} limit 1")
    public FreeFlash getFreeFlashByItemid(@Param("itemid") int itemid);

    @Select("select `id`,`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`status`,`priority`,`starttime`,`endtime` from `free_flash` where `itemid`=#{itemid} limit 1")
    public FreeFlash getFreeFlashByItemidNotContainStatus(@Param("itemid") int itemid);

    @Update("update `free_flash` set `applynum`=`applynum` + 1 where id=#{id}")
    int updateFreeFlashApplyNumById(@Param("id") int id);

    @Update("update `free_flash` set `status`=#{status} where id=#{id}")
    int updateFreeFlashApplyStatusById(@Param("status") int status, @Param("id") int id);

    @Insert("INSERT INTO `free_flash`(`onlinedate`,`itemid`,`skuid`,`applynum`,`freequantity`,`starttime`,`endtime`,`status`,`priority`, `createtime`) VALUES (#{onlinedate},#{itemid},#{skuid},#{applynum},#{freequantity},#{starttime},#{endtime},#{status},#{priority},#{createtime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createFreeFlash(FreeFlash freeFlash);

//    @Update("update `free_flash` set `onlinedate`=#{onlinedate},`itemid`=#{itemid},`skuid`=#{skuid},`applynum`=#{applynum},`freequantity`=#{freequantity},`starttime`=#{starttime},`endtime`=#{endtime},`status`=#{status},`priority`=#{priority} where id=#{id}")
//    int updateFreeFlashById(Category ent);
}
