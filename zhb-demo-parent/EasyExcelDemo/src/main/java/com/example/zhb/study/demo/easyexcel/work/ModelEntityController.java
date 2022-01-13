package com.example.zhb.study.demo.easyexcel.work;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: zhouhb
 * @date: 2021/12/15/16:08
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/modelEntity")
public class ModelEntityController {

    @Autowired
    private ExcelUtils excelUtils;

    /**
     * 上传excel
     * @param file
     */
    @PostMapping("/uploadExcel")
    public void uploadExcel(@RequestParam("file") MultipartFile file){
        // 这个是进入方法里面的一个实体类，用来装 需要携带进去以及返回回来的 数据
        ModelEntityReqAndResDTO modelEntityReqAndResDTO = new ModelEntityReqAndResDTO();
        excelUtils.importExcel(file,ImportExcelTypeEnum.Model_ONE,modelEntityReqAndResDTO);
        List<ModelEntityDTO> modelEntityDTOList = modelEntityReqAndResDTO.getModelEntityDTOList();
        log.info("=======解析完成==========,excel的数据为：{}", JSONObject.toJSONString(modelEntityDTOList));
    }


}
