/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.Property;
import com.hehua.item.dao.PropertyDAO;
import java.util.*;

public class PropertyServiceI{

    @javax.inject.Inject
    protected PropertyDAO dao;

    public Property getPropertyById(long id){
        return dao.getPropertyById(id);
    }
    
    public boolean hasPropertyWithId(long id){
        return dao.hasPropertyWithId(id)!=null;
    }
    
    public List<Property> getAllProperty(){
        return dao.getAllProperty();
    }
    
    public boolean createProperty(Property ent){
        return dao.createProperty(ent)==1;
    }
    
    public boolean updatePropertyById(Property ent){
        return dao.updatePropertyById(ent)==1;
    }
    
    public boolean deletePropertyById(long id){
        return dao.deletePropertyById(id)==1;
    }
    
}
