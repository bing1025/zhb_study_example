package com.example.zhb.study.demo.easyexcel;


import com.alibaba.fastjson.JSONObject;
import com.example.zhb.study.demo.easyexcel.work.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

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


    // /**
    //     * 下载指定模板，模板不需要变化
    //     * 把需要下载的的excel 模板放在resource下面,模板要什么样子自己修改excel就好
    //     * 这个不需要用easyExcel，原生的就能支持
    @Test
    public void test2(){
        excelUtils.downLoadDemo(request, response);
    }


}
