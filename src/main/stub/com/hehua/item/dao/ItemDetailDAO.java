/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@javax.inject.Named
public interface ItemDetailDAO extends ItemDetailDAOI {

    @Update("update `item_detail` set `meta`=#{meta} where id=#{id}")
    int updateItemMetaById(@Param("meta") String meta, @Param("id") int id);

    @Update("insert into `item_detail` (`cts`, `uts`, `itemid`, `desc`, `crowedid`, `meta`) values (now(), now(), #{itemid}, '', '', #{meta}) on duplicate key update `meta`=#{meta}")
    public int createOrUpdateItemMeta(@Param("meta") String meta, @Param("itemid") int itemid);
}
