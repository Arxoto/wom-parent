package com.example.magic;

import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MagicalBuilderTest {

    @Data
    @Builder
    @AliasAs("tbl_people")
    public static class People {
        @AliasAs("the_id")
        private Integer id;
        @AliasAs("name")
        private String name;
    }

    @Test
    public void testGetter() {
        Assertions.assertEquals("name", ((BiGetter<People>) People::getName).propertyName());
    }

    @Test
    public void test0() {
        String build = new MagicalBuilder<People>()
                .select(People::getName)
                .where(People::getId)
                .build(People.builder().id(0).build());

        Assertions.assertEquals("select name from tbl_people where the_id=0;", build);
    }

    @Test
    public void test1() {
        String build = new MagicalBuilder<People>()
                .where(People::getId)
                .where(People::getName)
                .build(People.builder().id(2).name("People").build());

        Assertions.assertEquals("select * from tbl_people where the_id=2 and name=People;", build);
    }

    @Test
    public void test2() {
        String build = new MagicalBuilder<People>()
                .select(People::getId)
                .select(People::getName)
                .build(People.builder().build());

        Assertions.assertEquals("select the_id as id, name from tbl_people;", build);
    }

}