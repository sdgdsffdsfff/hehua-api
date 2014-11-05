/*
 * 由系统于2014-08-10 17:04:26生成。
 */

package com.hehua.item.service;

import com.hehua.item.dao.CategoryDAO;
import org.apache.ibatis.annotations.Select;
import org.apache.log4j.Logger;
import com.hehua.item.domain.Category;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService implements InitializingBean{
    private static final Logger log = Logger.getLogger(CategoryService.class);

    @Autowired
    private CategoryDAO dao;

    public Category getCategoryById(long id){
        return dao.getCategoryById(id);
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

    public List<Category> getAllCategoryByParentId(long id){
        return dao.getAllCategoryByParentId(id);
    }

    public Category getCategoryBy(int cateId) {
       return dao.getById(cateId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
