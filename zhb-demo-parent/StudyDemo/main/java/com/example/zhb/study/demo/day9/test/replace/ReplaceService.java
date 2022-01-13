package com.example.zhb.study.demo.day9.test.replace;

import com.example.zhb.study.demo.day9.enhance.anno.ExtReplaceBean;
import lombok.extern.slf4j.Slf4j;

/**
 * 替换类的写法
 * @Author: zhouhb
 * @date: 2021/10/26/13:53
 * @Description:
 */
@Slf4j
@ExtReplaceBean(classTypes = {OldService.class})
public class ReplaceService extends OldService{

    public String test(){
        log.debug("这是替换的Service的返回");
        return "这是替换的Service的返回";
    }
}
