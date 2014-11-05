/*
 * 由系统于2014-09-12 15:02:10生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.BrandgroupItem;
import com.hehua.item.dao.BrandgroupItemDAO;
import java.util.*;

public class BrandgroupItemServiceI{

    @javax.inject.Inject
    protected BrandgroupItemDAO dao;

    public BrandgroupItem getBrandgroupItemById(long id){
        return dao.getBrandgroupItemById(id);
    }
    
    public boolean hasBrandgroupItemWithId(long id){
        return dao.hasBrandgroupItemWithId(id)!=null;
    }
    
    public List<BrandgroupItem> getAllBrandgroupItem(){
        return dao.getAllBrandgroupItem();
    }
    
    public boolean createBrandgroupItem(BrandgroupItem ent){
        return dao.createBrandgroupItem(ent)==1;
    }
    
    public boolean updateBrandgroupItemById(BrandgroupItem ent){
        return dao.updateBrandgroupItemById(ent)==1;
    }
    
    public boolean deleteBrandgroupItemById(long id){
        return dao.deleteBrandgroupItemById(id)==1;
    }
    
}
