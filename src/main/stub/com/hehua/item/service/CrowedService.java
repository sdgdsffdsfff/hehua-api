package com.hehua.item.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.hehua.commons.Transformers;
import com.hehua.item.domain.Crowd;

/**
 * Created by hewenjerry on 14-8-24.
 */
@javax.inject.Named
public class CrowedService extends CrowdServiceI {

    public Map<Integer, Crowd> getCrowdsByIds(Collection<Integer> ids) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<Crowd> crowds = dao.getCrowdsByIds(ids);
        return Transformers.transformAsOneToOneMap(crowds, Crowd.idExtractor);
    }
}
