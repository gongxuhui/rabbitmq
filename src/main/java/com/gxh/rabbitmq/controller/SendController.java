package com.gxh.rabbitmq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rabbitmq
 * @author: Mr.gong
 * @create: 2019-06-02 13:47
 * @description: ${description}
 **/
@RestController
public class SendController {
    @RequestMapping("/send")
    public String index(){
        System.out.println("==================");
      return "Hello World";
    }
}
