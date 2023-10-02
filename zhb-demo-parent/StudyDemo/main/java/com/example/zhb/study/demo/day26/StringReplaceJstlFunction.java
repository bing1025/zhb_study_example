package com.example.zhb.study.demo.day26;

import com.google.common.collect.Lists;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author zecong.nian
 * @since 2020/7/15
 */
public class StringReplaceJstlFunction extends AbstractFunction {
    public final static Pattern pattern = Pattern.compile("\\$\\{([^}]+?)\\}");


    @Override
    public String getName() {
        return "zhb.string.jstl";
    }

    // 自定义解析器的具体实现
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject str, AviatorObject defaultResult) {
        String text = FunctionUtils.getStringValue(str, env);

        // 正则找到文案中包含的参数
        List<String> params = Lists.newArrayList();
        Matcher m = pattern.matcher(text);
        while(m.find()){
            params.add(m.group(1));
        }

        // 替换参数
        for (String paramKey : params) {
            String param = "${" + paramKey + "}";
            if (text.contains(param) && env.containsKey(paramKey)) {
                // 这里 是替换 param 为对应 map 的key为paramKey的value 值
                text = text.replace(param, String.valueOf(env.get(paramKey)));

                // 扩展的玩玩-- 替换一次，后面添加一个 替换成功标志 success
                text += "-success-";
            }
        }

        if (pattern.matcher(text).find()){
            return defaultResult;
        } else {
            return new AviatorString(text);
        }

    }

    public static void main(String[] args) {
        String text = "文案${zhb-abc}tihuan";
        String defaultText = "文案def111";

        AviatorEvaluator.addFunction(new StringReplaceJstlFunction());
        String expression = "zhb.string.jstl(_text_,_default_)";
        Expression compiledExp = AviatorEvaluator.compile(expression);

        Map<String, Object> params = new HashMap<>();
        params.put("_text_", text);
        params.put("_default_", defaultText);
        // 默认值
        System.out.println(compiledExp.execute(params));
        // 动态赋值
        params.put("zhb-abc", "zhb123");
        System.out.println(compiledExp.execute(params));

        String text11 = "文案${zhb-abc1}tihuan ${zhb-abc2}";
        String defaultText11 = "文案def22222";

        AviatorEvaluator.addFunction(new StringReplaceJstlFunction());
        String expression11 = "zhb.string.jstl(_text1_,_text2_,_default_)";
        Expression compiledExp11 = AviatorEvaluator.compile(expression11);

        Map<String, Object> params11 = new HashMap<>();
        params11.put("_text_", text11);
        params11.put("_default_", defaultText11);
        // 默认值
        System.out.println(compiledExp.execute(params11));
        // 动态赋值
        params11.put("zhb-abc1", "zhb123");
        params11.put("zhb-abc2", "zhb123456");
        System.out.println(compiledExp.execute(params11));


    }

}
