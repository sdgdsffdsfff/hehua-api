package com.hehua.item.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.hehua.commons.utils.JSONUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hehua.item.service.ItemService;
import com.hehua.item.service.ItemServiceApiProxy;
import com.hehua.item.service.ItemServiceProxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
public class Test {

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "spring/applicationContext-item.xml");

    public <T> T get(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static void main(String[] args) {
//        double cc = (68.12/73.22)*10;
//        String str = String.valueOf(cc);
//        String s1 = str.substring(0, str.indexOf("."));
//
//        double cc2 = (23.12/73.22)*10;
//        String str2 = String.valueOf(cc2);
//        String s2 = str2.substring(0, str2.indexOf("."));
//
//        List<String> list = new ArrayList<String>(2);
//        list.add(s1);
//        list.add(s2);
//        System.out.println("order before:" + list);
//        Collections.sort(list);
//        System.out.println("order after:" + list);
//        Test test = new Test();
//        ItemService itemService = test.get(ItemService.class);
//        ItemServiceApiProxy itemServiceApiProxy = test.get(ItemServiceApiProxy.class);
//        System.out.println(itemServiceApiProxy.getFlashItem(1));
//        System.out.println(itemServiceApiProxy.getItemAppraise(1, 0, 100));
//        System.out.println(itemServiceApiProxy.getItemComment(1, 0, 100));
//        ItemServiceProxy itemServiceProxy = test.get(ItemServiceProxy.class);

//        ListMultimap<String,String> listMultimap = new LinkedListMultimap<String, String>();
        JSONArray jsonArray = (JSONArray) JSON.parse("[\"http://hhimg.oss-cn-beijing.aliyuncs.com/94117888-b8c4-4bbd-8d60-e21d97de761f.jpeg\"]");
        System.out.println(jsonArray.get(0));

        List<String> stringList = new ArrayList<String>();
        stringList.add("hello");
        stringList.add("world");

//        System.out.println(JSONUtils.convertJSONArrayBy(stringList));
//        JSON.parse()

        double realPrice = 20.2;
        double originPrice = 100;
        String tempStr = String.valueOf((realPrice / originPrice) * 10);
        String discountStr = "";
        if (tempStr.indexOf(".") != -1) {
            if (tempStr.indexOf(".0") != -1){
                discountStr = tempStr.substring(0, tempStr.indexOf("."));
            } else {
                discountStr = tempStr.substring(0, tempStr.indexOf(".") + 2);
            }
        } else {
        }
        System.out.println(discountStr);
        System.out.println(Double.valueOf(2));

        String c = String.valueOf((realPrice / originPrice) * 10);
        if (true) {
            System.out.println(Double.valueOf(tempStr.substring(0, c.indexOf("."))));
        }

    }
}
