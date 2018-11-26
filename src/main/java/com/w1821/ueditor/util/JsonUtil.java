package com.w1821.ueditor.util;


import com.alibaba.fastjson.JSON;

/**
 * json处理工具
 */
public class JsonUtil {

    /**
     * 对象转json字符串
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

}
