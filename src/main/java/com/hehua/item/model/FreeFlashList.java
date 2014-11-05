/**
 * 
 */
package com.hehua.item.model;

import com.hehua.item.domain.FreeFlash;
import org.apache.log4j.Logger;

import java.util.*;

/**
 *
 */
public class FreeFlashList {

    private static Logger log = Logger.getLogger(FreeFlashList.class.getName());

    private List<FreeFlash> freeFlashList;

    private Date reloadData;


    public static void main(String[] args) {


//        FreeFlashSession freeFlashSession = new FreeFlashSession(new Date(), new Date(), new Date());
//        System.out.println(freeFlashSession.isSessionToday());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar = DateUtils.truncate(calendar, Calendar.DATE);
//
//        System.out.println(DateUtils.formatDate(calendar.getTime()));
    }

    public List<FreeFlash> getFreeFlashList() {
        return freeFlashList;
    }

    public void setFreeFlashList(List<FreeFlash> freeFlashList) {
        this.freeFlashList = freeFlashList;
    }

    public Date getReloadData() {
        return reloadData;
    }

    public void setReloadData(Date reloadData) {
        this.reloadData = reloadData;
    }
}
