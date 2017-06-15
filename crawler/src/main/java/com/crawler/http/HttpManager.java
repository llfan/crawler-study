package com.crawler.http;


import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpManager {

    public void init(){
        PoolingHttpClientConnectionManager clientConnectionManager
                = new PoolingHttpClientConnectionManager();

        HttpClients.custom().setConnectionManager(clientConnectionManager).
    }
}
