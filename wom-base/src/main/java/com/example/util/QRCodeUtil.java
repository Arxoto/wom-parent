package com.example.util;

import com.example.common.CommonException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.Map;

public class QRCodeUtil {
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;

    private static final Map<EncodeHintType, Object> hints;

    static {
        hints = Map.of(
                // 容错
                EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H,
                // 编码
                EncodeHintType.CHARACTER_SET, "utf-8",
                // 边距
                EncodeHintType.MARGIN, 1
        );
    }

    public static BufferedImage createImageQRCode(String content) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter()
                    .encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        } catch (WriterException e) {
            throw CommonException.of(e);
        }

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x00000000 : 0xFFFFFFFF);
            }
        }

        return image;
    }
}
