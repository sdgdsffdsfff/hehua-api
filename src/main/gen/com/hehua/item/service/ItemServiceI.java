/*
 * 由系统于2014-08-08 01:02:14生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.Item;
import com.hehua.item.dao.ItemDAO;
import com.hehua.item.domain.ItemRecommend;

import java.util.*;

public class ItemServiceI{

    @javax.inject.Inject
    protected ItemDAO dao;

    public Item getItemById(long id){
        return dao.getItemById(id);
    }
    
    public boolean hasItemWithId(long id){
        return dao.hasItemWithId(id)!=null;
    }
    
    public List<Item> getAllItem(){
        return dao.getAllItem();
    }

    public List<Item> getItemByPage(int startIndex, int pageSize){
        return dao.getItemByPage(startIndex, pageSize);
    }

    public int getItemCount() {
        return dao.getItemCount();
    }

    public boolean createItem(Item ent){
        return dao.createItem(ent)==1;
    }

    public int createItemBy(Item ent){
        return dao.createItem(ent);
    }

    public boolean updateItemById(Item ent){
        return dao.updateItemById(ent)==1;
    }
    
    public boolean deleteItemById(long id){
        return dao.deleteItemById(id)==1;
    }

}
