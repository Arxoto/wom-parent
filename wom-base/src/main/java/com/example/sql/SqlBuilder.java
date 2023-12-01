package com.example.sql;

import com.example.common.CommonException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlBuilder<T> {
    private final List<SerialFunc<T>> select;
    private final List<SerialFunc<T>> where;

    public SqlBuilder() {
        select = new ArrayList<>();
        where = new ArrayList<>();
    }

    public SqlBuilder<T> select(SerialFunc<T> getter) {
        select.add(getter);
        return this;
    }

    public SqlBuilder<T> where(SerialFunc<T> getter) {
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
