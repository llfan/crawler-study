package com.crawler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fll on 17-6-9.
 */
public class FilterUtilsTest {

    @Test
    public void testFilter(){

        List<String> result = new ArrayList<>();

        String regxS = "href=\"(.+?)\"";
        String contentS
                = HttpUtils.getData("https://bj.lianjia.com/");

        result = FilterUtils.filter(regxS, contentS);

        Iterator<String> it =result.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }

    }

}
