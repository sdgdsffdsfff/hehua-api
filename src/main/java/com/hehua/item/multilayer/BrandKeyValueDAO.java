/**
 * 
 */
package com.hehua.item.multilayer;

import com.hehua.framework.dao.ReadOnlyKeyValueDAO;
import com.hehua.item.dao.BrandDAO;
import com.hehua.item.domain.Brand;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Component
public class BrandKeyValueDAO extends ReadOnlyKeyValueDAO<Long, Brand> {

    @Autowired
    private BrandDAO brandDAO;

    @Override
    public Brand get(Long key) {
        return brandDAO.getBrandById(key);
    }

    @Override
    public Map<Long, Brand> mget(Collection<Long> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }

        return null;
    }

}
