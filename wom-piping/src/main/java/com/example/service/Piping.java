package com.example.service;

import com.example.common.CommonException;
import com.example.entity.PipeLine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//    @Async && CompletableFuture
@Service
public class Piping {
    private final Map<String, PipeLine> pipingMap = new ConcurrentHashMap<>();

    public void channel(String key, InputStream inputStream, OutputStream sourceOutput) {
        PipeLine pipeLine = new PipeLine(inputStream, sourceOutput);

        synchronized (pipeLine) {
            if (pipingMap.putIfAbsent(key, pipeLine) != null) {
                // 之前有一个 input 在等待传输或者正在传输 需要报错
                throw CommonException.of("a input already exist");
            }

            try(sourceOutput) {
                sourceOutput.write("wait for target...\n".getBytes(StandardCharsets.UTF_8));
                sourceOutput.flush();
                // 是第一次加入 需要等待 output 完成传输
                pipeLine.wait();
            } catch (InterruptedException | IOException e) {
                throw CommonException.of(e);
            } finally {
                pipingMap.remove(key);
            }
        }
    }

    public void channel(String key, OutputStream outputStream) {
        PipeLine pipeLine = pipingMap.get(key);
        if (pipeLine == null) {
            // input 未加入 需等待其先加入 或者直接报错
            throw CommonException.of("need input first");
        }

        synchronized (pipeLine) {
            try {
                pipeLine.channel(outputStream);
            } finally {
                // 异常也要释放key
                pipeLine.notifyAll();
            }
        }
    }

//    public static void main(String[] args) {
//        Map<String, String> m = new ConcurrentHashMap<>();
//
////        m.put("a", "old");
//        m.put("a", "new");
//
//        String a = m.putIfAbsent("a", "new");
//        if (a == null) {
//            System.out.println("old not exist, put new suc: " + m.get("a"));
//        } else if ("old".equals(a)) {
//            System.out.println("old exist, put new failed: " + m.get("a"));
//        } else {
//            System.out.println("the old is new: " + m.get("a"));
//        }
//    }
}
