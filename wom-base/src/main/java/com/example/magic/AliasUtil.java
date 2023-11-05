package com.example.magic;

import com.example.common.CommonException;

import java.lang.reflect.AnnotatedElement;

public class AliasUtil {

    public static String fetchAlias(AnnotatedElement annotatedElement) {
        AliasAs annotation = annotatedElement.getAnnotation(AliasAs.class);
        if (annotation == null) {
            throw CommonException.of("the alias is null");
        }
        return annotation.value();
    }

}
