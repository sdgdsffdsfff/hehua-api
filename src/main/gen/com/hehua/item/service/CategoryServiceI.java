/*
 * 由系统于2014-08-10 17:04:26生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.Category;
import com.hehua.item.dao.CategoryDAO;
import java.util.*;

public class CategoryServiceI{

    @javax.inject.Inject
    protected CategoryDAO dao;

    public Category getCategoryById(long id){
        return dao.getCategoryById(id);
    }
    
    public boolean hasCategoryWithId(long id){
        return dao.hasCategoryWithId(id)!=null;
    }
    
    public List<Category> getAllCategory(){
        return dao.getAllCategory();
    }
    
    public boolean createCategory(Category ent){
        return dao.createCategory(ent)==1;
    }
    
    public boolean updateCategoryById(Category ent){
        return dao.updateCategoryById(ent)==1;
    }
    
    public boolean deleteCategoryById(long id){
        return dao.deleteCategoryById(id)==1;
    }
    
}
