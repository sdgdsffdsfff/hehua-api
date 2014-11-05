/*
 * 由系统于2014-08-21 22:53:54生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hehua.item.domain.Item;

public interface ItemDAOI {

    @Select("select * from `item` where id=#{id}")
    Item getItemById(long id);

    @Select("select 1 from `item` where id=#{id}")
    Integer hasItemWithId(long id);

    @Select("select * from `item` where `status`!=0")
    List<Item> getAllItem();

    @Select("select count(id) from `item` where `status`!=0")
    int getItemCount();

    @Select("select * from `item` where `status`!=0 order by cts desc limit #{start},#{size}")
    List<Item> getItemByPage(@Param(value = "start") int start, @Param(value = "size") int size);

    @Insert("INSERT INTO `item`(`cts`,`uts`,`name`,`originprice`,`realprice`,`image`,`images`,`brandid`,`sales`,`units`,`crowedid`,`genderid`,`materialid`,`flashid`,`purchaseid`,`warehouseid`,`desc`,`status`,`postagetype`)"
            + " VALUES"
            + " (#{cts},#{uts},#{name},#{originprice},#{realprice},#{image},#{images},#{brandid},#{sales},#{units},#{crowedid},#{genderid},#{materialid},#{flashid},#{purchaseid},#{warehouseid},#{desc},#{status},#{postagetype})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createItem(Item ent);

    @Update("update `item` set `cts`=#{cts},`uts`=#{uts},`name`=#{name},`originprice`=#{originprice},`realprice`=#{realprice},`image`=#{image}"
            + ",`images`=#{images},`brandid`=#{brandid},`sales`=#{sales},`units`=#{units},`crowedid`=#{crowedid},`genderid`=#{genderid}"
            + ",`materialid`=#{materialid},`flashid`=#{flashid},`purchaseid`=#{purchaseid},`warehouseid`=#{warehouseid},`desc`=#{desc},`status`=#{status},`postagetype`=#{postagetype}"
            + " where id=#{id}")
    int updateItemById(Item ent);

    @Delete("delete from `item` where id=#{id}")
    int deleteItemById(long id);

}
