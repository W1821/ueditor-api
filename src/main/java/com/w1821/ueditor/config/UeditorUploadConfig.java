package com.w1821.ueditor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ueditor.upload")
public class UeditorUploadConfig {

    /**
     * 所有上传文件根目录
     */
    private String rootPath;
    /**
     * 图片访问映射前缀
     */
    private String resourcePrefix = "/public/ueditor";
    /**
     * 图片文件映射匹配
     */
    private String resourceHandler = "/public/ueditor/**";


    public String getRootPath() {
        if (rootPath == null) {
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                return "c:\\ueditor";
            } else {
                return "/ueditor";
            }
        }
        return rootPath;
    }
}
