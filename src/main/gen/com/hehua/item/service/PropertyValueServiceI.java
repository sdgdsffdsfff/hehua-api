/*
 * 由系统于2014-08-18 23:58:24生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.PropertyValue;
import com.hehua.item.dao.PropertyValueDAO;
import java.util.*;

public class PropertyValueServiceI{

    @javax.inject.Inject
    protected PropertyValueDAO dao;

    public PropertyValue getPropertyValueById(long id){
        return dao.getPropertyValueById(id);
    }
    
    public boolean hasPropertyValueWithId(long id){
        return dao.hasPropertyValueWithId(id)!=null;
    }
    
    public List<PropertyValue> getAllPropertyValue(){
        return dao.getAllPropertyValue();
    }
    
    public boolean createPropertyValue(PropertyValue ent){
        return dao.createPropertyValue(ent)==1;
    }
    
    public boolean updatePropertyValueById(PropertyValue ent){
        return dao.updatePropertyValueById(ent)==1;
    }
    
    public boolean deletePropertyValueById(long id){
        return dao.deletePropertyValueById(id)==1;
    }
    
}
