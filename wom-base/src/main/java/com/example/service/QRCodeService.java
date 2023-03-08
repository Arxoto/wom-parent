package com.example.service;

import com.example.common.CommonException;
import com.example.util.CommonUtil;
import com.example.util.QRCodeUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class QRCodeService {
    @Value("${server.port}")
    private String port;
    private final String hostAddress = CommonUtil.getHostAddress();

    public void fillQRCode(HttpServletResponse response, String urlMapping) {
        response.setContentType("image/png");

        try (OutputStream os = response.getOutputStream()) {
            String content = String.format("http://%s:%s%s", hostAddress, port, urlMapping);

            BufferedImage img = QRCodeUtil.createImageQRCode(content);
            ImageIO.write(img, "png", os);
            os.flush();
        } catch (IOException e) {
            throw CommonException.of(e);
        }
    }
}
