package org.example.rabbitmq.pictures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private PictureSender pictureSender;

//    @ResponseBody
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @MessageMapping("/greeting")
    public String handle(String greeting){
        return greeting;
    }

    @GetMapping("/send")
    @ResponseBody
    public String send(){
        pictureSender.run();
//        template.convertAndSend("/topic/greeting","test");
        return "success";
    }
}
