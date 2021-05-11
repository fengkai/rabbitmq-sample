package org.example.rabbitmq.pictures;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.rabbitmq.pictures.util.ImgToBase64;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Send picture to rabbitmq.
 */
@Service
public class PictureSender{

    private static final String EXCHANGE_NAME = "picture-test";

    public static final String QUEUE_NAME="my-queue";

    public static void main(String[] args) throws Exception{
        new PictureSender().run();
    }

    public void run(String... args)  {
        //create exchange
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("root");
        factory.setVirtualHost("/");
        factory.setHost("192.168.181.6");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            String base64 ="";
            String msg = "";
            String filePath = "";
            //读取输入路径的文件
            File[] list = new File("/Users/kai/Desktop/test/").listFiles();
            int i = 0;
            for(File file : list)
            {
                if(file.isFile())
                {
                    if (file.getName().endsWith(".jpg")) {
                        // 就输出该文件的绝对路径
                        base64 = "data:image/jpeg;base64," + ImgToBase64.getImgFileToBase64(file.getAbsolutePath());

                        String toSend = base64;
                        channel.basicPublish(EXCHANGE_NAME,"all",null,toSend.getBytes(StandardCharsets.UTF_8));

                        i++;
                        System.out.println("当前传输第"+ i +"张");
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
