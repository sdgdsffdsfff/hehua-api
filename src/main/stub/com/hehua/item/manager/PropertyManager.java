/**
 * 
 */
package com.hehua.item.manager;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.Transformers;
import com.hehua.item.dao.PropertyDAO;
import com.hehua.item.dao.PropertyValueDAO;
import com.hehua.item.domain.Property;
import com.hehua.item.domain.PropertyValue;

/**
 * @author zhihua
 *
 */
@Component
public class PropertyManager implements InitializingBean {

    @Autowired
    private PropertyDAO propertyDAO;

    @Autowired
    private PropertyValueDAO propertyValueDAO;

    private Map<Integer, Property> propertiesMap;

    private Map<Integer, PropertyValue> propertyValuesMap;

    @Override
    public void afterPropertiesSet() throws Exception {

        List<Property> allProperty = propertyDAO.getAllProperty();
        List<PropertyValue> allPropertyValue = propertyValueDAO.getAllPropertyValue();

        this.propertiesMap = Collections.unmodifiableMap(Transformers.transformAsOneToOneMap(
                allProperty, Property.idExtractor));
        this.propertyValuesMap = Collections.unmodifiableMap(Transformers.transformAsOneToOneMap(
                allPropertyValue, PropertyValue.idExtractor));

        ListMultimap<Integer, PropertyValue> valuesByPid = Transformers.transformAsListMultimap(
                allPropertyValue, new Function<PropertyValue, Integer>() {

                    @Override
                    public Integer apply(PropertyValue input) {
                        return input.getPropertyid();
                    }

                });

        for (Property property : allProperty) {
            property.setValues(valuesByPid.get(property.getId()));
        }

    }

    public Property getProperty(int pid) {
        return this.propertiesMap.get(pid);
    }

    public PropertyValue getPropertyValue(int pvid) {
        return this.propertyValuesMap.get(pvid);
    }

}
