package com.crawler;

import org.junit.Test;

/**
 * Created by fll on 17-6-9.
 */
public class HttpUtilsTest {

    @Test
    public void testGetData(){

        String content
                = HttpUtils.getData("https://bj.lianjia.com/ershoufang/101101594495.html");

        System.out.println(content);
    }

}
