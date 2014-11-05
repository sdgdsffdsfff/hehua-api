/*
 * 由系统于2014-08-10 18:21:04生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemStatus;
import com.hehua.item.dao.ItemStatusDAO;
import java.util.*;

public class ItemStatusServiceI{

    @javax.inject.Inject
    protected ItemStatusDAO dao;

    public ItemStatus getItemStatusById(long id){
        return dao.getItemStatusById(id);
    }
    
    public boolean hasItemStatusWithId(long id){
        return dao.hasItemStatusWithId(id)!=null;
    }
    
    public ItemStatus getItemStatusByItemid(long itemid){
        return dao.getItemStatusByItemid(itemid);
    }
    
    public boolean hasItemStatusWithItemid(long itemid){
        return dao.hasItemStatusWithItemid(itemid)!=null;
    }
    
    public List<ItemStatus> getAllItemStatus(){
        return dao.getAllItemStatus();
    }
    
    public boolean createItemStatus(ItemStatus ent){
        return dao.createItemStatus(ent)==1;
    }
    
    public boolean updateItemStatusById(ItemStatus ent){
        return dao.updateItemStatusById(ent)==1;
    }
    
    public boolean deleteItemStatusById(long id){
        return dao.deleteItemStatusById(id)==1;
    }
    
}
