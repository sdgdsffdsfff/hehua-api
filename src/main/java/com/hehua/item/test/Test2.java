/**
 * 
 */
package com.hehua.item.test;

import java.util.HashSet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hehua.item.dao.ItemDAO;

/**
 * @author zhihua
 *
 */
public class Test2 {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath*:spring/applicationContext*.xml");

        ItemDAO itemService = applicationContext.getBean(ItemDAO.class);

        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(70);
        hashSet.add(71);

        System.out.println(itemService.getItemsByIds(hashSet));

    }
}
