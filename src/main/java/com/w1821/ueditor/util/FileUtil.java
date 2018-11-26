package com.w1821.ueditor.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * 文件处理工具类
 */
@SuppressWarnings("ALL")
public class FileUtil {

    public static final String JPG = "jpg";

    public static final String BACKSLASH_REGEX = "\\\\";
    public static final String SLASH = "/";
    public static final String SPOT = ".";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String HORIZONTAL_BAR = "-";

    public static final String BASE64_START_FLAG = "data:";
    public static final Integer BASE64_SPLIT_ARRAY_LENGTH = 2;

    /**
     * base64字符串转化成图片
     */
    public static String writeImageBase64ToFile(String base64ImgData, String rootPath, String resourcePrefix) {
        // UUID文件名
        String uuidFileName = getUUIDFileName();
        // 文件后缀
        String fileSuffix = SPOT + JPG;
        String fileName = uuidFileName + fileSuffix;

        // 根目录路径
        if (!rootPath.endsWith(File.separator)) {
            rootPath = rootPath + File.separator;
        }
        // 相对父目录
        String relativePath = FileUtil.getRelativePath(rootPath);
        // 文件全路径
        String fullPath = rootPath + relativePath + fileName;

        Base64 base64 = new Base64();
        byte[] fileBytes = base64.decode(base64ImgData);
        boolean success = saveFileToDisk(fileBytes, fullPath);
        if (!success) {
            return null;
        }
        // 访问的url
        String fileUrl = resourcePrefix + relativePath + fileName;
        // 转换url的分隔符
        return fileUrl.replaceAll(BACKSLASH_REGEX, SLASH);
    }

    /**
     * 获取文件后缀
     *
     * @param base64Str base64字符串
     * @return 文件后缀
     */
    private static String getImageSuffixByBase64(String base64Str) {
        return base64Str.substring(base64Str.lastIndexOf(SLASH) + 1, base64Str.lastIndexOf(SEMICOLON));
    }

    /**
     * 根据给定的文件名,获取其后缀信息
     *
     * @param filename 文件名
     * @return 文件后缀
     */
    public static String getSuffixByFilename(String filename) {
        return filename.substring(filename.lastIndexOf(SPOT)).toLowerCase();
    }


    public static String saveFile(MultipartFile upfile, String rootPath, String resourcePrefix) {
        // 根目录路径
        if (!rootPath.endsWith(File.separator)) {
            rootPath = rootPath + File.separator;
        }
        // 相对父目录
        String relativePath = FileUtil.getRelativePath(rootPath);
        // 文件后缀
        String fileSuffix = getFileSuffix(upfile.getOriginalFilename());
        // 文件名称
        String fileName = FileUtil.getUUIDFileName() + fileSuffix;
        // 文件全路径
        String fullPath = rootPath + relativePath + fileName;
        byte[] fileBytes;
        try {
            fileBytes = upfile.getBytes();
        } catch (NullPointerException | IOException e) {
            return null;
        }
        // 保存
        boolean success = FileUtil.saveFileToDisk(fileBytes, fullPath);
        if (!success) {
            return null;
        }
        // 访问的url
        String fileUrl = resourcePrefix + relativePath + fileName;
        // 转换url的分隔符
        return fileUrl.replaceAll(BACKSLASH_REGEX, SLASH);
    }

    /**
     * 获取文件名称
     */
    public static String getUUIDFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 上传文件的根目录，不存在就创建
     */
    public static String getRelativePath(String rootPath) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 图片上传的相对路径
        String relativePath = File.separator + format.format(new Date()) + File.separator;
        // 图片上传的完整路径
        String fullPath = rootPath + relativePath;
        File rootPathFile = new File(fullPath);
        if (!rootPathFile.exists() && !rootPathFile.isDirectory()) {
            if (!rootPathFile.mkdirs()) {
                return File.separator;
            }
        }
        return relativePath;
    }

    /**
     * 字节数组写入磁盘
     *
     * @param fileBytes 文件字节
     * @param fullPath  文件存放物理全路径
     * @return 文件访问url
     */
    public static boolean saveFileToDisk(byte[] fileBytes, String fullPath) {
        boolean success = true;
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
            out.write(fileBytes);
            out.flush();
        } catch (IOException e) {
            success = false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * 文件后缀名
     *
     * @param originalFilename 原始文件名
     */
    public static String getFileSuffix(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(SPOT)).toLowerCase();
    }


}
