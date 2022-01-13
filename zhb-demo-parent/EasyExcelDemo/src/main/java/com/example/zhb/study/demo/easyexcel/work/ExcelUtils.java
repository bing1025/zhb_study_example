package com.example.zhb.study.demo.easyexcel.work;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
     * 导入数据
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


}
