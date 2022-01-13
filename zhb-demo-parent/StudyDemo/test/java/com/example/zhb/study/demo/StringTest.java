package com.example.zhb.study.demo;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhouhb
 * @date: 2022/01/13/10:17
 * @Description:
 */
public class StringTest {

    /// <summary>
    /// 判断字符串是否是数字
    /// </summary>
    /// <param name="str"></param>
    /// <param name="nt"></param>
    /// <returns></returns>
    /// <summary>
    public static boolean IsNumType(String str, String type)
    {
        Pattern pattern = null;
        switch (type)
        {
            case "NonNegativeInt":
                //非负整数（正整数 + 0）
                pattern = Pattern.compile("^[1-9]+[0-9]*$|^0$");
                break;
            case "PositiveInt":
                //正整数
                pattern = Pattern.compile("^[1-9]+[0-9]*$");
                break;
            case "NonPositiveInt":
                //非正整数（负整数 + 0）
                pattern = Pattern.compile("^-[1-9]+[0-9]*$|^0$");
                break;
            case "NegativeInt":
                //负整数
                pattern = Pattern.compile("^^-[1-9]+[0-9]*$");
                break;
            case "Int":
                //整数
                pattern = Pattern.compile("^-?[1-9]+[0-9]*$|^0$");
                break;
//            case "NonNegative":
//                //非负数（正数 + 0）
//                pattern = Pattern.compile("(^(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)|^0$)");
//                break;
//            case "Positive":
//                //正数
//                pattern = Pattern.compile("^(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)");
//                // matchString = "^(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)";
//                break;
//            case "NonPositive":
//                //非正数（负数 + 0）
//                pattern = Pattern.compile("(^-(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)|^0$)");
//                // matchString = "(^-(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)|^0$)";
//                break;
//            case "Negative":
//                //负数
//                pattern = Pattern.compile("^-(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)");
//                //matchString = "^-(0\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\.[0-9]*[0-9]$|[1-9]+[0-9]*$)";
//                break;
            case "zhb":
                //最大五位正数的正则表达式 最多三位小数
                pattern = Pattern.compile("(^0\\.\\d{0,2}[1-9]$)|(^[1-9]\\d{0,4}\\.\\d*[1-9]$)|(^[1-9]\\d{0,4})");
                break;
            case "zhb11":
                //最大五位正数的正则表达式 最多三位小数
                pattern = Pattern.compile("^\\d{1,5}(?:\\.\\d*)?$");
                break;
            default:
                break;
        }
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断最大五位正数的正则表达式
     */
    @Test
    public void test1(){

        // (^0\.\d{0,2}[1-9]$)|(^[1-9]\d{0,4}\.\d*[1-9]$)|(^[1-9]\d{0,4})
        System.out.println("123456"+IsNumType("123456", "zhb")+"===="+IsNumType("123456", "zhb11"));
        System.out.println("12345.9999"+IsNumType("12345.9999", "zhb")+"===="+IsNumType("12345.9999", "zhb11"));
        System.out.println("12345.99999"+IsNumType("12345.99999", "zhb")+"===="+IsNumType("12345.99999", "zhb11"));
        System.out.println("0.0000"+IsNumType("0.0000", "zhb")+"===="+IsNumType("0.0000", "zhb11"));
        System.out.println("1.0000"+IsNumType("1.0000", "zhb")+"===="+IsNumType("1.0000", "zhb11"));
        System.out.println("-1.01"+IsNumType("-1.01", "zhb")+"===="+IsNumType("-1.01", "zhb11"));
        System.out.println("1.01"+IsNumType("1.01", "zhb")+"===="+IsNumType("1.01", "zhb11"));

        System.out.println("1.0199999999999"+IsNumType("1.0199999999999", "zhb")+"===="+IsNumType("1.0199999999999", "zhb11"));
        System.out.println("1.01"+IsNumType("1.01", "zhb")+"===="+IsNumType("1.01", "zhb11"));
        System.out.println("1.00"+IsNumType("1.00", "zhb")+"===="+IsNumType("1.00", "zhb11"));


    }
}
