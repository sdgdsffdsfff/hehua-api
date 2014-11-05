package com.hehua.item;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesheng on 14-10-27.
 */
public class TestListPage extends TestCase  {
    @Test
    public void testPage() {
        List<String> list = new ArrayList<>();
        list.add("i");
        list.add("like");
        list.add("you");
        list.add("and");
        list.add("family");

        int offset = 2, limit=4;
        System.out.println(list.subList(5, list.size()));
    }
}
