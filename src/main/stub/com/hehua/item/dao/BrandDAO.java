/*
 * 由系统于2014-08-18 23:58:23生成。
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Brand;

@javax.inject.Named
public interface BrandDAO extends BrandDAOI{
    @Select("select * from `brand` where name=#{name} limit 1")
    Brand getBrandByName(@Param("name") String name);
}
