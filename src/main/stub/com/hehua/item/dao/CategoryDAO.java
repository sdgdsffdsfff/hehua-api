/*
 * 由系统于2014-08-10 17:04:26生成。
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Category;

import java.util.List;

@javax.inject.Named
public interface CategoryDAO extends CategoryDAOI{

    @Select("select * from `category` where `parentid`=#{parentid}")
    List<Category> getAllCategoryByParentId(@Param("parentid") long parentId);

}
