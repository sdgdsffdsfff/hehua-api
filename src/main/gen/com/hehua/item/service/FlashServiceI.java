/*
 * 由系统于2014-08-25 15:22:37生成，请勿人为进行任何修改！
 */

package com.hehua.item.service;

import java.util.Date;
import java.util.List;

import com.hehua.item.dao.FlashDAO;
import com.hehua.item.domain.Flash;

public class FlashServiceI {

    @javax.inject.Inject
    protected FlashDAO dao;

    public Flash getFlashById(int id) {
        return dao.getFlashById(id);
    }

    public boolean hasFlashWithId(int id) {
        return dao.hasFlashWithId(id) != null;
    }

    public List<Flash> getAllFlash() {
        return dao.getAllFlash();
    }
    public List<Flash> getFlashByonlineDate(Date onlinedate) {
        return dao.getFlashsByOnlinedate(onlinedate);
    }

    public boolean createFlash(Flash ent) {
        return dao.createFlash(ent) == 1;
    }

    public boolean updateFlashById(Flash ent) {
        return dao.updateFlashById(ent) == 1;
    }

    public boolean deleteFlashById(int id) {
        return dao.deleteFlashById(id) == 1;
    }

}
