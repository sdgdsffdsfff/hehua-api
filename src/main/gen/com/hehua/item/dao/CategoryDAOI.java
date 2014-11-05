/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Category;
import java.util.List;

public interface CategoryDAOI{
    @Select("select * from `category` where id=#{id}")
    Category getCategoryById(long id);
    
    @Select("select 1 from `category` where id=#{id}")
    Integer hasCategoryWithId(long id);
    
    @Select("select * from `category`")
    List<Category> getAllCategory();
    
    @Insert("INSERT INTO `category`(`cts`,`uts`,`parentid`,`name`,`status`,`sort`) VALUES (#{cts},#{uts},#{parentid},#{name},#{status},#{sort})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createCategory(Category ent);
    
    @Update("update `category` set `cts`=#{cts},`uts`=#{uts},`parentid`=#{parentid},`name`=#{name},`status`=#{status},`sort`=#{sort} where id=#{id}")
    int updateCategoryById(Category ent);
    
    @Delete("delete from `category` where id=#{id}")
    int deleteCategoryById(long id);

    @Select("select `id`, `name`, `cts`, `uts`,`parentid`, `status`, `display` from `category` where `id` = #{id}")
    public Category getById(int id);
}
