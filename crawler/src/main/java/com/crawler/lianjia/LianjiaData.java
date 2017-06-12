package com.crawler.lianjia;

import com.crawler.FilterUtils;
import com.crawler.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LianjiaData {

    private static Logger LOG = LoggerFactory.getLogger(LianjiaData.class);

    private String oriUrl;

    public LianjiaData(String url){
        this.oriUrl = url;
    }

    public void start(){
        LOG.info("开始采集，url： {}", oriUrl);

        getContent(getFirstUrl());
    }



    private List<String> getFirstUrl(){
        String content = HttpUtils.getData(oriUrl);

        String regx = "href=\"https://bj.lianjia.com/ershoufang/(.+?)\"";

        return FilterUtils.filter(regx, content);
    }

    private void getContent(List<String> rootUrl){

        List<String> contents = new ArrayList<>();

        Iterator<String> it =rootUrl.iterator();

        while(it.hasNext()){
            String url = it.next();
            if(url.contains(".html")){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                url = url.substring(url.indexOf("https"), url.indexOf(".html")+5);
                HouseInfoParser.parse(url);
            }else{
                continue;
            }

        }

    }


}
