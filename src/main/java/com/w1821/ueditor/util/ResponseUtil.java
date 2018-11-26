package com.w1821.ueditor.util;


import com.w1821.ueditor.dto.State;

/**
 *
 */
public class ResponseUtil {

    /**
     * 返回错误信息
     *
     * @param errorMsg 错误信息字符串
     */
    public static String error(String errorMsg) {
        return JsonUtil.toJsonString(new State(errorMsg));
    }

    /**
     * 成功返回
     */
    public static String success(Object obj) {
        return JsonUtil.toJsonString(obj);
    }


}
