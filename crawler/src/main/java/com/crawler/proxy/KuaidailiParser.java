package com.crawler.proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * http://www.kuaidaili.com 代理解析
 *
 */
public class KuaidailiParser {

    private static Logger LOG = LoggerFactory.getLogger(KuaidailiParser.class);
    private static List<String> urls;
    private final static int pageSize = 10;//抓取10页代理
    private static int all = 0;//总代理数
    private static int canuse = 0;//可用代理数

    private static ProxyCheck check;

    static{
        urls = new ArrayList<>();
        for (int i = 1; i <= pageSize; i++) {
            urls.add("http://www.kuaidaili.com/free/intr/" + i + "/");
        }
    }


    public static void KuaidailiLoad(){
        LOG.info("开始加载检测kuaidaili上的代理");

        check = new ProxyCheck();

        Iterator<String> it = urls.iterator();

        while(it.hasNext()){
            String url = it.next();
            System.out.println(url);
            parse(url);

        }

        LOG.info("共加载{}个代理，其中{}个可用", all, canuse);
        Map<String,Integer> pools = ProxyPool.getProxys();

        Iterator<Map.Entry<String,Integer>> itt = pools.entrySet().iterator();
        while(itt.hasNext()){
            Map.Entry<String,Integer> v = itt.next();
            LOG.info("{}:{}", v.getKey(), v.getValue());
        }
        check.close();

    }

    private static void parse(String url){
        try {
            Document doc = Jsoup.connect(url).get();

            Elements ips = doc.getElementsByAttributeValue("data-title","IP");
            Elements ports = doc.getElementsByAttributeValue("data-title","PORT");

            int size = ips.size();
            int i =0;
            while(i < size){
                String ip = ips.get(i).html();
                int port = Integer.valueOf(ports.get(i).html());

                LOG.info("初始化添加代理，{}:{}", ip, port);

                if(check.check(ip, port)){
                    all++;
                    canuse++;
                    ProxyPool.addProxy(ip, port);
                }else{
                    all++;
                }

                i++;
            }

        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
    }

    public static void main(String[] args) {
        KuaidailiLoad();
    }

}
