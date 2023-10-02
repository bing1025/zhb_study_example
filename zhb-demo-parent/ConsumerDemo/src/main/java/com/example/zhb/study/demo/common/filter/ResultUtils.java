package com.example.zhb.study.demo.common.filter;

import com.example.zhb.study.demo.common.constants.ConsumerConstants;

/**
 * @Author: zhouhb
 * @date: 2022/02/24/16:51
 * @Description:
 */
public class ResultUtils {

    public ResultUtils(int doFilterCode, String doFilterException, Object o) {
    }

    /**
     *  跨域白名单
     * @return
     */
    public static ResultUtils doFilter() {
        return new ResultUtils(ConsumerConstants.doFilterCode, ConsumerConstants.doFilterException, null);
    }
}
