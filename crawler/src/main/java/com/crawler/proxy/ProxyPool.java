package com.crawler.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;


public class ProxyPool {

    private static Logger LOG = LoggerFactory.getLogger(ProxyPool.class);
    private static Map<String,Integer> pool = new HashMap<>();


    public ProxyPool(){

    }

    public static void init(){
        KuaidailiParser.KuaidailiLoad();
    }

    public static void addProxy(String url, int port){
        pool.put(url, port);
    }

    public static Map<String,Integer> getProxys(){

        return new HashMap<>(pool);
    }



}
