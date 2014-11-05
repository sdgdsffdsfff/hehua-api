/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.PurchaseAddress;
import com.hehua.item.dao.PurchaseAddressDAO;
import java.util.*;

public class PurchaseAddressServiceI{

    @javax.inject.Inject
    protected PurchaseAddressDAO dao;

    public PurchaseAddress getPurchaseAddressById(long id){
        return dao.getPurchaseAddressById(id);
    }
    
    public boolean hasPurchaseAddressWithId(long id){
        return dao.hasPurchaseAddressWithId(id)!=null;
    }
    
    public List<PurchaseAddress> getAllPurchaseAddress(){
        return dao.getAllPurchaseAddress();
    }
    
    public boolean createPurchaseAddress(PurchaseAddress ent){
        return dao.createPurchaseAddress(ent)==1;
    }
    
    public boolean updatePurchaseAddressById(PurchaseAddress ent){
        return dao.updatePurchaseAddressById(ent)==1;
    }
    
    public boolean deletePurchaseAddressById(long id){
        return dao.deletePurchaseAddressById(id)==1;
    }
    
}
