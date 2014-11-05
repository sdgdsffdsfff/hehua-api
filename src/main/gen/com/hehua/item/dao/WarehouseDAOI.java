/*
 * 由系统于2014-08-20 01:26:16生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Warehouse;
import java.util.List;

public interface WarehouseDAOI{
    @Select("select * from `warehouse` where id=#{id}")
    Warehouse getWarehouseById(long id);
    
    @Select("select 1 from `warehouse` where id=#{id}")
    Integer hasWarehouseWithId(long id);
    
    @Select("select * from `warehouse`")
    List<Warehouse> getAllWarehouse();
    
    @Insert("INSERT INTO `warehouse`(`cts`,`uts`,`name`) VALUES (#{cts},#{uts},#{name})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createWarehouse(Warehouse ent);
    
    @Update("update `warehouse` set `cts`=#{cts},`uts`=#{uts},`name`=#{name} where id=#{id}")
    int updateWarehouseById(Warehouse ent);
    
    @Delete("delete from `warehouse` where id=#{id}")
    int deleteWarehouseById(long id);
    
}
