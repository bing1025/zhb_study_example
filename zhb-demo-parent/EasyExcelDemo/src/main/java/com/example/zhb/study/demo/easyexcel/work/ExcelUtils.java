package com.example.zhb.study.demo.easyexcel.work;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目中真正实战 的工具类用法
 * @Author: zhouhb
 * @date: 2021/12/15/15:13
 * @Description:
 */
@Slf4j
@Component
public class ExcelUtils {

    /**
     * 导入数据：进过封装后的写法：工作中使用
     * @param file
     * @param importExcelTypeEnum
     * @param args
     */
    public void importExcel(MultipartFile file, ImportExcelTypeEnum importExcelTypeEnum, Object... args){
        AnalysisEventListener analysisEventListener = getAnalysisEventListenerByEnum(importExcelTypeEnum,args);
        // 在读的时候只需要new DemoDataListener 监听器传入就行了
        try {
            EasyExcel.read(file.getInputStream(), importExcelTypeEnum.getBeanModel(), analysisEventListener)
                    //从第几行开始读取
                    .headRowNumber(importExcelTypeEnum.getHeadCount())
                    .ignoreEmptyRow(Boolean.TRUE)
                    .autoCloseStream(Boolean.TRUE)
                    .password(importExcelTypeEnum.getType())
                    // 需要读取批注 默认不读取
                    .extraRead(CellExtraTypeEnum.COMMENT)
                    // 需要读取超链接 默认不读取
                    .extraRead(CellExtraTypeEnum.HYPERLINK)
                    // 需要读取合并单元格信息 默认不读取
                    .extraRead(CellExtraTypeEnum.MERGE)
                     // 读取第几个sheet页
                    .sheet(null==importExcelTypeEnum.getSheetNumber() ? 0:importExcelTypeEnum.getSheetNumber())
                    .doRead();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }

    /**
     * 获取实例
     * @param importExcelTypeEnum
     * @param args
     * @return
     */
    private AnalysisEventListener getAnalysisEventListenerByEnum(ImportExcelTypeEnum importExcelTypeEnum, Object[] args) {
        Class<? extends AnalysisEventListener> listenerClass = importExcelTypeEnum.getListenerClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
        }

        try {
            Constructor<? extends AnalysisEventListener> constructor = listenerClass.getConstructor(argsClass);
            return constructor.newInstance(args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 导入数据：常规写法，举例demo使用
     * @param is   导入文件输入流
     * @param clazz Excel实体映射类
     * @return
     */
    public static Boolean readExcel(InputStream is, Class clazz){

        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(is);
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(bis, ExcelTypeEnum.XLSX, null, listener);
            excelReader.read(new Sheet(1, 2, clazz));
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 下载指定模板，模板不需要变化
     * 把需要下载的的excel 模板放在resource下面,模板要什么样子自己修改excel就好
     * 这个不需要用easyExcel，原生的就能支持
     * @return
     */
    public void downLoadDemo(HttpServletRequest request, HttpServletResponse response ){
        //指定需要下载的文件路径
        // String downLoadPath ="E:"+File.separator+"upload"+File.separator+"template"+File.separator+"file_template.xlsx";
        String downLoadPath = "excel\\批导模板.xlsx";
        ClassPathResource classPathResource = new ClassPathResource(downLoadPath);

        try (OutputStream outputStream = response.getOutputStream();
             // xlsx格式用这个
             XSSFWorkbook workbook = new XSSFWorkbook(classPathResource.getInputStream());){

            String name="模板下载";
            name = new String(name.getBytes(), "UTF-8");
            response.setHeader("Content-Disposition","attachment; filename=" +name+".xlsx"); // 设置文件名称
            response.setContentType("application/x-download");
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("下载模板"+e.getMessage());
        }
    }


    /**
     * 导出数据
     * @param os 文件输出流
     * @param clazz Excel实体映射类
     * @param data 导出数据
     * @return
     */
    public static Boolean writeExcel(OutputStream os, Class clazz, List<? extends BaseRowModel> data){
        BufferedOutputStream bos= null;
        try {

            InputStream inputStream = FileUtil.getResourcesFileInputStream("excel\\批导模板.xlsx");
            bos = new BufferedOutputStream(os);
            ExcelWriter writer = EasyExcelFactory.getWriterWithTempAndHandler(inputStream, bos, ExcelTypeEnum.XLSX, false,
                    new AfterWriteHandlerImpl());

            //写第一个sheet, sheet1  数据全是List<String>
            Sheet sheet1 = new Sheet(2, 0,clazz);
            writer.write(data, sheet1);
            writer.finish();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
