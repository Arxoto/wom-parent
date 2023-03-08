package com.example.util;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class Trier {
    /**
     * 尝试运行函数
     *
     * @param runner 将执行的函数
     * @return 是否成功执行
     */
    public static boolean run(Runnable runner) {
        return run(runner, e -> log.warn("try run failed", e));
    }

    public static boolean run(Runnable runner, Consumer<Throwable> whenError) {
        try {
            runner.run();
            return true;
        } catch (Throwable e) {
            whenError.accept(e);
            return false;
        }
    }
}
