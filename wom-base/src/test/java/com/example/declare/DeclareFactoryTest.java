package com.example.declare;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeclareFactoryTest {

    @Test
    public void testHasText() {
        Assertions.assertTrue(DeclareFactory.CHECKER_MAP.get("hasText").apply("asd"));
        Assertions.assertFalse(DeclareFactory.CHECKER_MAP.get("hasText").apply(""));
    }

    @Test
    public void testEndWithGreat() {
        Assertions.assertTrue(DeclareFactory.CHECKER_MAP.get("endWithGreat").apply("something is great"));
        Assertions.assertFalse(DeclareFactory.CHECKER_MAP.get("endWithGreat").apply("bad is sad"));
    }

    @Test
    public void testCheck() {
        Assertions.assertTrue(DeclareFactory.runCheck("hasText ? asd"));
        Assertions.assertFalse(DeclareFactory.runCheck("hasText ?"));
        Assertions.assertTrue(DeclareFactory.runCheck("endWithGreat ? something is great"));
        Assertions.assertFalse(DeclareFactory.runCheck("endWithGreat ? bad is sad"));
    }

}