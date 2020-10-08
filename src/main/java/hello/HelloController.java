package hello;

import hello.service.OrderService;
import hello.service.User;
import hello.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@RestController
public class HelloController {
    private OrderService orderService;
    private UserService userService;

    @Inject
    public HelloController(UserService userService) {
        this.userService = userService;
    }

//    @Inject
//    public HelloController(OrderService orderService) {
//        this.orderService = orderService;
//    }

    @RequestMapping("/")
    public User index() {
        return userService.getUserById(1);
    }

}