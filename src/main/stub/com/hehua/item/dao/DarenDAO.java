/*
 * 由系统于2014-08-18 23:58:23生成。
 */

package com.hehua.item.dao;

import com.hehua.item.domain.Daren;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.List;

@javax.inject.Named
public interface DarenDAO {
    @Select("select `id`,`userid`,`name`,`nickname`,`location`,`stage`,`title`,`description`,`avatarid`,`gender`,`birthday` from `daren` where `userid`=#{userid} limit 1")
    public Daren getDarenByUserId(@Param("userid") long userid);


    @Select("select * from `daren`")
    public List<Daren> findAllDaren();


    @Insert("INSERT INTO `daren`(`userid`,`name`,`nickname`,`location`,`stage`,`title`,`description`,`avatarid`,`cts`,`uts`,`gender`, `birthday`)"
            + " VALUES"
            + " (#{userid},#{name},#{nickname},#{location},#{stage},#{title},#{description},#{avatarid},#{cts},#{uts},#{gender},#{birthday})")
    int save(Daren  daren);

    @Update("update `daren` set `name`=#{name},`nickname`=#{nickname},`location`=#{location},`stage`=#{stage},`title`=#{title},`description`=#{description},`gender`=#{gender},`birthday`=#{birthday},`avatarid`=#{avatarid},`uts`=NOW()  where id=#{id}")
    int update(Daren daren);

    List<Daren> getDarenListByUserIds(@Param("ids") Collection<Long> ids);
}
