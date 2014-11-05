/*
 * 由系统于2014-08-10 17:12:48生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemCategory;
import com.hehua.item.dao.ItemCategoryDAO;
import java.util.*;

public class ItemCategoryServiceI{

    @javax.inject.Inject
    protected ItemCategoryDAO dao;

    public ItemCategory getItemCategoryById(long id){
        return dao.getItemCategoryById(id);
    }
    
    public boolean hasItemCategoryWithId(long id){
        return dao.hasItemCategoryWithId(id)!=null;
    }
    
    public List<ItemCategory> getAllItemCategory(){
        return dao.getAllItemCategory();
    }
    
    public boolean createItemCategory(ItemCategory ent){
        return dao.createItemCategory(ent)==1;
    }
    
    public boolean updateItemCategoryById(ItemCategory ent){
        return dao.updateItemCategoryById(ent)==1;
    }
    
    public boolean deleteItemCategoryById(long id){
        return dao.deleteItemCategoryById(id)==1;
    }


    // TODO:目前ItemId 只是属于一个类目
    public ItemCategory getItemCategoryByItemId(long itemId){
        return dao.getItemCategoryByItemId(itemId);
    }

}
