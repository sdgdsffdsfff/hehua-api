/*
 * 由系统于2014-09-12 15:02:10生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.BrandgroupItem;
import java.util.List;

public interface BrandgroupItemDAOI{
    @Select("select * from `brandgroup_item` where id=#{id}")
    BrandgroupItem getBrandgroupItemById(long id);
    
    @Select("select 1 from `brandgroup_item` where id=#{id}")
    Integer hasBrandgroupItemWithId(long id);
    
    @Select("select * from `brandgroup_item`")
    List<BrandgroupItem> getAllBrandgroupItem();
    
    @Insert("INSERT INTO `brandgroup_item`(`groupid`,`itemid`,`cts`,`uts`,`status`, `priority`) VALUES (#{groupid},#{itemid},#{cts},#{uts},#{status},#{priority})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createBrandgroupItem(BrandgroupItem ent);
    
    @Update("update `brandgroup_item` set `groupid`=#{groupid},`itemid`=#{itemid},`cts`=#{cts},`uts`=#{uts},`status`=#{status},`priority`=#{priority} where id=#{id}")
    int updateBrandgroupItemById(BrandgroupItem ent);
    
    @Delete("delete from `brandgroup_item` where id=#{id}")
    int deleteBrandgroupItemById(long id);
    
}
