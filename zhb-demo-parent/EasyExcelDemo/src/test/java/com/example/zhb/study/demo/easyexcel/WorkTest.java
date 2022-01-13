package com.example.zhb.study.demo.easyexcel;


import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.easyexcel.work.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Author: zhouhb
 * @date: 2021/12/15/16:47
 * @Description:
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootApplication(scanBasePackages = "com.example.zhb.study.demo.easyexcel")
public class WorkTest {

    @Autowired
    private ExcelUtils excelUtils;

    @Test
    public void test1(){

        // 把File转化为MultipartFile过程记录 https://www.cnblogs.com/javasl/p/13834671.html

        File file = new File("学生信息表实战.xlsx");

        FileUtil fileUtil = new FileUtil();
        MultipartFile multipartFile = fileUtil.fileToMultipartFile(file);

        ModelEntityReqAndResDTO modelEntityReqAndResDTO = new ModelEntityReqAndResDTO();
        excelUtils.importExcel(multipartFile, ImportExcelTypeEnum.Model_ONE,modelEntityReqAndResDTO);
        List<ModelEntityDTO> modelEntityDTOList = modelEntityReqAndResDTO.getModelEntityDTOList();
        System.out.println("=======解析完成==========,excel的数据为："+ JSONObject.toJSONString(modelEntityDTOList));
    }
}
