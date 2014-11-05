/*
 * 由系统于2014-08-20 01:26:16生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.Warehouse;
import com.hehua.item.dao.WarehouseDAO;
import java.util.*;

public class WarehouseServiceI{

    @javax.inject.Inject
    protected WarehouseDAO dao;

    public Warehouse getWarehouseById(long id){
        return dao.getWarehouseById(id);
    }
    
    public boolean hasWarehouseWithId(long id){
        return dao.hasWarehouseWithId(id)!=null;
    }
    
    public List<Warehouse> getAllWarehouse(){
        return dao.getAllWarehouse();
    }
    
    public boolean createWarehouse(Warehouse ent){
        return dao.createWarehouse(ent)==1;
    }
    
    public boolean updateWarehouseById(Warehouse ent){
        return dao.updateWarehouseById(ent)==1;
    }
    
    public boolean deleteWarehouseById(long id){
        return dao.deleteWarehouseById(id)==1;
    }
    
}
