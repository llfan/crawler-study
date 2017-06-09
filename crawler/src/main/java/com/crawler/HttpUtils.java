package com.crawler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {

    private static Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    public static String getData(String url){

        LOG.info("爬取{}上的内容", url);

        BufferedReader reader;
        String content = "";

        try {
            URL oriUrl = new URL(url);
            URLConnection connection = oriUrl.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while((line=reader.readLine())!=null){
                content += line;
            }

        } catch (MalformedURLException e) {
            LOG.warn(e.getMessage());
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }

        return content;
    }


}
