/**
 * 
 */
package com.hehua.item.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.hehua.item.domain.ItemProperty;
import com.hehua.item.domain.ItemSku;
import com.hehua.item.domain.Property;
import com.hehua.item.domain.PropertyValue;
import com.hehua.item.manager.PropertyManager;
import com.hehua.item.model.ItemPropertyView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zhihua
 *
 */
@Component
public class ItemPropertyRender {

    @Autowired
    private PropertyManager propertyManager;

    @Autowired
    private ItemPropertyService itemPropertyService;

    public List<ItemPropertyView> renderProperties(ItemSku itemSku) {
        return renderProperties(itemSku.getPvids());
    }

    public List<ItemPropertyView> renderProperties(String pvids) {

        if (StringUtils.isBlank(pvids)) {
            return Collections.emptyList();
        }

        Map<String, String> split = Splitter.on(Pattern.compile("[;,]")).withKeyValueSeparator(":")
                .split(pvids);
        List<ItemPropertyView> result = new ArrayList<>(split.size());
        for (Map.Entry<String, String> entry : split.entrySet()) {

            int pid = Integer.parseInt(entry.getKey());
            int pvid = Integer.parseInt(entry.getValue());

            Property property = propertyManager.getProperty(pid);
            PropertyValue propertyValue = propertyManager.getPropertyValue(pvid);

            result.add(new ItemPropertyView(property, propertyValue));
        }

        Collections.sort(result);
        return result;
    }

    public String renderPropertiesStr(ItemSku itemSku) {

        StringBuilder stringBuilder = new StringBuilder();
        List<ItemPropertyView> renderProperties = renderProperties(itemSku);
        for (ItemPropertyView propertyView : renderProperties) {
            stringBuilder.append(propertyView.getProperty().getName()).append(": ")
                    .append(propertyView.getPropertyValue().getName());
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    /**
     * 与 renderProperties 方法的区别是，如果有别名则展示别名
     * @return
     */
    public List<ItemPropertyView> renderProp(ItemSku itemSku) {
        String pvids = itemSku.getPvids();
        if (StringUtils.isBlank(pvids)) {
            return Collections.emptyList();
        }
        Map<String, String> split = Splitter.on(Pattern.compile("[;,]")).withKeyValueSeparator(":")
                .split(pvids);
        List<ItemPropertyView> result = new ArrayList<>(split.size());
        for (Map.Entry<String, String> entry : split.entrySet()) {

            int pid = Integer.parseInt(entry.getKey());
            int pvid = Integer.parseInt(entry.getValue());

            Property property = propertyManager.getProperty(pid);
            PropertyValue propertyValue = propertyManager.getPropertyValue(pvid);

            ItemProperty itemProperty = itemPropertyService.getByItemIdPVIDAndSkuid(itemSku.getItemid(), pvid, itemSku.getId());
            if (itemProperty != null) {
                if (!StringUtils.isBlank(itemProperty.getPext())) {
                    JSONObject jo = JSON.parseObject(itemProperty.getPext());
                    property.setName(jo.getString("alias"));
                }
                if (!StringUtils.isBlank(itemProperty.getPvext())) {
                    JSONObject jo = JSON.parseObject(itemProperty.getPvext());
                    propertyValue.setName(jo.getString("alias"));
                }
            }

            result.add(new ItemPropertyView(property, propertyValue));
        }

        Collections.sort(result);
        return result;
    }
}
