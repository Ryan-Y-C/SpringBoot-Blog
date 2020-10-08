package hello.controller;

import hello.entity.User;
import hello.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService=userService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.contains("anonymous")) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "ok");
            map.put("isLogin", false);
            return map;
        } else {
            return new Auth("ok", true, userService.getUserByUsername(username));
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            Map<String, String> map = new HashMap<>();
            map.put("status", "fail");
            map.put("msg", "用户不存在");
            return map;
        }
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            User loggedInUser = new User(1, "张三","123");
            return new Result("ok", "登录成功", userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            //当密码不正确的时候会抛出BadCredentialsException异常
            Map<String, String> map = new HashMap<>();
            map.put("status", "fail");
            map.put("msg", "密码不正确");
            return map;
        }
    }

    private class Result {
        private String status;
        private String msg;
        private Object data;

        public Result() {
        }

        public Result(String status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public Result(String status, String msg, Object data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public String getStatus() {
            return "ok";
        }

        public Object getData() {
            return data;
        }
    }

    private class Auth {
        private String status;
        private boolean isLogin;
        private Object data;

        public Auth(String status, boolean isLogin, Object data) {
            this.status = status;
            this.isLogin = isLogin;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public boolean getIsLogin() {
            return isLogin;
        }

        public Object getData() {
            return data;
        }
    }
}
