package com.crawler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtils {

    private static Logger LOG = LoggerFactory.getLogger(FilterUtils.class);

    public static List<String> filter(String regx, String content){

        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(content);

        while(matcher.find()){
            result.add(matcher.group(0));
            LOG.info("解析出内容：{}", matcher.group(0));
        }

        return result;
    }

}
