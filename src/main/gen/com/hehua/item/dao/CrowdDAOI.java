/*
 * 由系统于2014-08-20 00:04:12生成，请勿人为进行任何修改！
 */

package com.hehua.item.dao;

import org.apache.ibatis.annotations.*;
import com.hehua.item.domain.Crowd;
import java.util.List;

public interface CrowdDAOI{
    @Select("select * from `crowd` where id=#{id}")
    Crowd getCrowdById(long id);
    
    @Select("select 1 from `crowd` where id=#{id}")
    Integer hasCrowdWithId(long id);
    
    @Select("select * from `crowd`")
    List<Crowd> getAllCrowd();
    
    @Insert("INSERT INTO `crowd`(`cts`,`uts`,`name`,`starttime`,`endtime`) VALUES (#{cts},#{uts},#{name},#{starttime},#{endtime})")
    @Options(useGeneratedKeys = true,keyProperty="id",keyColumn="id")
    int createCrowd(Crowd ent);
    
    @Update("update `crowd` set `cts`=#{cts},`uts`=#{uts},`name`=#{name},`starttime`=#{starttime},`endtime`=#{endtime} where id=#{id}")
    int updateCrowdById(Crowd ent);
    
    @Delete("delete from `crowd` where id=#{id}")
    int deleteCrowdById(long id);
    
}
