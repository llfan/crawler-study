package com.crawler;

import com.crawler.lianjia.LianjiaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        new LianjiaData("https://bj.lianjia.com/ershoufang/shangdi1/?sug=%E4%B8%8A%E5%9C%B0")
                .start();
    }


}
