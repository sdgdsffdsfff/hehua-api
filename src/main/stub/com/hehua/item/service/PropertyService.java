/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.service;

import org.apache.log4j.Logger;
import com.hehua.item.domain.Property;
import java.util.*;

@javax.inject.Named
public class PropertyService extends PropertyServiceI{
    private static final Logger log = Logger.getLogger(PropertyService.class);

    public List<Property> getPropertyByCateId(long id){
        
        return dao.getPropertyByCateId(id);
    }


}
