/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.Brand;
import com.hehua.item.dao.BrandDAO;
import java.util.*;

public class BrandServiceI{

    @javax.inject.Inject
    protected BrandDAO dao;

    public Brand getBrandById(long id){
        return dao.getBrandById(id);
    }

    public Brand getBrandByName(String name){
        return dao.getBrandByName(name);
    }


    public boolean hasBrandWithId(long id){
        return dao.hasBrandWithId(id)!=null;
    }

    public List<Brand> getAllBrand(){
        return dao.getAllBrand();
    }
    
    public boolean createBrand(Brand ent){
        return dao.createBrand(ent)==1;
    }
    
    public boolean updateBrandById(Brand ent){
        return dao.updateBrandById(ent)==1;
    }
    
    public boolean deleteBrandById(long id){
        return dao.deleteBrandById(id)==1;
    }
    
}
