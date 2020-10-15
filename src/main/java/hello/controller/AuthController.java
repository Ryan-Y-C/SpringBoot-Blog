package hello.controller;

import hello.entity.*;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Object registerUser(@RequestBody Map<String, Object> registerUsernameAndPassword) {
        String username = registerUsernameAndPassword.get("username").toString();
        String password = registerUsernameAndPassword.get("password").toString();
        boolean flag = Pattern.matches("^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", username);
        if ((username.length() > 0 && username.length() <= 16) && (flag)) {
            if (password.length() > 5 && password.length() < 17) {
                try {
                    userService.save(username, password);
                    return new LogInResult("ok", "注册成功", userService.getUserByUsername(username));
                } catch (DuplicateKeyException e) {
                    return Status.failStatus("用户已存在");
                }
            } else {
                return Status.failStatus("密码, 长度6到16个任意字符");
            }

        } else {
            return Status.failStatus("用户名长度1到15个字符，只能是字母数字下划线中文");
        }
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication!=null?authentication.getName():null);
        if (user == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "ok");
            map.put("isLogin", false);
            return map;
        } else {
            return new Auth("ok", true, user);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Status.failStatus("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            return Status.successStatus("注销成功");
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
            return Status.failStatus("用户不存在");
        }
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return new LogInResult("ok", "登录成功", userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            //当密码不正确的时候会抛出BadCredentialsException异常
            return Status.failStatus("密码不正确");
        }
    }
}
