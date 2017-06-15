package com.crawler.lianjia;

import com.crawler.FilterUtils;
import com.crawler.HttpManager;
import com.crawler.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LianjiaData implements Runnable{

    private static Logger LOG = LoggerFactory.getLogger(LianjiaData.class);

    private HttpManager httpManager;
    private String oriUrl;
    private ExecutorService service;

    public LianjiaData(String url){
        this.oriUrl = url;
        service = Executors.newFixedThreadPool(4);
    }



    public void start(){
        LOG.info("开始采集，url： {}", oriUrl);

        httpManager.httpGet(oriUrl);
    }

    @Override
    public void run() {

        service.execute(new Runnable() {
            @Override
            public void run() {
                String content = httpManager.getData();
                if(content.indexOf(oriUrl)>=0){
                    content = content.replace(oriUrl, "");
                    getSpecicalUrl(content);
                }else{
                    content = content.substring(content.indexOf(".html") + 5);
                    HouseInfoParser.parse(content);
                }
            }
        });
    }


    private void getSpecicalUrl(String content){

        String regx = "href=\"https://bj.lianjia.com/ershoufang/(.+?)\"";

        List<String> urlList = FilterUtils.filter(regx, content);

        Iterator<String> it = urlList.iterator();

        while(it.hasNext()){
            String url = it.next();
            if(url.contains(".html")){

                url = url.substring(url.indexOf("https"), url.indexOf(".html")+5);

                httpManager.httpGet(url);
            }else{
                continue;
            }
        }
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
                System.out.println(url);
                HouseInfoParser.parse(url);
            }else{
                continue;
            }

        }

    }


    public HttpManager getHttpManager() {
        return httpManager;
    }

    public void setHttpManager(HttpManager httpManager) {
        this.httpManager = httpManager;
    }


}
