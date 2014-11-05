/*
 * 由系统于2014-08-21 22:53:55生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemSku;
import java.util.List;

public interface ItemSkuDAOI{
    @Select("select * from `item_sku` where id=#{id}")
    ItemSku getItemSkuById(long id);
    
    @Select("select 1 from `item_sku` where id=#{id}")
    Integer hasItemSkuWithId(long id);
    
    @Select("select * from `item_sku`")
    List<ItemSku> getAllItemSku();
    
    @Insert("INSERT INTO `item_sku`(`cts`,`uts`,`itemid`,`originprice`,`realprice`,`quantity`,`pvids`,`status`,`barcode`) VALUES (#{cts},#{uts},#{itemid},#{originprice},#{realprice},#{quantity},#{pvids},#{status},#{barcode})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemSku(ItemSku ent);
    
    @Update("update `item_sku` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`originprice`=#{originprice},`realprice`=#{realprice},`quantity`=#{quantity},`pvids`=#{pvids},`status`=#{status},`barcode`=#{barcode} where id=#{id}")
    int updateItemSkuById(ItemSku ent);
    
    @Delete("delete from `item_sku` where id=#{id}")
    int deleteItemSkuById(long id);
    
}
