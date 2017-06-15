package com.crawler.proxy.com.crawler;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class HttpUtilsTest {

    @Test
    public void testGetData(){
//        HttpUtils.getData("222.85.39.15", 808,
//                "http://www.baidu.com");

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        HttpHost proxy =new HttpHost("222.85.39.15", 808,"http");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

        httpGet.setConfig(config);

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity()));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
