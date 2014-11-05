/*
 * 由系统于2014-08-21 22:53:54生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.ItemDetail;
import java.util.List;

public interface ItemDetailDAOI{
    @Select("select * from `item_detail` where id=#{id}")
    ItemDetail getItemDetailById(long id);
    
    @Select("select 1 from `item_detail` where id=#{id}")
    Integer hasItemDetailWithId(long id);
    
    @Select("select * from `item_detail` where `itemid`=#{itemid}")
    ItemDetail getItemDetailByItemid(@Param("itemid")long itemid);
    
    @Select("select 1 from `item_detail` where `itemid`=#{itemid}")
    Integer hasItemDetailWithItemid(@Param("itemid")long itemid);
    
    @Select("select * from `item_detail`")
    List<ItemDetail> getAllItemDetail();
    
    @Insert("INSERT INTO `item_detail`(`cts`,`uts`,`itemid`,`brandid`,`image`,`images`,`desc`,`purchaseid`,`warehouseid`,`genderid`, `crowedid`, `materialid`, `meta`) VALUES (#{cts},#{uts},#{itemid},#{brandid},#{image},#{images},#{desc},#{purchaseid},#{warehouseid},#{genderid},#{crowedid},#{materialid},#{meta})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createItemDetail(ItemDetail ent);
    
    @Update("update `item_detail` set `cts`=#{cts},`uts`=#{uts},`itemid`=#{itemid},`brandid`=#{brandid},`image`=#{image},`images`=#{images},`desc`=#{desc},`purchaseid`=#{purchaseid},`warehouseid`=#{warehouseid} where id=#{id}")
    int updateItemDetailById(ItemDetail ent);
    
    @Delete("delete from `item_detail` where id=#{id}")
    int deleteItemDetailById(long id);
    
}
