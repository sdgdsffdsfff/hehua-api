/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Brand;
import java.util.List;

public interface BrandDAOI{
    @Select("select * from `brand` where id=#{id}")
    Brand getBrandById(long id);
    
    @Select("select 1 from `brand` where id=#{id}")
    Integer hasBrandWithId(long id);


    
    @Select("select * from `brand`")
    List<Brand> getAllBrand();
    
    @Insert("INSERT INTO `brand`(`cts`,`uts`,`name`,`desc`) VALUES (#{cts},#{uts},#{name},#{desc})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createBrand(Brand ent);
    
    @Update("update `brand` set `cts`=#{cts},`uts`=#{uts},`name`=#{name},`desc`=#{desc} where id=#{id}")
    int updateBrandById(Brand ent);
    
    @Delete("delete from `brand` where id=#{id}")
    int deleteBrandById(long id);
    
}
