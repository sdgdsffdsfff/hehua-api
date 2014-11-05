/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.service;

import org.apache.log4j.Logger;
import com.hehua.item.domain.PropertyValue;
import java.util.*;

@javax.inject.Named
public class PropertyValueService extends PropertyValueServiceI{
    private static final Logger log = Logger.getLogger(PropertyValueService.class);

    public List<PropertyValue> getPropertyValuesByPropertyId(long id){
        return dao.getPropertyValuesByPropertyId(id);
    }

}
