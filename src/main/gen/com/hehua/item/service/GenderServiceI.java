/*
 * 由系统于2014-08-20 00:04:12生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.Gender;
import com.hehua.item.dao.GenderDAO;
import java.util.*;

public class GenderServiceI{

    @javax.inject.Inject
    protected GenderDAO dao;

    public Gender getGenderById(long id){
        return dao.getGenderById(id);
    }
    
    public boolean hasGenderWithId(long id){
        return dao.hasGenderWithId(id)!=null;
    }
    
    public List<Gender> getAllGender(){
        return dao.getAllGender();
    }
    
    public boolean createGender(Gender ent){
        return dao.createGender(ent)==1;
    }
    
    public boolean updateGenderById(Gender ent){
        return dao.updateGenderById(ent)==1;
    }
    
    public boolean deleteGenderById(long id){
        return dao.deleteGenderById(id)==1;
    }
    
}
