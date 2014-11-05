/*
 * 由系统于2014-08-10 17:12:48生成。
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemCategory;

@javax.inject.Named
public interface ItemCategoryDAO extends ItemCategoryDAOI{

    @Select("select * from `item_category` where itemid=#{itemid}")
    ItemCategory getItemCategoryByItemId(@Param("itemid") long itemid);
    
}
