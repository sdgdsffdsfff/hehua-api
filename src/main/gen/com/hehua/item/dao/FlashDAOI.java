/*
 * 由系统于2014-08-25 15:22:37生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.*;

import com.hehua.item.domain.Flash;

public interface FlashDAOI {

    @Select("select * from `flash` where id=#{id}")
    Flash getFlashById(int id);

    @Select("select * from `flash` where `itemid`=#{itemid} and `onlinedate`=#{onlinedate} and `status`>0")
    Flash getFlashByItemIdAndOnline(@Param("itemid")int itemid, @Param("onlinedate") Date onlinedate);

    @Select("select 1 from `flash` where id=#{id}")
    Integer hasFlashWithId(int id);

    @Select("select * from `flash` order by onlinedate desc")
    List<Flash> getAllFlash();

    @Select("select * from `flash` where `onlinedate`=#{onlinedate}  order by onlinedate desc")
    List<Flash> getFlashsByOnlinedate(Date onlinedate);

    @Insert("INSERT INTO `flash`(`onlinedate`,`itemid`,`starttime`,`endtime`,`status`,`croweds`,`gender`,`priority`,`createtime`, `flashtype`, `groupid`) VALUES (#{onlinedate},#{itemid},#{starttime},#{endtime},#{status},#{croweds},#{gender},#{priority},#{createtime},#{flashtype},#{groupid})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createFlash(Flash ent);

    @Update("update `flash` set `onlinedate`=#{onlinedate},`itemid`=#{itemid},`starttime`=#{starttime},`endtime`=#{endtime},`status`=#{status},`croweds`=#{croweds},`gender`=#{gender},`priority`=#{priority},`createtime`=#{createtime} where id=#{id}")
    int updateFlashById(Flash ent);

    @Delete("delete from `flash` where id=#{id}")
    int deleteFlashById(int id);

}
