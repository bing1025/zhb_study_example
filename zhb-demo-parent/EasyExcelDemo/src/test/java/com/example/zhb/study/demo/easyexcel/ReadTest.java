package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * * 简单读操作
 *  * 参考 https://www.jianshu.com/p/d1d264c817ef
 *  复杂场景直接看 https://www.yuque.com/easyexcel/doc/read#e3mb9 里面的试验
 * @Author: zhouhb
 * @date: 2021/11/09/17:36
 * @Description:
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.easyexcel")
public class ReadTest {

    // 简单读
    @Test
    public void test1(){
        List<Student_Read> list =  new ArrayList<>();
        /*
        Student_Read 对应的数据结构跟 学生信息表3.xlsx 的结构是对应的
         * EasyExcel 读取 是基于SAX方式
         * 因此在解析时需要传入监听器
         */
        // 第一个参数 为 excel文件路径
        // 读取时的数据类型
        // 监听器
        EasyExcel.read("学生信息表3" + ExcelTypeEnum.XLSX.getValue(), Student_Read.class, new AnalysisEventListener<Student_Read>() {

            // 每读取一行就调用该方法
            @Override
            public void invoke(Student_Read data, AnalysisContext context) {
                list.add(data);
            }

            // 全部读取完成就调用该方法
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                System.out.println("读取完成");
            }
        }).sheet().doRead();

        list.forEach(System.out::println);
    }



}
