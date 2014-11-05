/*
 * 由系统于2014-08-18 23:58:23生成。
 */

package com.hehua.item.service;


import com.google.common.base.Function;
import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.item.dao.DarenDAO;
import com.hehua.item.domain.Daren;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DarenService implements InitializingBean {
    private static final Logger log = Logger.getLogger(DarenService.class);

    @Autowired
    private DarenDAO darenDAO;

    public Daren getDarenInfo(long userId) {
        return darenDAO.getDarenByUserId(userId);
    }

    public List<Daren> findAllDarens(){
       return darenDAO.findAllDaren();
    }
    public int addDarens(Daren daren){
       return darenDAO.save(daren);
    }

    public Map<Long, Daren> getDarenListByUserIds(Collection<Long> userIds){
        if (org.apache.commons.collections.CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return Transformers.transformAsOneToOneMap(darenDAO.getDarenListByUserIds(userIds), new Function<Daren, Long>() {
            @Override
            public Long apply(Daren input) {
                return input.getUserid();
            }
        });
    }

    public int updateDaren(Daren daren){
       return darenDAO.update(daren);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
