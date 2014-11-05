/**
 * 
 */
package com.hehua.item.cache;

import com.alibaba.fastjson.JSON;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.framework.cache.AbstractRedisCache;
import com.hehua.framework.jedis.PoolableJedis;
import com.hehua.framework.jedis.PoolableJedisManager;
import com.hehua.item.domain.Brand;
import com.hehua.item.domain.BrandCategory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * hewenjerry
 */
@Component
public class BrandRedisCache extends AbstractRedisCache<Long, Brand> {

    public BrandRedisCache() {
        super(PoolableJedisManager.getDefaultCacheJedis());
    }

    @Override
    public String buildKey(Long key) {
        return "brand:" + key;
    }

    @Override
    public String encode(Brand object) {
        return JSON.toJSONString(object);
    }

    @Override
    public Brand decode(String text) {
        return JSON.parseObject(text, Brand.class);
    }

}
