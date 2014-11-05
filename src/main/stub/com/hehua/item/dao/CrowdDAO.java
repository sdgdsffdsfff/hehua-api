/*
 * 由系统于2014-08-20 00:04:12生成。
 */

package com.hehua.item.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehua.item.domain.Crowd;

@javax.inject.Named
public interface CrowdDAO extends CrowdDAOI {

    public List<Crowd> getCrowdsByIds(@Param("ids") Collection<Integer> ids);
}
