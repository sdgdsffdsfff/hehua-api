/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.PropertyValue;

import java.util.List;

@javax.inject.Named
public interface PropertyValueDAO extends PropertyValueDAOI{

    @Select("select * from `property_value` where propertyid=#{propertyid}")
    List<PropertyValue> getPropertyValuesByPropertyId(@Param("propertyid") long propertyid);
}
