/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemImage;
import com.hehua.item.dao.ItemImageDAO;
import java.util.*;

public class ItemImageServiceI{

    @javax.inject.Inject
    protected ItemImageDAO dao;

    public ItemImage getItemImageById(long id){
        return dao.getItemImageById(id);
    }
    
    public boolean hasItemImageWithId(long id){
        return dao.hasItemImageWithId(id)!=null;
    }
    
    public List<ItemImage> getAllItemImage(){
        return dao.getAllItemImage();
    }
    
    public boolean createItemImage(ItemImage ent){
        return dao.createItemImage(ent)==1;
    }

    public int createItemImageBy(ItemImage ent){
        return dao.createItemImage(ent);
    }


    public boolean updateItemImageById(ItemImage ent){
        return dao.updateItemImageById(ent)==1;
    }
    
    public boolean deleteItemImageById(long id){
        return dao.deleteItemImageById(id)==1;
    }
    
}
