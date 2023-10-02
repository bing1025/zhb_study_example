package com.example.zhb.study.demo.easyexcel.work;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

/**
 * https://www.cnblogs.com/javasl/p/13834671.html
 * 把File转化为MultipartFile过程记录
 * 　　大家都知道在做文件上传的时候，后端直接用MultipartFile类接收就行了，
 * 那么为什么还要考虑把File转化为MultipartFile呢？
 * 我偶然在测试Excel导入的时候用到了，直接用Junit测试，Excel文件放在工程中，
 * 这就需要把本地文件转为File，再转为MultipartFile。
 * 当然仅仅为了测试一个Excel文件导入完全不必这样。哈哈，没错，我就是闲的。
 */
public class FileUtil {

    public MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    private static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }


}