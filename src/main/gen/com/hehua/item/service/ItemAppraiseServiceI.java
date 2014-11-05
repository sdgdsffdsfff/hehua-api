/*
 * 由系统于2014-08-08 01:02:14生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemAppraise;
import com.hehua.item.dao.ItemAppraiseDAO;
import com.hehua.item.enums.AppraiseStatus;

import java.util.*;

public class ItemAppraiseServiceI{

    @javax.inject.Inject
    protected ItemAppraiseDAO dao;

    public ItemAppraise getItemAppraiseById(long id){
        return dao.getItemAppraiseById(id, AppraiseStatus.AUDIT_PASS.getCode());
    }

    public List<ItemAppraise> getItemAppraiseByItem(int itemId){
        return dao.getItemAppraiseByItemid(Long.valueOf(itemId), AppraiseStatus.AUDIT_PASS.getCode());
    }

    public ItemAppraise getItemAppraiseByOrderId(int orderId){
        return dao.getItemAppraiseByOrderId(Long.valueOf(orderId));
    }
    
    public boolean hasItemAppraiseWithId(long id){
        return dao.hasItemAppraiseWithId(id)!=null;
    }


    public List<ItemAppraise> getAllItemAppraise(){
        return dao.getAllItemAppraise();
    }
    
    public boolean createItemAppraise(ItemAppraise ent){
        return dao.createItemAppraise(ent)==1;
    }
    
    public boolean updateItemAppraiseById(ItemAppraise ent){
        return dao.updateItemAppraiseById(ent)==1;
    }
    
    public boolean deleteItemAppraiseById(long id){
        return dao.deleteItemAppraiseById(id)==1;
    }
    
}
