/*
 * 由系统于2014-09-12 15:02:10生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.BrandGroup;
import com.hehua.item.dao.BrandGroupDAO;
import java.util.*;

public class BrandGroupServiceI{

    @javax.inject.Inject
    protected BrandGroupDAO dao;

    public BrandGroup getBrandGroupById(long id){
        return dao.getBrandGroupById(id);
    }
    
    public boolean hasBrandGroupWithId(long id){
        return dao.hasBrandGroupWithId(id)!=null;
    }
    
    public List<BrandGroup> getAllBrandGroup(){
        return dao.getAllBrandGroup();
    }
    
    public boolean createBrandGroup(BrandGroup ent){
        return dao.createBrandGroup(ent)==1;
    }
    
    public boolean updateBrandGroupById(BrandGroup ent){
        return dao.updateBrandGroupById(ent)==1;
    }
    
    public boolean deleteBrandGroupById(long id){
        return dao.deleteBrandGroupById(id)==1;
    }
    
}
