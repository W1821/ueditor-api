package com.w1821.ueditor.main;



import com.w1821.ueditor.config.UeditorConfig;
import com.w1821.ueditor.constant.UeditorConstant;
import com.w1821.ueditor.constant.UeditorEnum;
import com.w1821.ueditor.dto.RequestParameter;
import com.w1821.ueditor.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
public class UeditorMain {


    /**
     * ueditor 后台主方法
     */
    public static String main(RequestParameter parameter, Action action, HttpServletRequest request, HttpServletResponse response) {
        // 设置响应头
        setHeader(response);

        // 校验参数
        String callbackName = parameter.getCallbackName();
        if (!validCallbackName(callbackName)) {
            return ResponseUtil.error(UeditorConstant.ILLEGAL_CALLBACK);
        }

        // 统一请求处理
        String jsonData = doAction(parameter, action, request, response);
        // 支持jsonp格式
        if (callbackName != null) {
            return supportJsonp(callbackName, jsonData);
        } else {
            return jsonData;
        }
    }

    /**
     * 设置响应头
     */
    private static void setHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/html");
    }

    /**
     * callback参数验证
     */
    private static boolean validCallbackName(String callbackName) {
        return callbackName == null || callbackName.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }

    /**
     * 执行请求
     */
    private static String doAction(RequestParameter parameter, Action action, HttpServletRequest request, HttpServletResponse response) {
        UeditorEnum.ACTION actionKey = UeditorEnum.ACTION.getActionByValue(parameter.getAction());
        switch (actionKey) {
            case CONFIG:
                // 读取配置
                return ResponseUtil.success(new UeditorConfig());

            case UPLOAD_IMAGE:
                // 图片
                return action.uploadImage(parameter.getUpfile(), response, request);
            case UPLOAD_SCRAWL:
                // 涂鸦
                return action.uploadScrawl(parameter.getScrawlBase64(), request);
            case UPLOAD_VIDEO:
                // 视频
                return action.uploadVideo(parameter.getUpfile(), request);
            case UPLOAD_FILE:
                // 附件
                return action.uploadFile(parameter.getUpfile(), request);

            case CATCH_IMAGE:
                // 远程抓图
                return action.catchImage(parameter.getSource());

            case LIST_IMAGE:
                // 图片列表
                return action.listFile(parameter.getStart(), parameter.getSize());
            case LIST_FILE:
                // 文件列表
                return action.listFile(parameter.getStart(), parameter.getSize());
            default:
                return ResponseUtil.error(UeditorConstant.INVALID_ACTION);
        }
    }

    /**
     * 支持jsonp
     */
    private static String supportJsonp(String callbackName, String resultData) {
        return callbackName + "(" + resultData + ");";
    }


}
