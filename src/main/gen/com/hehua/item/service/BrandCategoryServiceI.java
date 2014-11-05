/*
 * 由系统于2014-08-18 23:58:23生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.BrandCategory;
import com.hehua.item.dao.BrandCategoryDAO;
import java.util.*;

public class BrandCategoryServiceI{

    @javax.inject.Inject
    protected BrandCategoryDAO dao;

    public BrandCategory getBrandCategoryById(long id){
        return dao.getBrandCategoryById(id);
    }
    
    public boolean hasBrandCategoryWithId(long id){
        return dao.hasBrandCategoryWithId(id)!=null;
    }
    
    public List<BrandCategory> getAllBrandCategory(){
        return dao.getAllBrandCategory();
    }
    
    public boolean createBrandCategory(BrandCategory ent){
        return dao.createBrandCategory(ent)==1;
    }
    
    public boolean updateBrandCategoryById(BrandCategory ent){
        return dao.updateBrandCategoryById(ent)==1;
    }
    
    public boolean deleteBrandCategoryById(long id){
        return dao.deleteBrandCategoryById(id)==1;
    }
    
}
