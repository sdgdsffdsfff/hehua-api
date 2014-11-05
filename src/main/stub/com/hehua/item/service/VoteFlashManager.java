/**
 * 
 */
package com.hehua.item.service;

import com.hehua.commons.Transformers;
import com.hehua.commons.time.DateUtils;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.model.FreeFlashList;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hewenjerry
 *
 */
@Component
public class VoteFlashManager {

    private static Logger log = Logger.getLogger(VoteFlashManager.class.getName());

    @Inject
    private VoteFlashSessionLocalCache voteFlashSessionLocalCache;

    public FreeFlashList getTodayVoteFlashSession() {

        FreeFlashList freeFlashList = voteFlashSessionLocalCache.get();

        if (freeFlashList != null
                && freeFlashList.getReloadData() != null
                && !isReload(freeFlashList.getReloadData())) {
            return freeFlashList;
        }

        synchronized (this) {
            freeFlashList = voteFlashSessionLocalCache.get();
            if (freeFlashList != null
                    && freeFlashList.getReloadData() != null
                    && !isReload(freeFlashList.getReloadData()
                    )) {
                return freeFlashList;
            }

            voteFlashSessionLocalCache.reload();
            freeFlashList = voteFlashSessionLocalCache.get();
            freeFlashList.setReloadData(getReloadData());

        }
        return freeFlashList;
    }


    /**
     * 重新reload的条件是必须当前时间和reloadDate时间不在同一天，并且当前时间大于reloadDate
     *
     * @param date
     * @return
     */
    private boolean isReload(Date date) {
        return !DateUtils.isSameToday(date) && DateUtils.isBefore(date);
    }

    public Date getReloadData() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar = DateUtils.truncate(calendar, Calendar.HOUR);
        return calendar.getTime();
    }
}
