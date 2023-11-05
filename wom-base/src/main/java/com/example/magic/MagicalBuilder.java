package com.example.magic;

import com.example.common.CommonException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MagicalBuilder<T> {
    private final List<BiGetter<T>> select;
    private final List<BiGetter<T>> where;

    public MagicalBuilder() {
        select = new ArrayList<>();
        where = new ArrayList<>();
    }

    public MagicalBuilder<T> select(BiGetter<T> getter) {
        select.add(getter);
        return this;
    }

    public MagicalBuilder<T> where(BiGetter<T> getter) {
        where.add(getter);
        return this;
    }

    public String build(T t) {
        Class<?> aClass = t.getClass();

        StringBuilder query = new StringBuilder("select ");
        if (select.isEmpty()) {
            query.append("*");
        } else {
            query.append(select.stream()
                            .map(getter -> {
                                String propertyName = getter.propertyName();
                                String key = AliasUtil.fetchAlias(getter.field());
                                return key.equals(propertyName) ? key : String.format("%s as %s", key, propertyName);
                            })
                            .collect(Collectors.joining(", ")));
        }

        query.append(" from ").append(AliasUtil.fetchAlias(aClass));

        if (!where.isEmpty()) {
            query.append(" where ").append(where.stream().map(getter -> {
                String key = AliasUtil.fetchAlias(getter.field());
                Object value = getter.apply(t);
                if (value == null) {
                    throw CommonException.of("value must not null");
                }
                return String.format("%s=%s", key, value);
            }).collect(Collectors.joining(" and ")));
        }

        query.append(";");
        return query.toString();
    }

}
