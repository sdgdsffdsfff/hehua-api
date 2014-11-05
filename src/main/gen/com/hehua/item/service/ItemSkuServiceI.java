/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemSku;
import com.hehua.item.dao.ItemSkuDAO;
import java.util.*;

public class ItemSkuServiceI{

    @javax.inject.Inject
    protected ItemSkuDAO dao;

    public ItemSku getItemSkuById(long id){
        return dao.getItemSkuById(id);
    }
    
    public boolean hasItemSkuWithId(long id){
        return dao.hasItemSkuWithId(id)!=null;
    }
    
    public List<ItemSku> getAllItemSku(){
        return dao.getAllItemSku();
    }
    
    public boolean createItemSku(ItemSku ent){
        return dao.createItemSku(ent)==1;
    }

    public int createItemSkuBy(ItemSku ent){
        return dao.createItemSku(ent);
    }


    public boolean updateItemSkuById(ItemSku ent){
        return dao.updateItemSkuById(ent)==1;
    }
    
    public boolean deleteItemSkuById(long id){
        return dao.deleteItemSkuById(id)==1;
    }
    
}
