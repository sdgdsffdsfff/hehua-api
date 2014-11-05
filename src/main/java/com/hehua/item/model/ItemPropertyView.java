/**
 * 
 */
package com.hehua.item.model;

import com.hehua.item.domain.Property;
import com.hehua.item.domain.PropertyValue;

/**
 * @author zhihua
 *
 */
public class ItemPropertyView implements Comparable<ItemPropertyView> {

    private Property property;

    private PropertyValue propertyValue;

    /**
     * @param property
     * @param propertyValue
     */
    public ItemPropertyView(Property property, PropertyValue propertyValue) {
        super();
        this.property = property;
        this.propertyValue = propertyValue;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PropertyValue getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public int compareTo(ItemPropertyView o) {
        return this.property.compareTo(o.getProperty());
    }

}
