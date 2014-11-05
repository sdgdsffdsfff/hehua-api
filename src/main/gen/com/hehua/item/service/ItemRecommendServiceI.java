/*
 * 由系统于2014-08-08 01:02:14生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemRecommend;
import com.hehua.item.dao.ItemRecommendDAO;
import java.util.*;

public class ItemRecommendServiceI{

    @javax.inject.Inject
    protected ItemRecommendDAO dao;

    public ItemRecommend getItemRecommendById(long id){
        return dao.getItemRecommendById(id);
    }

    public ItemRecommend getItemRecommendByItemIdAndDarenId(int itemid, int darenid){
        return dao.getItemRecommendByitemIdAndDarenId(itemid, darenid);
    }

    public ItemRecommend getItemRecommendByItemIdAndDarenIdReason(int itemid, int darenid, String reason){
        return dao.getItemRecommendByitemIdAndDarenIdReason(itemid, darenid, reason);
    }
    
    public boolean hasItemRecommendWithId(long id){
        return dao.hasItemRecommendWithId(id)!=null;
    }
    
    public List<ItemRecommend> getAllItemRecommend(){
        return dao.getAllItemRecommend();
    }
    
    public boolean createItemRecommend(ItemRecommend ent){
        return dao.createItemRecommend(ent)==1;
    }

    public boolean updateItemRecommendById(ItemRecommend ent){
        return dao.updateItemRecommendById(ent)==1;
    }
    
    public boolean deleteItemRecommendById(long id){
        return dao.deleteItemRecommendById(id)==1;
    }
    
}
