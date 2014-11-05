/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.PropertyValue;
import java.util.List;

public interface PropertyValueDAOI{
    @Select("select * from `property_value` where id=#{id}")
    PropertyValue getPropertyValueById(long id);
    
    @Select("select 1 from `property_value` where id=#{id}")
    Integer hasPropertyValueWithId(long id);
    
    @Select("select * from `property_value`")
    List<PropertyValue> getAllPropertyValue();
    
    @Insert("INSERT INTO `property_value`(`cts`,`uts`,`propertyid`,`name`,`cateid`,`sort`,`extend`) VALUES (#{cts},#{uts},#{propertyid},#{name},#{cateid},#{sort},#{extend})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createPropertyValue(PropertyValue ent);
    
    @Update("update `property_value` set `cts`=#{cts},`uts`=#{uts},`propertyid`=#{propertyid},`name`=#{name},`cateid`=#{cateid},`sort`=#{sort},`extend`=#{extend} where id=#{id}")
    int updatePropertyValueById(PropertyValue ent);
    
    @Delete("delete from `property_value` where id=#{id}")
    int deletePropertyValueById(long id);
    
}
