package com.example.zhb.study.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * 简单写操作
 * 参考 https://www.jianshu.com/p/d1d264c817ef
 * @Author: zhouhb
 * @date: 2021/11/09/17:02
 * @Description:
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.easyexcel")
public class WriteTest {

    public List<Student1> getData() {
        List<Student1> lists = new ArrayList<>();
        for(int i = 0; i <= 10; i++) {
            Student1 student = new Student1();
            student.setId(i + 1);
            student.setName("李四" + i);
            student.setBirthday(new Date());
            student.setSalary(1500.00D);
            lists.add(student);
        }
        return lists;
    }

    @Test
    public void test1(){
        EasyExcel.write("学生信息表1.xlsx", Student1.class).sheet().doWrite(getData());
        System.out.println("==========");
    }


    @Test
    public void test2(){
        EasyExcel.write("学生信息表2.xlsx", Student2.class).sheet().doWrite(getData());
        System.out.println("==========");
    }

    @Test
    public void test3(){
        EasyExcel.write("学生信息表3.xlsx", Student3.class).sheet().doWrite(getData());
        System.out.println("==========");
    }

    @Test
    public void test4(){
        EasyExcel.write("学生信息表4.xlsx", Student4.class).sheet().doWrite(getData());
        System.out.println("==========");
    }

    @Test
    public void test5(){
        EasyExcel.write("学生信息表5.xlsx", Student5.class)
                // 自适应宽度，但是这个不是特别精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet()
                .doWrite(getData());
        System.out.println("==========");
    }

    // 指定写 -- 指定写需要动态指定哪些输出，哪些不输出
    @Test
    public void test6(){
        // 设置 要导出列的属性名
        // 必须要跟类型的属性名保持一致
        Set<String> set = new HashSet<>();
        set.add("id");
        set.add("name");
        set.add("birthday");

        EasyExcel.write("学生信息表6.xlsx", Student6.class)
                .includeColumnFiledNames(set)
                // 自适应宽度，但是这个不是特别精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet()
                .doWrite(getData());
        System.out.println("==========");
    }



}
