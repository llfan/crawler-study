package com.crawler.http;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class HttpCore {

    private static Logger LOG = LoggerFactory.getLogger(HttpCore.class);

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private CloseableHttpResponse httpResponse = null;


    public CloseableHttpResponse get(String url){

//        PoolingHttpClientConnectionManager clientManager
//                = new PoolingHttpClientConnectionManager();
//
//        HttpClients.custom().setConnectionManager(clientManager).

        HttpGet httpGet = new HttpGet(url);

        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
        return httpResponse;
    }

    public CloseableHttpResponse getByProxy(String url, String proxyIp, int proxyPort){
        httpClient = HttpClients.createDefault();
        httpResponse = null;
        HttpGet httpGet = new HttpGet(url);

        HttpHost proxy = new HttpHost(proxyIp, proxyPort);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .setSocketTimeout(2000)
                .setProxy(proxy)
                .build();

        httpGet.setConfig(config);
        try {
            System.out.println("============================");
            httpResponse = httpClient.execute(httpGet);
            System.out.println("++++++++++++++++++++++++++++");
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }

    public void close(){

        LOG.info("close HttpCore");

        if(httpClient != null){
            try {
                httpClient.close();
            } catch (IOException e) {
                LOG.warn(e.getMessage());
            }
        }
        if(httpResponse != null){
            try {
                httpResponse.close();
            } catch (IOException e) {
                LOG.warn(e.getMessage());
            }
        }
    }

}
