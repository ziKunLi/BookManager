package com.example.comment.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @author NewBies
 * @date 2018/4/4
 */
public class QRCodeUtil {

    /**
     * 获取二维码bitmap
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getQRCode(String content, int width, int height) {
        // 1.获取QRCodeWriter实例
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            // encode方法共有五个参数，第一个参数表示生成二维码的文本内容，第二个参数表示编码格式，
            // 第三个参数表示生成的二维码的宽度，第四个参数表示生成的二维码的高度，
            // 第五个参数可选，可以用来设置文本的编码，encode方法的返回值是一个BitMatrix
            // 可以把BitMatrix理解成一个二维数组，这个二维数组的每一个元素都表示一个像素点是否有数据。
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    //有数据就是黑色，没数据就是白色
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            // 第一个参数表示Bitmap中所有像素点的颜色，第二个参数表示像素点的偏移量，
            // 第三个参数表示Bitmap每行有多少个像素点，第四个参数表示生成的Bitmap的宽度，
            // 第五个参数表示生成的Bitmap的高度，第六个参数表示生成的Bitmap的色彩模式，
            // 因为二维码只有黑白两种颜色，所以我们可以不用考虑透明度，直接使用RGB_565即可
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
