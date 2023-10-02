package com.example.zhb.study.demo.day21;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用正则表达式
 * @Author: zhouhb
 * @date: 2022/03/08/15:39
 * @Description:
 */
public class PatternUtil {

    /**
     * 大于等于0，小于等于100的正则表达式
     * @param str
     * @return
     */
    public static boolean check100(String str){
        Pattern pattern = Pattern.compile("100.([0]+)|^100$|^(\\d|[1-9]\\d)(\\.\\d+)*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 大于等于0，小于等于10000的正则表达式
     * @param str
     * @return
     */
    public static boolean check10000(String str){
        Pattern pattern = Pattern.compile("10000.([0]+)|^10000$|^(\\d{1,4})(\\.\\d+)*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(check100("100.00000"));
        System.out.println(check100("0.00000"));
        System.out.println(check100("99.999999"));
        System.out.println(check100("100.000001"));
        System.out.println(check100("101"));
        System.out.println(check100("-10"));

        System.out.println("=============");

        System.out.println(check10000("10000.00000"));
        System.out.println(check10000("0.00000"));
        System.out.println(check10000("9999.999999"));
        System.out.println(check10000("10000.000001"));
        System.out.println(check10000("10001"));
        System.out.println(check10000("-10"));
    }
}
