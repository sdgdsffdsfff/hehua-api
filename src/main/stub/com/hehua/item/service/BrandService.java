/*
 * 由系统于2014-08-18 23:58:23生成。
 */

package com.hehua.item.service;

import com.google.common.base.Function;
import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.framework.dao.DAOUtils;
import com.hehua.framework.dao.MultilayerKeyValueDAO;
import com.hehua.framework.image.ImageRedisCache;
import com.hehua.framework.image.dao.ImageDAO;
import com.hehua.framework.image.dao.ImageKeyValueDAO;
import com.hehua.framework.image.domain.Image;
import com.hehua.framework.jedis.PoolableJedis;
import com.hehua.item.cache.BrandRedisCache;
import com.hehua.item.dao.BrandDAO;
import com.hehua.item.multilayer.BrandKeyValueDAO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.hehua.item.domain.Brand;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandService implements InitializingBean {
    private static final Logger log = Logger.getLogger(BrandService.class);

    @Autowired
    private BrandRedisCache brandRedisCache;

    @Autowired
    private BrandKeyValueDAO brandKeyValueDAO;

    private MultilayerKeyValueDAO<Long, Brand> brandMulDAO;

    private List<Long> allKeyList;

    @Autowired
    private BrandDAO dao;

    public Brand getBrandById(long id){
        return brandMulDAO.get(id);
    }

    public Brand getBrandByName(String name){
        return dao.getBrandByName(name);
    }

    public List<Brand> getAllBrand(){
        if (CollectionUtils.isEmpty(allKeyList)) {
            List<Brand>  brandList = dao.getAllBrand();
            allKeyList = Transformers.transformList(brandList, new Function<Brand, Long>() {
                @Override
                public Long apply(Brand brand) {
                    return brand.getId();
                }
            });

            Map<Long, Brand> retMap = Transformers.transformAsOneToOneMap(brandList, new Function<Brand, Long>() {
                @Override
                public Long apply(Brand brand) {
                    return brand.getId();
                }
            });
            brandRedisCache.mset(retMap);

            return brandList;
        }

        return Transformers.transformAsList(brandRedisCache.mget(allKeyList), new Function<Brand, Brand>() {
            @Override
            public Brand apply(Brand brand) {
                return brand;
            }
        });
    }

    public boolean createBrand(Brand ent){
        try {
            Brand brand = dao.getBrandByName(ent.getName());
            if (brand != null) {
                ent.setId(brand.getId());
                return true;
            }
            boolean retStatus = dao.createBrand(ent) == 1 ? true : false;
            brandMulDAO.set(ent.getId(), ent);
            return retStatus;
        } catch (Exception e) {
            log.error("create data error by brandid=" + ent.getId(), e);
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        brandMulDAO = DAOUtils.multiDAO(brandRedisCache, brandKeyValueDAO);
    }
}
