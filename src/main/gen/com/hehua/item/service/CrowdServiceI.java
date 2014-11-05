/*
 * 由系统于2014-08-20 00:04:12生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import java.util.List;

import com.hehua.item.dao.CrowdDAO;
import com.hehua.item.domain.Crowd;

public class CrowdServiceI {

    @javax.inject.Inject
    protected CrowdDAO dao;

    public Crowd getCrowdById(long id) {
        return dao.getCrowdById(id);
    }

    public boolean hasCrowdWithId(long id) {
        return dao.hasCrowdWithId(id) != null;
    }

    public List<Crowd> getAllCrowd() {
        return dao.getAllCrowd();
    }

    public boolean createCrowd(Crowd ent) {
        return dao.createCrowd(ent) == 1;
    }

    public boolean updateCrowdById(Crowd ent) {
        return dao.updateCrowdById(ent) == 1;
    }

    public boolean deleteCrowdById(long id) {
        return dao.deleteCrowdById(id) == 1;
    }

}
