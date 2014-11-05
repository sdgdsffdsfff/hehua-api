/*
 * 由系统于2014-08-10 18:21:04生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemAttr;
import com.hehua.item.dao.ItemAttrDAO;
import java.util.*;

public class ItemAttrServiceI{

    @javax.inject.Inject
    protected ItemAttrDAO dao;

    public ItemAttr getItemAttrById(long id){
        return dao.getItemAttrById(id);
    }
    
    public boolean hasItemAttrWithId(long id){
        return dao.hasItemAttrWithId(id)!=null;
    }
    
    public ItemAttr getItemAttrByItemid(long itemid){
        return dao.getItemAttrByItemid(itemid);
    }
    
    public boolean hasItemAttrWithItemid(long itemid){
        return dao.hasItemAttrWithItemid(itemid)!=null;
    }
    
    public List<ItemAttr> getAllItemAttr(){
        return dao.getAllItemAttr();
    }
    
    public boolean createItemAttr(ItemAttr ent){
        return dao.createItemAttr(ent)==1;
    }
    
    public boolean updateItemAttrById(ItemAttr ent){
        return dao.updateItemAttrById(ent)==1;
    }
    
    public boolean deleteItemAttrById(long id){
        return dao.deleteItemAttrById(id)==1;
    }
    
}
