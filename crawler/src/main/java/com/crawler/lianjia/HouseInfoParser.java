package com.crawler.lianjia;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HouseInfoParser {


    public static void parse(String html){
        Document doc = null;

        doc = Jsoup.parse(html);


        System.out.println("开始解析：" + doc.html());

        Element content = doc.getElementsByClass("content").get(1);

        String totalPrice = content.getElementsByClass("total").html();
        String unitPrice = content.getElementsByClass("unitPriceValue").html();
        unitPrice = unitPrice.replace("<i>","");
        unitPrice = unitPrice.replace("</i>","");

        String huxing = content.getElementsByClass("houseInfo").get(0)
                .getElementsByClass("room").get(0).getElementsByClass("mainInfo").get(0).html();
        String size = content.getElementsByClass("houseInfo").get(0)
                .getElementsByClass("area").get(0).getElementsByClass("mainInfo").get(0).html();

        Element aroundInfo = content.getElementsByClass("aroundInfo").get(0);
        String xiaoqu = aroundInfo.getElementsByClass("communityName")
                .get(0).getElementsByClass("info").get(0).html();
        Elements areaInfo = aroundInfo.getElementsByClass("areaName")
                .get(0).getElementsByClass("info").get(0).getElementsByTag("a");


        System.out.println("总价：" + totalPrice + "万, 单价：" + unitPrice + ", 户型："
                + huxing + ", 大小：" + size + ", 小区：" + xiaoqu
                + ", 位置：" + areaInfo.get(0).html() + "_" + areaInfo.get(1).html());

    }

}
