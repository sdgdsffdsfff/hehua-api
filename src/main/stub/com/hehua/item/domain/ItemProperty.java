/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ItemProperty extends ItemPropertyI {

    public static final String PROPERTY_ALIAS = "alias";

    public static final String PROPERTY_ID = "pid";

    public static final String PROPERTY_NAME = "pname";

    public static final String PROPERTY_VID = "pvid";

    public static final String PROPERTY_VNAME = "pvname";

    private Map<String, String> pextMap;

    private Map<String, String> pvextMap;

    public void setPropertyExtJSON(JSONObject jsonObject) {
        JSONObject newPObject = new JSONObject(2);
        newPObject.put(PROPERTY_ALIAS, jsonObject.get(PROPERTY_NAME));
        setPext(newPObject.toJSONString());
    }

    public void setPropertyValueExtJSON(JSONObject jsonObject) {
        JSONObject newPVObject = new JSONObject(2);
        newPVObject.put(PROPERTY_ALIAS, jsonObject.get(PROPERTY_VNAME));
        setPvext(newPVObject.toJSONString());
    }

    public void convertPropertyToMap() {
        if (!StringUtils.isEmpty(this.getPext())) {
            JSONObject jsonPObject = (JSONObject) JSON.parse(this.getPext());
            getPextMap().put(PROPERTY_ALIAS, (String) jsonPObject.get(PROPERTY_ALIAS));
        }

        if (!StringUtils.isEmpty(this.getPvext())) {
            JSONObject jsonPVObject = (JSONObject) JSON.parse(this.getPvext());
            getPvextMap().put(PROPERTY_ALIAS, (String) jsonPVObject.get(PROPERTY_ALIAS));
        }
    }

    public Map<String, String> getPextMap() {
        if (pextMap == null) {
            pextMap = new HashMap<String, String>(2);
            if (!StringUtils.isEmpty(this.getPext())) {
                JSONObject jsonPObject = (JSONObject) JSON.parse(this.getPext());
                getPextMap().put(PROPERTY_ALIAS, (String) jsonPObject.get(PROPERTY_ALIAS));
            }
        }

        return pextMap;
    }

    public Map<String, String> getPvextMap() {
        if (pvextMap == null) {
            pvextMap = new HashMap<String, String>(2);
            if (!StringUtils.isEmpty(this.getPvext())) {
                JSONObject jsonPVObject = (JSONObject) JSON.parse(this.getPvext());
                getPvextMap().put(PROPERTY_ALIAS, (String) jsonPVObject.get(PROPERTY_ALIAS));
            }
        }
        return pvextMap;
    }

}
