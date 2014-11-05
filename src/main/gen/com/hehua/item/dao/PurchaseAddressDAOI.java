/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.PurchaseAddress;
import java.util.List;

public interface PurchaseAddressDAOI{
    @Select("select * from `purchase_address` where id=#{id}")
    PurchaseAddress getPurchaseAddressById(long id);
    
    @Select("select 1 from `purchase_address` where id=#{id}")
    Integer hasPurchaseAddressWithId(long id);
    
    @Select("select * from `purchase_address`")
    List<PurchaseAddress> getAllPurchaseAddress();
    
    @Insert("INSERT INTO `purchase_address`(`cts`,`uts`,`name`) VALUES (#{cts},#{uts},#{name})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createPurchaseAddress(PurchaseAddress ent);
    
    @Update("update `purchase_address` set `cts`=#{cts},`uts`=#{uts},`name`=#{name} where id=#{id}")
    int updatePurchaseAddressById(PurchaseAddress ent);
    
    @Delete("delete from `purchase_address` where id=#{id}")
    int deletePurchaseAddressById(long id);
    
}
