package com.example.zhb.study.demo;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhouhb
 * @date: 2022/01/13/10:17
 * @Description:
 */
public class Day24Test {

    @Test
    public void test1(){
        // 总结一下：SimpleDateFormat 可以解析 长于他定义的时间精度，只能保证他定义的时间精度，之后的则会被省略！
        System.out.println(validDateTimeSimple("2022-04-23 09:53:51错误"));
        // 这种方式其实也是有弊端的，当dateTime字符串中间包含多余的空格的时候这种方式是无法辨别出来的，所以使用的时候需要注意
        System.out.println(validDateTimeSimple("  2022-04-23   09:53:51  "  ));
        // 总结一下：SimpleDateFormat df.setLenient(false);//表示严格验证，验证时间是否正确
        System.out.println(validDateTimeSimple("2022-02-30 09:53:51"));
        System.out.println(validDateTimeSimple("2022-06-31 09:53:51"));

        System.out.println("===========================");
        // 对于上面的两个坑，我们可以使用java8的DateTimeFormatter 来避免
        System.out.println(validDateTimeFormatter("2022-04-23 09:53:51错误"));
        System.out.println(validDateTimeFormatter("  2022-04-23   09:53:51  "));

        // 总结一下：SimpleDateFormat df.setLenient(false);//表示严格验证，验证时间是否正确
        System.out.println(validDateTimeFormatter("2022-02-30 09:53:51"));
        System.out.println(validDateTimeFormatter("2022-06-31 09:53:51"));

        System.out.println("===========================");
        System.out.println(validDateTimeFormatter2("2022-04-23 09:53:51错误"));
        System.out.println(validDateTimeFormatter2("  2022-04-23   09:53:51  "));
        System.out.println(validDateTimeFormatter2("2022-02-30 09:53:51"));
        System.out.println(validDateTimeFormatter2("2022-06-31 09:53:51"));
    }

    public static boolean validDateTimeSimple(String dateTime) {
        if(dateTime == null ) {
            return false;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setLenient(false);//表示严格验证
        try {
            df.parse(dateTime);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    // DateTimeFormatter  是线程安全的，可以定义为static 使用，
    // 最后，DateTimeFormatter 的解析比较严格，需要解析的字符串和格式不匹配时，会直接报错。而不是错误的解析
    public static boolean validDateTimeFormatter(String dateTime) {
        if(dateTime == null ) {
            return false;
        }

/*
        DateTimeFormatter类主要有三种实例化方式，也叫格式化方法：
        预定义的标准格式，如：ISO_LOCAL_DATE_TIME
        本地化相关的格式，如：ofLocalizedDateTime(FormatStyle.LONG)
        自定义的格式，如：ofPattern(“yyyy-MM-dd hh:mm:ss”)
        看不懂没关系，以下将以代码举例说明：

        // 1、预定义的标准格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // 2、本地化相关的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        // 3、自定义的格式
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");*/


        try{
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            df.parse(dateTime);
        }catch(Exception e){
            // System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // 严格检验 时间
    public static boolean validDateTimeFormatter2(String dateTime) {
        if(dateTime == null ) {
            return false;
        }
        /*DateTimeFormatter “with” 方法
        下列方法返回DateTimeFormatter实例。
        withChronology(Chronology chrono): 返回具有给定时间顺序的格式化器副本。
        withDecimalStyle(DecimalStyle decimalStyle): 返回具有给定十进制样式的格式化器副本。
        withLocale(Locale locale): 返回具有给定区域设置的格式化器副本。
        withResolverFields(TemporalField… resolverFields): 返回具有给定时间字段的格式化器副本。
        withResolverFields(Set resolverFields): 返回这个格式化器的副本，并将给定的时间字段作为Set。
        withResolverStyle(ResolverStyle resolverStyle): 返回具有给定解析器样式的格式化器副本。
        withZone(ZoneId zone): 返回这个格式化器的副本，并给定区域ID。*/

        try{
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withDecimalStyle(DecimalStyle.STANDARD)
                    .withChronology(IsoChronology.INSTANCE)
                    .withLocale(Locale.CANADA)
                    .withResolverStyle(ResolverStyle.STRICT)  // 时间严不严格校验的关键
                    .withZone(ZoneId.systemDefault());
            LocalDate.parse(dateTime,df);
        }catch(Exception e){
            // System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
