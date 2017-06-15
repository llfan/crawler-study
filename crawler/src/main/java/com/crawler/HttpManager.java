package com.crawler;


import com.crawler.proxy.ProxyPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class HttpManager implements Runnable{

    private static Logger LOG = LoggerFactory.getLogger(HttpManager.class);

    private volatile boolean stop = false;
    private ExecutorService service;
    private BlockingQueue<String> getQueue;
    private final static int DEPTH = 1000;
    private List<String> proxyIps;
    private List<Integer> proxyPorts;
    private BlockingQueue<String> getResult;

    public void init(){
        service = Executors.newFixedThreadPool(8);
        getQueue = new LinkedBlockingDeque<>(DEPTH);
        getResult = new LinkedBlockingDeque<>(DEPTH);
        proxyIps = new ArrayList<>();
        proxyPorts = new ArrayList<>();

        Map<String,Integer> proxyPools = ProxyPool.getProxys();
        Iterator<Map.Entry<String,Integer>> it = proxyPools.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,Integer> value = it.next();
            proxyIps.add(value.getKey());
            proxyPorts.add(value.getValue());
        }
    }

    @Override
    public void run() {
        int size = proxyIps.size();
        int i = 0;;

        while(!stop){
            i = i%size;

            try {
                final String url = getQueue.take();

                final String proxyIp = proxyIps.get(i);
                final int proxyPort = proxyPorts.get(i);

                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        String content = HttpUtils.getData(proxyIp, proxyPort, url);
                        try {
                            getResult.put(url + content);
                        } catch (InterruptedException e) {
                            LOG.warn(e.getMessage());
                        }
                    }
                });
            } catch (InterruptedException e) {
                LOG.warn(e.getMessage());
                continue;
            }

            i++;

        }

    }

    public void stop(){
        this.stop = true;
    }

    public void httpGet(String url){
        try {
            getQueue.put(url);
        } catch (InterruptedException e) {
            LOG.warn(e.getMessage());
        }
    }

    public String getData(){

        String result = "null";
        try {
            result = getResult.take();
        } catch (InterruptedException e) {
            LOG.warn(e.getMessage());
        }
        return result;
    }


}
