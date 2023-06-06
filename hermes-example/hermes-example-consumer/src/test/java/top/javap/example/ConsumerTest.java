package top.javap.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.javap.example.domain.User;
import top.javap.example.service.UserService;

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
        final UserService userService = helloFacade.getUserService();
        User user = userService.get("Spring");
        System.err.println(user);
        userService.getByAsync("Async Spring").whenComplete((r, e) -> {
            if (e != null) {
                e.printStackTrace();
            } else {
                System.err.println(r);
            }
        });
        user.setName("oneway");
        userService.oneway(user);
    }
}