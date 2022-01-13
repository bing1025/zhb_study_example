package com.example.zhb.study.demo.easyexcel.work;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 *
 * 有个很重要的点   不能被spring管理,要每次读取excel都要new。
 * 这边就会有一个问题：如果UserInfoDataListener中需要用到Spring中的主键怎么办？
 *
 * 这边推荐两种方法注入这个组件
 *     //第一种就是提供一个UserInfoDataListener的构造方法，这个方法提供一个参数是UserInfoDataListener类型
 *     //另外一种方法就是将 UserInfoDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）
 *
 * @Author: zhouhb
 * @date: 2021/12/15/15:31
 * @Description:
 */

@Slf4j
public class ModelEntityDTOListener  extends AnalysisEventListener<ModelEntityDTO> {

    //每次读取100条数据就进行保存操作
    private static final int BATCH_COUNT = 100;
    //由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
    List<ModelEntityDTO> list = new ArrayList<>();

    List<ModelEntityDTO> list1 = new ArrayList<>();

    // 记录第几次进入校验表头的方法里面
    int count = 0;

    /**
     * 交互的对象，用来存储进来的参数或者返回的值
     */
    ModelEntityReqAndResDTO modelEntityReqAndResDTO;

   /* 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
    假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
    private ModelEntityDTOService modelEntityDTOService;
    public ModelEntityDTOListener(ModelEntityDTOService modelEntityDTOService) {
        this.modelEntityDTOService = modelEntityDTOService;
    }*/

    public ModelEntityDTOListener(ModelEntityReqAndResDTO modelEntityReqAndResDTO) {
        this.modelEntityReqAndResDTO = modelEntityReqAndResDTO;
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param modelEntityDTO
     * @param analysisContext
     */
    @Override
    public void invoke(ModelEntityDTO modelEntityDTO, AnalysisContext analysisContext) {

        log.info("解析到一条数据:{}", JSON.toJSONString(modelEntityDTO));
        // 这个不清除，需要返回回去
        list1.add(modelEntityDTO);
        list.add(modelEntityDTO);
        if (list.size() >= BATCH_COUNT) {
            checkData();
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * // 所有数据解析完成了 都会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");

        if(null != modelEntityReqAndResDTO){
            modelEntityReqAndResDTO.setModelEntityDTOList(list1);
        }
    }


    /**
     * 获取表头数据
     * @param headMap 表头信息，定义了从第几行解析后，上面的未解析的全部会调用一下这个地方
     * @param context
     */
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info(String.format("第%d次进入到 invokeHeadMap 方法，标题头内容是%s",count++, JSONObject.toJSONString(headMap)));
        // 加入标题行在解析行上面一个
        Integer titleRow = ImportExcelTypeConstant.MODEL_ONE_HEADCOUNT - 1;
        if(titleRow.equals(count)){
            String[] markTitle = new String[]{"id","姓名","薪水","年龄"};

            if(headMap.size() != markTitle.length){
                // 后期直接抛异常
                log.error("模板不正确");
            }

            int i = 0;
            for (Map.Entry<Integer, String> integerStringEntry : headMap.entrySet()) {
                if(!integerStringEntry.equals(markTitle[i])){
                    log.error("模板不正确");
                }
            }
        }

    }

    // 读取条额外信息:批注、超链接、合并单元格信息等
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                log.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
                        extra.getText());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    log.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
                            extra.getColumnIndex(), extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    log.info(
                            "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
                                    + "内容是:{}",
                            extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                            extra.getLastColumnIndex(), extra.getText());
                } else {
                   // Assert.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                log.info(
                        "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                        extra.getLastColumnIndex());
                break;
            default:
        }
    }

    //在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }


    /**
     * 检查数据 就是按照业务要求对 excel 里面的数据进行校验
     */
    private void checkData() {
    }

    /**
     * 存储数据
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        //保存数据
        //modelEntityDTOService.save(list);
        log.info("存储数据库成功！");
    }


}
