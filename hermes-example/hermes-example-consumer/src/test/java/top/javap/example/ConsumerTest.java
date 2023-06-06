package top.javap.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.javap.example.service.HelloService;

import java.io.IOException;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
@SpringBootTest(classes = Consumer.class)
@RunWith(SpringRunner.class)
public class ConsumerTest {

    @Autowired
    HelloFacade helloFacade;

    @Test
    public void consume() throws IOException {
        System.err.println(helloFacade.helloService);
        HelloService helloService = helloFacade.helloService;
        System.err.println(helloService.say("spring"));
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                for (int j = 0; j < 1; j++) {
//                    String result = helloService.say(Thread.currentThread().getName() + "-" + j);
//                    System.err.println(Thread.currentThread().getName() + ":" + result);
//                }
//            }).start();
//        }
//        System.in.read();
    }
}