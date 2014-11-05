/*
 * 由系统于2014-08-08 01:02:14生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import com.hehua.item.domain.ItemComment;
import com.hehua.item.dao.ItemCommentDAO;
import java.util.*;

public class ItemCommentServiceI{

    @javax.inject.Inject
    protected ItemCommentDAO dao;

    public ItemComment getItemCommentById(long id){
        return dao.getItemCommentById(id);
    }
    
    public boolean hasItemCommentWithId(long id){
        return dao.hasItemCommentWithId(id)!=null;
    }
    
    public List<ItemComment> getAllItemComment(){
        return dao.getAllItemComment();
    }
    
    public boolean createItemComment(ItemComment ent){
        return dao.createItemComment(ent)==1;
    }
    
    public boolean updateItemCommentById(ItemComment ent){
        return dao.updateItemCommentById(ent)==1;
    }
    
    public boolean deleteItemCommentById(long id){
        return dao.deleteItemCommentById(id)==1;
    }
    
}
