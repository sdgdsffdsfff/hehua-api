/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.BrandCategory;
import java.util.List;

public interface BrandCategoryDAOI{
    @Select("select * from `brand_category` where id=#{id}")
    BrandCategory getBrandCategoryById(long id);
    
    @Select("select 1 from `brand_category` where id=#{id}")
    Integer hasBrandCategoryWithId(long id);
    
    @Select("select * from `brand_category`")
    List<BrandCategory> getAllBrandCategory();
    
    @Insert("INSERT INTO `brand_category`(`cts`,`uts`,`brandid`,`cateid`) VALUES (#{cts},#{uts},#{brandid},#{cateid})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createBrandCategory(BrandCategory ent);
    
    @Update("update `brand_category` set `cts`=#{cts},`uts`=#{uts},`brandid`=#{brandid},`cateid`=#{cateid} where id=#{id}")
    int updateBrandCategoryById(BrandCategory ent);
    
    @Delete("delete from `brand_category` where id=#{id}")
    int deleteBrandCategoryById(long id);
    
}
