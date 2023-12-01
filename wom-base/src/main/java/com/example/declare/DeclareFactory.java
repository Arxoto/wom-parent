package com.example.declare;

import com.example.common.CommonException;
import org.springframework.util.StringUtils;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 使用反射和Lambda元工厂 将方法转为闭包 将命令式的注册方式一定程度上变成了声明式
 */
public class DeclareFactory {

    public static final Map<String, Function<String, Boolean>> CHECKER_MAP;

    static {
        // 用于查询方法 注意用publicLookup会有问题
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // lambda接口的方法名 这里实现的是Function 所以是apply
        String interfaceMethodName = "apply";
        // 创建CallSite的工厂类型 返回值类型是Function 没有入参
        MethodType factoryType = MethodType.methodType(Function.class);
        // 动态生成的方法类型
        MethodType methodType = MethodType.methodType(Boolean.class, String.class);
        // 接口方法类型 入参返回值类型均为Object
        MethodType interfaceMethodType = methodType.generic();
        // 因为不需要生成动态lambda方法 所以使用getTarget 如果需要则使用dynamicInvoker
        // 使用invokeExact 要求类型完全匹配 不允许自动装箱 如boolean于Boolean 否则使用invoke

        Map<String, Function<String, Boolean>> tmp = new HashMap<>();
        Method[] methods = DeclareFactory.class.getMethods();
        for (Method method : methods) {
            // check type
            if (!method.getReturnType().isAssignableFrom(Boolean.class)) {
                continue;
            }
            if (method.getParameterCount() != 1) {
                continue;
            }
            if (!method.getParameterTypes()[0].isAssignableFrom(String.class)) {
                continue;
            }

            // declare name
            DeclareAs declareAs = method.getAnnotation(DeclareAs.class);
            if (declareAs == null) {
                continue;
            }
            String declareName = declareAs.value();

            // to func
            try {
                String methodName = method.getName();
                MethodHandle methodHandle = lookup.findStatic(DeclareFactory.class, methodName, methodType);
                @SuppressWarnings("unchecked")
                Function<String, Boolean> func = (Function<String, Boolean>) LambdaMetafactory.metafactory(
                        lookup,
                        interfaceMethodName,
                        factoryType,
                        interfaceMethodType,
                        methodHandle,
                        methodType).getTarget().invokeExact();
                tmp.put(declareName, func);
            } catch (Throwable e) {
                throw CommonException.of(e);
            }
        }

        CHECKER_MAP = Collections.unmodifiableMap(tmp);
    }

    public static boolean runCheck(String expression) {
        if (expression == null) {
            throw CommonException.of("language is null");
        }
        int i = expression.indexOf("?");
        if (i == -1) {
            throw CommonException.of("language must be '{checkType}?{string}'");
        }
        String checkType = expression.substring(0, i).trim();
        String statement = expression.substring(i + 1).trim();
        if (!CHECKER_MAP.containsKey(checkType)) {
            throw CommonException.of("illegal checkType");
        }
        Boolean apply = CHECKER_MAP.get(checkType).apply(statement);
        return apply != null && apply;
    }

    @DeclareAs("hasText")
    public static Boolean hasText(String s) {
        return StringUtils.hasText(s);
    }

    @DeclareAs("endWithGreat")
    public static Boolean endWithGreat(String s) {
        return s != null && s.endsWith("great");
    }

}
