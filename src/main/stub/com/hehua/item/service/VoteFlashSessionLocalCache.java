/**
 * 
 */
package com.hehua.item.service;

import com.hehua.framework.localcache.AbstractLocalCache;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.model.FreeFlashList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author zhihua
 *
 */
@Named
public class VoteFlashSessionLocalCache extends AbstractLocalCache<FreeFlashList> {

    public static final String KEY = "voteflashession";

    private static final Logger logger = LoggerFactory.getLogger(VoteFlashSessionLocalCache.class);

    @Inject
    private VoteFlashSessionService voteFlashSessionService;

    @Override
    public String key() {
        return KEY;
    }

    @Override
    public FreeFlashList load() {
        return buildVoteFlashSession();
    }

    public FreeFlashList buildVoteFlashSession() {
        logger.info("reload vote free flash info list starting......");
        List<FreeFlash> freeFlashs =  voteFlashSessionService.getCurrentVoteFlashList();
        // freeFlashList是可以保证不为空集合
        FreeFlashList freeFlashList = new FreeFlashList();
        freeFlashList.setFreeFlashList(freeFlashs);
        logger.info("reload vote free flash info list end......");

        return freeFlashList;
    }
}
