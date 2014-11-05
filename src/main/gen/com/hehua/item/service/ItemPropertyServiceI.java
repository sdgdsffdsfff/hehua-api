/*
 * 由系统于2014-08-19 22:41:31生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemProperty;
import com.hehua.item.dao.ItemPropertyDAO;
import java.util.*;

public class ItemPropertyServiceI{

    @javax.inject.Inject
    protected ItemPropertyDAO dao;

    public ItemProperty getItemPropertyById(long id){
        return dao.getItemPropertyById(id);
    }
    
    public boolean hasItemPropertyWithId(long id){
        return dao.hasItemPropertyWithId(id)!=null;
    }
    
    public List<ItemProperty> getAllItemProperty(){
        return dao.getAllItemProperty();
    }
    
    public boolean createItemProperty(ItemProperty ent){
        return dao.createItemProperty(ent)==1;
    }

    public int createItemPropertyBy(ItemProperty ent){
        return dao.createItemProperty(ent);
    }
    
    public boolean updateItemPropertyById(ItemProperty ent){
        return dao.updateItemPropertyById(ent)==1;
    }
    
    public boolean deleteItemPropertyById(long id){
        return dao.deleteItemPropertyById(id)==1;
    }
    
}
