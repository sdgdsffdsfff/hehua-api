/*
 * 由系统于2014-08-20 01:42:29生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Property;
import java.util.List;

public interface PropertyDAOI{
    @Select("select * from `property` where id=#{id}")
    Property getPropertyById(long id);

    @Select("select * from `property` where cateid=#{cateId}")
    List<Property> getPropertyByCateId(long cateId);
    
    @Select("select 1 from `property` where id=#{id}")
    Integer hasPropertyWithId(long id);
    
    @Select("select * from `property`")
    List<Property> getAllProperty();
    
    @Insert("INSERT INTO `property`(`cts`,`uts`,`name`,`parentid`,`cateid`,`status`,`isshow`) VALUES (#{cts},#{uts},#{name},#{parentid},#{cateid},#{status},#{isshow})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createProperty(Property ent);
    
    @Update("update `property` set `cts`=#{cts},`uts`=#{uts},`name`=#{name},`parentid`=#{parentid},`cateid`=#{cateid},`status`=#{status},`isshow`=#{isshow} where id=#{id}")
    int updatePropertyById(Property ent);
    
    @Delete("delete from `property` where id=#{id}")
    int deletePropertyById(long id);
    
}
