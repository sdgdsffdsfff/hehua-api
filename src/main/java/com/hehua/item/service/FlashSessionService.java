/**
 * 
 */
package com.hehua.item.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.hehua.item.model.FlashSession;

/**
 * @author zhihua
 *
 */
@Component
public class FlashSessionService {

    public FlashSession currentSession() {
        return getSessionByTime(new Date());
    }

    public FlashSession getSessionByDate(Date date) {
        Calendar sessionCalendar = Calendar.getInstance();
        sessionCalendar.setTime(date);

        Date sessionDate = DateUtils.truncate(sessionCalendar.getTime(), Calendar.DATE);

        Calendar startCalender = DateUtils.truncate(sessionCalendar, Calendar.DATE);
        startCalender.set(Calendar.HOUR_OF_DAY, 10);

        Calendar endCalender = DateUtils.truncate(sessionCalendar, Calendar.DATE);
        endCalender.add(Calendar.DATE, 1);
        endCalender.set(Calendar.HOUR_OF_DAY, 10);

        return new FlashSession(sessionDate, startCalender.getTime(), endCalender.getTime());
    }

    public FlashSession getSessionByTime(Date time) {
        Calendar sessionCalendar = Calendar.getInstance();
        sessionCalendar.setTime(time);
        // 在10点之前
        if (sessionCalendar.get(Calendar.HOUR_OF_DAY) < 10) {
            sessionCalendar.add(Calendar.DATE, -1);
        }

        Date sessionDate = DateUtils.truncate(sessionCalendar.getTime(), Calendar.DATE);
        Calendar startCalender = DateUtils.truncate(sessionCalendar, Calendar.DATE);
        startCalender.set(Calendar.HOUR_OF_DAY, 10);

        Calendar endCalender = DateUtils.truncate(sessionCalendar, Calendar.DATE);
        endCalender.add(Calendar.DATE, 1);
        endCalender.set(Calendar.HOUR_OF_DAY, 10);

        return new FlashSession(sessionDate, startCalender.getTime(), endCalender.getTime());
    }

    public static void main(String[] args) {
        FlashSessionService flashSessionService = new FlashSessionService();
        FlashSession flashSession = flashSessionService.currentSession();
        System.out.println(flashSession.getStartTime());
    }
}
