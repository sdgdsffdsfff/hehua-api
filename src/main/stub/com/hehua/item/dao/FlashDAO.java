/*
 * 由系统于2014-08-25 15:22:37生成。
 */

package com.hehua.item.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hehua.item.domain.Flash;

@javax.inject.Named
public interface FlashDAO extends FlashDAOI {

    @Select("select * from `flash` where `status` = 1")
    public List<Flash> getAllOnlineFlashes();

    @Select("select * from `flash` where `itemid` = #{itemid} and `status` = 1")
    public List<Flash> getOnlineFlashesByItemId(@Param("itemid") int itemid);

    @Select("select * from `flash` where `onlinedate` = #{onlinedate} and `itemid` = #{itemid} and `status` = 1")
    public Flash getFlashByOnlineDateAndItemId(@Param("onlinedate") Date onlinedate,
            @Param("itemid") int itemid);

    public List<Flash> getFlashesByIds(@Param("ids") Collection<Integer> ids);

    public List<Flash> getFlashesByItemIds(@Param("ids") Collection<Integer> ids, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Update("update `flash` set `status` = #{status} where `id` = #{id}")
    public int setStatus(@Param("id") int id, @Param("status") int status);

    @Select("select * from `flash` where `onlinedate` = #{onlinedate} and `status` = 1")
    public List<Flash> getSessionOnlineFlashes(@Param("onlinedate") Date onlinedate);

    @Select("select * from `flash` where groupid=#{groupid}")
    Flash getFlashByGroupId(@Param("groupid") int groupid);

}
