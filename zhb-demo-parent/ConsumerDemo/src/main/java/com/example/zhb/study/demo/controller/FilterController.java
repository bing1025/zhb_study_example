package com.example.zhb.study.demo.controller;

/**
 * @Author: zhouhb
 * @date: 2022/02/24/16:55
 * @Description:
 */

import com.alibaba.dubbo.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 过滤器控制
 */
@Slf4j
@RestController
@RequestMapping("/filter")
public class FilterController {

    @Value("${file.fiter.type}")
    private String allowFileType;

    /**
     * 过滤参数中包含 html属性的测试
     * http://localhost:8063/filter/checkFileName?fileName=<img src=x onerror=alert(1) />.pdf
     * @param fileName
     * @return
     */
    @RequestMapping(value = "checkFileName",method = RequestMethod.GET)
    public String checkFileName(@RequestParam("fileName") String fileName) throws Exception {
        log.info("========{}",fileName);
        // 校验文件格式 排除 .exe,.sh,.cmd等格式
        if(!checkFileNameType(fileName)){
            throw new Exception("文件格式不合法");
        }
        return "Filter ... ";
    }

    /**
     * 校验文件后缀格式
     * @param fileName
     * @return
     */
    private boolean checkFileNameType(String fileName) {
        if(!StringUtils.isBlank(allowFileType)&&!StringUtils.isBlank(fileName)){
            log.info("allowFileType========{}",allowFileType);
            String[] split = fileName.split("\\.");
            String s = split[split.length - 1];
            if(allowFileType.contains(s)){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * http://localhost:8063/filter/checkNoParam
     * @return
     */
    @RequestMapping(value = "checkNoParam",method = RequestMethod.GET)
    public String checkNoParam(){
        return "checkNoParam ... ";
    }
}
