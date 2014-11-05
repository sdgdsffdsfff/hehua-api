/*
 * 由系统于2014-09-12 15:02:10生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.BrandGroup;
import java.util.List;

public interface BrandGroupDAOI{
    @Select("select * from `brand_group` where id=#{id}")
    BrandGroup getBrandGroupById(long id);
    
    @Select("select 1 from `brand_group` where id=#{id}")
    Integer hasBrandGroupWithId(long id);
    
    @Select("select * from `brand_group`")
    List<BrandGroup> getAllBrandGroup();
    
    @Insert("INSERT INTO `brand_group`(`imageurl`,`name`,`title`,`desc`,`onlinetime`,`status`,`priority`,`cts`,`uts`,`starttime`,`endtime`) VALUES (#{imageurl},#{name},#{title},#{desc},#{onlinetime},#{status},#{priority},#{cts},#{uts},#{starttime},#{endtime})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createBrandGroup(BrandGroup ent);
    
    @Update("update `brand_group` set `imageurl`=#{imageurl},`name`=#{name},`title`=#{title},`desc`=#{desc},`onlinetime`=#{onlinetime},`status`=#{status},`priority`=#{priority},`cts`=#{cts},`uts`=#{uts}, `starttime`=#{starttime},`endtime`=#{endtime} where id=#{id}")
    int updateBrandGroupById(BrandGroup ent);
    
    @Delete("delete from `brand_group` where id=#{id}")
    int deleteBrandGroupById(long id);
    
}
