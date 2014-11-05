/**
 * 
 */
package com.hehua.item.service;

import com.hehua.commons.Transformers;
import com.hehua.commons.collection.CollectionUtils;
import com.hehua.item.dao.FreeFlashDAO;
import com.hehua.item.domain.FreeFlash;
import com.hehua.item.model.ItemLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hewenjerry
 *
 */
@Component
public class VoteFlashSessionService {

    @Autowired
    private FreeFlashDAO freeFlashDAO;

    @Autowired
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(VoteFlashSessionService.class);

    public List<FreeFlash> getFreeFlashList() {
        List<FreeFlash>  freeFlashList = freeFlashDAO.getSessionOnlineVoteFlashes(new Date());
        if (CollectionUtils.isEmpty(freeFlashList)) {
            return Collections.emptyList();
        }

        return freeFlashList;
    }

    public List<FreeFlash> getCurrentVoteFlashList() {
        List<FreeFlash> freeFlashList = getFreeFlashList();
        List<Integer> itemIds = Transformers.transformList(freeFlashList, FreeFlash.itemIdExtractor);

        if (CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyList();
        }
        final Map<Integer, ItemLite> itemMaps = itemService.getItemLitesByIds(itemIds);
        for (FreeFlash freeFlash : freeFlashList) {
            freeFlash.setItemLite(itemMaps.get(freeFlash.getItemid()));
        }

        return freeFlashList;
    }

    public FreeFlash getFreeFlash(int freeId) {
        return freeFlashDAO.getFreeFlashById(freeId);
    }

    public FreeFlash getFreeFlashByItemId(int itemId) {
        return freeFlashDAO.getFreeFlashByItemid(itemId);
    }
}
