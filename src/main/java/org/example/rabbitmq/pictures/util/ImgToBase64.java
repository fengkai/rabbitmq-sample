package org.example.rabbitmq.pictures.util;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author: amos.chen
 * @description:
 * @time: 2021/1/29  15:22
 */
public class ImgToBase64 {
    /**
     * 将本地图片转换成Base64编码字符串
     * @author: amos.chen
     * @description:
     * @time: 2021/1/29  15:20
     */
    public static String getImgFileToBase64(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream inputStream = null;
        byte[] buffer = null;
        //读取图片字节数组
        try {
            inputStream = new FileInputStream(imgFile);
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        return Base64.getEncoder().encodeToString(buffer);
    }

}
