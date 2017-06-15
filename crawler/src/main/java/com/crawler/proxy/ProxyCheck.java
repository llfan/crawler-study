package com.crawler.proxy;

import com.crawler.http.HttpCore;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

/**
 * 检测proxy可用性
 *
 */
public class ProxyCheck {

    private final static String URL = "https://bj.lianjia.com"; //默认通过get baidu检测代理可用性
    private HttpCore http;

    public ProxyCheck(){
        http = new HttpCore();
    }

    public boolean check(String proxyUrl, int port){
        CloseableHttpResponse response = http.getByProxy(URL, proxyUrl, port);


        if(response == null){
            return false;
        }

        if(response.getStatusLine().getStatusCode() == 200){
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


    }

    public void close(){
        http.close();
    }

}
