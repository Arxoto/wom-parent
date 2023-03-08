package com.example.entity;

import com.example.common.CommonException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class PipeLine {
    private final InputStream inputStream;
    private final OutputStream sourceOutput;

    /**
     * buffer 1KB
     */
    public void channel(OutputStream outputStream) {
        channel(outputStream, 10);
    }

    /**
     * @param level 10 -> 2^10 -> 1KB
     */
    public void channel(OutputStream outputStream, int level) {
        assert sourceOutput != null && inputStream != null && outputStream != null;

        try (inputStream; outputStream) {
            sourceOutput.write("target start to fetch...\n".getBytes(StandardCharsets.UTF_8));
            sourceOutput.flush();

            int len;
            byte[] bytes = new byte[1 << level];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
        } catch (IOException e) {
            throw CommonException.of(e);
        }
    }
}
