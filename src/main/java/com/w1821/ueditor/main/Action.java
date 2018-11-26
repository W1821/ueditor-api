package com.w1821.ueditor.main;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface Action {


    /**
     * 上传图片
     */
    String uploadImage(MultipartFile upfile, HttpServletResponse response, HttpServletRequest request);

    /**
     * 上传涂鸦
     */
    String uploadScrawl(String base64, HttpServletRequest request);

    /**
     * 上传视频
     */
    String uploadVideo(MultipartFile upfile, HttpServletRequest request);

    /**
     * 上传附件
     */
    String uploadFile(MultipartFile upfile, HttpServletRequest request);

    /**
     * 通过url抓取远程图片，保存本地
     *
     * @param source 远程图片url集合
     */
    String catchImage(List<String> source);

    /**
     * 返回图片集合
     */
    String listImage(Integer start, Integer size);

    /**
     * 返回文件集合
     */
    String listFile(Integer start, Integer size);

}
