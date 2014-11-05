/*
 * 由系统于2014-08-20 00:04:12生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Gender;
import java.util.List;

public interface GenderDAOI{
    @Select("select * from `gender` where id=#{id}")
    Gender getGenderById(long id);
    
    @Select("select 1 from `gender` where id=#{id}")
    Integer hasGenderWithId(long id);
    
    @Select("select * from `gender`")
    List<Gender> getAllGender();
    
    @Insert("INSERT INTO `gender`(`cts`,`uts`,`name`,`value`) VALUES (#{cts},#{uts},#{name},#{value})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createGender(Gender ent);
    
    @Update("update `gender` set `cts`=#{cts},`uts`=#{uts},`name`=#{name},`value`=#{value} where id=#{id}")
    int updateGenderById(Gender ent);
    
    @Delete("delete from `gender` where id=#{id}")
    int deleteGenderById(long id);
    
}
