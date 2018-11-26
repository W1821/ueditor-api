package com.w1821.ueditor.main;


import com.w1821.ueditor.config.UeditorConfig;
import com.w1821.ueditor.config.UeditorUploadConfig;
import com.w1821.ueditor.constant.UeditorConstant;
import com.w1821.ueditor.dto.UploadState;
import com.w1821.ueditor.util.FileUtil;
import com.w1821.ueditor.util.JsonUtil;
import com.w1821.ueditor.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * ueditor默认处理器
 *
 * @author saiya
 * @date 2018/5/9 0009
 */
@Component
public class DefaultAction implements Action {

    @Autowired
    private UeditorConfig config;

    @Autowired
    private UeditorUploadConfig ueditorUploadConfig;

    /**
     * 上传图片
     */
    @Override
    public String uploadImage(MultipartFile upfile, HttpServletResponse response, HttpServletRequest request) {
        // 判断文件是否存在
        if (upfile == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检查文件类型
        if (!fileTypeIsConformed(upfile, config.getImageAllowFiles())) {
            return ResponseUtil.error(UeditorConstant.NOT_ALLOW_FILE_TYPE);
        }
        // 检验图片大小
        if (upfile.getSize() > config.getImageMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.saveFile(upfile, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        String originalFileName = upfile.getOriginalFilename();
        return JsonUtil.toJsonString(new UploadState(UeditorConstant.SUCCESS, url, originalFileName, originalFileName));
    }


    /**
     * 上传涂鸦
     *
     * @param base64
     * @param request
     * @return
     */
    @Override
    public String uploadScrawl(String base64, HttpServletRequest request) {
        // 判断文件是否存在
        if (base64 == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检验图片大小
        if (base64.getBytes().length > config.getScrawlMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.writeImageBase64ToFile(base64, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        return JsonUtil.toJsonString(new UploadState(UeditorConstant.SUCCESS, url, "", ""));
    }

    /**
     * 上传视频
     */
    @Override
    public String uploadVideo(MultipartFile upfile, HttpServletRequest request) {
        // 判断文件是否存在
        if (upfile == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检查文件类型
        if (!fileTypeIsConformed(upfile, config.getVideoAllowFiles())) {
            return ResponseUtil.error(UeditorConstant.NOT_ALLOW_FILE_TYPE);
        }
        // 检验图片大小
        if (upfile.getSize() > config.getVideoMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.saveFile(upfile, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        String originalFileName = upfile.getOriginalFilename();
        return JsonUtil.toJsonString(new UploadState(UeditorConstant.SUCCESS, url, originalFileName, originalFileName));
    }

    /**
     * 上传附件
     */
    @Override
    public String uploadFile(MultipartFile upfile, HttpServletRequest request) {
        // 判断文件是否存在
        if (upfile == null) {
            return ResponseUtil.error(UeditorConstant.NOT_FOUND_UPLOAD_DATA);
        }
        // 检查文件类型
        if (!fileTypeIsConformed(upfile, config.getFileAllowFiles())) {
            return ResponseUtil.error(UeditorConstant.NOT_ALLOW_FILE_TYPE);
        }
        // 检验图片大小
        if (upfile.getSize() > config.getFileMaxSize()) {
            return ResponseUtil.error(UeditorConstant.MAX_SIZE);
        }
        // 保存图片
        String url = FileUtil.saveFile(upfile, ueditorUploadConfig.getRootPath(), ueditorUploadConfig.getResourcePrefix());
        if (url == null) {
            return ResponseUtil.error(UeditorConstant.IO_ERROR);
        }
        String originalFileName = upfile.getOriginalFilename();
        return JsonUtil.toJsonString(new UploadState(UeditorConstant.SUCCESS, url, originalFileName, originalFileName));
    }

    /**
     * 这里不支持远程抓图，关闭此功能。这个功能有安全风险
     *
     * @return
     */
    @Override
    public String catchImage(List<String> source) {
        return null;
    }

    @Override
    public String listImage(Integer start, Integer size) {
        return null;
    }

    @Override
    public String listFile(Integer start, Integer size) {
        return null;
    }


    /**
     * 检查文件类型
     */
    private boolean fileTypeIsConformed(MultipartFile upfile, String[] allowFiles) {
        String suffix = FileUtil.getSuffixByFilename(upfile.getOriginalFilename());
        // 检验格式是否正确
        return Arrays.asList(allowFiles).contains(suffix);
    }

}
