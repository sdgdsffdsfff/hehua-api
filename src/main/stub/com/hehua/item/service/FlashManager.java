/**
 * 
 */
package com.hehua.item.service;

import com.hehua.item.model.FlashSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author zhihua
 *
 */
@Component
public class FlashManager {

    private static Logger log = Logger.getLogger(FlashManager.class.getName());

    @Inject
    private FlashSessionLocalCache flashSessionCache;

    public FlashSession getTodayFlashSession() {

        FlashSession flashSession = flashSessionCache.get();

        if (flashSession.isSessionToday()) {
            return flashSession;
        }

        synchronized (this) {
            flashSession = flashSessionCache.get();
            if (flashSession.isSessionToday()) {
                return flashSession;
            }
            flashSessionCache.reload();
            flashSession = flashSessionCache.get();
        }
        return flashSession;
    }
}
