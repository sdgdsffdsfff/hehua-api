/**
 * 
 */
package com.hehua.item.cache;

import com.alibaba.fastjson.JSON;
import com.hehua.framework.cache.AbstractRedisCache;
import com.hehua.framework.jedis.PoolableJedisManager;
import com.hehua.item.domain.Brand;
import com.hehua.item.domain.Category;
import org.springframework.stereotype.Component;

/**
 * hewenjerry
 */
@Component
public class CategoryRedisCache extends AbstractRedisCache<Long, Category> {

    public CategoryRedisCache() {
        super(PoolableJedisManager.getDefaultCacheJedis());
    }

    @Override
    public String buildKey(Long key) {
        return "brand:" + key;
    }

    @Override
    public String encode(Category object) {
        return JSON.toJSONString(object);
    }

    @Override
    public Category decode(String text) {
        return JSON.parseObject(text, Category.class);
    }
}
