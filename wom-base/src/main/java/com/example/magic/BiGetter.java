package com.example.magic;

import com.example.common.CommonException;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * field 字段，类对象内部明确定义的变量，生命周期与对象一致
 * <p>
 * attribute 属性，字段被直接暴露给外部
 * <p>
 * property 属性，以getter/setter形式暴露给外部（允许不存在实际字段）
 * <p>
 * method 方法，被类对象所拥有
 * <p>
 * function 函数，独立于类对象之外，纯函数不能对外界造成影响
 * <p>
 * 满足需求：反射获取属性和值
 */
@FunctionalInterface
public interface BiGetter<T> extends Serializable {
    Object apply(T t);

    default SerializedLambda serializedLambda() {
        try {
            Method write = this.getClass().getDeclaredMethod("writeReplace");
            write.setAccessible(true);
            return (SerializedLambda) write.invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw CommonException.of("serialized lambda failed");
        }
    }

    default String methodName() {
        return serializedLambda().getImplMethodName();
    }

    default String propertyName() {
        String methodName = methodName();
        if (methodName != null && methodName.length() > 3 && methodName.startsWith("get")) {
            String fieldName = methodName.substring(3);
            // the first char must be upper
            if (!Character.isUpperCase(fieldName.charAt(0))) {
                throw CommonException.of("not camel");
            }
            // the first char to lower
            char[] charArray = fieldName.toCharArray();
            charArray[0] = Character.toLowerCase(charArray[0]);
            return String.valueOf(charArray);
        }
        throw CommonException.of("not getter");
    }

    default Field field() {
        try {
            String implClass = serializedLambda().getImplClass().replace('/', '.');
            Class<?> clz = Class.forName(implClass);
            return clz.getDeclaredField(propertyName());
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw CommonException.of(e);
        }
    }

}
